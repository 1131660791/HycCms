<template>
    <div id="sideNavigation" class="fl">
        <currentTime></currentTime>
        <div class="sideNavigationBox">
            <ul>
                <li :class="{on:showStatus}">
                    <div class="menuBox" v-on:click="showSubtopicList()">
                        <img src="../assets/images/banner_icon.png" alt="">
                        <p>Banner</p>
                    </div>
                    <div class="subtopic">
                        <span v-for="(item,index) in columnList" :key="index">
                            <i></i>
                            <a @click="bannerList(item.columnId)">{{item.columnName}}</a>
                        </span>
                    </div>
                </li>
                <li class="on">
                    <div class="menuBox">
                        <img src="../assets/images/news_icon.png" alt="">
                        <p>新闻管理</p>
                    </div>
                    <div class="subtopic">
                        <span v-for="(item,index) in columnList" :key="index">
                            <i></i>
                            <a @click="newsList(item.columnId)">{{item.columnName}}</a>
                        </span>
                    </div>
                </li>
                <li class="on">
                    <div class="menuBox">
                        <img src="../assets/images/column_icon.png" alt="">
                        <p>栏目管理</p>
                    </div>
                    <div class="subtopic">
                        <span v-for="(item,index) in columnList" :key="index">
                            <i></i>
                            <a @click="columnListInfo(item.columnId)">{{item.columnName}}</a>
                        </span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>


<script>
    import currentTime from "../components/currentTime";
    export default {
        name: 'sideNavigation',
        components:{ currentTime },
        data(){
            return {
                columnList:[
                    {
                        columnName:'远程医疗平台',
                        columnId:'1'
                    },
                    {
                        columnName:'官网平台',
                        columnId:'2'
                    },
                    {
                        columnName:'惠医通',
                        columnId:'3'
                    }
                ],
                showStatus:false,
            }
        },
        computed: {
			id() {
				return this.$route.params.id;
			},
			name() {
				return this.$route.params.name;
			}
		},
        mounted:function(){
            console.log(this.$parent.sideNav);

        },
        props:['sideNav'],
        methods: {
            showSubtopicList:function(){
                this.showStatus = this.showStatus?false:true;
                this.$router.push({ path: '/home/contentManagementIndex/banner/bannerList', query: { id: 1 }})
            },
            bannerList:function(id){
                this.$router.push({ path: '/home/contentManagementIndex/banner/bannerList', query: { id: id }})
            },
            newsList:function(id){
                this.$router.push({ path: '/home/contentManagementIndex/news/newsList', query: { id: id }})
            },
            columnListInfo:function(id){
                this.$router.push({ path: '/home/contentManagementIndex/column/columnList', query: { id: id }})
            }
        }
    }
</script>

<style scoped>

#sideNavigation {
  width: 206px;
  height: 100%;
  background-image: url(../assets/images/sidenavigation.jpg);
  background-repeat: no-repeat;
  background-position: left center;
  background-color: #120d58;
}
.sideNavigationBox {

}
.sideNavigationBox li {
  text-align: left;
}
.sideNavigationBox li img{
    float: left;
    margin-left: 20px;
    display: inline-block;
}
.sideNavigationBox li p{
    color: #fff;
    font-size: 16px;
    margin-right: 20px;
    height: 18px;
    line-height: 18px;
    margin-left: 52px;
    padding-right: 15px;
    background-image: url(../assets/images/l_r_icon.png);
    background-repeat: no-repeat;
    background-position: right center;
}
.menuBox{
    padding-top: 20px;
    height: 60px;
    box-sizing: border-box;
}
.menuBox:hover{
    background-color: #21013b;
}
.subtopic{
    display: none;
}
.subtopic span{
    padding-left: 52px;
    padding-right: 20px;
    height: 48px;
    line-height: 48px;
    font-size: 14px;
    position: relative;
    display: block;
    color: #fff;
}
.subtopic span i{
    display: none;
    position: absolute;
    left: 20px;
    top: 0;
    bottom: 0;
    margin: auto 0;
    width: 3px;
    height: 30px;
    background-color: #ab15bd;
}
.subtopic span.on{
    background-color: #21013b;
}
.subtopic span.on i{
    display: block;
}
.sideNavigationBox li.on .subtopic{
    display: block;
}
.subtopic span a{
    color: inherit;
    cursor: pointer;
}



</style>