<template>
    <div class="addColumn fl">
        <div class="addColumnBox">
            <crumbs></crumbs>
            <div class="contentInfo">
                <div class="title">
                    <i></i>基本设置
                </div>
                <div class="columnContent">
                    <div class="input_list showInfo">
                        <span>所属机构</span>
                        <p style="color:#5631e0">{{departname}}</p>
                    </div>
                    <div class="input_list showInfo" v-show="!!columnForm.parentColumnName"><span>父栏目</span>
                        <span style="color:#5631e0">{{columnForm.parentColumnName}}</span>
                    </div>
                    <div class="input_list"><span>栏目名称</span><input type="text" v-model="columnForm.columnName" placeholder="请输入栏目名称 最多30个字符" maxlength="30"></div>
                    <div class="input_list"><span>内容类型</span>
                        <select name="" id="" v-model="columnForm.fkColumnTypeId">
                            <option v-for="item in columnType" :key="item.id" :value="item.id">{{item.typeName}}</option>
                        </select>
                    </div>
                    <div class="input_list" ><label  @click="alertTip()"><span>栏目code</span><input type="text" v-model="columnCode" disabled></label></div>
                    <div class="input_list icon_com">
                        <span>栏目icon</span>
                        <div class="fl">
                            <!-- <el-upload
                                class="disabled"
                                action="/HycCms/fileOperate/fileUpload.do"
                                list-type="picture-card"
                                :limit="imgNum"
                                :http-request="httpRequest"
                                :on-success="imgUpLoadSuccess"
                            >
                            <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                                <img width="100%" :src="columnForm.columnUrl" alt="">
                            </el-dialog> -->
                            <el-upload
                                class="avatar-uploader"
                                action=""
                                :show-file-list="false"
                                :http-request="httpRequest"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                                <img v-if="columnForm.columnUrl" :src="columnForm.columnUrl" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </div>
                    </div>
                    <div class="status">
                        <span>*状态</span>
                        <div class="fl"><label for="status_one"><input type="radio" id="status_one" hidden v-model="columnForm.state" value="0"><s></s>启用</label></div>
                        <div class="fl"><label for="status_two"><input type="radio" id="status_two" hidden v-model="columnForm.state" value="1"><s></s>禁用</label></div>
                    </div>
                    <div class="submitBox">
                        <div class="save fr" @click="saveColumnInfo()">保存</div>
                        <div class="cancel fr" @click="cancelBtn()">取消</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getColumnTypeList, api_getColumnInfo, api_updataColumnInfo, api_uploadFile } from "../../../axios/apis.js";
    export default {
        name:'addColumn',
        components:{ crumbs },
        data() {
            return {
                columnForm:{
                    columnUrl:'',
                    columnName:'',
                    fkColumnTypeId:'',
                    state:'',
                    id:'',
                    columnProperty:''
                },
                columnCode:'',
                columId:'',
                fkPlatformId:'',
                hospitalId:'',
                columnType: [],
                dialogImageUrl: '',
                dialogVisible: false,
                imgNum:1,
                departname:'',
            }
        },
        created(){
            this.columId = this.$route.query.columId;
            this.fkPlatformId = this.$route.query.fkPlatformId;
        },
        mounted:function(){
            this.getColumnType()
            this.getColumnInfo()
        },
        methods: {
            // 获取栏目类型
            getColumnType:function(){
                api_getColumnTypeList()
                .then(res=>{
                    if (res.flag=='success') {
                        this.columnType = res.obj
                    }
                })
                .catch(err=>console.log(err))
            },
            // 图片上传成功后回显
            imgUpLoadSuccess:function(res, file, fileList){
                this.columnForm.columnUrl = res.obj[0].ossUrl;
            },
            // 提示无法修改
            alertTip(){
                this.$message({message: '栏目code不能进行编辑',type: 'warning',duration: 1500})
            },
            // 自定义文件上传方法
            httpRequest(options){
                let param = new FormData()  // 创建form对象
                param.append('img', options.file)  // file对象是 beforeUpload参数
                // 添加请求头
                api_uploadFile(param).then(res => {
                    this.columnForm.columnUrl = res.obj[0].ossUrl;
                })
                .catch(err=>console.log(err))
            },
            // 上传文件判断 大小/格式
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {this.$message.error('banner图只能上传 JPG或png 格式!')}
                if (!isLt2M) {this.$message.error('banner图单张大小不能超过 2MB!')}
                return isJPG && isLt2M;
            },
            handleAvatarSuccess(res, file) {
                this.columnForm.columnUrl = res.obj[0].ossUrl;
            },
            // 获取当前栏目信息
            getColumnInfo:function(){
                api_getColumnInfo({
                    columId:this.columId
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.columnForm.id = res.obj.id;
                        this.departname = res.obj.departname;
                        this.columnForm.columnName = res.obj.columnName;
                        this.columnForm.columnUrl = res.obj.columnUrl;
                        this.columnForm.fkColumnTypeId = res.obj.columnType.id;
                        this.columnForm.state = res.obj.state;
                        this.columnCode = res.obj.columnCode;
                        this.hospitalId = res.obj.hospitalId;
                        this.columnForm.columnProperty = res.obj.columnProperty;

                    }
                })
                .catch(err=>console.log(err))
            },
            // 保存栏目信息
            saveColumnInfo:function () {
                if (!this.columnForm.columnName) { this.$message({message: '请填写栏目名称',type: 'warning',duration: 1500});return;}
                if (!this.columnForm.fkColumnTypeId) { this.$message({message: '请选择内容类型',type: 'warning',duration: 1500});return;}
                api_updataColumnInfo(this.columnForm)
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if (res.flag == 'success') { setTimeout(() => { this.$router.go(-1) }, 1500); }
                })
                .catch(err=>{
                    console.log(err);
                })
            },
            // 取消编辑
            cancelBtn(){
                this.$router.go(-1);
            }
        }
    }
