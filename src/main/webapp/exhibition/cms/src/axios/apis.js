import http from './http'
let base = '/HycCms';

// 获取验证码
export const api_validateCode = params => http.axios.get(`${base}/login/validateCode.do`, params);
// 登录验证
export const api_signIn = params => http.post(`${base}/login/login.do`, params).then(res => res.data);
// 退出登录
export const api_loginOut = params => http.post(`${base}/login/logout.do`, params).then(res => res.data);

// 获取平台列表
export const api_getPlatformInfo = params => http.post(`${base}/platform/findRolePlatform.do`, params).then(res => res.data);
// 获取医院列表
export const api_getHospitalList = params => http.post(`${base}/userDepart/findUserDepart.do`, params).then(res => res.data);
// 获取栏目列表
export const api_getMenuList = params => http.post(`${base}/sysFunction/findRoleFunction.do`, params).then(res => res.data);

/* 工作管理 */
// 获取医生列表
export const api_getDoctorServerList = params => http.post(`${base}/platformService/findDoctorServices.do`, params).then(res => res.data);
// 禁止服务
export const api_disableServer = params => http.post(`${base}/platformService/forbidDoctorServices`, params).then(res => res.data);
// 获取医生信息
export const api_getDoctorInfo = params => http.post(`${base}/platformService/findDoctorService.do`, params).then(res => res.data);
// 获取平台服务列表
export const api_getPlatFormServerList = params => http.post(`${base}/platformService/findPlatformServiceInfo.do`, params).then(res => res.data);
// 查询医生已有套餐
export const api_getDoctorServerInfo = params => http.post(`${base}/platformService/findPlatformServiceInfo.do`, params).then(res => res.data);
// 保存新增或修改信息
export const api_saveDoctorServerInfo = params => http.post(`${base}/costConfiguration/saveDoctorPlatformService.do`, params).then(res => res.data);

/* 内容管理 */
// 查询新闻栏目类型
export const api_getNewsColumnType = params => http.post(`${base}/cmsColumn/findColumnByNews.do`, params).then(res => res.data);
// 获取当前平台下当前医院 的新闻列表
export const api_getNewsColumnList = params => http.post(`${base}/news/findNewss.do`, params).then(res => res.data);
// 删除文章
// 获取当前平台下当前医院 的新闻列表
export const api_delArtic = params => http.post(`${base}/news/deleteNews.do`, params).then(res => res.data);
// 根据平台机构查询栏目  用于文章/视频转载
export const api_findPlatformHospitalByColumn = params => http.post(`${base}/cmsColumn/findPlatformHospitalByColumn.do`, params).then(res => res.data);
// 请求转载相关信息
export const api_reprintedInfo = params => http.post(`${base}/news/reshipmentSkip.do`, params).then(res => res.data);
// 提交转载信息
export const api_reprintAjaxPost = params => http.post(`${base}/news/reshipmentNews.do`, params).then(res => res.data);
// 发布文章
export const api_publishAjax = params => http.post(`${base}/news/publishNews.do`, params).then(res => res.data);
// 权重修改
export const api_commitChanges = params => http.post(`${base}/news/updateNewsSort.do`, params).then(res => res.data);
// 获取文章信息
export const api_getArticleInfo = params => http.post(`${base}/news/findNews.do`, params).then(res => res.data);
// 保存或发布内容或新增或修改
export const api_submitNewsInfo = (url,params) => http.post(url, params).then(res => res.data);

// 获取当前平台下默认栏目列表
export const api_getPlatformDefaultColumnList = params => http.post(`${base}/defaultColumn/findDefaultColumns.do`, params).then(res => res.data);
// 获取当前平台下默认栏目权重修改
export const api_getPlatformDefaultCommitChanges = params => http.post(`${base}/defaultColumn/sortDefaultColumn.do`, params).then(res => res.data);
// 删除默认栏目
export const api_delDefaultColumn = params => http.post(`${base}/defaultColumn/deleteDefaultColumn.do`, params).then(res => res.data);
// 禁用栏目
export const api_isForbiddenDefaultColumn = params => http.post(`${base}/cmsColumn/updateColumn.do`, params).then(res => res.data);

