<template>
    <div class="serverDetails">
        <crumbs></crumbs>
        <div class="serverDetailsBox">
            <div class="title"><i></i>服务配置</div>
            <div class="serverDetailsContent">
                <div class="basicInfo bottomLine">
                    <div class="title"><i></i>基本信息</div>
                    <div class="basicInfoBox clearfix pl">
                        <div class="doctorLeftBox fl">
                            <div class="doctorInfoContent clearfix">
                                <div class="doctorPhoto fl">
                                    <img :src="userInfo.portrait" alt="">
                                </div>
                                <div class="doctorText fl">
                                    <p><span>医生名称：</span>{{userInfo.realname}}</p>
                                    <p><span>所属科室：</span>{{userInfo.departname}}</p>
                                    <p><span>所属医院：</span>{{userInfo.hospitalName}}</p>
                                    <p><span>擅长：</span>{{userInfo.expertGoodAt}}</p>
                                    <div class="doctorContactInfo clearfix">
                                        <p class="fl"><span>手机：</span>{{userInfo.mobilephone}}</p>
                                        <p class="fl"><span>办公室电话：</span>{{userInfo.officephone}}</p>
                                        <p class="fl"><span>专家职称：</span>{{userInfo.expertTitle}}</p>
                                        <p class="fl"><span>专家级别：</span>{{userInfo.expertLevel}}</p>
                                        <p class="fl"><span>身份证号：</span>{{userInfo.citizenNo}}</p>
                                        <p class="fl"><span>证书编号：</span>{{userInfo.empNo}}</p>
                                    </div>
                                </div>
                            </div>
                            <p class="descText"><span>专家介绍：</span>宅心仁厚首发高超，被市民誉为当地：仁医，曾代表当地区域参加人民大会堂关于医疗研究发言，宅心仁厚首发高超，被市民誉为当地：仁医，曾代表当地区域参加人民大会堂关于医疗研究发言。宅心仁厚首发高超，被市民誉为当地：仁医，曾代表当地区域参加人民大会堂关于医疗研究发言。宅心仁厚首发高超，被市民誉为当地。</p>
                        </div>
                        <div class="honerBox fr">
                            <div class="honerInfo"><img src="" alt=""></div>
                            <div class="honerInfo"><img src="" alt=""></div>
                        </div>
                    </div>
                </div>
                <div class="configInfoBox" v-if="configList && configList.length>0" v-for="item in configList" :key="item.id">
                    <div class="doctorConfig bottomLine">
                        <div class="title"><i></i>配置<span class="fr">编辑</span></div>
                        <div class="doctorConfigBox clearfix pl">
                            <div class="platFormInfo">
                                <span>平台选择</span>
                                <select name="" id="" v-model="currentPlatFormId" @change="getPlatFormServerList(currentPlatFormId)">
                                    <option :value="item.id" v-for="item in platformList" :key="item.id">{{item.platformName}}</option>
                                </select>
                            </div>
                            <div class="combo" v-for="item in platformServicesArr" :key="item.id">
                                <div class="comboTitle">{{item.serviceName}}</div>
                                <ul class="clearfix" v-if="item.platformServiceCombos && item.platformServiceCombos.length>0">
                                    <li v-for="(v,i) in item.platformServiceCombos" :key="v.id">
                                        <p>套餐{{i+1}}</p><span>￥{{v.packageMoney}}/{{v.packageTime}}{{v.packageTimeUnit}}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="publishSet bottomLine">
                        <div class="title"><i></i>发布设置</div>
                        <div class="publishBox pl clearfix">
                            <div class="etcInfo">
                                <p style="margin-bottom:10px;">发布时间</p>
                                <p v-if="editorConfigStatus">{{platformService.publicTime}}</p>
                                <el-date-picker
                                    v-else
                                    v-model="platformService.publicTime"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="2018-1-1"
                                    default-time="00:00:00">
                                </el-date-picker>
                            </div>
                            <div class="etcInfo">
                                <p style="margin-bottom:10px;">过期时间</p>
                                <p v-if="editorConfigStatus">{{platformService.expireTime}}</p>
                                <el-date-picker
                                    v-else
                                    v-model="platformService.expireTime"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="2030-12-31"
                                    default-time="23:59:59">
                                </el-date-picker>
                            </div>
                            <div class="etcInfo">
                                <p>权重</p>
                                <p>123</p>
                                <input type="number" placeholder="请输入权重">
                            </div>
                        </div>
                    </div>
                    <div class="recommendBox bottomLine clearfix">
                        <div class="title"><i></i>推荐位置</div>
                        <div class="pl etcInfo">
                            <span>栏目选择：</span>
                            <p v-if="editorConfigStatus">platformService.columnId</p>
                            <select name="" id="" v-model="platformService.columnId" v-else>
                                <option :value="item.id" v-for="item in columnsArr" :key="item.id">{{item.columnName}}</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="addConfig" v-if="addConfigStatus">新增配置</div>
                <div class="addConfigBox" v-else>
                    <div class="doctorConfig bottomLine">
                        <div class="title"><i></i>配置<span class="fr">编辑</span></div>
                        <div class="doctorConfigBox clearfix pl">
                            <div class="platFormInfo">
                                <span>平台选择</span>
                                <select name="" id="" v-model="currentPlatFormId" @change="getPlatFormServerList(currentPlatFormId)">
                                    <option :value="item.id" v-for="item in platformList" :key="item.id">{{item.platformName}}</option>
                                </select>
                            </div>
                            <div class="combo" v-for="item in platformServicesArr" :key="item.id">
                                <div class="comboTitle">{{item.serviceName}}</div>
                                <ul class="clearfix" v-if="item.platformServiceCombos && item.platformServiceCombos.length>0">
                                    <li v-for="(v,i) in item.platformServiceCombos" :key="v.id">
                                        <p>套餐{{i+1}}</p><span>￥{{v.packageMoney}}/{{v.packageTime}}{{v.packageTimeUnit}}</span>
                                    </li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="publishSet bottomLine">
                        <div class="title"><i></i>发布设置</div>
                        <div class="publishBox pl clearfix">
                            <div class="etcInfo">
                                <p style="margin-bottom:10px;">发布时间</p>
                                <el-date-picker
                                    v-model="platformService.publicTime"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="2018-1-1"
                                    default-time="00:00:00">
                                </el-date-picker>
                            </div>
                            <div class="etcInfo">
                                <p style="margin-bottom:10px;">过期时间</p>
                                <p>{{platformService.expireTime}}</p>
                                <el-date-picker
                                    v-model="platformService.expireTime"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="2030-12-31"
                                    default-time="23:59:59">
                                </el-date-picker>
                            </div>
                            <div class="etcInfo">
                                <p>权重</p>
                                <input type="number" placeholder="请输入权重">
                            </div>
                        </div>
                    </div>
                    <div class="recommendBox bottomLine clearfix">
                        <div class="title"><i></i>推荐位置</div>
                        <div class="pl etcInfo">
                            <span>栏目选择：</span>
                            <select name="" id="" v-model="platformService.columnId">
                                <option :value="item.id" v-for="item in columnsArr" :key="item.id">{{item.columnName}}</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="operation clearfix">
                <span class="fr publish" @click="addDoctorConfigInfo(0)">发布</span>
                <span class="fr save" @click="addDoctorConfigInfo(1)">保存草稿</span>
            </div>
        </div>
    </div>
