<template>
    <div class="detaileContent">
        <crumbs></crumbs>
        <div class="detaileContentBox">
            <P>用户信息</P>
            <div class="content">
                <p>基本信息</p>
                <div class="baseinfo clearfix">
                    <p><span>账户名称：</span>{{username}}</p>
                    <p><span>手机号码：</span>{{mobilephone}}</p>
                    <p><span>角色：</span>{{roleName}}</p>
                    <!-- <p><span>邮箱：</span>{{realname}}</p> -->
                    <p><span>真实姓名：</span>{{realname}}</p>
                    <p><span>性别：</span>{{sex}}</p>
                </div>
                <p>机构管理范围</p>
                <div class="baseinfo">
                    <table>
                        <tr>
                            <td width="20%">机构名称</td>
                            <td width="20%">时间</td>
                            <td width="20%">操作</td>
                        </tr>
                        <tr v-for="item in institutionList" :key="item.id">
                            <td>{{item.departname}}</td>
                            <td>{{item.updateDate}}</td>
                            <td>
                                <span @click="delHospital(item.id)" style="cursor:pointer">删除</span>
                            </td>
                        </tr>
                    </table>
                    <div class="addOrganization" @click="addShow()">+增加机构</div>
                </div>
            </div>
            <div class="cancel" @click="cancel">取消</div>
        </div>
        <div class="addBox" v-if="addShowStatus">
            <div class="tipBox">
                <div class="searchBox clearfxi">
                    <input type="text" placeholder="请输入要搜索的医院名称!" v-model="departName" class="search_keywords fl">
                    <div class="searchBtn fr" @click="getHospitalList()">搜索</div>
                </div>
                <div class="institutionList">
                    <ul>
                        <el-checkbox-group v-model="checkList">
                            <el-checkbox :label="item.id" v-for="item in allHospitalList" :key="item.id" :disabled="item.editorStatus"><p>{{item.departname}}</p></el-checkbox>
                        </el-checkbox-group>
                    </ul>
                </div>
                <div class="operate">
                    <el-pagination
                        class="fl"
                        v-show="totalPage>1"
                        @current-change="handleCurrentChange"
                        :current-page.sync="currentPage"
                        :page-size="showCount"
                        :page-count="totalPage"
                        layout="total, prev, pager, next, jumper"
                        :total="totalResult">
                    </el-pagination>
                    <div class="btn fr" @click="saveInfo()">确定</div>
                    <div class="btn fr" style="background:#999" @click="closeAdd()">取消</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getHospitalList, api_getUserInfoAndHospitalList, api_saveAddInfo, api_delHospital,api_findTopDeparts } from "../../../axios/apis.js";
    export default {
        name:'roleList',
        components:{ crumbs },
        data(){
            return{
                userId:'',
                mobilephone:'',
                realname:'',
                roleName:'',
                sex:'',
                username:'',
                institutionList:[],
                addShowStatus:false,
                allHospitalList:[],
                currentPage:1,          // 默认页 1
                showCount:10,           // 每页显示条数
                totalPage:null,         // 总页数
                totalResult:null,       // 总条数
                checkList:[],
                departName:'',
            }
        },
        created(){
            this.userId = this.$route.query.userId;
        },
        mounted(){
            this.getHospitalList()
        },
        methods:{
            // 取消
            cancel(){
                this.$router.go(-1);
            },
            // 获取所有机构列表
            getHospitalList(){
                api_findTopDeparts({
                    departName:this.departName,
                    currentPage:this.currentPage,
                    showCount:this.showCount
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.allHospitalList = [];
                        this.allHospitalList = res.obj.list;
                        this.totalPage = res.obj.totalPage;
                        this.totalResult = res.obj.totalResult;
                        this.getUserInfoAndHospitalList();
                    }
                })
                .catch(error=>console.log(error))
            },
            // 查询用户信息  获取已关联的机构列表
            getUserInfoAndHospitalList(){
               api_getUserInfoAndHospitalList({
                    userId:this.userId,
                })
                .then(res=>{
                    if(res.obj.length>0){
                        this.institutionList = [];
                        this.institutionList = res.obj[0].departs;
                        this.mobilephone = res.obj[0].mobilephone;
                        this.realname = res.obj[0].realname;
                        this.roleName = res.obj[0].roleName;
                        this.sex = res.obj[0].sex;
                        this.username = res.obj[0].username;
                        if(this.institutionList.length>0){
                            this.institutionList.forEach(item=>{
                                this.allHospitalList.forEach(v=>{
                                    if(v.id == item.id){
                                        v.editorStatus = true;
                                        if(!this.checkList.includes(item.id)){
                                            this.checkList.push(item.id)
                                        }
                                    }
                                })
                            });
                        }
                    }
                })
                .catch(error=>console.log(error))
            },
            // 展开关联机构列表
            addShow(){
                this.addShowStatus = true;
            },
            // 关闭关联机构列表
            closeAdd(){
                this.addShowStatus = false;
            },
            // 确认新增机构
            saveInfo(){
                let data = [];
                if(this.checkList && this.checkList.length>0){
                    this.checkList.forEach(v=>{
                        data.push({userId:this.userId,departId:v})
                    })
                }else{
                    this.$message({
                        message: '请选择要关联的机构',
                        type: 'error',
                        duration: 1500
                    });
                    return;
                }
                this.sureAdd(data)
            },
            sureAdd(data){
                api_saveAddInfo({
                    userDepartJson:JSON.stringify(data),
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'warning',
                        duration: 1500
                    });
                    if(res.flag=='success'){
                        this.addShowStatus = false;
                        this.getHospitalList()
                    }
                })
                .catch(error=>console.log(error))
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getHospitalList()
            },
            // 删除已关联机构
            delHospital(id){
                api_delHospital({
                    userId:this.userId,
                    departId:id
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'error',
                        duration: 1500
                    });
                    if(res.flag=='success'){
                        if(this.checkList.includes(id)){
                            this.checkList.splice(this.checkList.findIndex(item => item === id), 1)
                        }
                        this.getHospitalList()
                    }
                })
                .catch(error=>console.log(error))
            },
        },
    }
