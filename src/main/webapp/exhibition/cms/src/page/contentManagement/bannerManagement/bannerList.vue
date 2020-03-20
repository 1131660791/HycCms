<template>
    <div class="bannerListBox">
        <crumbs></crumbs>
        <div class="platformInfo">
            <span>机构选择</span>
            <select name="" id="platformInfo" v-model="hospitalId" @change="getBannerColumnList()">
                <!-- <option value="">请选择</option> -->
                <option v-for="item in hospitalList" :value="item.id" :key="item.id">{{item.departname}}</option>
            </select>
        </div>
        <div class="bannerList" >
            <form action="">
                <!-- <div class="menuSelect cb">
                    <input type="text" placeholder="请输入栏目名称" class="fl" v-model="columnName">
                    <input type="text" placeholder="请输入主题" class="fl" v-model="bannerTheme">
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
                    <div class="search_btn fl" @click="init()">查询</div>
                </div> -->
                <div>
                    <table>
                        <tr>
                            <td width="50px">ID</td>
                            <td width="16%">栏目</td>
                            <td width="12%">主题</td>
                            <td width="16%">所属机构</td>
                            <td width="12%">更新时间</td>
                            <td width="10%">banner数量</td>
                            <td width="12%">发布人</td>
                            <td width="18%">操作</td>
                        </tr>
                    </table>
                </div>
                <div v-show="bannerListArr.length>0">
                    <table style="border-top:none;">
                        <tr>
                            <td style="padding:0;border:none;">
                                <treeBannerList
                                    v-for="(item,index) in bannerListArr"
                                    :key="index"
                                    :item="item"
                                    :num="index"
                                    @addBtn="addBtn"
                                    @preview="preview"
                                    @clickState="clickState"
                                    @sureDelBanner="sureDelBanner"
                                ></treeBannerList>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
        <div class="bannerRunBox" v-show="isShow">
            <div class="banner_content">
                <div class="colseBtn" @click="isShowBtn()"><i></i><i></i></div>
                <el-carousel indicator-position="outside">
                    <el-carousel-item v-for="item in bannerContents" :key="item.id">
                        <div class="imgUrl">
                            <img :src="item.bannerUrl" alt="">
                        </div>
                    </el-carousel-item>
                </el-carousel>
            </div>
        </div>
        <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
            <span>将删除该栏目下banner内容，是否继续？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delBanner()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import treeBannerList from "./treeBannerList";
    import { api_getBannerColumnList, api_findBannerInfo, api_delBannerColumn } from "../../../axios/apis.js";
    export default {
        name:'bannerList',
        components:{ crumbs,treeBannerList },
        data(){
            return {
                bannerListArr:[],
                hospitalList:[],        // 医院列表
                fkPlatformId:'',        // 平台id
                hospitalId:'',          // 医院id
                departname:'',          // 医院名称
                columnName:'',          // 栏目名称
                bannerTheme:'',         // 主题名称
                startDate:'',           // 开始时间
                endDate:'',             // 结束时间
                isShow:false,           // 预览弹出  默认false
                bannerContents:[],
                dialogVisible : false,
                currentBannerId : '',
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
            // this.hospitalId = this.hospitalList.length>0?this.hospitalList[0].id:'';
            // this.departname = this.hospitalList.length>0?this.hospitalList[0].departname:'';
            this.init();

        },
        methods:{
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

                // 获取当前医院名称
                this.gethospitalIdName();
                // 栏目查询
                this.getBannerColumnList();
            },
            // 查询banner栏目
            getBannerColumnList:function(){
                this.bannerListArr = [];
                api_getBannerColumnList({
                    platformId: this.fkPlatformId,
                    hospitalId: this.hospitalId,
                    columnName: this.columnName,          // 栏目名称
                    bannerTheme: this.bannerTheme,         // 主题名称
                    startDate: this.startDate,           // 开始时间
                    endDate: this.endDate,             // 结束时间
                })
                .then(res=>{
                    if(res.flag=='success'){
                        let _bannerListArr = res.obj;
                        this.tableDataDispose(_bannerListArr);
                        if(_bannerListArr.length>0){
                            this.bannerListArr.push(_bannerListArr[0])
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
                        if(item.columnBannerVos&&item.columnBannerVos.length){
                            this.tableDataDispose(item.columnBannerVos)
                        }
                    })
                }
            },
            // 数据状态修改
            clickState(item){
                item.showState = !item.showState;
                item.show = item.show=='+'?'-':'+';
            },
            // 获取当前医院名称
            gethospitalIdName:function(){
                this.hospitalList.forEach(item => {
                    if(item.id == this.hospitalId){
                        this.departname = item.departname
                    }
                });
            },
            // 添加banner
            addBtn:function(id){
                this.showStatus = false
                this.$router.push({path:'/home/contentManagementIndex/banner/addBanner',query:{  currentMenuId:this.$route.query.currentMenuId,currentMenuIndex:this.$route.query.currentMenuIndex, currentSideIndex:this.$route.query.currentSideIndex, currentSideSecondIndex:this.$route.query.currentSideSecondIndex, hospitalId: this.hospitalId,fkPlatformId: this.fkPlatformId, departname: this.departname, fkColumnId:id }})
            },
            // 删除banner
            delBanner(){
                api_delBannerColumn({columnId:this.currentBannerId})
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'error',
                        duration: 1500
                    })
                    this.dialogVisible = false;
                    this.getBannerColumnList();
                })
            },
            // 删除提示
            sureDelBanner(id){
                this.currentBannerId = id;
                this.dialogVisible = true;
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            },
            // 关闭预览
            isShowBtn:function(){
                this.isShow = false;
            },
            // 展开预览
            preview:function(id){
                this.findBanner(id)
                this.isShow = true;
            },
            // 查询banner
            findBanner:function(id){
                api_findBannerInfo({
                    platformId:this.fkPlatformId,
                    hospitalId:this.hospitalId,
                    columnId:id
                })
                .then(res => {
                    if (res.flag=='success') {
                        this.bannerContents = res.obj.bannerContents;
                    }
                })
                .catch(error=>console.log(error))
            },
        }
    }
