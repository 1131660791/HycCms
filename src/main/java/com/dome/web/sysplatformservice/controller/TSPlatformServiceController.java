package com.dome.web.sysplatformservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsColumn;
import com.dome.bean.TSPlatform;
import com.dome.bean.TSRolePlatform;
import com.dome.bean.vo.PlatformServiceVo;
import com.dome.bean.vo.TSPlatformServiceVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmscolumn.service.CmsColumnService;
import com.dome.web.cmscostconfiguration.service.CmsCostConfigurationService;
import com.dome.web.sysplatform.service.TSPlatformService;
import com.dome.web.sysplatformservice.service.TSPlatformServiceService;
import com.dome.web.sysplatformservice.vo.CmsDoctorServiceVo;
import com.dome.web.sysroleplatform.service.TSRolePlatformService;
import com.dome.web.sysroleplatform.vo.CmsRolePlatformVo;
import com.dome.web.sysuser.service.TSUserService;

/**
 * 平台服务 控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("platformService")
public class TSPlatformServiceController {

	@Resource(name="tSUserService")
	private TSUserService tSUserService;
	
	@Resource(name="columnService")
	private CmsColumnService columnService;
	
	@Resource
	private TSRolePlatformService tSRolePlatform;
	
	@Resource(name="platformService")
	private TSPlatformService platformService;
	
	@Resource(name="platformServiceService")
	private TSPlatformServiceService platformServiceService;
	
	@Resource(name="costConfigurationService")
	private CmsCostConfigurationService costConfigurationService;
	
	/**
	 *  查询医生服务
	 * @param hospitalId - 医院id
	 * @param platformId - 平台id
	 * @param doctorName - 医生名称
	 */
	@ResponseBody
	@RequestMapping("findDoctorServices")
	public void findDoctorServices(Integer currentPage, Integer showCount, @RequestParam String hospitalId,@RequestParam String roleId,
			String platformId, String doctorName, HttpServletResponse response) {
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
		    
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("page", page);
			
			parameter.put("hospitalId", hospitalId);
			parameter.put("doctorName", doctorName);
			if(StringUtils.hasText(platformId)) { // 判断是否存在平台
				parameter.put("platformId", platformId);
			}else { // 不存在平台 查询该角色 有哪些平台
				
				Map<String, Object> par = new HashMap<>();
				par.put("roleId", roleId);
				List<TSRolePlatform> rolePlatforms =  tSRolePlatform.findRoleManagePlatform(par);
				List platformIds = new ArrayList();
				for (int i=0;i<rolePlatforms.size();i++) {
					platformIds.add(rolePlatforms.get(i).getFkPlatformId());
				}
				parameter.put("platformIds", platformIds);
			}
			List<TSPlatformServiceVo> platformServiceVos = platformServiceService.findDoctorServicesPage(parameter);
			page.setList(platformServiceVos);
			returnDate.setObj(page);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询医生服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 禁止医生 服务 
	 * @param doctorId - 医生Id
	 * @param state    - 0:启用,1:禁用
	 * @param platformId - 平台Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("forbidDoctorServices")
	public void forbidDoctorServices(@RequestParam String doctorId, int state,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("state", state);
			parameter.put("doctorId", doctorId);
			// 修改医生状态
			int reslut = costConfigurationService.updateDoctorState(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("操作医生服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 查询平台服务
	 * @param platformId - 平台id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findPlatformServiceInfo")
	public void findPlatformServiceInfo(@RequestParam String platformId, @RequestParam String hospitalId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 查询平台服务信息
	    	Map<String,Object> par = new HashMap();
	    	par.put("platformId", platformId);
	    	List<PlatformServiceVo> platformServices = platformServiceService.findPlatformServiceInfo(par);
	    	
	    	// 查询平台 栏目
	    	Map<String,Object> parameter = new HashMap();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnTypeId", "892880e64e1ef94d014e1efefc370005"); // 医生栏目类型id
			List<CmsColumn>  columns = columnService.findPHColumnTypeByColumn(parameter);
	    	
			Map<String,Object> reslut = new HashMap();
			reslut.put("columns", columns);
			reslut.put("platformServices", platformServices);
	    	returnDate.setObj(reslut);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询平台服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询医生服务
	 * @param doctorId - 医生id
	 * @param hospitalId - 医院id
	 * @param roleId   - 角色Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findDoctorService")
	public void findDoctorService(@RequestParam String doctorId, @RequestParam String hospitalId, @RequestParam String roleId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter  = new HashMap();
	    	parameter.put("doctorId", doctorId);
	    	parameter.put("hospitalId", hospitalId);
			// 查询用户信息
	    	Map<String,Object> sysUser = tSUserService.findDoctorInfo(parameter);
			
	    	// 查询该角色能超控的平台
	    	Map<String,Object> param = new HashMap();
	    	param.put("roleId", roleId);
	    	List<TSPlatform> platforms = platformService.findRolePlatforms(param);
	    	
			Map<String,Object> reslut  = new HashMap();
			reslut.put("sysUser", sysUser);
			reslut.put("platforms", platforms);
			returnDate.setObj(reslut);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("操作医生服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 *  查询医生平台服务
	 * @param doctorId - 医生id
	 * @param roleId - 角色Id
	 */
	@ResponseBody
	@RequestMapping("findDoctorPlatformService")
	public void findDoctorPlatformService(@RequestParam String doctorId,@RequestParam String roleId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap<>();
			parameter.put("roleId", roleId);
			// 查询该角色能 操作的 平台
			List<TSRolePlatform> rolePlatforms =  tSRolePlatform.findRoleManagePlatform(parameter);
			List platformIds = new ArrayList();
			for (int i=0;i<rolePlatforms.size();i++) {
				platformIds.add(rolePlatforms.get(i).getFkPlatformId());
			}
			
			// 查询平台服务信息
	    	Map<String,Object> par = new HashMap();
	    	par.put("doctorId", doctorId);
	    	par.put("platformIds", platformIds);
	    	List<CmsDoctorServiceVo> platformServices = platformServiceService.findDoctorPlatformService(par);
	    	returnDate.setObj(platformServices);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询医生平台服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	

}
