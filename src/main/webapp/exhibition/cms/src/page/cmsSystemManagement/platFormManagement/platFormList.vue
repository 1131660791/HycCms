<template>
    <div class="contentBox">
        <crumbs></crumbs>
        <div class="content">
            <div class="titleBox clearfix">
                <input type="text" placeholder="请输入平台名称" v-model="platFormName" class="fl">
                <el-date-picker
                    class="fl"
                    v-model="startDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="2018-1-1"
                    default-time="00:00:00">
                </el-date-picker><span class="fl" style="line-height:40px;margin: 0 10px;color:#999;">--</span>
                    <el-date-picker
                    class="fl"
                    v-model="endDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="2018-1-1"
                    default-time="23:59:59">
                </el-date-picker>
                <div class="search_btn fl" @click="getPlatFormList()">查询</div>
                <div class="addPlatFormBtn" @click="editorPlatForm()">添加平台</div>
            </div>
            <table>
                <tr>
                    <td width="3%">ID</td>
                    <td width="12%">平台名称</td>
                    <td width="22%">平台地址</td>
                    <td width="10%">创建人</td>
                    <td width="16%">更新时间</td>
                    <td width="11%">状态</td>
                    <td width="16%">操作</td>
                </tr>
                <tr v-for="(item,index) in platFormList" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.platformName}}</td>
                    <td>{{item.platformUrl}}</td>
                    <td>{{item.createUserName}}</td>
                    <td>{{item.updateTime}}</td>
                    <td>
                        <em style="color:#0a8c15" v-if="item.state==0?true:false">启用</em>
                        <em style="color:#cb1820" v-else>禁用</em>
                    </td>
                    <td class="buttonBox">
                        <el-button size="mini" type="primary" plain @click="editorPlatForm(item.id)">编辑</el-button>
                        <el-button size="mini" type="primary" plain v-if="item.state==0?false:true" @click="updataState(item.id,item.platformName,item.state)">启用</el-button>
                        <el-button size="mini" type="danger" plain v-else @click="updataState(item.id,item.platformName,item.state)">禁用</el-button>
                    </td>
                </tr>
            </table>
            <div style="text-align:center;">
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
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getPlatFormList, api_updataState } from "../../../axios/apis.js";
    export default {
        name:'platForm',
        components:{ crumbs },
        data(){
            return{
                startDate:'',           // 开始时间
                endDate:'',             // 结束时间
                platFormName:'',        // 平台名称
                platFormList:'',        // 平台列表
                currentPage:1,          // 默认页 1
                showCount:10,           // 每页显示条数
                totalPage:null,         // 总页数
                totalResult:null,       // 总条数
            }
        },
        created(){

        },
        mounted(){
            this.getPlatFormList()
        },
        methods:{
            // 查询平台列表
            getPlatFormList(){
                api_getPlatFormList({
                    currentPage:this.currentPage,
                    showCount:this.showCount,
                    platformName:this.platFormName,
                    startDate:this.startDate,
                    endDate:this.endDate
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.platFormList = res.obj.list;
                        this.totalPage = res.obj.totalPage;
                        this.totalResult = res.obj.totalResult;
                        sessionStorage.platformList = JSON.stringify(res.obj.list);
                    }
                })
                .catch(error=>console.log(error))
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getPlatFormList();
            },
            // 进入编辑页面
            editorPlatForm(id){

                id = id?id:'';
                this.$router.push({ path: '/home/cmsSystemManagementIndex/platForm/platFormDetaile',query:{ currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, platformId: id}})
            },
            updataState(id,platformName,state){
                api_updataState({
                    id:id,
                    platformName:platformName,
                    state:state=='0'?'1':'0'
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'error',
                        duration: 1500
                    });
                    if (res.flag=='success') {
                        this.getPlatFormList()
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            }

        }
    }
</script>

<style scoped>
    .contentBox{
        text-align: left;
    }
    .titleBox{
        margin-bottom: 20px;
    }
    .titleBox input{
        width: 160px;
        height: 40px;
        line-height: 40px;
        padding: 0 5px;
        font-size: 14px;
        color: #333;
        border: 1px solid #ccc;
        margin-right: 12px;
    }
    .search_btn{
        width: 112px;
        height: 40px;
        background-color: #9992fd;
        border-radius: 6px;
        font-size: 14px;
        margin-left: 12px;
        line-height: 40px;
        color: #fff;
        cursor: pointer;
        text-align: center;
    }
    .addPlatFormBtn{
        float: right;
        width: 100px;
        height: 36px;
        line-height: 36px;
        font-size: 14px;
        color: #fff;
        background: rgba(129, 103, 245, 1);
        text-align: center;
        border-radius: 4px;
        cursor: pointer;
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
        margin-bottom: 20px;
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
</style>