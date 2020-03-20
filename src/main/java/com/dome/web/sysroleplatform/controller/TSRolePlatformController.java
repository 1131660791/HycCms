package com.dome.web.sysroleplatform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.TSRole;
import com.dome.bean.TSRolePlatform;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.sysroleplatform.service.TSRolePlatformService;
import com.dome.web.sysroleplatform.vo.CmsRolePlatformVo;

/**
 * 角色平台
 * @author admin
 */
@Controller
@RequestMapping("rolePlatform")
public class TSRolePlatformController {

	
	@Resource
	private TSRolePlatformService tSRolePlatform;
	
	/**
	 * 查询角色平台
	 * @param roleId - 角色Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findRolePlatform")
	public void findRolePlatform(@RequestParam String roleId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("roleId", roleId);
			List<CmsRolePlatformVo> rolePlatforms = tSRolePlatform.findRolePlatform(parameter);
			returnDate.setObj(rolePlatforms);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询角色平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 保存角色 关联 平台
	 * @param rolePlatformJson
	 * {
			"roleId": "角色Id",
			"platformIds": [{
				"platformId": "平台id"
			}, {
				"platformId": "平台id"
			}]
		}
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("saveRolePlatform")
	public void saveRolePlatform(@RequestParam String rolePlatformJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("rolePlatformJson", rolePlatformJson);
			int reslut = tSRolePlatform.saveRolePlatform(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存角色平台异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
	
	
	
	
}
