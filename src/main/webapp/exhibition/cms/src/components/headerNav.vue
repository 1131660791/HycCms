<template>
    <div id="header" class="clearfix">
        <div class="logo fl">
            <a href="#/home/bigData"><i></i></a>
        </div>
        <nav class="fl">
            <ul>
                <li v-for="(item,index) in menuList" :key="item.id" @click="getSideNav(item.id,index)" :class="{ on:index==currentIndex?true:false }">
                    <p>{{item.functionname}}</p>
                    <i></i>
                </li>
            </ul>
        </nav>
        <div class="register fr">
            <div class="userInfo fl">
                <el-dropdown trigger="click">
                    <span class="el-dropdown-link">
                        <div class="userPhoto fl"><img src="" alt=""></div>
                        {{rolename}}
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown" style="width:140px;">
                        <el-dropdown-item v-for="item in sysRoles" :key="item.id" style="font-size:12px;" @click.native="getPageInfo(item.id,item.rolename)">{{item.rolename}}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <i></i>
            <div class="information fl"><span>99</span></div>
            <i></i>
            <div class="loginOut fl" @click="loginOut()"><i></i>退出登录</div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'headerNav',
        data(){
            return {
                sysRoles:[],
                roleId:'',
                rolename:'',
                menuList:[],
                currentIndex: null,
            }
        },
        mounted:function(){
            this.getLoginInfo();
            this.roleId = this.sysRoles[0].id;
            sessionStorage.roleId = JSON.stringify(this.sysRoles[0].id);
            this.rolename = this.sysRoles[0].rolename;
            this.init();
        },
        methods:{
            // 页面初始化
            init:function(){
                this.getPlatformInfo();
                this.getHospitalList();
                this.getColumnList();
            },
            // 获取用户信息
            getLoginInfo:function(){
                if(!sessionStorage.loginInfo){
                    this.$message({
                        message: '登录超时，请重新登录!',
                        type: 'info',
                        duration: 2000
                    });
                    setTimeout(() => {
                        this.$router.push('/login')
                    }, 2000);
                }else{
                    this.sysRoles = JSON.parse(sessionStorage.loginInfo).sysRoles;
                }
            },
            // 切换角色 请求页面信息
            getPageInfo(id,name){
                this.roleId = id;
                this.rolename = name;
                sessionStorage.roleId = JSON.stringify(id);
                this.getPlatformInfo();
                this.getHospitalList();
                this.getColumnList()
            },
            // 获取平台列表信息
            getPlatformInfo:function(){
                this.$axios.post('/HycCms/platform/findRolePlatform.do',this.$qs.stringify({
                    roleId:this.roleId
                }))
                .then(res=>{
                    sessionStorage.platformList = JSON.stringify(res.data.obj);
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 获取医院列表
            getHospitalList:function(){
                this.$axios.post('/HycCms/userDepart/findUserDepart.do')
                .then(res=>{
                    sessionStorage.hospitalList = JSON.stringify(res.data.obj)
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 退出登录
            loginOut:function(){
                this.$axios({
                    method:'post',
                    url:'/HycCms/login/logout.do'
                }).then(res=>{
                    if(res.status == 200){
                        sessionStorage.clear();
                        this.$router.push('/login');
                    }
                })
                .catch(e=>{
                    console.log(e);
                })
            },
            // 获取栏目列表
            getColumnList:function(){
                this.$axios.post('/HycCms/sysFunction/findRoleFunction.do',this.$qs.stringify({
                    roleId:this.roleId
                }))
                .then(res => {
                    if(res.status == 200){
                        if(res.data.obj && res.data.obj.length > 0){
                            this.menuList = [];
                            this.menuList = res.data.obj;
                            sessionStorage.menuList = JSON.stringify(res.data.obj)
                        }else{
                            this.loginOut();
                        }
                    }
                })
            },
            // 根据id/栏目名称 判断进入子页面
            getSideNav:function(menuId,index){
                this.menuList.forEach(item => {
                    if(item.id == menuId && item.functionname == '工作管理'){
                        this.$router.push({path:'/home/workManagementIndex',query:{ id:item.id,currentIndex:this.currentIndex }})
                    }else if(item.id == menuId && item.functionname == '内容管理'){
                        this.$router.push({path:'/home/contentManagementIndex',query:{ id:item.id,currentIndex:this.currentIndex }})
                    }else if (item.id == menuId && item.functionname == 'CMS系统管理') {
                        this.$router.push({path:'/home/cmsSystemManagementIndex',query:{ id:item.id,currentIndex:this.currentIndex }})
                    }
                });
                this.currentIndex = index;
            }
        }
    }
</script>

<style scoped>
    #header {
        width: 100%;
        height: 90px;
        background-color: #120d58;
        box-shadow: 0px 3px 8px 0px rgba(33, 36, 45, 0.73);
    }
    .logo {
        width: 206px;
        height: 90px;
    }
    .logo i {
        display: block;
        width: 100%;
        height: 100%;
        background-image: url(../assets/images/cms_logo.png);
        background-position: center;
        background-repeat: no-repeat;
    }
    nav{
        width: calc(100% - 600px);
    }
    nav ul{
        width: 100%;
    }
    nav li {
        float: left;
        width: calc( 100% / 7);
        height: 90px;
        text-align: center;
        position: relative;
    }
    nav li p {
        width: 100%;
        font-size: 18px;
        color: #fff;
        line-height: 90px;
    }
    nav li i {
        display: none;
        position: absolute;
        width: 100%;
        height: 4px;
        bottom: 0;
        right: 0;
        left: 0;
        margin: 0 auto;
        background-color: #9992fd;
    }
    nav li.on i {
        display: block;
    }
    nav li:hover{
        background-color: #050046;
    }
    .register{
        height: 90px;
        padding-top: 20px;
        box-sizing: border-box;
        padding-right: 32px;
    }
    .userInfo{
        line-height: 50px;
        color: #fff;
        font-size: 14px;
    }
    .userInfo .userPhoto{
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background-image: url(../assets/images/user_photo.png);
        background-position: center;
        background-repeat: no-repeat;
        margin-right: 5px;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: #fff;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }

    .register>i{
        display: block;
        width: 2px;
        background-color: #ffffff;
        height: 30px;
        opacity: 0.5;
        float: left;
        margin: 0 15px;
        margin-top: 10px;
    }
    .information{
        width: 30px;
        height: 33px;
        margin-top: 10px;
        background-image: url(../assets/images/information.png);
        background-position: center;
        background-repeat: no-repeat;
        position: relative;
    }
    .information span{
        position: absolute;
        top: -4px;
        right: -2px;
        width: 16px;
        height: 16px;
        display: block;
        border-radius: 8px;
        background-color: #f44f4a;
        color: #ffffff;
        font-size: 12px;
        text-align: center;
        line-height: 16px;
    }
    .loginOut{
        font-size: 14px;
        color: #ffffff;
        height: 50px;
        line-height: 50px;
        cursor: pointer;
    }
    .loginOut i{
        display: block;
        float: left;
        width: 28px;
        height: 30px;
        background-image: url(../assets/images/login_out.png);
        background-position: center;
        background-repeat: no-repeat;
        margin-top: 10px;
        margin-right: 5px;
    }
</style>
