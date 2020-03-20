import Vue from 'vue';//引用vue
import Vuex from 'vuex';//引用vuex
import actions from "./actions.js";
import getters from "./getters.js";

Vue.use(Vuex);//使用vuex
const state = {
    sysRolesList:[],
    platformList:[],
    hospitalList:[],
    roleId:'',
}

const mutations = {
    setSysRolesList(state, data){
        state.sysRolesList = data;
    },
    setPlatformList(state,data){
        state.platformList = data;
    },
    setHospitalList(state, data){
        state.hospitalList = data;
    },
    setRoleId(state, data){
        state.roleId = data;
    },

}

export default new Vuex.Store({////暴露Store对象
    state,
    getters,
    mutations,
    actions,
})