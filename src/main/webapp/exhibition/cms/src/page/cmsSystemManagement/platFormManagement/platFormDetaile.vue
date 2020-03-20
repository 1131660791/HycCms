<template>
    <div class="platFormDetaile">
        <crumbs></crumbs>
        <div class="contentBox">
            <P>基本设置</P>
            <div class="content">
                <p><span>*平台名称：</span><input type="text" placeholder="请填写平台名称" v-model="platformName"></p>
                <p><span>*平台地址：</span><input type="text" placeholder="请填写平台地址" v-model="platformUrl"></p>
                <p>
                    <span>*状态：</span>
                    <el-radio v-model="state" label="0">启用</el-radio>
                    <el-radio v-model="state" label="1">禁用</el-radio>
                </p>
            </div>
            <div class="cz">
                <div class="cancel btn" @click="cancel()">取消</div>
                <div class="sure btn" @click="savePlatformInfo()">确定</div>
            </div>
        </div>
    </div>
</template>

<script>
    import crumbs from "../../../components/crumbs";
    import { api_getCurrentPlatformInfo, api_addPlatform, api_updataPlatform } from "../../../axios/apis.js";
    export default {
        name:'platForm',
        components:{ crumbs },
        data(){
            return{
                platformId:'',
                state:'0',
                platformUrl:'',
                platformName:'',
            }
        },
        created(){
            this.platformId = this.$route.query.platformId;
        },
        mounted(){
            this.init();
        },
        methods:{
            // 初始化页面
            init(){
                // 判断 平台id是否为真 如果为真则请求平台信息 如果为假则为创建平台
                if(this.platformId){
                    this.getPlatformInfo()
                }
            },
            // 查询平台信息
            getPlatformInfo(){
                api_getCurrentPlatformInfo({
                    platformId:this.platformId
                })
                .then(res=>{
                    if (res.flag=='success') {
                        this.platformName = res.obj.platformName;
                        this.platformUrl = res.obj.platformUrl;
                        this.state = res.obj.state.toString();
                    }
                })
                .catch(error=>console.log(error))
            },
            // 提交平台信息
            savePlatformInfo(){
                if(!this.platformName){ this.$message({message: '请平台名称',type: 'error',duration: 1500});return;}
                if(this.platformId){
                    this.updataPlatform()
                }else{
                    this.addPlatform()
                }
            },
            // 新增平台
            addPlatform(){
                api_addPlatform({
                    platformName:this.platformName,
                    platformUrl:this.platformUrl
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'warning',
                        duration: 1500
                    });
                    if (res.flag=='success') {
                        setTimeout(() => {
                            this.$router.go(-1)
                        }, 1500);
                    }
                })
                .catch(error=>console.log(error))
            },
            // 修改平台信息
            updataPlatform(){
                api_updataPlatform({
                    id:this.platformId,
                    platformName:this.platformName,
                    state:this.state,
                    platformUrl:this.platformUrl
                })
                .then(res=>{
                    this.$message({
                        message: res.msg,
                        type: res.flag=='success'?'success':'warning',
                        duration: 1500
                    });
                    if (res.flag=='success') {
                        setTimeout(() => {
                            this.$router.go(-1)
                        }, 1500);
                    }
                })
                .catch(error=>{
                    console.log(error);
                })

            },
            // 取消编辑或新增
            cancel(){
                this.$router.go(-1)
            }
        }
    }
</script>

<style scoped>
    .platFormDetaile{
        text-align: left;
    }
    .contentBox>p{
        color: #333;
        width: 100%;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 12px;
    }
    .content{
        padding-left: 20px;
        margin-bottom: 60px;
        margin-top: 20px;
    }
    .content p{
        margin-bottom: 20px;
    }
    .content p span{
        display: inline-block;
        width: 120px;
    }
    .content input{
        width: 400px;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        border: 1px solid #ccc;
        padding: 0 5px;
    }
    .cz{
        text-align: center;
        width: 60%;
    }
    .cz .btn{
        display: inline-block;
        width: 120px;
        height: 36px;
        line-height: 36px;
        text-align: center;
        font-display: 14px;
        color: #fff;
        background: rgba(129, 103, 245, 1);
        border-radius: 4px;
        margin: 0 10px;
        cursor: pointer;
    }
    .cz .cancel{
        background: #999;
    }
</style>