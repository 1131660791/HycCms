<template>
    <div class="crumbs">
        <i class="fl"></i>
        <el-breadcrumb class="fl" separator-class="el-icon-arrow-right" style="line-height:30px;">
            <!-- <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: columnUrl,query:{currentMenuIndex:currentMenuIndex,currentMenuId:currentMenuId,currentSideIndex:null,currentSideSecondIndex:null,fkPlatformId:null} }">{{menuName}}</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: socendColumnUrl,query:{currentMenuIndex:currentMenuIndex,currentMenuId:currentMenuId,currentSideIndex:currentSideIndex,currentSideSecondIndex:null,fkPlatformId:fkPlatformId} }" v-if="sideFristName">{{sideFristName}}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="platformName">{{platformName}}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="pageName">{{pageName}}</el-breadcrumb-item> -->
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="menuName">{{menuName}}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="sideFristName">{{sideFristName}}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="platformName">{{platformName}}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="pageName">{{pageName}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                levelList: null,

                currentMenuIndex: '',
                currentMenuId: '',
                currentSideIndex: '',
                currentSideSecondIndex: '',
                fkPlatformId: '',
                menuName:'',
                sideFristName:'',
                platformName:'',
                pageName:'',

                platformList:[],
                sideNav:[],
                menuList:[],

                columnUrl:'',
                socendColumnUrl:''
            }
        },
        created(){
            this.currentMenuIndex = this.$route.query.currentMenuIndex;
            this.currentMenuId = this.$route.query.currentMenuId;
            this.currentSideIndex = this.$route.query.currentSideIndex?this.$route.query.currentSideIndex:0;
            this.currentSideSecondIndex = this.$route.query.currentSideSecondIndex;
            this.fkPlatformId = this.$route.query.fkPlatformId;
            this.platformList = JSON.parse(sessionStorage.platformList);
            this.getBreadcrumb();
        },
        mounted() {

        },
        watch: {
            $route(to, from) {
                this.currentMenuIndex = to.query.currentMenuIndex;
                this.currentMenuId = to.query.currentMenuId;
                this.currentSideIndex = to.query.currentSideIndex?to.query.currentSideIndex:0;
                this.currentSideSecondIndex = to.query.currentSideSecondIndex;
                this.fkPlatformId = to.query.fkPlatformId;
                this.getBreadcrumb();
            }
        },
        methods: {
            getBreadcrumb() {
                this.menuList = JSON.parse(sessionStorage.menuList);
                this.menuName = this.menuList[this.currentMenuIndex].functionname;
                this.sideNav = JSON.parse(sessionStorage.sideNav);
                if(this.currentSideIndex != null && this.currentSideIndex != undefined){
                    this.sideFristName = this.sideNav[this.currentSideIndex].functionname;
                }
                if(this.fkPlatformId){
                    this.platformList.forEach(item=>{
                        if(item.id == this.fkPlatformId){
                            this.platformName = item.platformName;
                        }
                    })
                    let matched = this.$route.matched;
                    this.pageName = this.$route.name;
                }
                this.columnUrl = this.$route.matched[1].path;
                this.socendColumnUrl = this.$route.matched[2].path;
            }
        },
    }
</script>
<style scoped>
    .crumbs {
        width: 100%;
        height: 30px;
        line-height: 30px;
        margin-bottom: 10px;
        text-align: left;
        background-color: #fafafa;
    }
    .crumbs i {
        line-height: 30px;
        padding-left: 24px;
        width: 30px;
        height: 30px;
        background-image: url(../assets/images/crumbs.png);
        background-position: left center;
        background-repeat: no-repeat;
    }
    .crumbs span,.crumbs a{
        color: #666666;
        font-size: 12px;
    }
</style>