</script>

<style scoped>
    .addColumn {
        width: 100%;
        height: 100%;
        padding-left: 3%;
        padding-right: 2%;
        padding-top: 10px;
        padding-bottom: 0;
        box-sizing: border-box;
        overflow: scroll;
    }
    .addColumnBox {
        width: 100%;
        height: 100%;
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

    .columnContent {
        background-color: #ffffff;
        box-shadow: 0px 0px 8px 0px rgba(33, 36, 45, 0.15);
        min-height: 600px;
        min-width: 1000px;
        /* padding: 15px 30px; */
        padding-top: 15px;
        padding-left: 30px;
    }
    .input_list{
        text-align: left;
        height: 40px;
        margin-bottom: 30px;
        width: 100%;
        display: flex;
    }
    .input_list span{
        display:block;
        width: 120px;
        font-size: 14px;
        line-height: 40px;
        color: #333;
        float: left;
    }
    .showInfo{
        height: 30px;
        margin-bottom: 6px;
    }
    .showInfo span{
        line-height: 30px;
    }
    .input_list input,.input_list select{
        width: 800px;
        /* width: 70%; */
        height: 40px;
        background-color: #ffffff;
        border: solid 1px #e5e5e5;
        box-sizing: border-box;
        padding: 0 8px;
        font-size: 14px;
    }
    .icon_com{
        height: 170px;
    }
    .disabled .el-upload--picture-card{
        display: none;
    }

    .status {
        /* padding-left: 120px; */
        margin-bottom: 60px;
        text-align: left;
    }
    .status span{
        width: 116px;
        display: block;
        float: left;
        line-height: 20px;
    }
    .status div{
        font-size: 14px;
        color: #333;
        margin-right: 68px;
        line-height: 20px;
    }
    .status div s{
        width: 14px;
        height: 14px;
        border-radius: 2px;
        border: solid 2px #6b61f2;
        float: left;
        vertical-align: middle;
        margin-right: 10px;
    }
    .status div input:checked + s{
        background-color: #6b61f2;
    }
    .submitBox{
        width: 920px;
    }
    .submitBox div{
        width: 110px;
        height: 38px;
        font-size: 16px;
        background-color: #29a2fb;
        border-radius: 6px;
        text-align: center;
        line-height: 38px;
        color: #ffffff;
        cursor: pointer;
    }
    .submitBox .cancel{
        background-color: #cccccc;
        margin-right: 30px;
    }
    .avatar-uploader,.avatar,.avatar-uploader-icon{
        width: 120px;
        height: 120px;
    }
    .avatar-uploader-icon{
        background: #ddd;
        border-radius: 4px;
        border: 1px dashed #ccc;
    }
    .avatar-uploader-icon::before{
        text-align: center;
        line-height: 120px;
    }

</style>