// 获取默认栏目类型列表
export const api_getDefaultColumnTypeList = params => http.post(`${base}/cmsColumn/findColumnType.do`, params).then(res => res.data);
// 验证默认栏目code是否存在
export const api_verifyDefaultColumnCode = params => http.post(`${base}/defaultColumn/verifyDefaultColumnCode.do`, params).then(res => res.data);
// 获取默认栏目信息
export const api_getDefaultColumnInfo = params => http.post(`${base}/defaultColumn/findDefaultColumnsById.do`, params).then(res => res.data);
// 保存默认栏目信息
export const api_saveDefaultColumnInfo = (url,params) => http.post(url, params).then(res => res.data);

// 获取栏目列表
export const api_getColumnList = params => http.post(`${base}/cmsColumn/findColumns.do`, params).then(res => res.data);
// 栏目权重修改
export const api_changesColumnSort = params => http.post(`${base}/cmsColumn/sortColumn.do`, params).then(res => res.data);
// 删除栏目
export const api_delColumn = params => http.post(`${base}/cmsColumn/deleteColumn.do`, params).then(res => res.data);
// 栏目禁用
export const api_isForbiddenColumn = params => http.post(`${base}/cmsColumn/updateColumn.do`, params).then(res => res.data);
// 使用默认栏目
export const api_sureRefer = params => http.post(`${base}/cmsColumn/importDefaultColumn.do`, params).then(res => res.data);

// 验证栏目code是否存在
export const api_verifyColumnCode = params => http.post(`${base}/cmsColumn/verifyColumnCode.do`, params).then(res => res.data);
// 获取栏目类型列表
export const api_getColumnTypeList = params => http.post(`${base}/cmsColumn/findColumnType.do`, params).then(res => res.data);
// 保存栏目信息
export const api_saveAddColumnInfo = params => http.post(`${base}/cmsColumn/addColumn.do`, params).then(res => res.data);
// 获取当前栏目信息
export const api_getColumnInfo = params => http.post(`${base}/cmsColumn/findColumn.do`, params).then(res => res.data);
// 更新栏目信息
export const api_updataColumnInfo = params => http.post(`${base}/cmsColumn/updateColumn.do`, params).then(res => res.data);

// 查询banner列表
export const api_getBannerColumnList = params => http.post(`${base}/banner/findColumnBanners.do`, params).then(res => res.data);
// 查询banner信息
export const api_findBannerInfo = params => http.post(`${base}/banner/findBanner.do`, params).then(res => res.data);
// 删除banner
export const api_delBanner = params => http.post(`${base}/banner/deleteBannerContent.do`, params).then(res => res.data);
// 保存banner信息
export const api_commitBannerInfo = (url,params) => http.post(url, params).then(res => res.data);
// banner栏目删除
export const api_delBannerColumn = params => http.post(`${base}/banner/deleteColumnBanner.do`, params).then(res => res.data);

// 可关联栏目类型
export const api_findArticleColumnType = params => http.post(`${base}/columnType/findBannerInternalColumnType.do`, params).then(res => res.data);
// 查询可关联文章栏目
export const api_findArticleColumnList = params => http.post(`${base}/cmsColumn/findPHColumnTypeByColumn.do`, params).then(res => res.data);
// 查询可关联文章列表
export const api_getArticleList = params => http.post(`${base}/news/findBannerNews.do`, params).then(res => res.data);

// 可关联视屏列表
export const api_findVideoList = params => http.post(`${base}/video/findVideoColumnVideos.do`, params).then(res => res.data);
// 可关联学习列表
export const api_findLearnList = params => http.post(`${base}/materials/findColumnMaterials.do`, params).then(res => res.data);


/* 系统管理 */
// 查询角色列表
export const api_getRoleList = params => http.post(`${base}/sysRole/findRoles.do`, params).then(res => res.data);
// 查询所有平台 已关联平台
export const api_getPlatformList = params => http.post(`${base}/rolePlatform/findRolePlatform.do`, params).then(res => res.data);
// 已关联平台
export const api_saveUserInfo = params => http.post(`${base}/rolePlatform/saveRolePlatform.do`, params).then(res => res.data);

