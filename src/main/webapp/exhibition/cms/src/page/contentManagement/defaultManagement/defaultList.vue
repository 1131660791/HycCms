<template>
    <div class="default">
        <crumbs></crumbs>
        <div class="platformInfo clearfix">
            <div class="addClumn fr" @click="addDefault()">新增栏目</div>
        </div>
        <div class="defaultBox">
            <div class="defaultList">
                <div class="tableHead">
                    <table>
                        <tr>
                            <td width="3%">ID</td>
                            <td width="20%">栏目名称</td>
                            <td width="10%">内容类型</td>
                            <td width="10%">日期</td>
                            <td width="15%">编辑人</td>
                            <td width="6%">状态</td>
                            <td width="6%">排序</td>
                            <td width="30%">操作</td>
                        </tr>
                    </table>
                </div>
                <div class="tableWrap">
                    <table style="border-top:none;">
                        <tr>
                            <td style="padding:0;border:none;">
                                <tableTree v-for="(item,index) in defaultListArr"
                                    :key="index"
                                    :item="item"
                                    :num="index"
                                    @editable="editable"
                                    @commitChanges="commitChanges"
                                    @clickState="clickState"
                                    @addDefault="addDefault"
                                    @del="del"
                                    @isForbidden="isForbidden"
                                ></tableTree>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import tableTree from "./table";
    import { api_getPlatformDefaultColumnList, api_getPlatformDefaultCommitChanges, api_delDefaultColumn, api_isForbiddenDefaultColumn } from "../../../axios/apis.js";
    export default {
        name:'default',
        components:{ crumbs, tableTree },
        props:[  ],
        data (){
            return {
                defaultListArr:[],
                hospitalId:'',
                departname:'',
                fkPlatformId:'',
            }
        },
        beforeRouteUpdate(to,from,next){
            this.fkPlatformId = to.query.fkPlatformId;
            this.init()
            next();
        },
        created(){
            this.fkPlatformId = this.$route.query.fkPlatformId;
        },
        mounted(){
            this.init();
        },
        methods: {
            // 初始化数据
            init:function(){
                this.getColumnList();
            },
            // 获取当前平台下的默认栏目列表
            getColumnList:function(){
                api_getPlatformDefaultColumnList({
                    platformId: this.fkPlatformId,
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.defaultListArr = [];
                        let defaultListArr = res.obj;
                        this.tableDataDispose(defaultListArr);
                        this.defaultListArr = defaultListArr;
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
                        if(item.defaultColumnVos&&item.defaultColumnVos.length){
                            this.tableDataDispose(item.defaultColumnVos)
                        }
                    })
                }
            },
            //新增栏目
            addDefault:function(id,name,type){
                // 判断是否存在栏目id
                id = id?id:'';
                // 判断是否存在父栏目名称
                name = name?name:'';
                if(type=='add'){
                    this.$router.push({path:'/home/contentManagementIndex/defaultIndex/addDefault',query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, fkPlatformId: this.fkPlatformId, pId: id, parentColumnName:name}});
                }else{
                    this.$router.push({path:'/home/contentManagementIndex/defaultIndex/addDefault',query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, fkPlatformId: this.fkPlatformId, columId: id }});
                }
            },
            // 数据状态修改
            clickState(item){
                item.showState = !item.showState;
                item.show = item.show=='+'?'-':'+';
            },
            // 权重修改
            editable:function(item){
                item.editing = true;
            },
            // 权重修改提交
            commitChanges:function(item,id,sort){
                if(!item.sort){
                    this.$message({
                        type: 'error',
                        message: '排序不能为空！'
                    });
                    return;
                }
                item.editing = false;
                api_getPlatformDefaultCommitChanges({
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
                .catch(error=>{
                    console.log(error);
                })
            },
            // 删除栏目
            del:function (id) {
                api_delDefaultColumn({
                    defaultColumnId:id
                })
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
            isForbidden:function(id,state){
                api_isForbiddenDefaultColumn({
                    id:id,
                    state:state==0?'1':'0'
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if (res.flag=='success') {
                        this.getColumnList()
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            },
        }
    }
</script>

<style scoped>
    .default {
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

    .defaultBox {
        width: 100%;
        height: calc( 100% - 60px);
    }
    .addClumn {
        display: block;
        background-color: #6b61f2;
        text-align: center;
        border: none;
        width: 100px;
        height: 30px;
        /* margin-bottom: 30px; */
        border-radius: 4px;
        line-height: 30px;
        font-size: 1px;
        color: #ffffff;
        margin-right: 20px;
        cursor: pointer;
    }
    .defaultList{
        overflow: scroll;
        height: calc( 100% - 90px);
    }
    .defaultList .tableHead td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 15px;
        margin: 0;
    }
    .defaultList table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        font-size: 14px;
        color: #333333;
        width: 100%;
        min-width: 900px;
        text-align: left;
    }
    .defaultList tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }

    .defaultList .tableHead tr{
        background-color: #e5e3e6;
    }
    .defaultList tr td:first-child,.defaultList tr td:nth-child(2){
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