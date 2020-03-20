import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router);


export default new Router({
    routes: [
        {path: '/',redirect: 'login'},
        {path: '/login',component: () => import("@/page/login/login")},
{
    path: '/home',
        component: () => import("@/page/home"),
    meta: { title: '首页' },
    children: [
        { path: '', redirect: 'bigData'},
        {
            meta: { title: '数据显示' },
            path: 'bigData',
            component: () => import("@/page/bigData"),
},
    {
        meta: { title: '内容管理'},
        path: 'contentManagementIndex',
            component: () => import("@/page/contentManagement/contentManagementIndex"),
        children: [
        { path: '', redirect: 'news'},
        {
            name: 'banner',
            meta: {title: '广告管理'},
            path: 'banner',
            component: () => import("@/page/contentManagement/bannerManagement/banner"),
        children: [
            {path: '',redirect: 'bannerList'},
            {name: '广告列表',meta: {title: '广告列表'},path: 'bannerList',component: () => import("@/page/contentManagement/bannerManagement/bannerList")},
        {name: '新增广告',meta: {title: '新增广告'},path: 'addBanner',component: () => import("@/page/contentManagement/bannerManagement/addBanner")},
    ]
    },
        {
            name: 'news',
                meta: {title: '新闻管理'},
            path: 'news',
                component: () => import("@/page/contentManagement/newsManagement/news"),
            children: [
            {path: '',redirect: 'newsList'},
            {name: '新闻列表',meta: {title: '新闻列表'},path: 'newsList',component: () => import("@/page/contentManagement/newsManagement/newsList")},
            {name: '添加新闻',meta:{title:'添加新闻'},path:'addNews',component: () => import("@/page/contentManagement/newsManagement/addNews")},
        ]
        },
        {
            name: 'column',
                meta: {title: '栏目管理'},
            path: 'column',
                component: () => import("@/page/contentManagement/columnManagement/column"),
            children:[
            {path: '',redirect: 'columnList'},
            {name: '栏目列表',meta: {title: '栏目列表'},path: 'columnList',component: () => import("@/page/contentManagement/columnManagement/columnList")},
            {name: '新增栏目',meta: {title: '新增栏目'},path: 'addColumn',component: () => import("@/page/contentManagement/columnManagement/addColumn")},
            {name: '编辑栏目',meta: {title: '编辑栏目'},path: 'editorColumn',component: () => import("@/page/contentManagement/columnManagement/editorColumn")},
        ]
        },
        {
            name: 'defaultIndex',
                meta: { title: '默认栏目管理' },
            path: 'defaultIndex',
                component: () => import("@/page/contentManagement/defaultManagement/default"),
            children: [
            { path: '', redirect: 'defaultList' },
            { name: '默认栏目列表', meta: { title: '默认栏目列表' }, path: 'defaultList', component: () => import("@/page/contentManagement/defaultManagement/defaultList") },
            { name: '新增默认栏目', meta: { title: '新增默认栏目' }, path: 'addDefault', component: () => import("@/page/contentManagement/defaultManagement/addDefault") },
        ]
        },
        {
            name: 'video',
                meta: { title: '视频管理' },
            path: 'video',
                component: () => import('@/page/contentManagement/videoManagement/video.vue'),
            children: [
            { path: '', redirect: 'videoList' },
            { name: '视频列表', meta: { title: '视频列表' }, path: 'videoList', component: () => import('@/page/contentManagement/videoManagement/videoList.vue') },
            { name: '新增视频', meta: { title: '新增视频' }, path: 'addVideo', component: () => import('@/page/contentManagement/videoManagement/addVideo.vue') },
        ]
        },
        {
            name: 'learning',
                meta: { title: '学习资料管理' },
            path: 'learning',
                component: () => import('@/page/contentManagement/learningManagement/learning.vue'),
            children: [
            { path: '', redirect: 'learningList' },
            { name: '学习资料列表', meta: { title: '学习资料列表' }, path: 'learningList', component: () => import('@/page/contentManagement/learningManagement/learningList.vue') },
            { name: '上传资料', meta: { title: '上传资料' }, path: 'learningDetailes', component: () => import('@/page/contentManagement/learningManagement/learningDetailes.vue') },
        ]
        }
    ]
    },{
    meta: {title: '工作管理'},
    path: 'workManagementIndex',
        component: () => import("@/page/workManagement/workManagementIndex"),
        children:[
        { path: '', redirect: 'server' },
        {
            name: 'server',
            meta: {
                title: '医生服务'
            },
            path: 'server',
            component: () => import("@/page/workManagement/serverManagement/server"),
    children: [
        {path: '',redirect: 'serverList'},
        {name: 'serverList',meta: {title: '服务列表'},path: 'serverList',component: () => import("@/page/workManagement/serverManagement/serverList")},
    {name:'doctorServerDetails',meta:{title:'医生服务详情'},path:'doctorServerDetails',component: () => import("@/page/workManagement/serverManagement/doctorServerDetails")}
]
},{
        name: 'course',
            meta: {
            title: '课程安排'
        },
        path: 'course',
            component: () => import("@/page/workManagement/ArrangeCourse/course"),
            children: [
            {path: '',redirect: 'courseList'},
            {name: 'courseList',meta: {title: '课程列表'},path: 'courseList',component: () => import("@/page/workManagement/ArrangeCourse/courseList")},

    ]
    },
]
},{
    meta: {title: 'cms系统管理'},
    path: 'cmsSystemManagementIndex',
        component: () => import("@/page/cmsSystemManagement/cmsSystemManagementIndex"),
        children:[
        { path: '', redirect: 'platForm' },
        {
            name: 'platForm',
            meta: {title: '平台管理'},
            path: 'platForm',
            component: () => import("@/page/cmsSystemManagement/platFormManagement/platForm"),
    children: [
        { path: '', redirect: 'platFormList'},
        { name: '平台列表', meta: { title: '平台列表' }, path: 'platFormList', component: () => import("@/page/cmsSystemManagement/platFormManagement/platFormList")},
    { name: '平台详情', meta: { title: '平台详情' }, path: 'platFormDetaile', component: () => import("@/page/cmsSystemManagement/platFormManagement/platFormDetaile") },
]
},
    {
        name: 'rolePlatForm',
            meta: { title: '用户单位管理' },
        path: 'rolePlatForm',
            component: () => import("@/page/cmsSystemManagement/rolePlatFormManagement/rolePlatForm"),
        children: [
        { path: '', redirect: 'rolePlatFormList' },
        { name: '用户列表', meta: { title: '用户列表' }, path: 'rolePlatFormList', component: () => import("@/page/cmsSystemManagement/rolePlatFormManagement/rolePlatFormList") },
        { name: '用户详情', meta: { title: '用户详情' }, path: 'rolePlatdetaile', component: () => import("@/page/cmsSystemManagement/rolePlatFormManagement/rolePlatdetaile") },
    ]
    },
    {
        name: 'userUnit',
            meta: { title: '角色平台管理' },
        path: 'userUnit',
            component: () => import("@/page/cmsSystemManagement/userUnitManagement/userUnit"),
        children: [
        { path: '', redirect: 'userUnitList' },
        { name: '角色平台列表', meta: { title: '角色平台列表' }, path: 'userUnitList', component: () => import("@/page/cmsSystemManagement/userUnitManagement/userUnitList") },
        { name: '角色详情', meta: { title: '角色详情' }, path: 'userUnitDetaile', component:  () => import("@/page/cmsSystemManagement/userUnitManagement/userUnitDetaile") },
    ]
    }
]
}
]
},
]
})