</script>

<style scoped>
    .detaileContentBox{
        text-align: left;
    }
    .detaileContentBox>p{
        color: #333;
        width: 100%;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 12px;
    }
    .content>p{
        font-size: 16px;
        color: #048bf0;
        margin-bottom: 10px;
    }
    .baseinfo{
        margin-bottom: 20px;
    }
    .baseinfo p{
        float: left;
        width: 40%;
        margin-bottom: 10px;
        font-size: 14px;
    }
    .baseinfo p span{
        display: inline-block;
        width: 80px;
        text-align: right;
    }
    .baseinfo table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        width: 70%;
        margin-bottom: 12px;
    }
    .baseinfo tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }
    .baseinfo tr:hover{
        background-color: #eeedfc;
    }
    .baseinfo tr:first-child{
        background-color: #eeedfc;
    }
    .baseinfo tr td:first-child,.baseinfo tr td:nth-child(2){
        text-align: center;
    }
    .baseinfo td{
        height: 30px;
        line-height: 30px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 18px;
        margin: 0;
        font-size: 14px;
    }
    .addOrganization{
        cursor: pointer;
        font-size: 14px;
        color: #048bf0;
    }

    .addBox{
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 10;
        background: rgba(0, 0, 0, 0.5)
    }
    .tipBox{
        width: 660px;
        height: 440px;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin: auto;
        background: #fafafa;
        border-radius: 4px;
        padding: 10px;
        text-align: left;
    }
    .searchBox{
        margin-bottom: 20px;
        height: 36px;
    }
    .search_keywords{
        border: 1px solid #ccc;
        width: 490px;
        height: 36px;
        line-height: 36px;
        font-size: 14px;
        padding: 0 5px;
        border-radius: 4px;
    }
    .searchBtn{
        width: 140px;
        height: 36px;
        text-align: center;
        line-height: 36px;
        font-size: 14px;
        color: #ffffff;
        background: #048bf0;
        border-radius: 4px;
        cursor: pointer;
    }
    .institutionList{
        margin-bottom: 20px;
        height: 300px;
    }
    .el-checkbox{
        width: 100%;
        margin-left: 0;
        line-height: 30px;
    }
    .btn{
        margin-left: 10px;
        height: 30px;
        line-height: 30px;
        color: #fff;
        border-radius: 4px;
        width: 80px;
        font-size: 14px;
        text-align: center;
        background: #048bf0;
        cursor: pointer;
    }
    .cancel{
        width: 120px;
        text-align: center;
        line-height: 40px;
        height: 40px;
        border-radius: 4px;
        background-color: #ccc;
        color: #333;
        cursor: pointer;
        margin-top: 50px;
    }
    .cancel:hover{
        color: #fff;
        background-color: #048bf0;
    }
</style>