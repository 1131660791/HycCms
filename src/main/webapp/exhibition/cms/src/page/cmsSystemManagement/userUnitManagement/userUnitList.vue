<template>
    <div class="userUnit">
        <crumbs></crumbs>
        <div class="contentBox">
            <table>
                <tr>
                    <td width="3%">ID</td>
                    <td width="12%">角色编码</td>
                    <td width="15%">角色名称</td>
                    <td width="40%">所属平台</td>
                    <td width="10%">操作</td>
                </tr>
                <tr v-for="(item,index) in roleList" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.rolecode}}</td>
                    <td>{{item.rolename}}</td>
                    <td>{{item.platformName}}</td>
                    <td class="buttonBox">
                        <el-button size="mini" type="primary" plain @click="getPlatformList(item.id,item.rolecode,item.rolename)">编辑</el-button>
                    </td>
                </tr>
            </table>
            <div style="text-align:center;margin-top:20px;">
                <el-pagination
                    v-show="totalPage>1"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage"
                    :page-size="showCount"
                    :page-count="totalPage"
                    layout="total, prev, pager, next, jumper"
                    :total="totalResult">
                </el-pagination>
            </div>
        </div>
        <div class="tipBox" v-if="editorStatus">
            <div class="tipContent">
                <h2>编&nbsp;&nbsp;&nbsp;&nbsp;辑</h2>
                <p><span>角色编码:</span><input type="text" placeholder="请填写角色编码" v-model="CurrentEditorRloeCode" disabled></p>
                <p><span>角色名称:</span><input type="text" placeholder="请填写角色名称" v-model="CurrentEditorRloeName" disabled></p>
                <div class="platformList">
                    <el-checkbox-group v-model="checkList">
                        <el-checkbox :label="item.id" v-for="item in allPlatformList" :key="item.id"><p>{{item.platformName}}</p></el-checkbox>
                    </el-checkbox-group>
                </div>
                <div class="clearfix">
                    <!-- <el-pagination
                        class="fl"
                        v-show="platformTotalPage>1"
                        @current-change="CurrentChange"
                        :current-page.sync="platformCurrentPage"
                        :page-size="platformShowCount"
                        :page-count="platformTotalPage"
                        layout="total, prev, pager, next, jumper"
                        :total="platformTotalResult">
                    </el-pagination> -->
                    <div class="sureBtn btn fr" @click="saveInfo()">确定</div>
                    <div class="cancel btn fr" @click="cancel()">取消</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getRoleList, api_getPlatformList, api_saveUserInfo } from "../../../axios/apis.js";
    export default {
        name:'roleList',
        components:{ crumbs },
        data(){
            return{
                roleList:'',
                currentPage:1,          // 默认页 1
                showCount:10,           // 每页显示条数
                totalPage:null,         // 总页数
                totalResult:null,       // 总条数
                // platformCurrentPage:1,  // 默认页 1
                // platformShowCount:6,    // 每页显示条数
                // platformTotalPage:null,
                // platformTotalResult:null,
                CurrentEditorId:'',     // 当前编辑的角色id
                CurrentEditorRloeCode:'',
                CurrentEditorRloeName:'',
                editorStatus: false,     //

                checkList:[],
                allPlatformList:[],
            }
        },
        created(){

        },
        mounted(){
            this.getRoleList();
            // this.getPlatformList();
        },
        methods:{
            // 查询角色列表
            getRoleList(){
                api_getRoleList({
                    currentPage:this.currentPage,
                    showCount:this.showCount
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.roleList = [];
                        this.roleList = res.obj.list;
                        this.totalPage =  res.obj.totalPage;
                        this.totalResult =  res.obj.totalResult;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getRoleList()
            },
            // 翻页
            CurrentChange(num) {
                this.platformCurrentPage = num;
                // this.getPlatformList()
            },
            // 编辑角色
            editorRoleInfo(id,code,name){
                this.editorStatus = true;
                this.CurrentEditorId = id;
                this.CurrentEditorRloeCode = code;
                this.CurrentEditorRloeName = name;
            },
            sure(){
                this.editorStatus = false;
                this.checkList = [];
            },
            cancel(){
                this.editorStatus = false;
                this.checkList = [];
                this.CurrentEditorId = '';
            },
            // 查询所有平台 已关联平台
            getPlatformList(id,code,name){
                this.editorStatus = true;
                this.CurrentEditorId = id;
                this.CurrentEditorRloeCode = code;
                this.CurrentEditorRloeName = name;
                api_getPlatformList({
                    roleId:id
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.allPlatformList = res.obj;
                        this.allPlatformList.forEach(v=>{
                            if(v.flag == 1 && !this.checkList.includes(v.id)){
                                this.checkList.push(v.id)
                            }
                        })
                    }
                })
                .catch(error=>console.log(error))
            },
            // 保存修改内容
            saveInfo(){
                let data = {
                    roleId:this.CurrentEditorId,
                    platformIds:[],
                }
                if(this.checkList && this.checkList.length>0){
                    this.checkList.forEach(v=>{
                        if(!data.platformIds.includes(v)){
                            data.platformIds.push({platformId:v})
                        }
                    })
                    this.postAjax(data);
                }else{
                    this.$message({
                        message: '至少选择一个平台',
                        type: 'warning',
                        duration: 1500
                    });
                    return;
                }
            },
            // 提交请求
            postAjax(data){
                api_saveUserInfo({
                    rolePlatformJson:JSON.stringify(data)
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'warning',
                        duration: 1500
                    });
                    if (res.flag=='success') {
                        this.editorStatus = false;
                        this.checkList = [];
                        this.CurrentEditorId = '';
                        this.getRoleList();
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
        }
    }
</script>

<style scoped>
    .userUnit{
        text-align: left;
    }
    .contentBox table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        font-size: 14px;
        color: #333333;
        width: 100%;
        min-width: 800px;
        text-align: left;
        overflow: scroll;
        display: table;
        max-height: 500px;
        margin-top: 20px;
    }
    .contentBox tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }
    .contentBox tr:hover{
        background-color: #eeedfc;
    }
    .contentBox tr:first-child{
        background-color: #eeedfc;
    }
    .contentBox tr td:first-child,.contentBox tr td:nth-child(2){
        text-align: center;
    }
    .contentBox td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 18px;
        margin: 0;
    }

    .tipBox{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0, 0, 0, 0.5);
        z-index: 10;
    }
    .tipContent{
        padding: 10px 20px;
        width: 800px;
        height: 500px;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        background: #fff;
        border-radius: 4px;
    }
    .tipContent h2{
        color: #333;
        text-align: center;
        margin-bottom: 20px;
        font-size: 16px;
        border-bottom: 1px solid #ccc;
        padding-bottom: 12px;
    }
    .tipContent p{
        margin-bottom: 12px;
    }
    .tipContent p span{
        display: inline-block;
        width: 80px;
        font-size: 14px;
        line-height: 36px;
    }
    .tipContent p input{
        height: 36px;
        line-height: 36px;
        font-size: 14px;
    }
    .tipContent p input:disabled{
        background: none;
    }
    .el-checkbox{
        width: 100%;
        margin-left: 0;
        line-height: 30px;
        background: #f1f1f1;
        margin-bottom: 4px;
        line-height: 36px;
        padding: 0 4px;
    }
    .el-checkbox+.el-checkbox {
        margin-left: 0px;
    }
    .el-checkbox:hover{
        background: #e6e6e6;
    }
    .platformList{
        height: 270px;
    }
    .tipContent .btn{
        display: inline-block;
        width: 120px;
        height: 36px;
        line-height: 36px;
        text-align: center;
        font-display: 14px;
        color: #fff;
        background: rgba(129, 103, 245, 1);
        border-radius: 4px;
        margin: 0 10px;
        cursor: pointer;
    }
    .tipContent .cancel{
        background: #999;
    }

</style>