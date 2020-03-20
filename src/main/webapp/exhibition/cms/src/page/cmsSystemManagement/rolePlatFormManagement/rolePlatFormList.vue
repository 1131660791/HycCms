<template>
    <div class="roleList">
        <crumbs></crumbs>
        <div class="roleListContent">
            <div class="condition clearfix">
                <!-- <select name="" id="" class="fl">
                    <option value="">用户角色</option>
                </select> -->
                <input type="text" placeholder="账户名称/手机号" class="fl" v-model="phone">
                <el-date-picker
                    class="fl"
                    v-model="startDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="开始时间"
                    default-time="00:00:00">
                </el-date-picker><span class="fl" style="line-height:40px;margin: 0 10px;color:#999;">--</span>
                    <el-date-picker
                    class="fl"
                    v-model="endDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="结束时间"
                    default-time="23:59:59">
                </el-date-picker>
                <div class="search_btn fl" @click="getUserList()">查询</div>
            </div>
            <table>
                <tr>
                    <td width="3%">ID</td>
                    <td width="12%">用户名</td>
                    <td width="12%">手机号</td>
                    <td width="10%">邮箱</td>
                    <td width="20%">更新时间</td>
                    <td width="11%">创建人</td>
                    <td width="22%">操作</td>
                </tr>
                <tr v-for="(item,index) in userList" :key="item.id">
                    <td>{{index+1}}</td>
                    <td>{{item.tsBaseUser.username}}</td>
                    <td>{{item.mobilephone}}</td>
                    <td>{{item.email}}</td>
                    <td>{{item.updateDate}}</td>
                    <td>{{item.createBy}}</td>
                    <td class="buttonBox">
                        <el-button size="mini" type="primary" plain @click="view(item.id)">查看</el-button>
                        <!-- <el-button size="mini" type="warning" plain @click="del(item.id)">删除</el-button> -->
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
    import { api_getUserList } from "../../../axios/apis.js";
    export default {
        name:'roleList',
        components:{ crumbs },
        data(){
            return{
                startDate:'',           // 开始时间
                endDate:'',             // 结束时间
                userList:'',
                phone:'',
                currentPage:1,          // 默认页 1
                showCount:10,           // 每页显示条数
                totalPage:null,         // 总页数
                totalResult:null,       // 总条数
            }
        },
        mounted(){
            this.getUserList();
        },
        methods:{
            // 获取用户列表
            getUserList(){
                api_getUserList({
                    currentPage:this.currentPage,
                    showCount:this.showCount,
                    startDate:this.startDate,
                    endDate:this.endDate,
                    userName:this.phone
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.userList = [];
                        this.userList = res.obj.list;
                        this.totalPage =  res.obj.totalPage;
                        this.totalResult =  res.obj.totalResult;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getUserList()
            },
            view(id){
                this.$router.push({ path: '/home/cmsSystemManagementIndex/rolePlatForm/rolePlatdetaile',query:{ currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, userId: id}})
            }
        }
    }
</script>

<style scoped>
    .condition{
        margin-bottom: 12px;
    }
    .condition select,.condition input{
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
    }
    .roleList table{
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
    .roleList tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }
    .roleList tr:hover{
        background-color: #eeedfc;
    }
    .roleList tr:first-child{
        background-color: #eeedfc;
    }
    .roleList tr td:first-child,.roleList tr td:nth-child(2){
        text-align: center;
    }
    .roleList td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 18px;
        margin: 0;
    }

</style>