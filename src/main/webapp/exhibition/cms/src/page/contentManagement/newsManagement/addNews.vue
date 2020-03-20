<template>
    <div class="addNews">
        <crumbs></crumbs>
        <div class="newsInfo ">
            <div class="newsInfoCon clearfix">
                <div class="newsContent fl">
                    <!-- <div class="title" style="margin-left:10px;">
                        <i></i>新闻内容
                    </div> -->
                    <div class="newsContentLeft infoBox">
                        <div class="title minTitle">
                            <!-- <i style="width:6px;height:6px;margin-right:10px;"></i> -->
                            <span style="color:#c02;">*</span>
                            栏目
                        </div>
                        <select name="" id="column" v-model="columnId">
                            <option v-for="item in newsColumnList" :value="item.id" :key="item.id">{{item.columnName}}</option>
                        </select>
                        <div class="title minTitle">
                            <!-- <i style="width:6px;height:6px;margin-right:10px;"></i> -->
                            <span style="color:#c02;">*</span>
                            标题
                        </div>
                        <input type="text" placeholder="请输入文章标题 最多40个字符" id="theme" v-model="newsName" maxlength="40">
                        <div class="descInfo clearfix">
                            <p class="fl"><span style="color:#c02;">*</span>作者</p><p class="fr">关键词</p>
                            <input type="text" class="fl" placeholder="作者名称 最多8个字符" v-model="newsWriter" maxlength="8">
                            <input type="text" class="fr" placeholder="请输入关键词 最多30个字符" v-model="newsKeyword" maxlength="30">
                        </div>
                        <div class="title minTitle">
                            <!-- <i style="width:6px;height:6px;margin-right:10px;"></i> -->
                            来源
                        </div>
                        <input type="text" placeholder="请输入文章来源 最多40个字符" id="sourceInfo" v-model="newsSource" maxlength="40">
                        <div class="contentText">
                            <div class="thumbnail">
                                <p>封面图</p>
                                <div class="infobox clearfix">
                                    <div class="imgBox fl" style="overflow:hidden">
                                        <el-upload
                                            class="avatar-uploaderBox"
                                            action="/HycCms/fileOperate/fileUpload.do"
                                            :show-file-list="false"
                                            :on-success="handleAvatarSuccess"
                                            :before-upload="beforeAvatarUpload">
                                            <img v-if="coverLink" :src="coverLink" class="avatar">
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                    </div>
                                    <div class="fl funBox">
                                        <el-button type="danger" plain  @click="delImg()">删除</el-button>
                                        <!-- <p>重传</p> -->
                                    </div>
                                </div>
                            </div>
                            <div class="desc">
                                <p>摘要</p>
                                <div class="infobox">
                                    <textarea name="" id="infobox" cols="30" rows="10" v-model="newsDigest" placeholder="请输入文章摘要内容 最多120个字符" maxlength="120"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="title minTitle">
                            <!-- <i style="width:6px;height:6px;margin-right:10px;"></i> -->
                            <span style="color:#c02;">*</span>
                            正文内容
                        </div>
                        <div class="contentBox">
                            <editor ref="editorInfo" v-on:listenChildEvent="showMessage" :newsContent="newsContent"></editor>
                        </div>
                    </div>
                </div>
                <div class="newsSet fr">
                    <!-- <div class="title">
                        <i style="background-color:#29a2fb;"></i>发布设置
                    </div> -->
                    <div class="newsContentRight infoBox cb">
                        <p>发布时间</p>
                        <el-date-picker
                            v-model="publicTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder=""
                            default-time="00:00:00"
                            :picker-options="startDateOpt"
                        >
                        </el-date-picker>
                        <p>过期时间</p>
                        <el-date-picker
                            v-model="expireTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="2030-12-31"
                            default-time="23:59:59"
                            :picker-options="endDateOpt"
                        >
                        </el-date-picker>
                        <p>权重设置</p>
                        <input type="number" placeholder="请输入数字 设置文章权重" v-model="sort" oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')">
                        <p>推荐位置</p>
                        <div class="clearfix">
                            <label for="home" class="address_box fl" style="margin-right:30px;"><input type="radio" id="home" class="fl" name="address" value="1" v-model="newsArrposid">首页新闻</label>
                            <div class="columnOption fl">
                                <label for="columnBox" class="fl address_box"><input type="radio" id="columnBox" name="address" value="2" v-model="newsArrposid">栏目新闻</label>
                                <!-- <select name="" id="" class="fl" v-model="newsArrposid">
                                    <option v-for="item in newsColumnList" :value="item.id" :key="item.id">{{item.columnName}}</option>
                                </select> -->
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <!-- <div class=""></div> -->
            <div class="foo_btn">
                <div class="saveDraft" @click="cancel()">取消</div>
                <div class="saveDraft" @click="saveDraftBtn()">存草稿</div>
                <div class="preview" @click="previewPage()" v-show="!!newsId&&newsContent.length>0">预览</div>
                <div class="newsSave" @click="issueBtn()">发布</div>
            </div>
        </div>
        <div class="previewPageBox" v-show="isPreview">
            <div class="previewPageBoxContent">
                <div class="colseBtn" @click="colsePreview"><i></i><i></i></div>
                <div>
                    <div class="art_title">{{artic.newsName}}</div>
                    <p class="articleInfo">
                        <span>作者：{{artic.newsWriter}}</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span style="font-size:14px;">发布日期：{{artic.updateTime.substr(0,10)}}</span>
                    </p>
                    <p class="articleSource">文章来源：{{artic.newsSource}}</p>
                    <div class="art_content" v-html="artic.newsContent"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import editor from "../../../components/editor";
    import { api_getArticleInfo, api_submitNewsInfo } from "../../../axios/apis.js";
    export default {
        name:'addNews',
        components:{ crumbs, editor },
        data(){
            return {
                newsColumnList:[],
                columnId:'',        // 关联栏目id
                fkPlatformId:'',    // 关联平台id
                hospitalId:'',      // 医院Id
                departname:'',      // 所属机构名称
                newsArrposid:'2',   // 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻
                newsName:'',        // 标题
                newsWriter:'',      // 作者
                newsKeyword:'',     // 关键词
                newsSource:'',      // 来源
                coverLink:'',       // 封面图路径
                newsDigest:'',      // 摘要
                newsContent:'',     // 新闻内容
                publicTime:'',      // 发布时间
                expireTime:'2030-12-30 23:59:59',      // 过期时间
                sort:'0',            // 权重
                state:'',           // 新闻状态,0:发布,1:草稿
                saveDraft:'1',      // 保存
                issue:'0',          // 发布
                dialogVisible: false,       //
                isPreview: false,       //是否预览
                newsId:'',
                artic:{
                    newsName:'',
                    updateTime:'',
                    newsContent:'',
                    newsWriter:'',
                    newsSource:'',
                },
                startDateOpt:{},
                endDateOpt:{},
            }
        },
        created(){
            this.fkPlatformId = this.$route.query.fkPlatformId;
            this.hospitalId = this.$route.query.hospitalId;
            this.departname = this.$route.query.departname;
            this.newsId = this.$route.query.newsId;
        },
        mounted:function(){
            this.newsColumnList = JSON.parse(sessionStorage.columnListArr)
            this.columnId = this.newsColumnList[0].id;
            this.initPageInfo();
            if (!this.newsId) { this.getCurrentTime() };
        },
        methods:{
            initPageInfo:function(){
                if(!this.newsId){return;}
                this.getArticleInfo()
            },
            // 取消
            cancel(){
                this.$router.go(-1);
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
                this.publicTime = clock;
            },
            // 接收子组件传等内容
            showMessage:function(data){
                this.newsContent = data;
            },
            // 缩略图删除
            delImg:function(){
                this.coverLink = '';
            },
            // 请求文章内容
            getArticleInfo:function(){
                let _this = this;
                // 发起请求
                api_getArticleInfo({
                    newsId:this.newsId
                })
                .then(res => {
                    if (res.flag=='success') {
                        this.artic.newsName = this.newsName = res.obj.newsName;
                        this.artic.newsContent = this.newsContent = res.obj.newsContent;
                        this.artic.updateTime = res.obj.updateTime;
                        this.artic.newsWriter = this.newsWriter = res.obj.newsWriter;
                        this.artic.newsSource = this.newsSource = res.obj.newsSource;
                        this.departname = res.obj.departname;
                        this.newsArrposid = res.obj.newsArrposid;
                        this.newsKeyword = res.obj.newsKeyword;
                        this.coverLink = res.obj.coverLink;
                        this.newsDigest = res.obj.newsDigest;
                        this.sort = res.obj.sort;
                        this.publicTime = res.obj.publicTime;
                        this.expireTime = res.obj.expireTime;
                        this.columnId = res.obj.column.id;
                    }else{
                        this.getCurrentTime();
                    }
                })
                .then(()=>{
                    // 开始时间
                    this.startDateOpt.disabledDate = function(time){
                        // 设置可选择的日期为 当前时间到结束时间之间的时间
                        return time.getTime() < Date.now() - 8.64e7 || time.getTime() > new Date(_this.expireTime).getTime();
                    }
                    // 结束时间
                    this.endDateOpt.disabledDate = function(time){
                        // 设置可选择的日期为 当前时间到结束时间之间的时间
                        return time.getTime() < new Date(_this.publicTime).getTime();
                    }
                })
                .catch(error=>console.log(error))
            },
            // 预览
            previewPage:function(){
                this.getArticleInfo()
                this.isPreview = true;
            },
            // 关闭预览
            colsePreview:function(){
                this.isPreview = false;
            },

            // 存草稿
            saveDraftBtn:function(){
                this.state = this.saveDraft;
                this.postNewsData()
            },
            // 立即发布
            issueBtn:function(){
                this.state = this.issue;
                this.postNewsData()
            },
            postNewsData(){
                if(this.newsId){
                    this.submitNewsInfo('/HycCms/news/updateNews.do',{
                        id:this.newsId,
                        fkColumnId:this.columnId,        // 关联栏目id
                        newsArrposid:this.newsArrposid,   // 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻
                        newsName:this.newsName,        // 标题
                        newsWriter:this.newsWriter,      // 作者
                        newsKeyword:this.newsKeyword,     // 关键词
                        newsSource:this.newsSource,      // 来源
                        coverLink:this.coverLink,       // 封面图路径
                        newsDigest:this.newsDigest,      // 摘要
                        newsContent:this.newsContent,     // 新闻内容
                        publicTime:this.publicTime,      // 发布时间
                        expireTime:this.expireTime,      // 过期时间
                        sort:this.sort,            // 权重
                        state:this.state,           // 新闻状态,0:发布,1:草稿
                    })
                }else{
                    this.submitNewsInfo('/HycCms/news/addNews.do',{
                        fkColumnId:this.columnId,        // 关联栏目id
                        fkPlatformId:this.fkPlatformId,    // 关联平台id
                        hospitalId:this.hospitalId,      // 医院Id
                        departname:this.departname,      // 所属机构名称
                        newsArrposid:this.newsArrposid,   // 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻
                        newsName:this.newsName,        // 标题
                        newsWriter:this.newsWriter,      // 作者
                        newsKeyword:this.newsKeyword,     // 关键词
                        newsSource:this.newsSource,      // 来源
                        coverLink:this.coverLink,       // 封面图路径
                        newsDigest:this.newsDigest,      // 摘要
                        newsContent:this.newsContent,     // 新闻内容
                        publicTime:this.publicTime,      // 发布时间
                        expireTime:this.expireTime,      // 过期时间
                        sort:this.sort,            // 权重
                        state:this.state,           // 新闻状态,0:发布,1:草稿
                    })
                }
            },
            // 提交信息
            submitNewsInfo:function(url,data){
                // 验证表单信息
                if (!data.fkColumnId) { this.$message({message: '请选择栏目',type: 'warning',duration: 1500});return;}
                if (!data.newsName) { this.$message({message: '请填写文章标题',type: 'warning',duration: 1500});return;}
                if (!data.newsWriter) { this.$message({message: '请填写作者名称',type: 'warning',duration: 1500});return;}
                if (!data.newsContent) { this.$message({message: '请填写新闻内容',type: 'warning',duration: 1500});return;}
                // 发起请求
                api_submitNewsInfo(url,data)
                .then(res => {
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if (res.flag == 'success') { setTimeout(() => { this.$router.go(-1) }, 1500) }
                })
                .catch(error=>console.log(error))
            },
            handleAvatarSuccess(res, file) {
                this.coverLink = res.obj[0].ossUrl;
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.$message.error('文章缩略图只能是 jpg/png 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('文章缩略图大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        }
    }
</script>

<style scoped>
    .addNews {
        width: 100%;
        height: 100%;
        padding-right: 12px;
        padding-left: 2px;
        overflow: scroll;
    }

    .newsInfo {
        width: 100%;
    }
    .title {
        text-align: left;
        font-size: 16px;
        color: #333333;
        height: 20px;
        line-height: 20px;
        margin-bottom: 15px;
    }
    .title i {
        display: block;
        float: left;
        width: 8px;
        height: 8px;
        margin-top: 6px;
        border-radius: 50%;
        background-color: #9992fd;
        margin-right: 22px;
    }
    .minTitle{
        margin-bottom: 5px;
    }
    .newsContent{
        width: 60%;
    }
    .newsSet{
        width: calc( 40% - 15px );
    }
    .newsContentLeft{
        padding-left: 10px;
        padding-right: 20px;
        padding-bottom: 20px;
    }
    .newsContentRigth{
        width: 40%;
    }
    .infoBox{
        box-shadow: 0 0 8px #ddd;
        padding-top: 10px;
    }
    #column{
        width: 100%;
        height: 40px;
        line-height: 40px;
        border-color: #ccc;
        margin-bottom: 10px;
    }
    .min-title{
        height: 45px;
        line-height: 45px;
        font-size: 14px;
        color: #666666;
        text-align: left;
    }
    #keywords,#theme,#newsName,#sourceInfo{
        width: 100%;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        color: #333;
        text-align: left;
        border: 1px solid #ccc;
        padding: 0 5px;
    }
    .descInfo{
        width: 100%;
        margin-top: 10px;
        margin-bottom: 10px;
    }
    .descInfo p{
        width: 48%;
        text-align: left;
        font-size: 14px;
        line-height: 30px;
    }
    .descInfo input{
        width: 48%;
        height: 40px;
        line-height: 48px;
        border: 1px solid #ccc;
        font-size: 14px;
        padding: 0 5px;
    }
    .contentText{
        margin-bottom: 15px;
    }
    .contentText>div{
        width: 48%;
    }
    .contentText>div.desc{
        width: 100%;
    }
    .contentText>div>p{
        text-align: left;
        height: 40px;
        line-height: 40px;
    }

    .infobox{
        width: 100%;
        height: 180px;
    }
    .imgBox{
        width: 180px;
        height: 180px;
        position: relative;
        background-color: #f0f0f0;
        border: 1px solid #ccc;
    }
    .funBox{
        width: 80px;
        font-size: 14px;
        margin-left: 15px;
        padding-top: 140px;
    }
    .funBox p{
        line-height: 30px;
        cursor: pointer;
    }
    .imgBox img{
        /* width: 100%; */
        /* vertical-align: middle; */
    }
    .addbtn{
        display: block;
        width: 50px;
        height: 80px;
        margin: 0 auto;
        background-image: url(../../../assets/images/add_icon.png);
        background-size: 100% auto;
        background-position: center;
        background-repeat: no-repeat;
    }
    .imgBox p{
        line-height: 40px;
        font-size: 14px;
        color: #666;
    }
    #image-file{
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 1;
        opacity: 0;
        width: 100%;
        height: 100%;
    }

    #infobox{
        width: 100%;
        height: 100%;
        border: 1px solid #ccc;
        resize: none;
        padding: 5px;
        font-size: 14px;
    }

    .newsSet {
        height: 100%;
    }
    .newsContentRight{
        height: 100%;
        text-align: left;
        padding-left: 15px;
        padding-bottom: 15px;
        padding-right: 10px;
        box-sizing: border-box;
    }
    .newsContentRight p{
        height: 40px;
        line-height: 40px;
        font-size: 14px;
    }
    .newsContentRight input{
        height: 40px;
        line-height: 40px;
        border: 1px solid #ccc;
        padding: 0 5px;
        width: 100%;
    }

    .savenewsInfo{
        height: 22px;
        width: 40px;
        line-height: 22px;
        font-size: 14px;
        background: #4c48e8;
        border-radius: 4px;
        color: #fff;
        float: right;
        margin-top: 20px;
        cursor: pointer;
    }

    .foo_btn{
        width: 100%;
        margin: 30px 0;
        text-align: center;
    }
    .foo_btn div{
        width: 120px;
        height: 38px;
        border-radius: 4px;
        text-align: center;
        line-height: 38px;
        font-size: 14px;
        display: inline-block;
        background-color: #9992fd;
        color: #fff;
        cursor: pointer;
        margin: 0 12px;
    }
    .foo_btn div:hover{
        background-color: #29a2fb;
    }

    .contentBox{
        height: 580px;
    }
    .newsContentRight input{
        font-size: 14px;
    }
    #home,#columnBox{
        margin-right: 10px;
        margin-top: 8px;
        height: 14px;
        width: 14px;
        float: left;
    }
    .address_box{
        font-size: 14px;
        line-height: 30px;
        margin-bottom: 10px;
        display: block;
    }
    .columnOption select{
        display: block;
        margin-left: 20px;
        height: 30px;
        width: 180px;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        width: 100%;
        height: 100%;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 180px;
        height: 180px;
        display: block;
    }

    /* 预览页面 */
    .previewPageBox{
        position: fixed;
        z-index: 100;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        margin:auto;
        background: rgba(0, 0, 0, 0.8);
    }
    .colseBtn{
        cursor: pointer;
        width: 30px;
        height: 30px;
        right: 5px;
        top: 15px;
        background: #000;
        border-radius: 50%;
        position: absolute;
    }
    .colseBtn i{
        display: block;
        width: 20px;
        height: 2px;
        background: #ccc;
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
    .previewPageBoxContent{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        width: 1200px;
        margin: 25px auto;
        max-height: calc( 100% - 50px);
        background: #f5f5f5;
        /* border-radius: 4px; */
        overflow: scroll;
        text-align: left;
        padding: 15px 12px;
    }
    .art_title{
        font-size: 16px;
        line-height: 24px;
        max-width: 80%;
        font-weight: bold;
        text-align: center;
    }
    .articleInfo{
        margin-top: 15px;
    }
    .articleInfo span{
        line-height: 24px;
    }
    .articleSource{
        font-style: italic;
        font-size: 14px;
        color: #000;
        margin: 5px 0;
        border-bottom: 1px dashed #999;
        padding-bottom: 12px;
        margin-bottom: 20px;
    }
    .art_content >>> p{
        line-height: 24px;
        font-size: 14px;
        margin-bottom: 10px;
        text-align: justify;
    }
    .art_content >>> img{
        width: 100%;
    }











</style>