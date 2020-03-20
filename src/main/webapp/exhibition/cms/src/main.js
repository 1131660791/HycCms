import Vue from 'vue'
// import Vuex from 'vuex'
import store from './vuex/store'
import router from './router'


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

Vue.use(VueQuillEditor).use(ElementUI)
import '../src/common/reset.css'
import '../src/common/conmon.scss'

Vue.config.productionTip = false

import App from './App'

var vm = new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})
