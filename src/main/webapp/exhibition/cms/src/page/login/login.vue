<template>
    <div class="login">
        <div class="loginBg">
            <div class="btn_back" v-show="findPasswordStatus" @click="go_back"></div>
        </div>
        <!-- 登录 -->
        <div class="loginContent" v-show="!findPasswordStatus">
            <div class="userInfo">
                <label for="userName">
                    <i class="userNameIcon"></i>
                    <input type="text" id="userName" v-model="formInline.userName" placeholder="请输入用户名" autocomplete="off" value="" @keyup.enter="infoVerify()">
                    <!-- cmsSystem -->
                </label>
                <label for="userPass">
                    <i class="userPassIcon"></i>
                    <input type="password" id="userPass" v-model="formInline.passWord" placeholder="请输入密码" autocomplete="off" @keyup.enter="infoVerify()">
                </label>
                <div class="yzCode">
                    <label for="userCode" class="fl">
                        <input type="text" id="userCode" v-model="formInline.validateCode" placeholder="请输入验证码" autocomplete="off" @keyup.enter="infoVerify()">
                    </label>
                    <div class="yzCodeBox fl">
                        <img :src="codeImg" alt="">
                    </div>
                    <transition>
                        <div class="refresh fl" @click="validateCode()">换一换<i></i></div>
                    </transition>
                </div>
                <div class="btnBox clearfix">
                    <div class="loginBtn fl" @click="infoVerify()">登录</div>
                    <div class="userReset fr" @click="resetInfo()">重置</div>
                </div>
                <!-- <div class="findPassword" @click="findPassword()">找回密码？</div> -->
            </div>
        </div>
        <!-- 找回密码 -->
        <div class="findPasswordBox" v-show="findPasswordStatus">
            <div class="passwordTitle" ><i></i>填写账号</div>
            <div class="passwordTitleInfo" v-bind:class="{on:false}"><p>1、填写账号</p><p>2、重置密码</p></div>
            <div class="findPasswordInfoBox" v-if="true">
                <ul>
                    <li><input type="text" placeholder="填写您的账号"></li>
                    <li><input type="text" placeholder="填写邮箱" class="fl"><span class="fl">获取验证码</span></li>
                    <li><input type="text" placeholder="填写验证码" class="fl"><b>下一步</b></li>
                </ul>
            </div>
            <div class="resetPasswordInfo" v-else>
                <ul>
                    <li><input type="text" placeholder="输入新密码"></li>
                    <li><input type="text" placeholder="再次输入密码"></li>
                </ul>
                <div class="saveInfo">确定</div>
            </div>
        </div>
        <!-- <div id="loginDiv" tabindex="1" style="outline:0;" @keyup.enter="infoVerify()">
            <input type="text" v-focus style="opacity:0;position:absolute;">
        </div> -->
    </div>
</template>

<script>
    import { api_signIn,api_validateCode } from '../../axios/apis.js'
    export default {
        name:'login',
        data () {
            return {
                formInline: {
                    userName: '',
                    passWord: '',
                    validateCode: '',
                    remember:false
                },
                codeImg:'',
                findPasswordStatus: false,
            }
        },
        mounted(){
            this.validateCode();
        },
        directives: {
            focus: {
                // 指令的定义
                inserted: function (el) {
                    el.focus()
                }
            }
        },
        methods: {
            // 重新请求验证码
            validateCode: function() {
                let _this = this;
                api_validateCode({responseType: 'arraybuffer'})
                .then(response => {
                    return 'data:image/png;base64,' + btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
                })
                .then(function (res) {
                    _this.codeImg = res;
                })

            },
            // 登录前验证 是否为空 不为空验证 密码/验证码是否正确
            infoVerify:function () {
                if (!this.formInline.userName) {this.$message({message: '请输入用户名称！',type: 'warning',duration: 1500});return;}
                if (!this.formInline.passWord) {this.$message({message: '请输入密码！',type: 'warning',duration: 1500});return;}
                if (!this.formInline.validateCode) {this.$message({message: '请输入验证码！',type: 'warning',duration: 1500});return;}
                this.signIn()
            },
            // 登录验证请求
            signIn: function() {
                api_signIn(this.formInline).then(res=>{
                    console.log(res);
                    // 验证成功 缓存登录信息 跳转到首页
                    if(res.flag == 'success'){
                        sessionStorage.loginInfo = JSON.stringify(res.obj);
                        sessionStorage.sysRoles = JSON.stringify(res.obj.sysRoles);
                        this.$router.push('/home')
                    }else{
                        this.$message({
                            message: res.msg,
                            type: 'error',
                            duration: 1500
                        });
                    }
                })
            },
            // 重置用户填写的登录信息
            resetInfo:function(){
                this.formInline.userName = '';
                this.formInline.passWord = '';
                this.formInline.validateCode = '';
                this.$message({
                    message: '重置信息成功',
                    type: 'success',
                    duration: 1500
                });
            },
            findPassword:function(){
                this.findPasswordStatus = true;
            },
            go_back:function(){
                this.findPasswordStatus = false;
            }
        }
    }
</script>

