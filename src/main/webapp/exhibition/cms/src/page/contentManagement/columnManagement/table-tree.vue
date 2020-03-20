<template>
    <div class="table-tree-box">
        <table class="table-row">
            <tr>
                <td width="3%">{{num+1}}</td>
                <td width="15%">{{item.columnName.length>10?item.columnName.substr(0,10):item.columnName}}<i v-show="item.cmsColumnVos&&item.cmsColumnVos.length>0" @click="clickState(item)" title="展开/关闭下架栏目">{{item.show}}</i></td>
                <td width="7%">{{item.columnType.typeName}}</td>
                <td width="10%">{{item.updateTime.substr(0,10)}}</td>
                <td width="10%">{{item.updateUserName}}</td>
                <td width="5%">
                    <em style="color:#0a8c15" v-if="item.state==0?true:false">启用</em>
                    <em style="color:#cb1820" v-else>禁用</em>
                </td>
                <td width="12%">{{item.departname}}</td>
                <td width="6%"  @dblclick="editable(item)" title="双击开始编辑">
                    <span v-show="!item.editing">{{item.sort==null?'null':item.sort}}</span>
                    <input type="number" style="border:none;height:30px;background:none;width:100%;max-width:100px;ime-mode:disabled;background: #ccc;" v-model="item.sort" v-show="item.editing" @blur="commitChanges(item,item.id,item.sort)" onpaste="return false" ondragenter="return false" oninput="javascript:this.value=this.value.replace(/[^\d]/g,'')">
                </td>
                <td width="26%" class="buttonBox">
                    <el-button size="mini" type="primary" plain @click="addColumn(item.columnId,item.columnName)">添加子栏目</el-button>
                    <el-button size="mini" type="primary" plain @click="editorColumn(item.id)">编辑</el-button>
                    <el-button size="mini" type="warning" plain @click="delbefor(item.id)" v-if="item.columnProperty==1?false:true">删除</el-button>
                    <el-button size="mini" type="warning" plain @click="delbefor(item.id)" v-else disabled>删除</el-button>
                    <el-button size="mini" type="primary" plain v-if="item.state==0?false:true" @click="isForbidden(item.id,item.state,item.columnProperty)">启用</el-button>
                    <el-button size="mini" type="danger" plain v-else @click="isForbidden(item.id,item.state,item.columnProperty)">禁用</el-button>
                </td>
            </tr>
        </table>
        <div v-show="item.showState">
            <table-tree v-for="(v,i) in cmsColumnVos"
                :key="i"
                :item="v"
                :num="i"
                :lsit.sync="cmsColumnVos"
                @editable="editable"
                @commitChanges="commitChanges"
                @addColumn="addColumn"
                @clickState="clickState"
                @editorColumn="editorColumn"
                @delbefor="delbefor"
                @isForbidden="isForbidden"
                ></table-tree>
        </div>
    </div>
</template>
<script>
    export default {
        name:'table-tree',
        props: ['item', 'num','list'],
        data(){
            return{
                cmsColumnVos:[],
            }
        },
        created(){
            if(this.item.cmsColumnVos&&this.item.cmsColumnVos.length>0){
                this.cmsColumnVos = this.item.cmsColumnVos;
            }
        },
        mounted(){

        },
        methods:{
            //新增栏目
            addColumn:function(id,name){
                this.$emit('addColumn',id,name)
            },
            // 编辑栏目
            editorColumn:function(id){
                this.$emit('editorColumn',id)
            },
            // 删除栏目
            delbefor:function (id) {
                this.$emit('delbefor',id)
            },
            // 栏目禁用
            isForbidden:function(id,state,columnProperty){
                this.$emit('isForbidden',id,state,columnProperty)
            },
            editable(item){
                this.$emit('editable',item)
            },
            // 状态修改
            clickState(item){
                this.$emit('clickState',item)
            },
            commitChanges(item,id,sort){
                this.$emit('commitChanges',item,id,sort)
            }
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