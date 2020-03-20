<template>
    <div class="homeBox" v-on:mouseover="mouseover()">
        <!-- 头部 S -->
        <transition name="move" >
            <div id="header" class="clearfix" :class="{on:!isShowHeader}">
                <div class="logo fl">
                    <a href="#/home/bigData"><i></i></a>
                </div>
                <nav class="fl">
                    <ul>
                        <li v-for="(item,index) in menuList" :key="item.id" @click="getSideNav(item.id,index)" :class="{ active:index==currentMenuIndex?true:false }">
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
                <div class="showHeaderBox" @click="changeHeaderState()">
                    <i></i>
                    <div class="btnBoxHeader" :class="{on:!isShowHeader}"><span><s></s></span></div>
                </div>
            </div>
        </transition>
        <!-- 头部 E -->

        <!-- 侧导航 S -->
        <div class="fl" id="sideNavigation" :class="{on:!classState}">
            <currentTime></currentTime>
            <div class="sideNavigationBox">
                <ul>
                    <template v-if="currentMenuIndex=='1'">
                        <li v-for="(item,index) in sideNav" :key="item.id" :class="{on:index == currentSideIndex?true:false}">
                            <div class="menuBox" v-on:click="isShowSideNav(index)">
                                <p>{{item.functionname}}</p>
                            </div>
                            <div class="subtopic">
                                <span v-for="(val,index) in platformList" :key="index" :class="{ on:index==currentSideSecondIndex?true:false }" @click="judgeColumnType(index,item.functionname,val.id)">
                                    <i></i><a>{{val.platformName}}</a>
                                </span>
                            </div>
                        </li>
                    </template>
                    <template v-if="currentMenuIndex=='0'||currentMenuIndex=='2'">
                        <li v-for="(item,index) in sideNav" :key="item.id" :class="{active:index == currentSideIndex?true:false}" @click="judeColumnInfo(index,item.functionname)">
                            <div class="menuBox" v-on:click="isShowSideNav(index)">
                                <p>{{item.functionname}}</p>
                            </div>
                        </li>
                    </template>
                </ul>
            </div>
            <div class="btn" @click="slideNavIsShow()">
                <div class="btnBox"><i></i></div>
            </div>
        </div>

        <!-- 主体 S -->
        <transition name="move" >
            <router-view class="content_box fl" :class="{on:!classState}"/>
        </transition>
        <!-- 主体 S -->
    </div>
</template>

