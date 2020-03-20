package com.dome.web.cmscolumn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsBanner;
import com.dome.bean.CmsColumn;
import com.dome.bean.CmsColumnType;
import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsColumnVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsbanner.service.CmsBannerService;
import com.dome.web.cmscolumn.service.CmsColumnService;
import com.dome.web.cmscolumntype.service.CmsColumnTypeService;
import com.dome.web.cmsnews.service.CmsNewsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 栏目控制层
 * @author admin
 */
@Controller
@RequestMapping("cmsColumn")
public class CmsColumnController {
	
	@Resource(name="columnService")
	private CmsColumnService columnService;
	
	@Resource(name="columnTypeService")
	private CmsColumnTypeService columnTypeService;
	
	@Resource(name="bannerService")
	private CmsBannerService bannerService; 
	
	@Resource(name="newsService")
	private CmsNewsService newsService;
	
	/**
	 * 查询栏目集合
	 * @param platformId - 平台 id
	 * @param hospitalId - 医院 id
	 * @param response 
	 */
	@ResponseBody
	@RequestMapping(value="findColumns")
	public void findColumns(@RequestParam String platformId, @RequestParam String hospitalId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			List<CmsColumnVo>  columnVos = columnService.findHospitalColumn(parameter);
			returnDate.setObj(columnVos);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 根据栏目平台id 医院id 栏目类型 查询栏目
	 */
	@ResponseBody
	@RequestMapping(value="findPHColumnTypeByColumn")
	public void findPHColumnTypeByColumn(@RequestParam String platformId, @RequestParam String hospitalId,
			@RequestParam String columnTypeId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnTypeId", columnTypeId);
			List<CmsColumn>  columns = columnService.findPHColumnTypeByColumn(parameter);
			returnDate.setObj(columns);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 根据平台,机构 查询新闻类型栏目
	 * @param hospitalId - 医院id
	 * @param platformId - 平台id
	 */
	@ResponseBody
	@RequestMapping(value="findPlatformHospitalByColumn")
	public void findPlatformHospitalByColumn(@RequestParam String hospitalId,@RequestParam String platformId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("hospitalId", hospitalId);
			parameter.put("platformId", platformId);
			parameter.put("columnTypeId", "192880e64e1ef94d014e1efefc340003"); // 新闻类型id
			// 单位信息
			List<CmsColumn> columns = columnService.findPHColumnTypeByColumn(parameter);
			returnDate.setObj(columns);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询栏目类型
	 */
	@ResponseBody
	@RequestMapping(value="findColumnType")
	public void findColumnType(HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			List<CmsColumnType> columnTypes = columnTypeService.findAllColumnType();
			returnDate.setObj(columnTypes);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加栏目异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 验证栏目code 是否存在
	 * @param columnId - 栏目id
	 * @param platformId -平台id
	 * @param columnCode - 栏目code
	 * @param hospitalId - 医院Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="verifyColumnCode")
	public void verifyColumnCode(String columnId, @RequestParam String platformId, @RequestParam String columnCode,
			@RequestParam String hospitalId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("columnId", columnId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnCode", columnCode);
			parameter.put("platformId", platformId);
			// 1 已存在 0 不存在
			int reslut =  columnService.verifyColumnCode(parameter);
			returnDate.setObj(reslut);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("验证栏目code是否已存在异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 添加栏目
	 * @param colum
	 */
	@ResponseBody
	@RequestMapping(value="addColumn")
	public void addColumn(CmsColumn colum,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			String time = sdf.format(new Date());
			String id = UUIDUtil.getUUID();
			colum.setId(id);
			colum.setColumnId(id);
			colum.setColumnProperty(0); // 默认为 非默认栏目 为用户添加 的 栏目， 如果初始化维护栏目将此次改为 1
			colum.setCreateTime(time);
			colum.setUpdateTime(time);
			colum.setCreateUser(userVo.getId());
			colum.setCreateUserName(realname);
			colum.setUpdateUser(userVo.getId());
			colum.setUpdateUserName(realname);
			int reslut =  columnService.insertSelective(colum);
			if(reslut < 1){
				returnDate.setFlag("error");
				returnDate.setMsg("添加栏目失败!");
			}
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加栏目异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 查看栏目详情
	 * @param columId - 栏目id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findColumn")
	public void findColumnById(String columId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsColumnVo columnVo = columnService.findColumnById(columId);
			returnDate.setObj(columnVo);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 修改栏目
	 * @param id - 栏目id
	 * @param columnName - 栏目名称
	 * @param fkColumnTypeId - 所属栏目Id
	 * @param state - 状态 0 启用 1禁用
	 * @param columnUrl - 栏目图片路径
	 * @param columnProperty - 栏目属性:0:非默认栏目,1:为默认栏目
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "updateColumn")
	public void updateColumn(@RequestParam String id, String columnName, String fkColumnTypeId, Integer state, String columnUrl,
			int columnProperty,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		Session session = SecurityUtils.getSubject().getSession();
		TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
		String realname = userVo.getTsBaseUser().getRealname();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			CmsColumn colum =  new CmsColumn();
			colum.setId(id);
			colum.setState(state);
			colum.setUpdateTime(time);
			colum.setColumnUrl(columnUrl);
			colum.setUpdateUser(userVo.getId());
			colum.setUpdateUserName(realname);
			colum.setColumnName(columnName);
			colum.setFkColumnTypeId(fkColumnTypeId);
			colum.setColumnProperty(columnProperty);
			columnService.updateByPrimaryKeySelective(colum);
		} catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 删除栏目
	 * @param columId - 栏目id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="deleteColumn")
	public void deleteColumn(@RequestParam String columId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsColumn column = columnService.selectByPrimaryKey(columId);
			// 为莫认栏目不可以删除
			if(column.getColumnProperty() == 1) {
				returnDate.setFlag("error");
				returnDate.setMsg("此栏目为默认栏目不可以删除,只能禁用 !");
			}else {
				// 查询栏目下是否还存在banner
				Map<String,Object> par = new HashMap();
				par.put("columId", columId);
				
				List<CmsBanner> banners = bannerService.findColumnBannerByColumId(par);
				if(banners != null && banners.size() >0) {
					returnDate.setFlag("error");
					returnDate.setMsg("该栏目下还存在banner无法删除 !");
				}else {
					// 查询栏目下是否还存在新闻
					List<CmsNews> news = newsService.findColumnNewsByColumId(par);
					if(news != null && news.size() > 0) {
						returnDate.setFlag("error");
						returnDate.setMsg("该栏目下还存在新闻无法删除 !");
					}else {
						Map<String,Object> parameter = new HashMap();
						parameter.put("columId", columId);
						// 验证栏目下面是否还有子栏目
						List<CmsColumn> columns = columnService.findSonColumn(parameter);
						if(columns != null && columns.size() >0) {
							returnDate.setFlag("error");
							returnDate.setMsg("此栏目下还有子栏目,无法删除 !");
						}else {
							int reslut = columnService.deleteByPrimaryKey(columId);
							if(reslut < 1) {
								returnDate.setFlag("error");
								returnDate.setMsg("删除栏目失败 !");
							}
						}
					}
				
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 栏目排序
	 * @param columnJson - [{"id":"122","sort":1},{"id":"122","sort":2}]
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="sortColumn")
	public void sortColumn(@RequestParam String columnJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Gson gson = AjaxUtil.newGson();		   // 创建  Gson 对象
			List<CmsColumn> collection = null;  // 创建类型返回集合  
			if(columnJson != null){
				collection = gson.fromJson(columnJson, new TypeToken<List<CmsColumn>>(){}.getType());
			}
			// 遍历  对象 集合
			if(collection.size()>0){
				columnService.sortColumn(collection);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存栏目排序异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询新闻类型栏目
	 * @param platformId - 平台id
	 * @param hospitalId - 医院id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findColumnByNews")
	public void findColumnByNews(@RequestParam String platformId,@RequestParam String hospitalId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			List<CmsColumn> columns =  columnService.findColumnByNews(parameter);
			returnDate.setObj(columns);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻类型栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 查询视频类型栏目
	 * @param platformId - 平台id
	 * @param hospitalId - 医院id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findColumnByVideoNews")
	public void findColumnByVideoNews(@RequestParam String platformId,@RequestParam String hospitalId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnTypeId", "662880e64e1ef94d014e1efefc360006"); // 视频类型
			List<CmsColumn> columns =  columnService.findColumnByType(parameter);
			returnDate.setObj(columns);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询视频类型栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询学习资料类型栏目
	 * @param platformId - 平台id
	 * @param hospitalId - 医院id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findColumnByMaterials")
	public void findColumnByMaterials(@RequestParam String platformId, @RequestParam String hospitalId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnTypeId", "882880e64e1ef94d014e1efefc3e0008"); // 学习资料类型
			List<CmsColumn> columns =  columnService.findColumnByType(parameter);
			returnDate.setObj(columns);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料类型栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 导入平台默认栏目
	 * @param platformId - 平台id
	 * @param hospitalId - 医院id
	 * @param departname - 单位名称
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="importDefaultColumn")
	public void importDefaultColumn(@RequestParam String platformId, @RequestParam String hospitalId,@RequestParam String departname,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		Session session = SecurityUtils.getSubject().getSession();
		TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("userId", userVo.getId());
			parameter.put("hospitalId", hospitalId);
			parameter.put("departname", departname);
			parameter.put("userName", userVo.getTsBaseUser().getRealname());
			int relsut = columnService.importDefaultColumn(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("导入平台默认栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	

}
