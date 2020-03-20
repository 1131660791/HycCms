<template>
    <div class="serverListBox">
        <crumbs></crumbs>
        <div class="contentBox">
            <div class="tableBoxTitle clearfix">
                <select name="" id="" v-model="hospitalId">
                    <option :value="item.id" v-for="item in hospitalList" :key="item.id">{{item.departname}}</option>
                </select>
                <select name="" id="" v-model="platformId">
                    <option value="">请选择平台</option>
                    <option :value="item.id" v-for="item in platformList" :key="item.id">{{item.platformName}}</option>
                </select>
                <input type="text" placeholder="输入医生名字" v-model="searchKeywords">
                <div class="searchBtn fl" @click="getDoctorServerList()">查询</div>
            </div>
            <table>
                <tr>
                    <td width="3%">ID</td>
                    <td width="8%">医生名字</td>
                    <td width="15%">所在医院</td>
                    <td width="16%">支持服务</td>
                    <td width="12%">推荐</td>
                    <td width="6%">状态</td>
                    <td width="10%">发布人</td>
                    <td width="30%">操作</td>
                </tr>
                <tr v-for="(item,index) in doctorServer" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.realname}}</td>
                    <td>{{item.departname}}</td>
                    <td>{{item.serviceName}}</td>
                    <td>{{item.columnName}}</td>
                    <td>
                        <span style="color:#246ce2" v-if="item.state==0?true:false">启用</span>
                        <span style="color:#ec3232" v-else>启用</span>
                    </td>
                    <td>{{item.servicePublicName}}</td>
                    <td>
                        <el-button size="mini" type="primary" plain @click="serverConfig(item.userId)">详情</el-button>
                        <el-button size="mini" type="danger" plain @click="disableServer(item.userId,item.state)" v-if="item.state==0?false:true">启用服务</el-button>
                        <el-button size="mini" type="danger" plain @click="disableServer(item.userId,item.state)" v-else>禁止服务</el-button>
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
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getDoctorServerList, api_disableServer } from "../../../axios/apis.js";
    export default {
        name:'serverList',
        components:{
            crumbs
        },
        data(){
            return{
                doctorServer:[],    // 当前医生列表
                platformList:[],    // 平台列表
                hospitalList:[],    // 医院列表

                hospitalId:'',      // 当前医院id
                platformId:'',      // 当前平台id
                searchKeywords:'',  // 搜索关键词
                roleId:'',          // 角色Id
                currentPage:1,      // 默认显示页码
                showCount:10,       // 每页显示条数
                totalPage:null,     // 总页数
                totalResult:null,   // 总条数
            }
        },
        created(){
            this.platformList = JSON.parse(sessionStorage.platformList);
            this.hospitalList = JSON.parse(sessionStorage.hospitalList);
            this.roleId = JSON.parse(sessionStorage.roleId);
            this.hospitalId = this.hospitalList.length>0?this.hospitalList[0].id:'';
            this.getDoctorServerList();
        },
        methods:{
            // 获取当前平台下
            getDoctorServerList(){
                api_getDoctorServerList({
                        currentPage: this.currentPage,
                        showCount: this.showCount,
                        roleId:this.roleId,
                        hospitalId: this.hospitalId,
                        doctorName:this.searchKeywords,
                        platformId: this.platformId,
                    })
                .then(res=>{
                    if (res.flag=='success') {
                        this.doctorServer = res.obj.list;
                        this.totalResult = res.obj.totalResult;
                        this.totalPage = res.obj.totalPage;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 配置服务
            serverConfig(id){
                this.$router.push({ path: '/home/workManagementIndex/server/doctorServerDetails',query:{
                    hospitalId: this.hospitalId,
                    doctorId:id,
                }})
            },
            // 禁止服务
            disableServer(id,state){
                api_disableServer({
                        state:state=='1'?'0':'1',
                        doctorId:id,
                    })
                .then(res=>{
                    if(res.flag=='success'){ this.doctorServer = res.obj.list}
                })
                .catch(error=>console.log(error))
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getDoctorServerList();
            },
        }
    }
</script>

<style scoped>
    .serverListBox{
        text-align: left;
    }
    .tableBoxTitle{
        margin-bottom: 20px;
    }
    .tableBoxTitle select,.tableBoxTitle input{
        height: 36px;
        line-height: 36px;
        border: 1px solid #ccc;
        width: 160px;
        margin-right: 15px;
        padding-left: 5px;
        padding-right: 5px;
        font-size: 14px;
        color: #333;
        float: left;
    }
    .searchBtn{
        width: 80px;
        height: 36px;
        line-height: 36px;
        border-radius: 4px;
        background: #877ff4;
        text-align: center;
        color: #fff;
        cursor: pointer;
    }
    .contentBox table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        font-size: 14px;
        color: #333333;
        width: 100%;
        min-width: 900px;
        text-align: left;
    }
    .contentBox td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 15px;
        margin: 0;
    }
    .contentBox tr:nth-child(2n){
        background-color: #f1f1f1;
    }
    .contentBox tr{
        background-color: #eee;
    }
    .contentBox tr:hover{
        background-color: #e5e5e5;
    }
    .contentBox tr td:first-child{
        text-align: center;
    }

</style>