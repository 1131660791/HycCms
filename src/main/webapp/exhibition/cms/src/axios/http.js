/*引入资源请求插件*/
import axios from 'axios';
import qs from 'qs'
// 5s没响应则认为该请求失败
axios.defaults.timeout = 5000;
// 默认false 会导致后台接收到的同一用户的不同请求sessionid都不同,需要改为true
axios.defaults.withCredentials = true;
// 环境的切换
if (process.env.NODE_ENV == 'development') {
    // axios.defaults.baseURL = 'http://localhost:8090';}
    axios.defaults.baseURL = 'http://192.168.1.130:8081';}
else if (process.env.NODE_ENV == 'production') {
    axios.defaults.baseURL = '';
}
const Axios = axios.create({
    withCredentials: true, // 是否允许带cookie这些
    timeout: 600000,
    headers: { 'Content-Type': 'multipart/form-data' }
});
// http request 拦截器 所有请求发出前都需要执行以下代码
axios.interceptors.request.use(
    request => {
        request.data = qs.stringify(request.data); //这里就用qs对data就行处理
        request.headers = {
            'Content-Type': 'application/x-www-form-urlencoded' //设置header覆盖content-type
        };
        return request
    },
    err => {
        return Promise.reject(err)
    }
)
// http response 拦截器
axios.interceptors.response.use(
    res => {
        return res
    },
    err => {
        console.log(err);
        // if (err && err.response) {
        //     switch (err.response.status) {
        //         case 400: err.message = '请求错误(400)'; break;
        //         case 401: return history.push('/login'); break;
        //         case 403: err.message = '拒绝访问(403)'; break;
        //         case 404: err.message = '请求出错(404)'; break;
        //         case 408: err.message = '请求超时(408)'; break;
        //         case 500: err.message = '服务器错误(500)'; break;
        //         case 501: err.message = '服务未实现(501)'; break;
        //         case 502: err.message = '网络错误(502)'; break;
        //         case 503: err.message = '服务不可用(503)'; break;
        //         case 504: err.message = '网络超时(504)'; break;
        //         case 505: err.message = 'HTTP版本不受支持(505)'; break;
        //         default: err.message = `连接出错(${err.response.status})!`;
        //     }
        // } else {
        //     err.message = '连接服务器失败!'
        // }
        // message.error(err.message);
        return Promise.reject(err);
    }
)

// 这种写法是导出后可以通过在main.js中使用Vue.use(http) 挂载到vue实例上
export default {
    get: (url, params = {})=>{
        return new Promise((resolve, reject) => {
            axios.get(url, {params})
                .then(response => resolve(response))
                .catch(error => reject(error))
        })
    },
    post: (url, params = {})=>{
        return new Promise((resolve, reject) => {
            axios.post(url, params)
                .then(response => resolve(response))
                .catch(error => reject(error))
        })
    },
    axios: Axios
}
