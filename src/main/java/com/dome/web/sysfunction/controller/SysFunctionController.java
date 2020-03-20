package com.dome.web.sysfunction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.TSFunction;
import com.dome.bean.vo.TSFunctionVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.sysfunction.service.TSFunctionService;
import com.google.gson.Gson;

/**
 * 模块
 * @author admin
 */
@Controller
@RequestMapping("sysFunction")
public class SysFunctionController {
	
	@Resource(name="tSFunctionService")
	private TSFunctionService tSFunctionService;
	
	/**
	 * 根据用户角色查询所属模块
	 * @param roleId - 角色Id
	 */
	@ResponseBody
	@RequestMapping(value="findRoleFunction")
	public void findRoleFunction(@RequestParam String roleId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("roleId", roleId);
			
			List<TSFunctionVo>  functions = tSFunctionService.findRoleFunction(parameter);
			// 组合树目录
			List<TSFunctionVo> functionvos= tSFunctionService.loadFunctionTree(functions);
			
			returnDate.setObj(functionvos);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询菜单异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
	
}
