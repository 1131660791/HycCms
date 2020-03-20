<template>
    <div class="addDefault fl">
        <div class="addDefaultBox">
            <crumbs></crumbs>
            <div class="contentInfo">
                <div class="title">
                    <i></i>基本设置
                </div>
                <div class="columnContent">
                    <div class="input_list showInfo">
                        <span>平台</span>
                        <p style="color:#5631e0">{{fkPlatformName}}</p>
                    </div>
                    <div class="input_list showInfo" v-show="!!parentColumnName">
                        <span>父栏目</span>
                        <span style="color:#5631e0">{{parentColumnName}}</span>
                    </div>
                    <div class="input_list"><span>栏目名称</span><input type="text" v-model="columnForm.columnName" placeholder="请输入栏目名称 最多30个字符" maxlength="30"></div>
                    <div class="input_list"><span>内容类型</span>
                        <select name="" id="" v-model="columnForm.fkColumnType">
                            <option v-for="item in columnType" :key="item.id" :value="item.id">{{item.typeName}}</option>
                        </select>
                    </div>
                    <div class="input_list" v-if="columId"><label @click="alertTip()"><span>栏目code</span><input type="text" v-model="columnForm.columnCode" disabled></label></div>
                    <div class="input_list" v-else><span>栏目code</span><input type="text" v-model="columnForm.columnCode" @blur="verifyColumnCode()" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')"></div>
                    <div class="input_list icon_com">
                        <span>栏目icon</span>
                        <div class="fl">
                            <el-upload
                                class="disabled"
                                action="/HycCms/fileOperate/fileUpload.do"
                                list-type="picture-card"
                                :limit="imgNum"
                                :on-success="imgUpLoadSuccess"
                            >
                            <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                                <img width="100%" :src="dialogImageUrl" alt="">
                            </el-dialog>
                        </div>
                    </div>
                    <div class="status">
                        <span>*状态</span><input type="text">
                        <div class="fl"><label for="status_one"><input type="radio" id="status_one" hidden v-model="columnForm.state" value="0"><s></s>启用</label></div>
                        <div class="fl"><label for="status_two"><input type="radio" id="status_two" hidden v-model="columnForm.state" value="1"><s></s>禁用</label></div>
                    </div>
                    <div class="submitBox">
                        <div class="cancel" @click="cancel()">取消</div>
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
    import { api_getDefaultColumnTypeList, api_verifyDefaultColumnCode, api_getDefaultColumnInfo, api_saveDefaultColumnInfo } from "../../../axios/apis.js";
    export default {
        name:'addDefault',
        components:{ crumbs },
        data() {
            return {
                columnForm:{
                    columnUrl:'',
                    columnName:'',
                    fkColumnType:'',
                    state:'0',
                    columnCode:''
                },
                pId:'',
                fkPlatformId:'',
                fkPlatformName:'',
                parentColumnName:'',
                columId:'',
                columnType: [],
                imagelist: [],
                platformList:[],
                dialogImageUrl: '',
                dialogVisible: false,
                imgNum:1,
                dialogVisibleState:false,
            }
        },
        created(){
            this.fkPlatformId = this.$route.query.fkPlatformId;
            this.pId = this.$route.query.pId
            this.parentColumnName = this.$route.query.parentColumnName;
            this.columId = this.$route.query.columId;
        },
        mounted:function(){
            this.getColumnType()
            this.platformList = JSON.parse(sessionStorage.platformList)
            this.platformList.forEach(v => {
                if(v.id == this.fkPlatformId){this.fkPlatformName = v.platformName;}
            });
            if(this.columId){
                this.getColumnInfo();
            }
        },
        methods: {
            // 获取栏目类型
            getColumnType(){
                api_getDefaultColumnTypeList()
                .then(res=>{
                    if (res.flag=='success') { this.columnType = res.obj }
                })
                .catch(err=>console.log(err))
            },
            imgUpLoadSuccess(res, file, fileList){
                this.columnForm.columnUrl = res.obj[0].ossUrl;
            },
            // 验证栏目Code是否存在
            verifyColumnCode(){
                if(this.columnForm.columnCode){
                    api_verifyDefaultColumnCode({
                        platformId:this.fkPlatformId,
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
            // 弹窗选择是否确定使用此栏目code
            handleClose(done) {
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            },
            // 提示无法修改
            alertTip(){
                this.$message({message: '栏目code不能进行编辑',type: 'warning',duration: 1500})
            },
            // 确认使用此栏目code 提交栏目信息
            sure(){
                this.dialogVisibleState = false;
                this.save('/HycCms/defaultColumn/addDefaultColumn.do');
            },
            // 查询栏目信息
            getColumnInfo(){
                api_getDefaultColumnInfo({
                    platformId: this.fkPlatformId,
                    defaultColumnId:this.columId
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.columnForm.columnName = res.obj.columnName;
                        this.columnForm.fkColumnType = res.obj.columnType.id;
                        this.columnForm.state = res.obj.state;
                        this.columnForm.columnUrl = res.obj.columnUrl;
                        this.columnForm.columnCode = res.obj.columnCode;
                        this.pId = res.obj.pId;
                    }
                })
                .catch(err=>console.log(err))
            },
            // 保存栏目信息
            saveColumnInfo:function () {
                if (!this.columnForm.columnName) { this.$message({message: '请填写栏目名称',type: 'warning',duration: 1500});return;}
                if (!this.columnForm.fkColumnType) { this.$message({message: '请选择内容类型',type: 'warning',duration: 1500});return;}
                if(this.columId){
                    this.columnForm.id = this.columId;
                    this.save('/HycCms/defaultColumn/updateDefaultColumn.do');
                }else{
                    this.columnForm.fkPlatformId = this.fkPlatformId;
                    this.columnForm.pId = this.pId;
                    this.dialogVisibleState = true;
                }
            },
            save(url){
                api_saveDefaultColumnInfo(url,this.columnForm)
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='error'?'error':'success',
                        duration: 1500
                    })
                    if (res.flag == 'success') { setTimeout(() => { this.$router.go(-1) }, 1500); }
                })
                .catch(err=>console.log(err))
            },
            // 取消新建栏目
            cancel:function(){
                this.$router.go(-1)
            }
        }
    }
</script>

<style scoped>
    .addDefault {
        width: 100%;
        height: 100%;
        padding-left: 3%;
        padding-right: 2%;
        padding-top: 10px;
        padding-bottom: 0;
        box-sizing: border-box;
    }
    .addDefaultBox {
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
        padding-left: 120px;
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
</style>