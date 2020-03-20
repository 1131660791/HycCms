package com.dome.web.cmsnews.controller;

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

import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsNewsVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsnews.service.CmsNewsService;

/**
 * 新闻 远程 调用 控制层
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("newsRemote")
public class CmsNewsRemoteController {

	@Resource(name = "newsService")
	private CmsNewsService newsService;

	/**
	 * 远程调用 - 查询新闻列表
	 * 
	 * @param platformId
	 *            - 平台Id
	 * @param hospitalId
	 *            - 医院id
	 * @param columnId
	 *            - 栏目Id - 首页新闻就不传
	 * @param newsArrposid
	 *            - 1：首页新闻,2:栏目新闻
	 */
	@ResponseBody
	@RequestMapping("findNewsListRemote")
	public void findRemoteNewsList(Integer currentPage, Integer showCount, String hospitalId,
			@RequestParam String platformId, String columnId, int newsArrposid, HttpServletResponse response)
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
			par.put("newsArrposid", newsArrposid);
		    par.put("page", page);
			List<CmsNews> news = newsService.findRemoteNewsPage(par);
			page.setList(news);
			returnDate.setObj(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 查询新闻
	 * 
	 * @param newsId
	 *            -新闻Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findNewsRemote")
	public void findRemoteNews(@RequestParam String newsId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("newsId", newsId);
			// 删除新闻
			CmsNewsVo newsVo = newsService.findNews(parameter);
			returnDate.setObj(newsVo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
}