<script>
    import currentTime from "../components/currentTime";
    import { api_getPlatformInfo, api_getHospitalList, api_getMenuList, api_loginOut } from '../axios/apis.js'
    export default {
        name:'home',
        components:{
            currentTime
        },
        data:function(){
            return {
                // 头部
                sysRoles:[],                            // 角色列表
                roleId:'',                              // 当前角色id
                rolename:'',                            // 角色名称
                menuList:[],                            // 菜单列表
                currentMenuIndex: null,                 // 当前选中菜单下标
                currentMenuId:'',
                isShowHeader:true,

                // 登录超时验证
                lineTime: new Date().getTime(),         // 最后一次操作的时间
                currentTime: new Date().getTime(),      // 当前时间
                timeOut: 30 * 60 * 1000,                // 超时时间30min
                currentUrl:'',                          // 当前地址

                // 侧导航
                classState:true,                        // true:显示 /false: 关闭 侧导航
                sideNav:[],                             // 侧导航列表
                platformList:[],                        // 平台列表
                currentSideIndex:null,                    // 当前选中侧导航下标
                currentSideSecondIndex:null,               // 当前选中侧导航二级栏目下标
                fkPlatformId:'',
            }
        },
        beforeRouteUpdate(to,from,next){
            this.currentMenuIndex = to.query.currentMenuIndex;
            this.currentMenuId = to.query.currentMenuId;
            this.currentSideIndex = to.query.currentSideIndex;
            this.currentSideSecondIndex = to.query.currentSideSecondIndex;
            this.fkPlatformId = to.query.fkPlatformId;
            next();
        },
        created(){

            this.currentMenuIndex = this.$route.query.currentMenuIndex;
            this.currentMenuId = this.$route.query.currentMenuId;
            this.currentSideIndex = this.$route.query.currentSideIndex;

            this.currentSideSecondIndex = this.$route.query.currentSideSecondIndex;
            this.fkPlatformId = this.$route.query.fkPlatformId;
        },
        mounted:function(){
            // 获取用户信息
            this.getLoginInfo();
            // 验证是否操作 超过30分钟未操作跳转到登录页
            this.setTime();
            // 缓存当前地址
            this.currentUrl = window.location.hash;
        },
        methods:{
            // 头部
            // 页面初始化
            init:function(){
                this.getPlatformInfo();
                this.getHospitalList();
                this.getColumnList();
            },
            // 获取用户信息
            getLoginInfo:function(){

                // 如果角色信息不存在 提示登录超时并回到登录页
                if(!sessionStorage.loginInfo){
                    this.$message({
                        message: '登录超时，请重新登录!',
                        type: 'info',
                        duration: 2000
                    });
                    setTimeout(() => {
                        this.$router.push('/login')
                    }, 2000);
                    // 清空所有缓存
                    sessionStorage.clear();
                }else{
                    // 读取当前角色信息
                    this.sysRoles = JSON.parse(sessionStorage.sysRoles);
                    // 获取角色列表第一个角色id
                    this.roleId = this.sysRoles[0].id;
                    sessionStorage.roleId = JSON.stringify(this.sysRoles[0].id);
                    this.rolename = this.sysRoles[0].rolename;
                    // 通过角色id 初始化页面菜单及侧栏目
                    this.init();
                }
            },
            // 切换角色 请求页面信息
            getPageInfo(id,name){
                this.roleId = id;
                this.rolename = name;
                sessionStorage.roleId = JSON.stringify(id);
                this.init();
            },
            // 获取平台列表信息
            getPlatformInfo:function(){
                api_getPlatformInfo({roleId:this.roleId})
                .then(res=>{
                    if(res.flag=='success'){
                        sessionStorage.platformList = JSON.stringify(res.obj);
                        this.platformList = res.obj;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 获取医院列表
            getHospitalList:function(){
                api_getHospitalList()
                .then(res=>{
                    // 缓存机构列表
                    if (res.flag == 'success') { sessionStorage.hospitalList = JSON.stringify(res.obj) }
                })
                .catch(error=> console.log(error))
            },
            // 获取栏目列表
            getColumnList:function(){
                api_getMenuList({roleId:this.roleId})
                .then(res => {

                    console.log(this.menuList);
                    if(res.obj && res.obj.length > 0){
                        this.menuList = [];

                        this.menuList = res.obj;
                        sessionStorage.menuList = JSON.stringify(res.obj);
                        if(this.currentMenuId){ this.getSideNavList(this.currentMenuId); }
                    }else{
                        this.loginOut();
                    }
                })
                .catch(e=>console.log(e))
            },
            // 退出登录
            loginOut:function(){
                api_loginOut().then(res=>{
                    if(res.flag=='success'){
                        sessionStorage.clear();
                        this.$router.push('/login');
                    }
                })
                .catch(e=>console.log(e))
            },
            // 根据id/栏目名称 判断进入子页面
            getSideNav:function(menuId,index){
                this.menuList.forEach(item => {
                    if(item.id == menuId && item.functionname == '工作管理'){
                        this.$router.push({path:'/home/workManagementIndex',query:{ currentMenuId:item.id,currentMenuIndex:index }})
                        this.currentMenuId = item.id;
                    }else if(item.id == menuId && item.functionname == '内容管理'){
                        this.$router.push({path:'/home/contentManagementIndex',query:{ currentMenuId:item.id,currentMenuIndex:index }})
                        this.currentMenuId = item.id;
                    }else if (item.id == menuId && item.functionname == 'CMS系统管理') {
                        this.$router.push({path:'/home/cmsSystemManagementIndex',query:{ currentMenuId:item.id,currentMenuIndex:index }})
                        this.currentMenuId = item.id;
                    }
                });
                this.currentMenuIndex = index;
                this.currentSideIndex = null;
                this.currentSideSecondIndex = null;
                this.getSideNavList(menuId);
            },
            // 获取侧导航信息
            getSideNavList(menuId){
                console.log(this.menuList);
                this.menuList.forEach(item => {
                    if(item.id == menuId){
                        this.sideNav = item.functions;
                        sessionStorage.sideNav = JSON.stringify(item.functions)
                    }
                });
            },
            // 点击展开左侧导航列表
            isShowSideNav:function(index){
                if(this.currentSideIndex || this.currentSideIndex == 0){
                    if(this.currentSideIndex==index){
                        this.currentSideIndex = null;
                    }else{
                        this.currentSideIndex = index;
                    }
                }else{
                    this.currentSideIndex = index;
                }
                this.currentSideSecondIndex = null;
            },
            // 判断栏目类型 调用对应方法
            judgeColumnType:function(index,type,id){
                this.currentSideSecondIndex = index;
                if(type == 'Banner管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/banner/bannerList', query: {currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
                if(type == '新闻管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/news/newsList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
                if(type == '栏目管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/column/columnList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
                if(type == '默认栏目管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/defaultIndex/defaultList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
                if(type == '视频管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/video/videoList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
                if(type == '学习资料管理'){
                    this.$router.push({ path: '/home/contentManagementIndex/learning/learningList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex, currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex, fkPlatformId: id}})
                }
            },
            judeColumnInfo(index,name){
                console.log(name);
                if(name == '医生服务配置'){
                    this.$router.push({ path: '/home/workManagementIndex/server/serverList', query: {currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex,currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex}})
                }
                if(name == '课程安排'){
                    this.$router.push({ path: '/home/workManagementIndex/course/courseList', query: {currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex,currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex}})
                }
                if(name == '平台管理'){
                    this.$router.push({ path: '/home/cmsSystemManagementIndex/platForm/platFormList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex,currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex}})
                }
                if(name == '用户单位管理'){
                    this.$router.push({ path: '/home/cmsSystemManagementIndex/rolePlatForm/rolePlatFormList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex,currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex}})
                }
                if(name == '角色平台管理'){
                    this.$router.push({ path: '/home/cmsSystemManagementIndex/userUnit/userUnitList', query: { currentMenuId:this.currentMenuId,currentMenuIndex:this.currentMenuIndex,currentSideIndex:this.currentSideIndex, currentSideSecondIndex:this.currentSideSecondIndex}})
                }
            },
            //当界面被操作 更新操作时间
            mouseover() {
                this.lineTime = new Date().getTime();
            },
            // 每秒验证一次是否超时
            setTime(){
                this.timer = setInterval(this.judgeCurrentTime, 1000)
            },
            // 验证是否超时
            judgeCurrentTime() {
                this.currentTime = new Date().getTime();
                if (this.currentTime - this.lineTime > this.timeOut){
                    if(!this.currentUrl.includes('/login')){
                        clearInterval(this.timer);
                        this.loginOut();
                    };
                    sessionStorage.clear();
                }
            },
            // 侧导航的显示与隐藏
            slideNavIsShow(){this.classState = !this.classState},
            // 头部隐藏显示
            changeHeaderState(){this.isShowHeader = !this.isShowHeader;},
        }
    }
</script>

<style scoped>
    /* 侧导航 */
    @import url(../assets/css/sideNav.css);
    /* 头部 */
    .homeBox{
        width: 100%;
        height: 100%;
    }
    #header {
        width: 100%;
        height: 90px;
        background-color: #120d58;
        box-shadow: 0px 3px 8px 0px rgba(33, 36, 45, 0.73);
        position: relative;
        transition: 0.3s;
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
        cursor: pointer;
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
    nav li.active i {
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

    .showHeaderBox{
        position: absolute;
        right: 10px;
        bottom: -40px;
        width: 60px;
        height: 20px;
        background: #120d58;
        cursor: pointer;
        border-radius: 4px;
    }
    .showHeaderBox i{
        display: block;
        position: absolute;
        top: -20px;
        height: 20px;
        width: 0;
        left: 0;
        right: 0;
        margin: 0 auto;
        border-right: 1px dashed #120d58;
    }
    .btnBoxHeader{
        width: 10px;
        height: 10px;
        overflow: hidden;
        margin: auto;
        margin-top: 5px;
        transform: rotateZ(180deg);
        transition: 0.3s;
    }
    #header.on{
        margin-top: -90px;
        box-shadow: none;
        transition: 0.3s;
    }
    .btnBoxHeader.on{
        transform: rotateZ(0);
        transition: 0.3s;
    }
    .btnBoxHeader span{
        width: 10px;
        height: 10px;
        background: #fff;
        margin: auto;
        transform: rotateZ(45deg);
        margin-top: -5px;
        display: block;
    }
    .btnBoxHeader s{
        width: 9px;
        height: 9px;
        background: #120d58;
        display: block;
    }


</style>
