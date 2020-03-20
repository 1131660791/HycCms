<template>
    <div class="table-tree-box">
        <table class="table-row">
            <tr>
                <td width="3%">{{num+1}}</td>
                <td width="20%">{{item.columnName}}<i v-show="item.defaultColumnVos&&item.defaultColumnVos.length>0" @click="clickState(item)" title="展开/关闭下架栏目">{{item.show}}</i></td>
                <td width="10%">{{item.columnType.typeName}}</td>
                <td width="10%">{{item.updateTime.substr(0,10)}}</td>
                <td width="15%">{{item.updateUserName}}</td>
                <td width="6%">
                    <em style="color:#0a8c15" v-if="item.state==0?true:false">启用</em>
                    <em style="color:#cb1820" v-else>禁用</em>
                </td>
                <td width="6%"  @dblclick="editable(item)" title="双击开始编辑">
                    <span v-show="!item.editing">{{item.sort==null?'null':item.sort}}</span>
                    <input type="number" style="border:none;height:30px;background:none;width:100%;max-width:100px;ime-mode:disabled;background: #ccc;" v-model="item.sort" v-show="item.editing" @blur="commitChanges(item,item.id,item.sort)" onpaste="return false" ondragenter="return false">
                </td>
                <td width="30%" class="buttonBox">
                    <el-button size="mini" type="primary" plain @click="addDefault(item.id,item.columnName,'add')">添加子栏目</el-button>
                    <el-button size="mini" type="primary" plain @click="addDefault(item.id,'','editor')">编辑</el-button>
                    <el-button size="mini" type="warning" plain @click="del(item.id)">删除</el-button>
                </td>
            </tr>
        </table>
        <div v-show="item.showState">
            <table-tree v-for="(item,i) in item.defaultColumnVos"
                :key="i"
                :item="item"
                :num="i"
                @commitChanges="commitChanges"
                @clickState="clickState"
                @editable="editable"
                @addDefault="addDefault"
                @del="del"
                @isForbidden="isForbidden"
                ></table-tree>
        </div>
    </div>
</template>

<script>
    export default {
        name:'table-tree',
        props: ['item', 'num'],
        data(){
            return{

            }
        },
        methods:{
            //新增栏目
            addDefault:function(id,name,type){
                this.$emit('addDefault',id,name,type)
            },
            // 删除栏目
            del:function (id) {
                this.$emit('del',id)
            },
            // 栏目禁用
            isForbidden:function(id,state){
                this.$emit('isForbidden',id,state)
            },
            // 状态修改
            clickState(item){
                this.$emit('clickState',item)
                console.log(item);

            },
            commitChanges(item,id,sort){
                console.log(item);

                this.$emit('commitChanges',item,id,sort)
            },
            editable(item){
                this.$emit('editable',item)
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