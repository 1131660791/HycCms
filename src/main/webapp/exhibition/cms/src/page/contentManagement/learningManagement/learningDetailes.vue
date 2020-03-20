<template>
    <div class="addNews">
        <crumbs></crumbs>
        <div class="newsInfo ">
            <div class="newsInfoCon clearfix">
                <div class="newsContent fl">
                    <div class="newsContentLeft infoBox">
                        <div class="title minTitle"><span style="color:#c02;">*</span>栏目</div>
                        <select name="" id="column" v-model="fileInfo.fkColumnId">
                            <option v-for="item in fileColumnList" :value="item.id" :key="item.id">{{item.columnName}}</option>
                        </select>
                        <div class="title minTitle"><span style="color:#c02;">*</span>文件名称</div>
                        <input type="text" placeholder="请输入标题 最多40个字符" id="theme" v-model="fileInfo.materialsName" maxlength="40">
                        <div class="title minTitle" style="margin-top:10px;"><span style="color:#c02;">*</span>主讲人</div>
                        <input type="text" placeholder="请输入标题 最多10个字符" id="theme" v-model="fileInfo.speechmaker" maxlength="10">
                        <div class="title minTitle" style="margin-top:10px;"><span></span>所在科室</div>
                        <select name="" id="sectionBox" v-model="fileInfo.sectionId" @change="getDepartname(fileInfo.sectionId)">
                            <option value="">请选择科室</option>
                            <option v-for="(item,index) in department" :value="item.id">{{item.departname}}</option>
                        </select>
                        <div class="contentText">
                            <div class="thumbnail">
                                <p>封面图</p>
                                <div class="infobox clearfix">
                                    <div class="imgBox fl" style="overflow:hidden">
                                        <el-upload
                                            class="avatar-uploader"
                                            action=""
                                            :show-file-list="false"
                                            :http-request="httpRequestImg"
                                            :before-upload="beforeAvatarUploadImg">
                                            <img v-if="fileInfo.materialsLink" :src="fileInfo.materialsLink" class="avatar"/>
                                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                        </el-upload>
                                    </div>
                                    <div class="fl funBox"><el-button type="danger" plain  @click="delImg()">删除</el-button></div>
                                </div>
                            </div>
                            <div class="desc">
                                <p>文件描述</p>
                                <div class="infobox">
                                    <textarea name="" id="infobox" cols="30" rows="10" v-model="fileInfo.materialsDescribe" placeholder="请输入文件描述内容 最多120个字符" maxlength="120"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="title"><span style="color:#c02;">*</span>收费方式</div>
                        <div class="collcet">
                            <label for="freePrice"><input id="freePrice" name="price" type="radio" value="0" v-model="fileInfo.chargeType">免费</label>
                            <label for="price"><input id="price" name="price" type="radio" value="1" v-model="fileInfo.chargeType">付费</label>
                            <label for="" v-show="fileInfo.chargeType==1?true:false"><input type="number" v-model="fileInfo.materialsPrice" oninput="this.value=this.value.replace(/[^0-9\.]/g,'')">元</label>
                        </div>
                        <div class="title"><span style="color:#c02;">*</span>文件上传</div>
                        <div class="collcet" style="width:70%;">
                            <el-upload class="avatar-uploader"
                                action="/HycCms/fileOperate/fileUpload.do"
                                :before-upload="beforeAvatarUpload"
                                :on-progress="updateProgress"
                                :on-success="handleChange"
                                :file-list="fileList"
                                :on-error="uploadError"
                                :limit="1"
                            >
                                <el-button size="small" type="primary" class="fl">点击上传</el-button>
                                <div class="el-upload__tip fl" style="margin-left:20px;">只能上传 pdf/word/ppt/xls/txt/zip 文件，且不超过100M</div>
                            </el-upload>
                            <p style="font-size:14px;margin-top: 10px;" v-show="fileStartFlag">文件正在上传,请稍等...</p>
                        </div>
                    </div>
                </div>
                <div class="newsSet fr">
                    <div class="newsContentRight infoBox cb">
                        <p>发布时间</p>
                        <el-date-picker
                            v-model="fileInfo.publicTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder=""
                            default-time="00:00:00"
                            :picker-options="startDateOpt"
                            @change="changeStart()"
                        >
                        </el-date-picker>
                        <p>过期时间</p>
                        <el-date-picker
                            v-model="fileInfo.expireTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="2030-12-31"
                            default-time="23:59:59"
                            :picker-options="endDateOpt"
                        >
                        </el-date-picker>
                        <p>权重设置</p>
                        <input type="number" placeholder="请输入数字 设置文件权重" v-model="fileInfo.sort" oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')">
                        <p>推荐位置</p>
                        <div class="clearfix">
                            <label for="home" class="address_box fl" style="margin-right:30px;"><input type="radio" id="home" class="fl" name="address" value="1" v-model="fileInfo.materialsArrposid">首页</label>
                            <div class="columnOption fl">
                                <label for="columnBox" class="fl address_box"><input type="radio" id="columnBox" name="address" value="2" v-model="fileInfo.materialsArrposid">栏目</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foo_btn">
                <div class="saveDraft" @click="cancel()">取消</div>
                <div class="saveDraft" @click="saveDraftBtn()">存草稿</div>
                <div class="newsSave" @click="issueBtn()">发布</div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_findMaterials, api_uploadFile, api_addMaterials, api_updateMaterials, api_getKs } from "../../../axios/apis.js";

    export default {
        name:'addVideos',
        components:{ crumbs },
        data(){
            return {
                fileInfo:{
                    fkColumnId:'',        // 关联栏目id
                    materialsName:'',        // 标题
                    materialsLink:'',       // 封面图路径
                    chargeType: '0',      // 付费方式 0免费 1付费
                    materialsPrice:'',      // 付费价格
                    materialsDescribe:'',   // 描述
                    materialsUrl:'',        // 文件地址
                    sort:'0',            // 权重
                    state:'',           // 新闻状态,0:发布,1:草稿
                    materialsArrposid:'2',   // 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻
                    fkPlatformId:'',    // 关联平台id
                    platformName:'',    // 平台名称
                    hospitalId:'',      // 医院Id
                    departname:'',      // 所属机构名称
                    publicTime:'',      // 发布时间
                    expireTime:'2030-12-30 23:59:59',      // 过期时间
                    materialsAttName:'',
                    speechmaker:'',
                    sectionId:'',
                    sectionName:''
                },
                department:[],
                id:'',                  // 文件栏目id
                percentage: null,
                fileColumnList:[],
                startDateOpt:{},
                endDateOpt:{},
                fileList:[],
                platformNameList:[],
                ct_month:'',
                fileStartFlag: false
            }
        },
        created(){
            this.fileInfo.fkPlatformId = this.$route.query.fkPlatformId;
            this.fileInfo.hospitalId = this.$route.query.hospitalId;
            this.fileInfo.departname = this.$route.query.departname;
            this.id = this.$route.query.learnId;
        },
        mounted:function(){
            this.fileColumnList = JSON.parse(sessionStorage.columnListArr);
            this.platformNameList = JSON.parse(sessionStorage.platformList);
            if(this.fileInfo.fkPlatformId){
                this.platformNameList.forEach(v => {
                    if(v.id == this.fileInfo.fkPlatformId){
                        this.fileInfo.platformName = v.platformName;
                    }
                });
            }
            this.fileInfo.fkColumnId = this.fileColumnList[0].id;
            this.initPageInfo();
            if (!this.id) { this.getCurrentTime() };
        },
        methods:{
            initPageInfo:function(){
                if(!this.id){return;}
                this.getVideoInfo()
            },
            // 取消
            cancel(){
                this.$router.go(-1);
            },
            // 获取当前时间
            getCurrentTime(){
                this.ct_month = new Date();
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
                this.fileInfo.publicTime = clock;
            },
            // 缩略图删除
            delImg:function(){
                this.fileInfo.materialsLink = '';
            },
            // 查询科室
            getKsList(){
                api_getKs({hospitalId:this.addVideo.hospitalId})
                .then(res => {
                    if(res.obj.length>0){
                        this.department = res.obj;
                    }
                })
            },
            // 获取当前科室名称
            getDepartname(id){
                this.department.forEach(v => {
                    if(v.id == id){
                        this.addVideo.sectionName = v.departname;
                        this.fileInfo.departname = this.$route.query.departname;
                    }
                })
            },
            // 请求文件内容
            getVideoInfo:function(){
                let _this = this;
                // 发起请求
                api_findMaterials({
                    id:this.id
                })
                .then(res => {
                    if (res.flag=='success') {
                        this.fileInfo.materialsName = res.obj.materialsName;
                        this.fileInfo.fkColumnId = res.obj.fkColumnId;        // 关联栏目id
                        this.fileInfo.materialsLink = res.obj.materialsLink;       // 封面图路径
                        this.fileInfo.chargeType = res.obj.chargeType;      // 付费方式 0免费 1付费
                        this.fileInfo.materialsPrice = res.obj.materialsPrice? res.obj.materialsPrice : '';      // 付费价格
                        this.fileInfo.materialsDescribe = res.obj.materialsDescribe;   // 描述
                        this.fileInfo.materialsUrl = res.obj.materialsUrl;        // 文件地址
                        this.fileInfo.sort = res.obj.sort;            // 权重
                        this.fileInfo.state = res.obj.state;           // 新闻状态,0:发布,1:草稿
                        this.fileInfo.materialsArrposid = res.obj.materialsArrposid;   // 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻
                        this.fileInfo.fkPlatformId = res.obj.fkPlatformId;    // 关联平台id
                        this.fileInfo.platformName = res.obj.platformName;    // 平台名称
                        this.fileInfo.hospitalId = res.obj.hospitalId;      // 医院Id
                        this.fileInfo.departname = res.obj.departname;      // 所属机构名称
                        this.fileInfo.publicTime = res.obj.publicTime;      // 发布时间
                        this.fileInfo.materialsAttName = res.obj.materialsAttName;
                        this.fileInfo.speechmaker = res.obj.speechmaker;
                        this.fileInfo.sectionId = !res.obj.sectionId?'':res.obj.sectionId;
                        this.fileInfo.sectionName = res.obj.sectionName;

                        if(this.fileInfo.materialsUrl && this.fileInfo.materialsAttName){
                            this.fileList.push({
                                name: this.fileInfo.materialsAttName,
                                url: this.fileInfo.materialsUrl
                            })
                        }
                    }else{
                        this.getCurrentTime();
                    }
                })
                .then(()=>{
                    // 开始时间
                    this.startDateOpt.disabledDate = function (time) {
                        // 设置可选择的日期为 当前时间到结束时间之间的时间
                        return (
                            time.getTime() < Date.now() - 8.64e7 ||
                            time.getTime() >
                            new Date(_this.expireTime).getTime()
                        );
                    };
                    // 结束时间
                    this.endDateOpt.disabledDate = function (time) {
                        // 设置可选择的日期为 当前时间到结束时间之间的时间
                        return (
                            time.getTime() < new Date(_this.publicTime).getTime()
                        );
                    };
                })
                .catch(error=>console.log(error))
            },
            // 存草稿
            saveDraftBtn:function(){
                this.fileInfo.state = '1';

                if (!this.fileInfo.materialsName) { this.$message.warning('请填写标题内容');return;}
                if (!this.fileInfo.speechmaker) { this.$message.warning('请填写主讲人姓名');return;}
                if (this.fileInfo.chargeType=='1') {
                    if(!this.fileInfo.materialsPrice){ this.$message.warning('请填写付费价格');return; }
                }
                if (!this.fileInfo.materialsUrl) { this.$message.warning('请上传视频');return;}

                if(this.id){
                    this.fileInfo.id = this.id;
                    this.upDateVideo(this.fileInfo);
                }else{
                    this.addVideos(this.fileInfo);
                }
            },
            // 立即发布
            issueBtn:function(){
                // 非空校验
                if (!this.fileInfo.materialsName) { this.$message.warning('请填写标题内容');return;}
                if (!this.fileInfo.speechmaker) { this.$message.warning('请填写主讲人姓名');return;}
                if (this.fileInfo.chargeType=='1') {
                    if(!this.fileInfo.materialsPrice){ this.$message.warning('请填写付费价格');return; }
                }

                this.fileInfo.state = '0';
                if(this.id){
                    this.fileInfo.id = this.id;
                    this.upDateVideo(this.fileInfo);
                }else{
                    this.addVideos(this.fileInfo);
                }
            },
            // 新增文件
            addVideos(data){
                api_addMaterials(data)
                .then( res =>{
                    if(res.flag=='success'){
                        setTimeout(()=>{
                            this.$router.go(-1)
                        },1500)
                    }
                    this.$message({
                        type: res.flag,
                        message: res.msg
                    })
                })
            },
            // 更新文件信息
            upDateVideo(data){
                api_updateMaterials(data)
                .then( res =>{
                    if(res.flag=='success'){
                        setTimeout(()=>{
                            this.$router.go(-1)
                        },1500)
                    }
                    this.$message({
                        type: res.flag,
                        message: res.msg
                    })
                })
            },

            // 上传文件判断 大小/格式
            beforeAvatarUploadImg(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {this.$message.error('只能上传jpg或png格式文件!')}
                if (!isLt2M) {this.$message.error('图片不能超过2MB!')}
                return isJPG && isLt2M;
            },
            // 自定义文件上传方法
            httpRequestImg(options){
                let param = new FormData()  // 创建form对象
                param.append('img', options.file)  // file对象是 beforeUpload参数
                // 添加请求头
                api_uploadFile(param)
                .then(res => {
                    if(res.flag=='success'){
                        this.fileInfo.materialsLink = res.obj[0].ossUrl;
                        this.$message({
                            type:'success',
                            message:'图片上传成功!'
                        })
                    }
                })
                .catch(err=>console.log(err))
            },

            // 上传文件前 判断 大小/格式
            beforeAvatarUpload(file) {
                const format = file.type === 'application/msword'
                || file.type === 'application/zip'
                || file.type === 'application/vnd.openxmlformats-officedocument.presentationml.presentation'
                || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                || file.type === 'text/plain'
                || file.type === 'application/pdf';
                const isLt500M = file.size / 1024 / 1024 < 100;
                if (!format) { this.$message.error('只能上传pdf/word/ppt/xls/txt/zip文件!') }
                if (!isLt500M) { this.$message.error('单个文件文件不能超过 100MB!') }
                return format && isLt500M;
            },
            // 上传进度
            updateProgress(event, file, fileList) {
                this.fileStartFlag = false;
                this.percentage = parseInt(file.percentage.toFixed(0));
            },
            // 列表发生变化
            handleChange(file, fileList) {
                if(file.flag=='success'){
                    this.fileInfo.materialsAttName = file.obj[0].fileName;
                    this.fileInfo.materialsUrl = file.obj[0].ossUrl;
                    this.$message.success('文件上传成功!');
                    this.fileStartFlag = false;
                }else{
                    this.$message.error('文件上传失败!')
                }
            },
            // 上传失败
            uploadError(err, file, fileList){
                if(file.status=='fail'){
                    this.fileStartFlag = false;
                    this.$message.error('文件上传失败!')
                }
            },
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
    #sectionBox{
        width: 100%;
        height: 40px;
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
    #keywords,#theme,#materialsName,#sourceInfo{
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

    .collcet{
        text-align: left;
        margin-bottom: 20px;
    }
    .collcet label{
        margin-right: 20px;
        height: 30px;
        line-height: 30px;
    }
    .collcet label:nth-child(3) input{
        height: 30px;
        width: 80px;
    }
    .el-upload-list{

    }
</style>
