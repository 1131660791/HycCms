<template>
    <div class="tipBox">
        <div class="reminderBox" v-show="reminderBox">
            <div class="message">
                <i></i>{{info}}
            </div>
        </div>
        <div class="alertBox" v-show="alert">
            {{info}}
        </div>
    </div>
</template>

<script>

    export default {
        name:'reminder',
        data(){
            return{
                alert:false,
                reminderBox:false,
                info:'',
            }
        },
        methods:{
            reminder:function(e){
                this.info = e;
                this.reminderBox = true;
                setTimeout(() => {
                    this.info = '';
                    this.reminderBox = false;
                }, 1500);
            },
            alertTip:function(e){
                this.info = e;
                this.alert = true;
                setTimeout(() => {
                    this.info = '';
                    this.alert = false;
                }, 2500);
            }
        }

    }
</script>

<style scoped>
    .reminderBox{
        position: fixed;
        width: 100%;
        height: 100%;
        z-index: 10;
        top: 0;
        left: 0;
        background-color: rgba(0,0,0,0.2);
    }
    .message{
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        margin: auto;
        width: 448px;
        height: 186px;
        border-radius: 12px;
        background-color: #ffffff;
        box-shadow: 0 0 10px #888;
        font-size: 24px;
        color: #333333;
        line-height: 186px;
    }
    .message i{
        display: inline-block;
        width: 70px;
        height: 70px;
        border-radius: 50%;
        background-image: url(../assets/images/success.png);
        background-repeat: no-repeat;
        background-position: center;
        vertical-align: middle;
        margin-right: 57px;
    }
    .alertBox{
        position: fixed;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        margin: auto;
        width: 280px;
        height: 60px;
        color: #333;
        font-size: 14px;
        line-height: 60px;
        border-radius: 6px;
        background-color: #fff;
        box-shadow: 0 0 10px #666;
    }
</style>