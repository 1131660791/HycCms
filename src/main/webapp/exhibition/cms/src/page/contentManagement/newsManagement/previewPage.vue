<template>
    <div class="previewPage">

    </div>
</template>

<script>
    export default {
        name:'previewPage',
        data(){
            return {

            }
        },
        mounted:function(){

        },
        methods:{
            previewPage:function(){
                // 发起请求
                this.$axios({
                    method:'post',
                    url:'/HycCms/news/findNews.do',
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'
                    },
                    data: this.$qs.stringify({
                        newsId:this.newsId
                    })
                })
                .then(res => {
                    console.log(res);

                    // this.$message({
                    //     message: res.data.msg,
                    //     type: res.data.flag=='error'?'error':'success',
                    //     duration: 1500
                    // })
                    // if (res.data.flag == 'success') {
                    //     this.isPreview = true;
                    //     // setTimeout(() => { this.$router.go(-1) }, 1500);
                    // }
                })
                .catch(error=>{
                    console.log(error);
                })

            },
        }
    }
</script>

<style scoped>

</style>