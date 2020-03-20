package com.dome.web.cmsmaterials.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.dome.bean.CmsMaterials;
import com.dome.bean.CmsVideo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsmaterials.service.CmsMaterialsService;

/**
 * 远程调用 学习资料 控制层
 * @author admin
 *
 */
@Controller
@RequestMapping(value="materialsRemote")
public class CmsMaterialsRemoteController {

	@Resource(name="materialsService")
	private CmsMaterialsService materialsService;
	
	
	/**
	 * 远程调用 - 查询学习资料列表
	 * 
	 * @param platformId
	 *            - 平台Id
	 * @param hospitalId
	 *            - 医院id
	 * @param columnId
	 *            - 栏目Id - 首页新闻就不传
	 * @param materialsArrposid
	 *            - 1：首页学习资料,2:栏目学习资料
	 */
	@ResponseBody
	@RequestMapping("findMaterialsListRemote")
	public void findVideoListRemote(Integer currentPage, Integer showCount, String hospitalId,
			@RequestParam String platformId, String columnId, int materialsArrposid, HttpServletResponse response)
			throws IOException {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			Map<String, Object> par = new HashMap();
			String time = sdf.format(new Date());
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 99999;
			}
			if(!StringUtils.hasText(hospitalId)) { // 如果医院id不存在
				hospitalId = "4028810267ddd9370167dddc6d1f0001"; // 就默认为 本平台最外层
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			par.put("columnId", columnId);
			par.put("hospitalId", hospitalId);
			par.put("platformId", platformId);
			par.put("materialsArrposid", materialsArrposid);
		    par.put("page", page);
			List<CmsMaterials> materials = materialsService.findRemoteMaterialsPage(par);
			page.setList(materials);
			returnDate.setObj(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 观看学习资料
	 * @param videoId - 观看视频
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("watchMaterialsRemote")
	public void watchMaterialsRemote(@RequestParam String materialsId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 查询视频
			CmsMaterials materials = materialsService.selectByPrimaryKey(materialsId);
			if(materials !=null) {
				Integer learnNumber = materials.getLearnNumber();
				if(learnNumber == null || learnNumber.equals(null)) {
					learnNumber = 0;
				}
				learnNumber = learnNumber + 1;
				CmsMaterials m = new CmsMaterials();
				m.setId(materialsId);
				m.setLearnNumber(learnNumber);
				materialsService.updateByPrimaryKeySelective(m);
			}else {
				returnDate.setFlag("error");
				returnDate.setMsg("未发现该学习资料!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("增加观看学习资料次数异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询学习资料
	 * @param materialsId - 学习资料id
	 * @param response 
	 */
	@ResponseBody
	@RequestMapping("findMaterialsRemote")
	public void findMaterialsRemote(@RequestParam String materialsId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("materialsId", materialsId);
			// 查询学习资料
			CmsMaterials materials = materialsService.findMaterialsRemote(parameter);
			returnDate.setObj(materials);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
}
