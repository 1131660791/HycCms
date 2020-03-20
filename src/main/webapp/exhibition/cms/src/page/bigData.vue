<template>
    <div class="dataBox">
        <div class="contentBox">
            <div class="totalData clearfix">
                <p class="title">汇总数据</p>
                <div class="classifyBox">
                    <div class="classify fl">
                        <div class="text">
                            <p>500</p>
                            <span>文章发布量</span>
                        </div>
                        <div class="icon">
                            <img src="../assets/images/01.png" alt="">
                        </div>
                    </div>
                    <div class="classify fl">
                        <div class="text">
                            <p>608</p>
                            <span>视频发布量</span>
                        </div>
                        <div class="icon">
                            <img src="../assets/images/02.png" alt="">
                        </div>
                    </div>
                    <div class="classify fl">
                        <div class="text">
                            <p>436</p>
                            <span>入住医院数</span>
                        </div>
                        <div class="icon">
                            <img src="../assets/images/03.png" alt="">
                        </div>
                    </div>
                    <div class="classify fl">
                        <div class="text">
                            <p>403</p>
                            <span>入驻医生数</span>
                        </div>
                        <div class="icon">
                            <img src="../assets/images/04.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="clearfix">
                <div class="redayQuantity statistics fl">
                    <!-- 数据显示 -->
                    <p class="title">阅读统计</p>
                    <div id="readStatistics" style="width:100%; height:380px"></div>
                </div>
                <div class="playQuantity statistics fr">
                    <p class="title">播放统计</p>
                    <div id="playStatistics" style="width:100%; height:380px"></div>
                </div>
            </div>
            <div class="visitors">
                <p class="title">访客量</p>
                <div id="visitors" style="width:100%; height:420px"></div>
            </div>
        </div>

    </div>
</template>

<script>
    import echarts from 'echarts'
    export default {
        mounted(){
            // 基于准备好的dom，初始化echarts实例
            var readStatistics = echarts.init(document.getElementById('readStatistics'));
            var playStatistics = echarts.init(document.getElementById('playStatistics'));
            var visitors = echarts.init(document.getElementById('visitors'));
            // 绘制图表
            readStatistics.setOption({
                title: { text: '' },
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: ["2018-6","2018-7","2018-8","2018-9","2018-10","2018-11"],
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                series: [{
                    data: [400, 600, 628, 520, 630, 412],
                    type:'line',
                    lineStyle:{
                        color:['#f37c85']
                    },
                    itemStyle:{
                        color:['#c02']
                    },
                    areaStyle:{
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#d68262'
                        }, {
                            offset: 1,
                            color: '#ffe'
                        }]),
                    },
                    smooth: true
                }]
            });
            playStatistics.setOption({
                title: { text: '' },
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: ["2018-6","2018-7","2018-8","2018-9","2018-10","2018-11"],
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                series: [{
                    data: [3341, 4388, 2677, 4388, 5300, 7020],
                    type:'line',
                    lineStyle:{
                        color:['#54c0f2']
                    },
                    itemStyle:{
                        color:['#9676f1']
                    },
                    areaStyle:{
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#54c0f2'
                        }, {
                            offset: 1,
                            color: '#ffe'
                        }]),
                    },
                    smooth: true
                }]
            });
            visitors.setOption({
                title: { text: '' },
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: ["2017-12","2018-1","2018-2","2018-3","2018-4","2018-5","2018-6","2018-7","2018-8","2018-9","2018-10","2018-11"],
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine:{
                        lineStyle:{
                            color:'#999',
                        }
                    }
                },
                series: [{
                    data: [1125, 2533, 2377, 2488, 3300, 4020, 3803, 3488, 3677, 4388, 5300, 6020],
                    type:'line',
                    lineStyle:{
                        color:['#9676f1']
                    },
                    itemStyle:{
                        color:['#9676f1']
                    },
                    areaStyle:{
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#9676f1'
                        }, {
                            offset: 1,
                            color: '#ffe'
                        }]),
                    },
                    smooth: true
                }]
            });
        },
    }
</script>

<style scoped>
    .dataBox{
        padding: 20px;
        height: calc(100% - 90px);
        background: #f0f0f0;
    }
    .contentBox{
        height: 100%;
        border: 1px solid #eee;
        box-shadow: 0 0 10px #ddd;
        background: #fff;
        padding: 10px;
        overflow-y: scroll;
    }
    .totalData{
        margin-bottom: 30px;
    }
    .title{
        height: 30px;
        line-height: 30px;
        text-align: left;
        font-size: 16px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
    }
    .classify{
        width: 24%;
        margin: 0 0.5%;
        height: 120px;
        background: #54c0f2;
        border-radius: 4px;
        color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .classify p{
        font-size: 30px;
        margin-bottom: 6px;
    }
    .text{
        margin-right: 15px;
    }
    .classify:nth-child(2){background-color: #f37c85;}
    .classify:nth-child(3){background-color: #27cfbd;}
    .classify:nth-child(4){background-color: #9676f1;}
    .statistics{
        width: 45%;
    }


</style>