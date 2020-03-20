<template>
    <div class="newsListBox">
        <crumbs></crumbs>
        <div class="platformInfo">
            <span>机构选择</span>
            <select name="" id="platformInfo" v-model="hospitalId" @change="getNewsColumnType()">
                <!-- <option value="">请选择</option> -->
                <option v-for="(item,index) in hospitalList" :value="item.id" :key="index">{{item.departname}}</option>
            </select>
        </div>
        <div class="newsListContent">
            <div class="newsList">
                <div class="functionModule clearfix">
                    <div @click="addNews()">添加</div>
                    <div v-show="state!='1'?true:false" id="gray_btn">批量发布</div>
                    <div v-show="state!='0'?true:false" id="gray_btn">批量转载</div>
                    <div @click="publishArticle()" v-show="state==='1'?true:false">批量发布</div>
                    <div @click="reprintedArticles('','check')" v-show="state==='0'?true:false">批量转载</div>
                </div>
                <div class="menuSelect cb">
                    <select name="" id="" v-model="columnId" @change="getColumnList">
                        <option v-for="(item,index) in columnListArr" :value="item.id" :key="index">{{item.columnName}}</option>
                    </select>
                    <input type="text" placeholder="请输入新闻标题" id="keywords" class="fl" v-model="newsName">
                    <select name="" id="" v-model="state" @change="getColumnList()">
                        <option value="">请选择文章状态</option>
                        <option value="0">已发布</option>
                        <option value="1">未发布</option>
                    </select>
                    <el-date-picker
                        class="fl"
                        v-model="startDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder=""
                        default-time="00:00:00">
                    </el-date-picker><span class="fl" style="line-height:40px;margin: 0 10px;color:#999;">--</span>
                     <el-date-picker
                        class="fl"
                        v-model="endDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder=""
                        default-time="23:59:59">
                    </el-date-picker>
                    <div class="search_btn fl" @click="getColumnList()">查询</div>
                    <div class="sort times fl" @click="chronological()">按时间</div>
                    <div class="sort weight fl" @click="According()">前台顺序</div>
                </div>
                <div class="newsListInfo">
                    <table>
                        <tr>
                            <td width="3%"><input type="checkbox" @click="checkAll()" :checked="newsColumnList.length === checkArticles.length && checkArticles.length"></td>
                            <td width="4%">ID</td>
                            <td width="20%">标题</td>
                            <td width="10%">权重</td>
                            <td width="10%">推荐位置</td>
                            <td width="10%">日期</td>
                            <td width="10%">状态</td>
                            <td width="10%">发布人</td>
                            <td width="20%">操作</td>
                        </tr>
                        <tr v-for="(item,index) in newsColumnList" :key="index">
                            <td><input type="checkbox" :checked="checkArticles.indexOf(item.id) > -1" @click="checkArticle(item.id)"></td>
                            <td>{{index+1}}</td>
                            <td :title="item.newsName">{{item.newsName.length>16?item.newsName.substr(0,16)+'...':item.newsName}}</td>
                            <td  @dblclick="editable(item.id)"><span v-show="!item.editing">{{item.sort}}</span><input type="number" style="border:none;height:30px;background:none;max-width:100px;background: #ccc;" v-model="item.sort" v-show="item.editing" @blur="commitChanges(item,item.id,item.sort)" oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')"></td>
                            <td>{{item.newsArrposid==1?'首页新闻':'栏目新闻'}}</td>
                            <td>{{item.updateTime.substr(0,10)}}</td>
                            <!-- <td>{{item.state==1?'未发布':'已发布'}}</td> -->
                            <td>{{articleState(item.state,item.publicTime)}}</td>
                            <td>{{item.baseUser.realname}}</td>
                            <td class="buttonBox">
                                <el-button size="mini" type="primary" plain @click="editorArticle(item.id)">编辑</el-button>
                                <el-button size="mini" type="warning" plain @click="delArticle(item.id,item.quoteBannerFlag)">删除</el-button>
                                <el-button size="mini" type="primary" plain @click="reprintedArticles(item.id,'only')" v-show="item.state==1?false:true">转载</el-button>
                            </td>
                        </tr>
                    </table>
                </div>
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
        <div class="reprintBox" v-show="isShow">
            <div class="colseBtn"></div>
            <div class="reprintBoxContent">
                <div class="title">转 载</div>
                <p class="selectArticle">已选择<span>{{checkArticlesLength}}</span>篇文章</p>
                <div class="infoBox">
                    <p>
                        转载到
                        <select name="" id="platForm" v-model="reprint.platformId" >
                            <option value="">请选择平台</option>
                            <option v-for="(item,index) in platforms" :key="index" :value="item.id">{{item.platformName}}</option>
                        </select>
                    </p>
                    <p>
                        <select name="" id="" v-model="reprint.hospitalId" @change="findPlatformHospitalByColumn(reprint.platformId,reprint.hospitalId)">
                            <option value="">请选择机构</option>
                            <option v-for="(item,index) in departs" :key="index" :value="item.id">{{item.departname}}</option>
                        </select>
                    </p>
                    <p>
                        <select name="" id="" v-model="reprint.columnId">
                            <option value="">请选择栏目</option>
                            <option v-for="(item,index) in reprintColumnList" :key="index" :value="item.id">{{item.columnName}}</option>
                        </select>
                    </p>
                    <!-- <p style="line-height:40px;height:40px;">
                        *发布时间
                        <el-date-picker
                            class="fr"
                            v-model="reprint.publicTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="2018-1-1"
                            default-time="00:00:00">
                        </el-date-picker>
                    </p>
                    <p style="line-height:40px;height:40px;">
                        *过期时间
                        <el-date-picker
                            class="fr"
                            v-model="reprint.expireTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="2030-12-31"
                            default-time="11:59:59">
                        </el-date-picker>
                    </p> -->
                    <div class="btnBox">
                        <span class="sure" @click="sureReprint()">确认</span>
                        <span class="colse" @click="closeReprint()">取消</span>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
            <span>文章删除后将无法恢复，是否继续？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="sureDelArticle()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getNewsColumnType, api_getNewsColumnList, api_delArtic, api_findPlatformHospitalByColumn, api_reprintedInfo, api_reprintAjaxPost, api_publishAjax, api_commitChanges } from "../../../axios/apis.js";
    import {mapState} from 'vuex'
    export default {
        name:'newsList',
        components:{ crumbs },
        data(){
            return {
                loginInfo:'',       // 登录信息
                roleId:'',          // 角色id
                departname:'',      // 医院名称
                columnListArr:[],   // 栏目列表
                columnId:'',        // 当前栏目id
                fkPlatformId:'',    // 平台id
                hospitalList: [],   // 医院列表
                hospitalId: '',     // 医院id
                newsColumnList:[],  // 查询结果列表
                newsName:'',        // 输入的搜索关键字 只搜索 新闻名称
                state:'',           // 状态 0:发布,1:草稿
                startDate:'',       // 开始时间
                endDate:'',         // 结束时间
                currentPage:1,      // 开始页 默认第一页开始
                showCount:10,       // 每页显示条数 默认条数10
                dataOrder:'2',      // 时间排序  1:升序,2：降序  默认2
                sortOrder:'0',      // 是否按排序 进行排序 0:否, 1:是

                totalPage:null,     // 总页数
                totalResult:null,   // 总条数

                platforms:[],       // 平台列表
                departs:[],         // 医院列表
                platformList:[],
                // 是否显示转载弹框
                isShow: false,
                reprint:{
                    platformId:'',
                    hospitalId:'',
                    columnId:'',
                    publicTime:'',
                    expireTime:'2030-12-31 11:59:59',
                    newsIds:[]
                },
                // 可 转载到的平台机构下的栏目列表
                reprintColumnList:[],
                // 是否全选
                isCheckedAll:false,
                // 已勾选的文章
                checkArticles:[],
                checkArticlesLength:'',
                // 已勾选的发布内容
                newsIdJson:[],
                // 是否显示分页
                editing:false,
                newsSort:'',
                dialogVisible: false,
                currentArtId:'',
                currentArtFlag:'',
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
        computed:{
            articleState(state,time){
                return function(state,time){
                    var date = new Date().getTime();
                    var startTime = new Date(time).getTime();
                    if(state=='1'){
                        return '未发布';
                    }else{
                        return startTime>date?'待发布':'已发布';
                    }
                }
            }
        },
        mounted(){
            this.hospitalList = JSON.parse(sessionStorage.hospitalList);
            this.loginInfo = JSON.parse(sessionStorage.loginInfo);
            this.roleId = this.loginInfo.sysRoles[0].id
            this.platformList = JSON.parse(sessionStorage.platformList)
            if(!this.fkPlatformId){
                this.fkPlatformId = this.platformList[0].id;
            }
            // 初始化页面
            this.init();
            this.getCurrentTime();
        },
        methods: {
            // 初始化页面
            init:function(){
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
                // 查询栏目列表
                this.getNewsColumnType()
                // 请求平台 机构信息  用于转载
                this.reprintedInfo();
                // 请求文章列表
                this.getColumnList();
                // 获取当前医院名称
                this.gethospitalIdName();
            },
            // 获取当前时间
            getCurrentTime(){
                this.ct_month= new Date();
                // this.ct_month.setTime(this.ct_month.getTime() - 3600 * 1000 * 24 * 30);//获取上个月的日期（这一行去掉就是获取今天计算机上的日期了）
                var now = this.ct_month;
                var year = now.getFullYear(); //年
                var month = now.getMonth() + 1; //月
                var day = now.getDate(); //日
                var hh = now.getHours(); //时
                var mm = now.getMinutes(); //分
                var ss = now.getSeconds(); //秒
                var clock = year + "-";
                if(month < 10){ clock += "0"; }
                clock += month + "-";
                if(day < 10){ clock += "0"; }
                clock += day + " ";
                if(hh < 10){ clock += "0"; }
                clock += hh + ":";
                if(mm < 10){ clock += "0"; }
                clock += mm + ":";
                if(ss < 10){ clock += "0"; }
                clock += ss;
                this.reprint.publicTime = clock;
            },
            // 获取当前医院名称
            gethospitalIdName:function(){
                this.hospitalList.forEach(item => {
                    if(item.id == this.hospitalId){
                        this.departname = item.departname
                    }
                });
            },
            // 查询平台及机构下 新闻类型栏目
            getNewsColumnType:function(){
                this.columnListArr = [];
                sessionStorage.columnListArr = '';
                api_getNewsColumnType({
                    platformId: this.fkPlatformId,
                    hospitalId: this.hospitalId,
                })
                .then(res=>{
                    if (res.flag=='success') {
                        if(res.obj && res.obj.length>0){
                            this.columnListArr = res.obj
                            this.columnId = this.columnListArr[0].id;
                            sessionStorage.columnListArr = JSON.stringify(res.obj)
                        }
                        this.getColumnList()
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 获取当前平台下当前医院 的新闻列表
            getColumnList:function(){
                api_getNewsColumnList({
                    platformId: this.fkPlatformId,
                    hospitalId: this.hospitalId,
                    columnId: this.columnId,
                    sortOrder: this.sortOrder,
                    currentPage: this.currentPage,
                    showCount:this.showCount,
                    newsName: this.newsName,
                    state:this.state,
                    startDate:this.startDate,
                    endDate:this.endDate,
                    dataOrder:this.dataOrder,
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.newsColumnList = [];
                        var newsColumnList = res.obj.list;
                        newsColumnList.forEach((v,i) => {
                            v.editing = false;
                        })
                        this.newsColumnList = newsColumnList;

                        this.currentPage = res.obj.currentPage;
                        this.totalPage = res.obj.totalPage;
                        this.totalResult = res.obj.totalResult;
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 选择文章
            checkArticle:function(id){
                let idIndex = this.checkArticles.indexOf(id)
                if (idIndex >= 0) {
                    //如果已经包含就去除
                    this.checkArticles.splice(idIndex, 1)
                } else {
                    //如果没有包含就添加
                    this.checkArticles.push(id)
                }
            },
            // 全选
            checkAll:function(){
                this.isCheckedAll = !this.isCheckedAll;
                if (this.isCheckedAll) {
                    //全选时
                    this.checkArticles = []
                    this.newsColumnList.forEach(item => {
                        this.checkArticles.push(item.id)
                    })
                } else {
                    this.checkArticles = []
                }
            },
            // 新建文章
            addNews:function(){
                if(this.columnListArr&&this.columnListArr.length>0){
                    this.$router.push({path:'/home/contentManagementIndex/news/addNews', query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, hospitalId: this.hospitalId,fkPlatformId: this.fkPlatformId, departname: this.departname }})
                }else{
                    this.$message({message: '请先添加新闻类型栏目！',type: 'warning',duration: 1500});return;
                }
            },
            // 编辑文章
            editorArticle:function(id){
                this.$router.push({path:'/home/contentManagementIndex/news/addNews',query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, newsId: id, hospitalId: this.hospitalId,fkPlatformId: this.fkPlatformId, departname: this.departname }});
            },
            // 删除文章 提示是否删除
            delArticle:function(id,Flag){
                var _this = this;
                if(Flag==1){
                    this.$confirm('该新闻已与banner进行关联,是否继续删除?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    })
                    .then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        _this.delArtic()
                    })
                    .catch(() => {
                        // this.$message({
                        //     type: 'info',
                        //     message: '已取消删除'
                        // });
                    });
                }else{
                    this.dialogVisible = true;
                    this.currentArtId = id;
                    this.currentArtFlag = Flag;
                }
            },
            // 删除提示
            sureDelArticle(id,Flag){
                this.delArtic(this.currentArtId,this.currentArtFlag)
                this.dialogVisible = false;
                this.currentArtId = '';
                this.currentArtFlag = '';
            },
            // 删除文章方法
            delArtic:function (id,Flag){
                api_delArtic({
                    newsId: id,
                    bannerFlag: Flag,
                })
                .then(res=>{
                    this.$message({
                        type: res.flag=='success'?'success':'error',
                        message: res.msg
                    });
                    if(res.flag=='success'){
                        this.getColumnList();
                    }
                })
                .catch(error=>console.log(error))
            },
            // 根据平台机构 查询栏目  用于文章转载
            findPlatformHospitalByColumn:function(platformId,hospitalId){
                api_findPlatformHospitalByColumn({
                    platformId: platformId,
                    hospitalId: hospitalId,
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.reprintColumnList = [];
                        let reprintColumnList = res.obj;
                        reprintColumnList.forEach((item,index)=>{
                            if(item.columnId == this.columnId){
                                reprintColumnList.splice(index,1)
                            }
                        })
                        this.reprintColumnList = reprintColumnList;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 请求转载相关信息
            reprintedInfo:function(){
                api_reprintedInfo({
                    roleId: this.roleId
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.departs = res.obj.departs;
                        this.platforms = res.obj.platforms;
                    }
                })
                .catch(error=>console.log(error))
            },
            // 清空当前选择的目标平台和机构等信息
            resetReprint(){
                this.reprint.platformId = '';
                this.reprint.hospitalId = '';
                this.reprint.columnId = '';
                this.reprint.newsIds = []
            },
            // 取消转载
            closeReprint:function(){
                this.isShow = false;
                this.resetReprint();
            },
            // 转载选择 平台 机构 栏目
            reprintedArticles:function(id,type){
                if(type == 'only' && id){
                    this.checkArticles = [];
                    this.checkArticle(id);
                }
                this.checkArticles.forEach(item => {
                    this.reprint.newsIds.push({'newsId':item})
                });
                this.checkArticlesLength = this.checkArticles.length;
                if(this.checkArticlesLength > 0){
                    this.isShow = true;
                }else{
                    this.$message({message: '请选择需要转载的文章',type: 'warning',duration: 1500});return;
                }
            },
            // 转载事件
            sureReprint:function(){
                if(!this.reprint.platformId){ this.$message({message: '请选择平台',type: 'warning',duration: 1500});return;}
                if(!this.reprint.hospitalId){ this.$message({message: '请选择机构',type: 'warning',duration: 1500});return; }
                if(!this.reprint.columnId){ this.$message({message: '请选择栏目',type: 'warning',duration: 1500});return; }
                // if(!this.reprint.publicTime){ this.$message({message: '请选择发布时间',type: 'warning',duration: 1500});return; }
                this.reprintAjaxPost();
            },
            // 提交转载内容
            reprintAjaxPost:function(){
                api_reprintAjaxPost({
                    newsJson: JSON.stringify(this.reprint)
                })
                .then(res=>{
                    this.$message({
                        type: res.flag=='success'?'success':'error',
                        message: res.msg
                    });
                    if(res.flag=='success'){
                        this.checkArticles = [];
                        this.isShow = false;
                        this.resetReprint();
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 文章发布
            publishArticle:function(){
                this.checkArticles.forEach(item => {
                    this.newsIdJson.push({'newsId':item})
                });
                this.checkArticlesLength = this.checkArticles.length;
                if(this.checkArticlesLength > 0){
                    this.publishAjax();
                }else{
                    this.$message({message: '请选择需要发布的文章',type: 'warning',duration: 1500});return;
                }
            },
            // 发布请求
            publishAjax:function(){
                api_publishAjax({
                    newsIdJson: JSON.stringify(this.newsIdJson)
                })
                .then(res=>{
                    this.$message({
                        type: res.flag=='success'?'success':'error',
                        message: res.msg
                    });
                    if(res.flag=='success'){
                        this.checkArticles = [];
                        this.getColumnList()
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 按时间排序
            chronological:function(){
                this.dataOrder = this.dataOrder == '2'?'1':'2';
                this.sortOrder = '0';
                this.getColumnList();
            },
            // 按前端显示排序
            According:function(){
                this.sortOrder = '1';
                this.dataOrder = '';
                this.getColumnList()
            },
            // 翻页
            handleCurrentChange(num) {
                this.currentPage = num;
                this.getColumnList()
            },
            // 权重修改
            editable:function(id){
                this.newsColumnList.forEach((v,i) => {
                    if(v.id == id){
                        v.editing = true;
                    }
                })
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
                this.newsColumnList.forEach((v,i) => {
                    if(v.id == id){
                        v.editing = false;
                    }
                })
                api_commitChanges({
                    newsId: id,
                    sort: sort
                })
                .then(res=>{
                    this.$message({
                        type: res.flag=='success'?'success':'error',
                        message: res.msg
                    });
                    if(res.flag=='success'){
                        this.checkArticles = [];
                        this.getColumnList();
                    }
                })
                .catch(error=>console.log(error))
            },
            // 弹出层
            handleClose(done) {
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            }
        }
    }
</script>

<style scoped>
    .newsListBox{
        width: 100%;
        height: 100%;
    }
    .platformInfo{
        text-align: left;
        height: 30px;
        line-height: 30px;
        margin-bottom: 20px;
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
    .functionModule{
        margin-bottom: 10px;
    }
    .functionModule div{
        float: left;
        width: 120px;
        height: 36px;
        background-color: #6b61f2;
        border-radius: 6px;
        font-size: 16px;
        line-height: 36px;
        text-align: center;
        color: #fff;
        margin-right: 8px;
        cursor: pointer;
    }
    .functionModule div:hover{
        background-color: #599bee;
    }
    .functionModule div.on{
        background-color: #599bee;
    }
    .menuSelect{
        margin-bottom: 15px;
        height: 40px;
    }
    .menuSelect select,#keywords{
        float: left;
        width: 136px;
        height: 40px;
        margin-right: 15px;
        border: 1px solid #ccc;
        font-size: 12px;
        color: #333;
        border-radius: 4px;
    }
    #keywords{
        padding: 0 5px;
    }
    .search_btn,.sort{
        width: 80px;
        height: 40px;
        background-color: #9992fd;
        border-radius: 6px;
        font-size: 14px;
        line-height: 40px;
        color: #fff;
        cursor: pointer;
        margin-left: 10px;
    }
    .sort{
        width: 102px;
        padding-right: 26px;
        background-repeat: no-repeat;
        background-size: 16px 12px;
        background-position: 70px center;
        background-image: url(../../../assets/images/sort.png);
    }
    .newsListInfo{
        max-height: 470px;
    }
    .newsList{
        min-width: 1250px;
    }
    .newsList td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 18px;
        margin: 0;
    }
    .newsList table{
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        border-spacing: 0;
        font-size: 14px;
        color: #333333;
        width: 100%;
        min-width: 900px;
        text-align: left;
    }
    .newsList tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }
    .newsList tr:hover{
        background-color: #eeedfc;
    }
    .newsList tr:first-child{
        background-color: #eeedfc;
    }
    .newsList tr td:first-child,.newsList tr td:nth-child(2){
        text-align: center;
    }
    .reprintBox{
        position: fixed;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin: auto;
        background: rgba(0, 0, 0, 0.5);
        width: 100%;
        height: 100%;
    }
    .colseBtn{
        width: 100%;
        height: 100%;
    }
    .reprintBoxContent{
        width: 500px;
        height: 400px;
        background: #fff;
        border-radius: 6px;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin: auto;
        overflow: hidden;
    }
    .title{
        width: 100%;
        height: 50px;
        background: #29a2fb;
        line-height: 50px;
        text-align: center;
        font-size: 18px;
        color: #fff;
    }
    .selectArticle{
        font-size: 14px;
        line-height: 40px;
        margin: 12px;
    }
    .selectArticle span{
        padding: 0 10px;
        color: #29a2fb;
    }
    .infoBox{

    }
    .infoBox p{
        font-size: 14px;
        width: 300px;
        height: 30px;
        margin: auto;
        text-align: left;
        line-height: 30px;
        margin-bottom: 8px;
    }
    .infoBox select{
        width: 150px;
        height: 30px;
        float: right;
        border-color: #ddd;
    }
    .infoBox p:nth-child(1) select{
        width: 250px;
    }
    .infoBox p:nth-child(2) select{
        width: 200px;
    }
    .infoBox p:nth-child(2){
        background-image: url(../../../assets/images/zz.png);
        background-repeat: no-repeat;
        background-position: 20px -12px;
    }
    .infoBox p:nth-child(3){
        background-image: url(../../../assets/images/zz.png);
        background-repeat: no-repeat;
        background-position: 70px -12px;
    }
    .btnBox{
        width: 100%;
        padding-top: 20px;
        width: 300px;
        height: 40px;
        margin: auto;
    }
    .btnBox span{
        float: right;
        width: 80px;
        height: 30px;
        line-height: 30px;
        border-radius: 4px;
        background-color: #e5e3e3;
        color: #666666;
        font-size: 14px;
        cursor: pointer;
    }
    .btnBox .sure{
        background-color: #29a2fb;
        color: #fff;
        margin-left: 30px;
    }
</style>