</template>
<script>
    import crumbs from "../../../components/crumbs";
    import { api_getDoctorInfo, api_getPlatFormServerList, api_getDoctorServerInfo, api_saveDoctorServerInfo } from "../../../axios/apis.js";
    export default {
        name:'serverDetails',
        components:{
            crumbs
        },
        data(){
            return{
                userInfo:'',
                roleId:'',
                platformList:[],        // 平台
                currentPlatFormId:'',   // 默认当前平台
                platformServicesArr:[], // 平台服务
                columnsArr:[],          // 推荐位置

                configList:[],          // 平台已配置服务

                platformServiceJson:{
                    state:'0',           // 0:发布:1:草稿
                    doctorId:'',        // 医生Id
                    hospitalId:'',      // 医院id
                    doctorName:'',      // 医生名称
                    hospitalName:'',    // 医院名称
                    platformService:[], //
                },
                platformService:{
                    platformId:'',
                    columnId:'',
                    serviceId:'',
                    comboId:'',
                    publicTime:'',
                    expireTime:'',
                    sort:''
                },
                comboId:[],
                addConfigStatus: false,
                editorConfigStatus: false,
            }
        },
        mounted(){
            this.platformServiceJson.doctorId = this.$route.query.doctorId;
            this.platformServiceJson.hospitalId = this.$route.query.hospitalId;
            this.roleId = JSON.parse(sessionStorage.roleId);
            this.platformList = JSON.parse(sessionStorage.platformList);
            this.currentPlatFormId = this.platformList[0].id;

            this.getDoctorInfo();
            this.getPlatFormServerList(this.currentPlatFormId);
        },
        methods:{
            // 获取医生信息
            getDoctorInfo(){
                api_getDoctorInfo({
                    roleId: this.roleId,
                    hospitalId: this.platformServiceJson.hospitalId,
                    doctorId: this.platformServiceJson.doctorId
                })
                .then(res => {
                    if (res.flag=='success') {
                        this.userInfo = res.data.obj.sysUser;
                        this.platformServiceJson.hospitalName = res.data.obj.sysUser.hospitalName;
                        this.platformServiceJson.doctorName = res.data.obj.sysUser.realname;
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 获取平台服务列表
            getPlatFormServerList(id){
                api_getPlatFormServerList({
                    platformId:id,
                    hospitalId:this.platformServiceJson.hospitalId
                })
                .then(res => {
                    if (res.flag=='success') {
                        this.columnsArr = res.data.obj.columns;
                        this.platformServicesArr = res.data.obj.platformServices;
                        this.platformService.columnId = this.columnsArr[0].id;
                        this.getDoctorServerInfo()
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 查询医生已有套餐
            getDoctorServerInfo(){
               api_getDoctorServerInfo({
                    doctorId:this.platformServiceJson.doctorId,
                    roleId: this.roleId
                })
                .then(res => {
                    console.log(res);
                    if (res.flag=='success') {
                        console.log(res.obj);
                    }
                })
                .catch(error=>{
                    console.log(error);
                })
            },
            // 保存新增或修改 前 修改数据结构
            addDoctorConfigInfo(state){
                this.platformServiceJson.state = state;
                if (this.comboId&&this.comboId.length>0){ this.$message({message: '请选择服务套餐',type: 'warning',duration: 1500});return;}
                this.platformService.comboId = this.comboId;
                if (!this.platformId){ this.$message({message: '请选择平台',type: 'warning',duration: 1500});return;}
                if (!this.platformId){ this.$message({message: '请选择平台',type: 'warning',duration: 1500});return;}
                this.platformServiceJson.platformService = this.platformService;
            },
            // 保存新增或修改信息
            saveDoctorServerInfo(){
                api_saveDoctorServerInfo({
                    roleId:'',
                    platformServiceJson: this.platformServiceJson,
                })
                .then(res => {
                    console.log(res);

                })
                .catch(error=>{
                    console.log(error);
                })
            },

        },

    }
</script>

<style scoped>
    .serverDetails{
        text-align: left;
        height: 100%;
        overflow-y: scroll;
    }
    .serverDetailsBox{
        padding-top: 10px;
        max-width: 1100px;
        padding-bottom: 30px;
    }
    .title {
        text-align: left;
        font-size: 16px;
        color: #333333;
        height: 20px;
        line-height: 20px;
        margin-bottom: 15px;
        margin-left: 5px;
    }
    .title i {
        display: block;
        float: left;
        width: 6px;
        height: 6px;
        margin-top: 6px;
        border-radius: 50%;
        background-color: #6b61f2;
        margin-right: 10px;
    }
    .serverDetailsContent{
        padding-top: 5px;
        padding-left: 40px;
    }
    .bottomLine{
        border-bottom: 1px solid #ccc;
        padding-bottom: 20px;
        margin-bottom: 30px;
    }
    .pl{
        padding-left: 15px;
    }
    .basicInfoBox{

    }
    .doctorPhoto{
        width: 240px;
        height: 240px;
        border: 1px dashed #ccc;
        background: #eee;
        margin-right: 30px;
        text-align: center;
    }
    .doctorPhoto img{
        height: 100%;
    }
    .doctorText{
        margin-bottom: 30px;
    }
    .doctorLeftBox p{
        font-size: 14px;
        color: #333;
        line-height: 30px;
    }
    .doctorLeftBox p span{
        font-weight: bold;
    }
    .doctorContactInfo{
        border-top: 2px solid #6b61f2;
        margin-top: 12px;
        padding-top: 10px;
        width: 450px;
    }
    .doctorContactInfo p{
        width: 50%;
        padding-right: 15px;
    }
    p.descText{
        font-size: 14px;
        line-height: 20px;
        width: 720px;
    }
    .honerBox{

    }
    .honerInfo{
        width: 300px;
        height: 140px;
        border: 1px dashed #ccc;
        background: #eee;
        margin-bottom: 35px;
    }
    .honerInfo:last-child{
        margin-bottom: 0;
    }
    .doctorConfig{

    }
    .platFormInfo{
        height: 32px;
        margin-bottom: 15px;
    }
    .platFormInfo span,.platFormInfo select{
        float: left;
        border: 1px solid #ccc;
        height: 32px;
        line-height: 32px;
        font-size: 14px;
        padding: 0 5px;
    }
    .platFormInfo span{
        border-right: none;
    }
    .doctorConfig .title span{
        font-size: 14px;
        color: #6b61f2;
        cursor: pointer;
    }
    .combo{
        width: 45%;
    }
    .comboTitle{
        border-radius: 4px;
        background: #6b61f2;
        height: 32px;
        color: #fff;
        text-align: center;
        line-height: 32px;
        margin-bottom: 10px;
    }
    .combo:nth-child(2n){
        float: left;
    }
    .combo:nth-child(2n+1){
        float: right;
    }
    .combo li{
        height: 60px;
        padding-top: 8px;
        float: left;
        width: 32%;
        margin-right: 2%;
        font-size: 16px;
        text-align: center;
        background: #ccc;
        margin-bottom: 8px;
        border-radius: 4px;
        cursor: pointer;
    }
    .combo li:nth-child(3n){
        margin-right: 0;
    }
    .combo li span{
        font-size: 14px;
        margin-top: 3px;
    }
    .configInfoBox.on .combo li.on,
    .configInfoBox.on .combo li:hover,
    .addConfigBox .combo li.on,
    .addConfigBox .combo li:hover{
        background: #6b61f2;
        color: #fff;
    }
    .etcInfo{
        float: left;
        width: 30%;
        margin-right: 5%;
    }
    .etcInfo:nth-child(3n){
        margin-right: 0;
    }
    .etcInfo p{
        font-size: 14px;
        color: #333;
    }
    .etcInfo input,.etcInfo select{
        width: 100%;
        height: 40px;
        border: 1px solid #ccc;
        margin-top: 6px;
        padding: 0 5px;
        font-size: 14px;
    }
    .recommendBox .etcInfo{
        width: 100%;
    }
    .recommendBox .etcInfo span{
        float: left;
        line-height: 40px;
        height: 40px;
        border: 1px solid #ccc;
        border-right: none;
        padding: 0 5px;
        font-size: 14px;
    }
    .recommendBox .etcInfo select{
        width: 180px;
        margin-top: 0;
    }

    .addConfig{
        font-size: 14px;
        color: #6b61f2;
        cursor: pointer;
    }
    .operation{
        height: 60px;
        margin-top: 20px;
    }
    .operation span{
        width: 160px;
        height: 40px;
        border-radius: 4px;
        background: #9992fd;
        text-align: center;
        line-height: 40px;
        margin-left: 15px;
        color: #fff;
        cursor: pointer;
    }
    .operation .publish{
        background-color: #29a2fb;
    }













</style>