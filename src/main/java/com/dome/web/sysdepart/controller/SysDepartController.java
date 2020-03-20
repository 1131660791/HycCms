package com.dome.web.sysdepart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.TSDepart;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.sysdepart.service.TSDepartService;

/**
 * 单位控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("sysDepart")
public class SysDepartController {

	
	@Resource(name = "departService")
	private TSDepartService departService;
	
	/**
	 * 查询所有一级单位
	 * @param currentPage - 显示页
	 * @param showCount  - 每页显示条数
	 * @param userId - 用户Id
	 * @param departId - 单位Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findTopDeparts")
	public void findTopDeparts(Integer currentPage, Integer showCount,String departName,HttpServletResponse response) {
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
			Map<String, Object> parameter = new HashMap();
			parameter.put("page", page);
			parameter.put("departName", departName);
			List<TSDepart> departs = departService.findTopDepartsPage(parameter);
			
			page.setList(departs);
			returnDate.setObj(page);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询所有一级单位异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询医院科室
	 * @param hospitalId - 医院id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findHospitalSection")
	public void findHospitalSection(@RequestParam String hospitalId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("hospitalId", hospitalId);
			List<TSDepart> departs = departService.findHospitalSection(parameter);
			returnDate.setObj(departs);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询医院科室异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
}
