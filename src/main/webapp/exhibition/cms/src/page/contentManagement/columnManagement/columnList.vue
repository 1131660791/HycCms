<template>
    <div class="newColumn">
        <crumbs></crumbs>
        <div class="platformInfo clearfix">
            <span class="fl">机构选择</span>
            <select name="" id="platformInfo" v-model="hospitalId" class="fl" @change="getColumnList()">
                <!-- <option value="">请选择</option> -->
                <option v-for="item,index in hospitalList" :value="item.id" :key="index">{{item.departname}}</option>
            </select>
            <div class="addClumn fr" @click="addColumn()" v-show="columnListArr.length>0?false:true">新增栏目</div>
            <div class="addClumn fr" @click="referDefault()" v-show="columnIsShow">引用默认栏目</div>
        </div>
        <div class="newColumnBox">
            <div class="columnList">
                <div class="tableHead">
                    <table>
                        <tr>
                            <td width="3%">ID</td>
                            <td width="15%">栏目名称</td>
                            <td width="7%">内容类型</td>
                            <td width="10%">日期</td>
                            <td width="10%">编辑人</td>
                            <td width="5%">状态</td>
                            <td width="12%">所属机构</td>
                            <td width="6%">排序</td>
                            <td width="26%">操作</td>
                        </tr>
                    </table>
                </div>
                <div class="tableWrap">
                    <table style="border-top:none;">
                        <tr>
                            <td style="padding:0;border:none;">
                                <tableTree v-for="(item,index) in columnListArr"
                                    ref="treeTable"
                                    :key="index"
                                    :item="item"
                                    :num="index"
                                    :lsit.sync="columnListArr"
                                    @editable="editable"
                                    @commitChanges="commitChanges"
                                    @clickState="clickState"
                                    @addColumn="addColumn"
                                    @editorColumn="editorColumn"
                                    @delbefor="delbefor"
                                    @isForbidden="isForbidden"
                                ></tableTree>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <el-dialog
            title="提示"
            :visible.sync="centerDialogVisible"
            width="30%"
            center>
            <span>{{msg}}</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogShow(false)">取 消</el-button>
                <el-button type="primary" @click="dialogShow(true)">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import tableTree from "./table-tree";
    import { api_getColumnList, api_changesColumnSort, api_delColumn, api_isForbiddenColumn, api_sureRefer } from "../../../axios/apis.js";
    export default {
        name:'newColumn',
        components:{ crumbs, tableTree },
        props:[  ],
        data (){
            return {
                columnListArr:[],
                hospitalList: [],
                hospitalId:'',
                departname:'',
                fkPlatformId:'',
                centerDialogVisible:false,
                columnIsShow:'',
                msg:'',
                currentEvent:'',            // 当前事件
                currentColumnId:''          // 当前操作的栏目id
            }
        },
        beforeRouteUpdate(to,from,next){
            this.fkPlatformId = to.query.fkPlatformId;
            this.init();
            next();
        },
        created(){
            this.fkPlatformId = this.$route.query.fkPlatformId;
        },
        mounted(){
            this.hospitalList = JSON.parse(sessionStorage.hospitalList);
            this.init();
        },
        methods: {
            // 初始化数据
            init:function(){
                if(this.departname=='本站点'){
                    this.columnIsShow = false;
                };
                if(this.fkPlatformId=='232880e61e1ef92d011e1efefc380005'){
                    this.hospitalList.forEach(item=>{
                        if(item.departname == '本站点'){
                            this.hospitalList = [];
                            this.hospitalList.push(item);
                        }
                    })
                }else{
                    this.hospitalList = [];
                    this.hospitalList = JSON.parse(sessionStorage.hospitalList);
                }
                this.hospitalId = this.hospitalList.length>0?this.hospitalList[0].id:'';
                this.departname = this.hospitalList.length>0?this.hospitalList[0].departname:'';
                this.columnIsShow = true;
                this.columnListArr = [];
                this.gethospitalIdName();
                this.getColumnList();
            },
            // 获取当前医院名称
            gethospitalIdName:function(){
                this.hospitalList.forEach(item => {
                    if(item.id == this.hospitalId){
                        this.departname = item.departname
                    }
                });
            },
            // 获取当前平台下当前医院 的栏目列表
            getColumnList:function(){
                this.columnListArr = [];
                api_getColumnList({
                    platformId: this.fkPlatformId,
                    hospitalId: this.hospitalId
                })
                .then(res=>{
                    if(res.flag=='success'){
                        let _columnListArr = res.obj;
                        this.tableDataDispose(_columnListArr);
                        this.judeColumn(_columnListArr);
                        if(_columnListArr.length>0){
                            _columnListArr.forEach((v,i)=>{
                                this.columnListArr.push(_columnListArr[i])
                            })
                        }
                    }
                })
                .catch(error=>console.log(error))
            },
            // 列表数据处理
            tableDataDispose(data){
                if(data && data.length>0){
                    data.forEach(item=>{
                        item.showState = true;
                        item.show = '-';
                        item.editing = false;
                        if(item.cmsColumnVos&&item.cmsColumnVos.length){
                            this.tableDataDispose(item.cmsColumnVos)
                        }
                    })
                }
            },
            // 判断是否存在引用栏目
            judeColumn(data){
                if(data.length>0){
                    let state = data.some(item=>{
                        if(item.columnProperty != 1){
                            if(item.cmsColumnVos&&item.cmsColumnVos.length){ this.judeColumn(item.cmsColumnVos) }
                        }else{
                            this.columnIsShow = false;
                            return;
                        }
                    });
                }
            },
            // 数据状态修改
            clickState(item){
                item.showState = !item.showState;
                item.show = item.show=='+'?'-':'+';
            },
            //新增栏目
            addColumn:function(id,name){
                // 判断是否存在父栏目id
                id = id?id:'';
                // 判断是否存在父栏目名称
                name = name?name:'';
                // 判断是否已经选择机构
                if(!this.hospitalId){
                    this.$message({
                        message: '请先选择机构！',
                        type: 'error',
                        duration: 1500
                    });
                    return;
                }
                this.$router.push({path:'/home/contentManagementIndex/column/addColumn',query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, hospitalId: this.hospitalId, departname: this.departname, fkPlatformId: this.fkPlatformId, pId: id, parentColumnName:name }});
            },
            // 权重修改
            editable:function(item){
                item.editing = true;
            },
            // 权重修改提交
            commitChanges:function(item,id,sort){
                if(item.sort==null && item.sort == undefined ){
                    this.$message({
                        type: 'error',
                        message: '排序不能为空！'
                    });
                    return;
                }
                item.editing = false;
                api_changesColumnSort({
                    columnJson:JSON.stringify([{
                        id : item.id,
                        sort : item.sort
                    }])
                })
                .then(res=>{
                    this.$message({
                        type: res.flag=='success'?'success':'error',
                        message: res.msg
                    });
                    if(res.flag=='success'){
                        this.getColumnList()
                    }
                })
                .catch(error=>console.log(error))
            },
            // 编辑栏目
            editorColumn:function(id){
                this.$router.push({path:'/home/contentManagementIndex/column/editorColumn',query:{ currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, columId: id, fkPlatformId: this.fkPlatformId}});
            },
            // 删除前提示确认
            delbefor(id){
                this.centerDialogVisible = true;
                this.msg = '删除栏目后将无法恢复,是否继续？'
                this.currentEvent = 'del';
                this.currentColumnId = id;
            },
            // 删除栏目
            del:function (id) {
                api_delColumn({columId:id})
                .then(res=>{
                    if(res.flag == 'success'){
                        this.getColumnList();
                    }
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                })
                .catch(err=>console.log(err))
            },
            // 栏目禁用
            isForbidden:function(id,state,columnProperty){
                api_isForbiddenColumn({
                    id:id,
                    state:state==0?'1':'0',
                    columnProperty:columnProperty
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if(res.flag=='success'){
                        this.getColumnList()
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            },
            //
            referDefault(){
                this.centerDialogVisible = true;
                this.currentEvent = 'refer';
                this.msg = '使用默认栏目将清空原有默认栏目下数据，是否继续？'
            },
            // 使用默认栏目
            dialogShow(val){
                if(val){
                    if(this.currentEvent == 'refer'){
                        this.sureRefer();
                    }else if(this.currentEvent == 'del'){
                        this.del(this.currentColumnId);
                    }
                }
                this.centerDialogVisible = false
                this.currentEvent = '';
                this.msg = '';
                this.currentColumnId = '';
            },
            sureRefer() {
                api_sureRefer({
                    platformId: this.fkPlatformId,
                    hospitalId: this.hospitalId,
                    departname: this.departname
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if (res.flag=='success') {
                        this.getColumnList();
                    }
                })
                .catch(error=>console.log(error))
            }
        }
    }
</script>

<style scoped>
    .newColumn {
        width: 100%;
        height: 100%;
    }

    .platformInfo{
        text-align: left;
        height: 30px;
        line-height: 30px;
        margin-bottom: 10px;
    }
    .platformInfo span{
        width: 120px;
        height: 30px;
        float: left;
        background-color: #6b61f2;
        color: #fff;
        text-align: center;
        font-size: 14px;
    }
    .platformInfo select{
        width: 160px;
        height: 30px;
        padding-left: 10px;
        border-left:none;
        display: block;
    }

    .newColumnBox {
        width: 100%;
        max-height: 470px;
        overflow: scroll;
    }
    .addClumn {
        display: block;
        background-color: #6b61f2;
        text-align: center;
        border: none;
        width: 100px;
        height: 30px;
        border-radius: 4px;
        line-height: 30px;
        font-size: 1px;
        color: #ffffff;
        margin-right: 20px;
        cursor: pointer;
    }
    .columnList{
        min-width: 1300px;
        margin-right: 10px;
    }
    .columnList .tableHead td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 15px;
        margin: 0;
    }
    .columnList table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        font-size: 14px;
        color: #333333;
        width: 100%;
        min-width: 900px;
        text-align: left;
    }
    .columnList tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }

    .columnList .tableHead tr{
        background-color: #e5e3e6;
    }
    .columnList tr td:first-child,.columnList tr td:nth-child(2){
        text-align: center;
    }
    .buttonBox span{
        margin: 0 5px;
        cursor: pointer;
    }
    .buttonBox span:first-child{
        color: #246ce2;
    }
    .buttonBox span:nth-child(2){
        color: #eaa216;
    }
    .buttonBox span:nth-child(3){
        color: #e85f5f;
    }
    .buttonBox span:nth-child(4){
        color: #cb1820;
    }




</style>