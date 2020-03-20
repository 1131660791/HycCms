<template>
    <div class="table-tree-box">
        <table class="table-row">
            <tr>
                <td width="50px" style="text-align:center;">{{num+1}}</td>
                <td width="16%"><i v-show="item.columnBannerVos&&item.columnBannerVos.length>0" @click="clickState(item)" title="展开/关闭下架栏目">{{item.show}}</i>{{item.columnName}}</td>
                <td width="12%" :title="item.bannerTheme">{{item.bannerTheme&&item.bannerTheme.length>10?item.bannerTheme.substr(0,8)+'...':item.bannerTheme}}</td>
                <td width="16%">{{item.departname}}</td>
                <td width="12%">{{item.updateTime?item.updateTime.substr(0,10):''}}</td>
                <td width="10%">{{item.bannerCount}}</td>
                <td width="12%">{{item.userName}}</td>
                <td class="buttonBox" width="18%">
                    <el-button size="mini" type="primary" plain @click="addBtn(item.columnId)" v-if="item.bannerCount==0">添加</el-button>
                    <el-button size="mini" type="primary" plain @click="preview(item.columnId)" v-if="item.bannerCount>0">预览</el-button>
                    <el-button size="mini" type="primary" plain @click="addBtn(item.columnId)" v-if="item.bannerCount>0">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="sureDelBanner(item.columnId)" v-if="item.bannerCount>0">删除</el-button>
                </td>
            </tr>
        </table>
        <div v-show="item.showState">
            <treeBannerList
                v-for="(item,index) in columnBannerVos"
                :key="index"
                :item="item"
                :num="index"
                @addBtn="addBtn"
                @preview="preview"
                @clickState="clickState"
                @sureDelBanner="sureDelBanner"
            ></treeBannerList>
        </div>
    </div>
</template>
<script>
    export default {
        name:'treeBannerList',
        props: ['item', 'num'],
        data(){
            return{
                columnBannerVos:[],
            }
        },
        created(){
            if(this.item.columnBannerVos&&this.item.columnBannerVos.length>0){
                this.columnBannerVos = this.item.columnBannerVos;
            }
        },
        methods:{
            addBtn:function(id){
                this.$emit('addBtn',id)
            },
            preview:function(id){
                this.$emit('preview',id)
            },

            // 状态修改
            clickState(item){
                this.$emit('clickState',item)
            },
            sureDelBanner(id){
                this.$emit('sureDelBanner',id)
            },
        }
    }
</script>

<style scoped>
    .table-tree-box{
        width: 100%;
    }
    .table-tree-box table{
        border-spacing:0;
        width: 100%;
        text-align: left;
    }
    .table-row{
        height: 40px;
    }
    .table-row td{
        height: 40px;
        line-height: 40px;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        padding:  0 18px;
        position: relative;
    }
    .table-row td:nth-child(2){
        background-repeat: no-repeat;
        padding-left: 35px;
    }
    .table-row td i{
        position: absolute;
        display: block;
        width: 12px;
        height: 12px;
        top: 0;
        bottom: 0;
        left: 12px;
        margin: auto;
        line-height: 10px;
        text-align: center;
        color: #fff;
        background: #aaa;
        border-radius: 2px;
        cursor: pointer;
    }
    .table-tree-box .table-tree-box td:nth-child(2){
        background-image: url(../../../assets/images/j.png);
        background-position:40px center;
        padding-left: 70px;
    }
    .table-tree-box .table-tree-box .table-tree-box td:nth-child(2){
        background-image: url(../../../assets/images/j.png);
        background-position:60px center;
        padding-left: 90px;
    }
    .table-tree-box .table-tree-box .table-tree-box .table-tree-box td:nth-child(2){
        background-image: url(../../../assets/images/j.png);
        background-position:80px center;
        padding-left: 110px;
    }
    .table-tree-box .table-tree-box .table-tree-box .table-tree-box .table-tree-box td:nth-child(2){
        background-image: url(../../../assets/images/j.png);
        background-position:100px center;
        padding-left: 130px;
    }
    .columnList .table-row tr:hover{
        background-color: #e5e3e6;
    }
</style>