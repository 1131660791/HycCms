package com.dome.web.sysplatform.controller;

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

import com.dome.bean.TSPlatform;
import com.dome.bean.vo.CmsNewsVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.sysplatform.service.TSPlatformService;

/**
 * 平台控制层
 * @author admin
 */
@Controller
@RequestMapping("platform")
public class TSPlatformController {
	
	@Resource(name="platformService")
	private TSPlatformService platformService;
	
	
	/**
	 * 查询所有平台
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findPlatformList")
	public void findPlatformList(HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			List<TSPlatform> platforms = platformService.findPlatformList();
			returnDate.setObj(platforms);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询所有平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询平台列表
	 * @param currentPage - 显示页
	 * @param showCount  - 显示条数 
	 * @param platformName - 栏目名称
	 * @param startDate  - 开始时间 - yyyy-MM-dd HH:mm:ss
	 * @param endDate 	 - 结束时间 - yyyy-MM-dd HH:mm:ss
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findPlatforms")
	public void findPlatforms(Integer currentPage, Integer showCount, String platformName,
			String startDate,String endDate,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 20;
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> parameter = new HashMap();
			parameter.put("page", page);
			parameter.put("platformName", platformName);
			parameter.put("startDate", startDate);
			parameter.put("endDate", endDate);
			
			List<TSPlatform> platforms = platformService.findPlatformsPage(parameter);
			page.setList(platforms);
			returnDate.setObj(page);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻列表异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 添加平台
	 * @param platform
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("addPlatform")
	public void addPlatform(@RequestParam String platformName,String platformUrl,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time = sdf.format(new Date());
			 Session session = SecurityUtils.getSubject().getSession();
			 TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			 String realname = userVo.getTsBaseUser().getRealname();
			 String userId = userVo.getTsBaseUser().getId();
			 
			 TSPlatform platform = new TSPlatform();
			 platform.setId(UUIDUtil.getUUID());
			 platform.setPlatformName(platformName);// 平台名称
			 platform.setState(0);
			 platform.setPlatformUrl(platformUrl);
			 platform.setCreationTime(time);
			 platform.setUpdateTime(time);
			 platform.setCreateUser(userId);
			 platform.setUpdateUser(userId);
			 platform.setCreateUserName(realname);
			 platform.setUpdateUserName(realname);
			 
			 int reslut = platformService.insertSelective(platform);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询平台
	 * @param platformId - 平台id
	 */
	@ResponseBody
	@RequestMapping("findPlatform")
	public void findPlatform(@RequestParam String platformId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			TSPlatform platform = platformService.selectByPrimaryKey(platformId);
			returnDate.setObj(platform);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
	/**
	 * 查询角色管理的平台
	 * @param roleId
	 */
	@ResponseBody
	@RequestMapping("findRolePlatform")
	public void findRolePlatform(@RequestParam String roleId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("roleId", roleId);
			List<TSPlatform> platforms = platformService.findRolePlatforms(parameter);
			returnDate.setObj(platforms);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询角色管理的平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 修改平台
	 * @param platform
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("updatePlatform")
	public void updatePlatform(TSPlatform platform,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time = sdf.format(new Date());
			 Session session = SecurityUtils.getSubject().getSession();
			 TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			 String realname = userVo.getTsBaseUser().getRealname();
			 String userId = userVo.getTsBaseUser().getId();
			 
			 platform.setUpdateTime(time);
			 platform.setUpdateUser(userId);
			 platform.setUpdateUserName(realname);
			 
			 int reslut = platformService.updateByPrimaryKeySelective(platform);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 禁用平台
	 * @param platformId - 平台Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("forbiddenPlatform")
	public void forbiddenPlatform(@RequestParam String platformId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time = sdf.format(new Date());
			 Session session = SecurityUtils.getSubject().getSession();
			 TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			 String realname = userVo.getTsBaseUser().getRealname();
			 String userId = userVo.getTsBaseUser().getId();
			 
			 TSPlatform platform = new TSPlatform();
			 platform.setId(platformId);
			 platform.setState(1);
			 int reslut = platformService.updateByPrimaryKeySelective(platform);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("禁用平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}	
	
	
	
	
	
}
