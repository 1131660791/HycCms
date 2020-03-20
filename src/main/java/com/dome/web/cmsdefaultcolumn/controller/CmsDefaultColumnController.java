package com.dome.web.cmsdefaultcolumn.controller;

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

import com.dome.bean.CmsDefaultColumn;
import com.dome.bean.vo.CmsDefaultColumnVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsdefaultcolumn.service.CmsDefaultColumnService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 默认栏目 控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("defaultColumn")
public class CmsDefaultColumnController {

	@Resource(name="defaultColumnService")
	private CmsDefaultColumnService defaultColumnService;
	
	/**
	 * 查询平台默认栏目
	 * @param platformId - 平台Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findDefaultColumns")
	public void findDefaultColumns(@RequestParam String platformId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			List<CmsDefaultColumnVo> defaultColumns =  defaultColumnService.findDefaultColumns(parameter);
			returnDate.setObj(defaultColumns);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询平台默认栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 根据栏目平台id
	 * @param platformId  - 平台Id
	 * @param columnId	  - 栏目Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findDefaultColumnsById")
	public void findDefaultColumnsById(@RequestParam String platformId,@RequestParam String defaultColumnId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("defaultColumnId", defaultColumnId);
			CmsDefaultColumnVo defaultColumnVo = defaultColumnService.findDefaultColumnsById(parameter);
			returnDate.setObj(defaultColumnVo);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 验证栏目code是否已存在
	 * @param columnId - 默认栏目id 新增不传,修改时传入
	 * @param platformId - 平台id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="verifyDefaultColumnCode")
	public void verifyDefaultColumnCode(String columnId, @RequestParam String platformId,
			@RequestParam String columnCode, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("columnCode", columnCode);
			parameter.put("columnId", columnId);
			// 1 已存在 0 不存在
			int reslut =  defaultColumnService.verifyDefaultColumnCode(parameter);
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
	 * 添加默认栏目
	 * @param defaultColumn - 默认栏目
	 */
	@ResponseBody
	@RequestMapping(value="addDefaultColumn")
	public void addDefaultColumn(CmsDefaultColumn defaultColumn,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			String time = sdf.format(new Date());
			String id = UUIDUtil.getUUID();
			defaultColumn.setId(id);
			defaultColumn.setCreateTime(time);
			defaultColumn.setUpdateTime(time);
			defaultColumn.setCreateUser(userVo.getId());
			defaultColumn.setCreateUserName(realname);
			defaultColumn.setUpdateUser(userVo.getId());
			defaultColumn.setUpdateUserName(realname);
			int reslut =  defaultColumnService.insertSelective(defaultColumn);
			if(reslut < 1){
				returnDate.setFlag("error");
				returnDate.setMsg("添加默认栏目失败!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加默认栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 修改栏目
	 * @param id - 栏目id
	 * @param columnName - 栏目名称
	 * @param fkColumnType - 所属栏目Id
	 * @param state - 状态  0 启用  1 禁用
	 * @param columnUrl - 栏目图片路径
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "updateDefaultColumn")
	public void updateDefaultColumn(@RequestParam String id, String columnName, String fkColumnType, Integer state,
			String columnUrl, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		Session session = SecurityUtils.getSubject().getSession();
		TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
		String realname = userVo.getTsBaseUser().getRealname();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			CmsDefaultColumn colum = new CmsDefaultColumn();
			colum.setId(id);
			colum.setState(state);
			colum.setUpdateTime(time);
			colum.setColumnUrl(columnUrl);
			colum.setColumnName(columnName);
			colum.setUpdateUserName(realname);
			colum.setUpdateUser(userVo.getId());
			colum.setFkColumnType(fkColumnType);
			defaultColumnService.updateByPrimaryKeySelective(colum);
		} catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 删默认栏目
	 * @param columId - 栏目id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="deleteDefaultColumn")
	public void deleteDefaultColumn(@RequestParam String defaultColumnId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
		    List<CmsDefaultColumn> defaultColumns = defaultColumnService.findSonDefaultColumn(defaultColumnId);
			if(defaultColumns != null && defaultColumns.size() > 0) {
				returnDate.setFlag("error");
				returnDate.setMsg("该栏目下还存在子栏目,不可删除!");
			}else {
				int relsut = defaultColumnService.deleteByPrimaryKey(defaultColumnId);
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删默认栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 栏目排序
	 * @param columnJson - [{"id":"122","sort":1},{"id":"122","sort":2}]
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="sortDefaultColumn")
	public void sortDefaultColumn(@RequestParam String columnJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Gson gson = AjaxUtil.newGson();		   // 创建  Gson 对象
			List<CmsDefaultColumn> collection = null;  // 创建类型返回集合  
			if(columnJson != null){
				collection = gson.fromJson(columnJson, new TypeToken<List<CmsDefaultColumn>>(){}.getType());
			}
			// 遍历  对象 集合
			if(collection.size()>0){
				int reslut = defaultColumnService.sortDefaultColumn(collection);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存默认栏目排序异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
	
}
