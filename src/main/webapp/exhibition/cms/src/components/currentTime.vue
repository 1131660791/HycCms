<template>
    <div class="currentTime">
        <div v-show="times.timeCurrentStatus">
            <p>{{times.hour}}:{{times.min}}</p>
            <h2><span>{{times.month}}月{{times.day}}日</span> <strong>{{times.week}}</strong></h2>
        </div>
    </div>
</template>

<script>
    export default {
        name:'currentTime',
        data(){
            return {
                times:{
                    month:'',
                    day:'',
                    hour:'',
                    min:'',
                    week:'',
                    timeCurrentStatus:false
                },
            }
        },
        beforeMount () {
            var _this = this; //声明一个变量指向vue实例this,保证作用域一致
            this.timer = setInterval(function() {
                var weeks = new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");
                var time = new Date();
                var day = time.getDay();
                var month = time.getMonth()+1
                _this.times.month = month < 10 ? '0' + month : month
                var day = time.getDate();
                _this.times.day = day < 10 ? '0' + day : day;
                _this.times.hour = time.getHours();
                var min = time.getMinutes();
                if(min < 10){
                    _this.times.min = min == 0 ? '00' : '0' + min
                }else{
                    _this.times.min = min
                }
                _this.times.week = weeks[day];
                _this.times.timeCurrentStatus = true;

            }, 1000);
        },
        beforeDestroy () {
            if(this.timer) {
                clearInterval(this.timer);//在vue实例销毁钱，清除我们的定时器
            }
        }
    }
</script>

<style scoped>
    .currentTime{
        height: 108px;
        text-align: center;
        padding-top: 32px;
        box-sizing: border-box;
    }
    .currentTime *{
        font-weight: normal;
        font-size: 14px;
        line-height: 18px;
        color: #ffffff;
    }
</style>