</script>

<style scoped>
    .bannerListBox {
        height: 100%;
        width: 100%;
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


    .bannerList {
        width: 100%;
        height: calc( 100% - 60px);
    }
    .bannerList form{
        height: calc( 100% - 90px);
    }
    .bannerList td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding: 0 18px;
        margin: 0;
    }
    .bannerList table{
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
    }
    .bannerList tr:nth-child(2n+1){
        background-color: #f1f1f1;
    }
    .bannerList tr:hover{
        background-color: #eeedfc;
    }
    .bannerList tr:first-child{
        background-color: #eeedfc;
    }
    .bannerList tr td:first-child,.bannerList tr td:nth-child(2){
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
    .menuSelect{
        margin-bottom: 15px;
        height: 40px;
    }
    .menuSelect input{
        float: left;
        width: 112px;
        height: 40px;
        margin-right: 15px;
        padding-left: 5px;
        border: 1px solid #ccc;
        font-size: 1px;
    }
    .menuSelect input{
        width: 160px;
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

        /* banner轮播预览 */
    .bannerRunBox{
        position:fixed;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin: auto;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.8);
    }
    .banner_content{
        position:absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin:auto;
        width: 1100px;
        height: 400px;
        /* background: #fff; */
    }

    .el-carousel__item{
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }
    .el-carousel__item {
        background-color: #ccc;
    }
    .banner_content .imgUrl{
        width: 100%;
        height: 100%;
        line-height:400px;
        position:relative;
    }
    .banner_content img{
        position:absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin:auto;
        width: 100%;
        vertical-align: middle;
    }
    .colseBtn{
        cursor: pointer;
        width: 30px;
        height: 30px;
        right: -30px;
        top: -30px;
        background: #ccc;
        border-radius: 50%;
        position: absolute;
    }
    .colseBtn i{
        display: block;
        width: 20px;
        height: 2px;
        background: #000;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        margin: auto;
    }
    .colseBtn i:nth-child(1){
        transform: rotateZ(45deg)
    }
    .colseBtn i:nth-child(2){
        transform: rotateZ(-45deg)
    }

</style>