// 获取用户列表
export const api_getUserList = params => http.post(`${base}/user/findUserss.do`, params).then(res => res.data);
// 查询所有机构列表
export const api_findTopDeparts = params => http.post(`${base}/sysDepart/findTopDeparts.do`, params).then(res => res.data);
// 查询用户信息  获取已关联的机构列表
export const api_getUserInfoAndHospitalList = params => http.post(`${base}/userDepart/findUserManageDeparts.do`, params).then(res => res.data);
// 管理机构新增
export const api_saveAddInfo = params => http.post(`${base}/userDepart/saveUserManageDepart.do`, params).then(res => res.data);
// 删除管理机构
export const api_delHospital = params => http.post(`${base}/userDepart/deleteUserDepart.do`, params).then(res => res.data);

// 查询平台列表
export const api_getPlatFormList = params => http.post(`${base}/platform/findPlatforms.do`, params).then(res => res.data);
// 编辑平台状态 // 启用、禁用
export const api_updataState = params => http.post(`${base}/platform/updatePlatform.do`, params).then(res => res.data);
// 获取平台信息
export const api_getCurrentPlatformInfo = params => http.post(`${base}/platform/findPlatform.do`, params).then(res => res.data);

// 新增平台
export const api_addPlatform = params => http.post(`${base}/platform/addPlatform.do`, params).then(res => res.data);
// 更新平台信息
export const api_updataPlatform = params => http.post(`${base}/platform/updatePlatform.do`, params).then(res => res.data);

// 文件上传
export const api_uploadFile = (params) => http.axios.post(`${base}/fileOperate/fileUpload.do`, params).then(res => res.data);


// 查询视频栏目列表
export const api_getVideoColumnTypeList = (params) => http.post(`${base}/cmsColumn/findColumnByVideoNews.do`, params).then(res => res.data);
// 查询视频列表
export const api_getVideoList = (params) => http.post(`${base}/video/findVideos.do`, params).then(res => res.data);
// 删除视频
export const api_deleteVideo = (params) => http.post(`${base}/video/deleteVideo.do`, params).then(res => res.data);
// 批量发布视频
export const api_publishVideo = (params) => http.post(`${base}/video/publishVideo.do`, params).then(res => res.data);
// 批量转载
export const api_reshipmentVideo = (params) => http.post(`${base}/video/reshipmentVideo.do`, params).then(res => res.data);
// 修改视频权重
export const api_updateVideoSort = (params) => http.post(`${base}/video/updateVideoSort.do`, params).then(res => res.data);


// 查询视频相关信息
export const api_getVideoInfo = (params) => http.post(`${base}/video/findVideo.do`, params).then(res => res.data);
// 添加视频
export const api_addVideo = (params) => http.post(`${base}/video/addVideo.do`, params).then(res => res.data);
// 修改视频
export const api_UpdateVideo = (params) => http.post(`${base}/video/updateVideo.do`, params).then(res => res.data);


// 查询文件栏目列表
export const api_getFileColumnTypeList = (params) => http.post(`${base}/cmsColumn/findColumnByMaterials.do`, params).then(res => res.data);
// 查询学习资料列表
export const api_findMaterialsList = (params) => http.post(`${base}/materials/findMaterialsList.do`, params).then(res => res.data);
// 删除学习资料
export const api_deleteMaterials = (params) => http.post(`${base}/materials/deleteMaterials.do`, params).then(res => res.data);
// 批量发布学习资料
export const api_publishAllMaterials = (params) => http.post(`${base}/materials/publishAllMaterials.do`, params).then(res => res.data);
// 修改权重
export const api_updateMaterialsSort = (params) => http.post(`${base}/materials/updateMaterials.do`, params).then(res => res.data);
// /HycCms/materials/updateMaterials.do


// 查询学习资料
export const api_findMaterials = (params) => http.post(`${base}/materials/findMaterials.do`, params).then(res => res.data);
// 添加学习资料
export const api_addMaterials = (params) => http.post(`${base}/materials/addMaterials.do`, params).then(res => res.data);
// 修改学习资料
export const api_updateMaterials = (params) => http.post(`${base}/materials/updateMaterials.do`, params).then(res => res.data);

// 查询科室
export const api_getKs = (params) => http.post(`${base}/sysDepart/findHospitalSection.do`, params).then(res => res.data);
















