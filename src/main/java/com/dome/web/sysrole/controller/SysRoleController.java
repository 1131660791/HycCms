package com.dome.web.sysrole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.TSRole;
import com.dome.bean.vo.TSRoleVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.sysrole.service.TSRoleService;

@Controller
@RequestMapping("sysRole")
public class SysRoleController {
	
	
	@Resource(name="tSRoleService")
	private TSRoleService tSRoleService;
	
	/**
	 * 查询 系统类型 角色列表
	 * @param currentPage
	 * @param showCount
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findRoles")
	public void findRoles(Integer currentPage, Integer showCount,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 10;
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> parameter = new HashMap();
			parameter.put("page", page);
			
			List<TSRoleVo> roles = tSRoleService.findRolesPage(parameter);
			page.setList(roles);
			returnDate.setObj(page);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询系统类型角色列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
	
}
