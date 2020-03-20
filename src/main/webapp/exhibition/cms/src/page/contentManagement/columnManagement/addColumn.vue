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
                        <p style="color:#5631e0">{{columnForm.departname}}</p>
                    </div>
                    <div class="input_list showInfo" v-show="!!columnForm.parentColumnName">
                        <span>父栏目</span>
                        <span style="color:#5631e0;width:calc(100% - 120px);">{{columnForm.parentColumnName}}</span>
                    </div>
                    <div class="input_list"><span>栏目名称</span><input type="text" v-model="columnForm.columnName" placeholder="请输入栏目名称 最多10个字符" maxlength="10"></div>
                    <div class="input_list"><span>内容类型</span>
                        <select name="" id="" v-model="columnForm.fkColumnTypeId">
                            <option v-for="item in columnType" :key="item.id" :value="item.id">{{item.typeName}}</option>
                        </select>
                    </div>
                    <div class="input_list"><span>栏目code</span><input type="text" placeholder="请输入栏目code 最多30个字符" maxlength="30" v-model="columnForm.columnCode" @blur="verifyColumnCode()" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')"></div>
                    <div class="input_list icon_com">
                        <span>栏目icon</span>
                        <div class="fl">
                            <!-- <el-upload
                                class="disabled"
                                action=""
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
                        <div class="cancel" @click="cancelBtn()">取消</div>
                        <div class="save" @click="saveColumnInfo()">保存</div>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog
            title="提示"
            :visible.sync="dialogVisibleState"
            width="30%"
            :before-close="handleClose"
        >
            <span style="color:#333;font-weight:bold;">确定使用此栏目code吗？保存后将无法再进行修改！</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleState = false">取 消</el-button>
                <el-button type="primary" @click="sure()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_verifyColumnCode, api_getColumnTypeList, api_saveColumnInfo, api_saveAddColumnInfo, api_uploadFile } from "../../../axios/apis.js";
    export default {
        name:'addColumn',
        components:{ crumbs },
        data() {
            return {
                columnForm:{
                    pId:'',
                    parentColumnName:'',
                    columnUrl:'',
                    columnName:'',
                    fkColumnTypeId:'',
                    state:'0',
                    hospitalId:'',
                    departname:'',
                    fkPlatformId:'',
                    columnCode:'',
                    sort:'0',

                },
                columnType: [],
                imagelist: [],
                dialogImageUrl: '',
                dialogVisible: false,
                imgNum:1,
                dialogVisibleState:false,
            }
        },
        beforeRouteUpdate(to,from,next){
            next();
        },
        created(){
            this.columnForm.hospitalId = this.$route.query.hospitalId;
            this.columnForm.departname = this.$route.query.departname;
            this.columnForm.fkPlatformId = this.$route.query.fkPlatformId;
            this.columnForm.pId = this.$route.query.pId
            this.columnForm.parentColumnName = this.$route.query.parentColumnName;
        },
        mounted:function(){
            this.getColumnType()
        },
        methods: {
            // 验证栏目Code是否存在
            verifyColumnCode(){
                if(this.columnForm.columnCode){
                    api_verifyColumnCode({
                        hospitalId:this.columnForm.hospitalId,
                        platformId:this.columnForm.fkPlatformId,
                        columnCode:this.columnForm.columnCode,
                    })
                    .then(res=>{
                        if(res.obj!=0){
                            this.$alert('你输入的栏目code已被使用,请重新输入!', '提示', {confirmButtonText: '确定'})
                        }
                    })
                    .catch(err=>console.log(err))
                }
            },
            // 自定义文件上传方法
            httpRequest(options){
                let param = new FormData()  // 创建form对象
                param.append('img', options.file)  // file对象是 beforeUpload参数
                // 添加请求头
                api_uploadFile(param).then(res => {
                    // this.dialogImageUrl = res.obj[0].ossUrl;
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
            imgUpLoadSuccess:function(res, file, fileList){
                this.columnForm.columnUrl = res.obj[0].ossUrl;
            },
            // 弹窗选择是否确定使用此栏目code
            handleClose(done) {
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            },
            // 确认使用此栏目code 提交栏目信息
            sure(){
                this.dialogVisibleState = false;
                this.postAjaxColumnInfo()
            },
            // 保存栏目信息
            saveColumnInfo:function () {
                if (!this.columnForm.columnName) { this.$message({message: '请填写栏目名称',type: 'warning',duration: 1500});return;}
                if (!this.columnForm.fkColumnTypeId) { this.$message({message: '请选择内容类型',type: 'warning',duration: 1500});return;}
                if (!this.columnForm.columnCode) { this.$message({message: '请输入栏目code',type: 'warning',duration: 1500});return;}
                if(this.columnForm.columnCode){
                    this.dialogVisibleState = true;
                }
            },
            postAjaxColumnInfo(){
                api_saveAddColumnInfo(this.columnForm)
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
            // 取消新建栏目
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
    }
    .addColumnBox {
        width: 100%;
        height: 100%;
        overflow: scroll;
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
        min-width: 800px;
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
        max-width: 800px;
        width: calc( 100% - 150px);
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
        margin-right: 10px;
        margin-top: 3px;
    }
    .status div input:checked + s{
        background-color: #6b61f2;
    }
    .submitBox{
        /* width: 920px; */
        text-align: center;
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
        display: inline-block;
        margin: 0 10px;
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