<style scoped>
    .login{
        width: 100%;
        height: 100%;
        background-color: #fff;
        box-sizing: border-box;
    }
    /* 登录页样式 */
    .loginBox{
        width: 100%;
        height: 100%;
    }
    .loginBg{
        height: 35%;
        background-image: url(../../assets/images/login_bg.jpg);
        background-repeat: no-repeat;
        background-position: center bottom;
        background-size: cover;
        margin-bottom: 40px;
        position: relative;
    }
    .loginContent,.findPasswordBox{
        width: 500px;
        margin:  0 auto;
    }
    .userInfo{
        width: 100%;
    }
    .userInfo label{
        display: block;
        width: 100%;
        border-radius: 4px;
        overflow: hidden;
        margin-bottom: 20px;
    }
    .userInfo label i{
        display: block;
        height: 40px;
        width: 40px;
        float: left;
        background-color: #43b1f1;
        background-repeat: no-repeat;
        background-position: center;
    }
    .userNameIcon{
        background-image: url(../../assets/images/name_icon.png);
    }
    .userPassIcon{
        background-image: url(../../assets/images/password_icon.png);
    }
    .userInfo label input{
        box-sizing: border-box;
        height: 40px;
        padding: 0 6px;
        border: 1px solid #ccc;
        width: 460px;
        border-radius: 0 4px 4px 0;
        font-size: 14px;
    }
    .yzCode{
        height: 50px;

    }
    .userInfo .yzCode label{
        width: auto;
    }
    .userInfo .yzCode input{
        height: 38px;
        float: left;
        border-radius: 4px;
        width: 240px;
        margin-top: 12px;
    }
    .yzCodeBox{
        width: 120px;
        height: 50px;
        margin-left: 20px;
        background-color: #43b1f1;
        border-radius: 4px;
        margin-right: 30px;
        text-align: center;
        line-height: 50px;
    }
    .yzCodeBox img{
        display: inline-block;
        vertical-align: middle;
    }
    .refresh{
        height: 30px;
        color: #999999;
        line-height: 30px;
        font-size: 14px;
        cursor: pointer;
        margin-top: 10px;
    }
    .refresh:hover{
        color: #43b1f1;
    }
    .refresh i{
        display: block;
        margin-left: 15px;
        width: 14px;
        height: 16px;
        background-image: url(../../assets/images/refresh_icon.png);
        float: right;
        margin-top: 7px;
    }
    .refresh:hover i{
        transform: rotateZ(50deg)
    }
    .btnBox{
        margin-top: 60px;
    }
    .btnBox div{
        width: 240px;
        height: 50px;
        line-height: 50px;
        font-size: 16px;
        border-radius: 4px;
        color: #ffffff;
        cursor: pointer;
    }
    .btnBox .loginBtn{
        background: #dd4fb1;
    }
    .btnBox .userReset{
        background: #999999;
    }

    .findPassword{
        text-align: center;
        margin-top: 28px;
        color: #4086e4;
        font-size: 14px;
        cursor: pointer;
    }
    .passwordTitle{
        font-size: 18px;
        color: #333333;
        text-align: center;
        height: 30px;
        line-height: 30px;
        margin-bottom: 30px;
    }
    .passwordTitle i{
        display: inline-block;
        width: 25px;
        height: 30px;
        background: url(../../assets/images/m_icon.png) no-repeat center;
        background-size: cover;
        vertical-align: top;
        margin-right: 15px;
    }
    .passwordTitleInfo{
        width: 400px;
        height: 40px;
        background-image: url(../../assets/images/bg1.png);
        background-position: left center;
        background-repeat: no-repeat;
        margin-bottom: 20px;
    }
    .passwordTitleInfo.on{
        background-image: url(../../assets/images/bg2.png);
    }
    .passwordTitleInfo p{
        width: 200px;
        float: left;
        color: #fff;
        line-height: 40px;
    }
    .findPasswordInfoBox,.resetPasswordInfo{
        text-align: left;
    }
    .findPasswordInfoBox input,.resetPasswordInfo input{
        width: 220px;
        height: 36px;
        line-height: 36px;
        font-size: 14px;
        color: #333;
        border: 1px solid #e5e5e5;
        padding: 0 5px;
        margin-bottom: 30px;
        margin-right: 15px;
    }
    .findPasswordInfoBox li{
        height: 66px;
    }
    .findPasswordInfoBox span{
        display: block;
        height: 36px;
        width: 98px;
        line-height: 36px;
        text-align: center;
        font-size: 14px;
        color: #999999;
        background-color: #e6e6e6;
        border-radius: 4px;
    }
    /* 644de3 */
    .findPasswordInfoBox b{
        width: 98px;
        height: 36px;
        background-color: #9181e6;
        border-radius: 4px;
        color: #fff;
        float: left;
        text-align: center;
        line-height: 36px;
        font-size: 14px;
        cursor: pointer;
    }
    .saveInfo{
        width: 220px;
        height: 32px;
        background-color: #9181e6;
	    border-radius: 4px;
        text-align: center;
        line-height: 32px;
        font-size: 14px;
        color: #fff;
        cursor: pointer;
    }
    .btn_back{
        position: absolute;
        bottom: 30px;
        right: 300px;
        width: 30px;
        height: 30px;
        background: url(../../assets/images/btn_back.png) no-repeat center;
    }


</style>
