package com.dome.web.cmsbanner.controller;

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

import com.dome.bean.CmsBannerNews;
import com.dome.bean.vo.CmsBannerVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsbanner.service.CmsBannerService;
import com.dome.web.cmsbanner.vo.ColumnBannerVo;
import com.dome.web.cmsbannernews.service.CmsBannerNewsService;
import com.dome.web.cmscolumn.service.CmsColumnService;

/**
 * banner管理控制层
 * @author admin
 */
@Controller
@RequestMapping(value = "banner")
public class CmsBannerController {

	@Resource(name = "bannerService")
	private CmsBannerService bannerService;
	
	@Resource(name = "columnService")
	private CmsColumnService columnService;
	
	@Resource(name="bannerNewsService")
	private CmsBannerNewsService bannerNewsService;
	
	/**
	 * 查询栏目的 banner 列表
	 * @param platformId - 平台Id
	 * @param hospitalId - 医院Id
	 * @param columnName - 栏目名称
	 * @param bannerTheme - 主题名称
	 * @param startDate - 开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endDate   - 结束时间 yyyy-MM-dd HH:mm:ss
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "findColumnBanners")
	public void findColumnBanners(@RequestParam String platformId,@RequestParam String hospitalId,
			String columnName,String bannerTheme,String startDate,String endDate,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnName", columnName);
			parameter.put("bannerTheme", bannerTheme);
			parameter.put("startDate", startDate);
			parameter.put("endDate", endDate);
			List<ColumnBannerVo> columnBanners = bannerService.findColumnBanners(parameter);
			returnDate.setObj(columnBanners);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存栏目排序异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 添加banner
	 * @param bannerJson 
	 * {
		"bannerTheme": "主题名称",
		"fkColumnId": "栏目id",
		"state":0,
		"hospitalId": "医院id",
		"departname": "机构名称",
		"fkPlatformId": "平台id",
		"publicTime": "yyyy-MM-dd HH:mm:ss",
		"expireTime": "yyyy-MM-dd HH:mm:ss",
		"bannerContents": [{
			"bannerKeyword": "关键字",
			"bannerName": "banner名称",
			"bannerUrl": "banner图片路径",
			"sort": "排序",
			"bannerLinkFlag": "banner链接类型: 0:内联,1外联",
			"bannerLink": "banner链接",
			"relationObjId": "关联对象id",
			"columnTypeId":"栏目类型id",
			"bannerTarget": "banner: html页面打开方式"
		}, {
			"bannerKeyword": "关键字",
			"bannerName": "banner名称",
			"bannerUrl": "banner图片路径",
			"sort": "排序",
			"bannerLinkFlag": "banner链接类型: 0:内联,1外联",
			"bannerLink": "banner链接",
			"relationObjId": "关联对象id",
			"columnTypeId":"栏目类型id",
			"bannerTarget": "banner: html页面打开方式"
		}]
	  }
	 */
	@ResponseBody
	@RequestMapping(value = "addBanner")
	public void addBanner(@RequestParam String bannerJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("bannerJson", bannerJson);
			parameter.put("userId", userVo.getId());
			parameter.put("userName", userVo.getTsBaseUser().getRealname());
			int reslut = bannerService.addBanner(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存banner异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询banner
	 * @param platformId - 平台
	 * @param hospitalId - 医院Id
	 * @param columnId   - 栏目Id
	 */
	@ResponseBody
	@RequestMapping(value = "findBanner")
	public void findBanner(@RequestParam String platformId, @RequestParam String hospitalId,
			@RequestParam String columnId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("platformId", platformId);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnId", columnId);
			CmsBannerVo bannerVo =  bannerService.findBanner(parameter);
			returnDate.setObj(bannerVo);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询banner异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 修改banner
	 * @param bannerJson 
	 * {
	    "id":"bannerId",
		"bannerTheme": "主题名称",
		"state":0,
		"publicTime": "yyyy-MM-dd HH:mm:ss",
		"expireTime": "yyyy-MM-dd HH:mm:ss",
		"bannerContents": [{
			"bannerContentId":"有就传没有就传空",
			"bannerKeyword": "关键字",
			"bannerName": "banner名称",
			"bannerUrl": "banner图片路径",
			"sort": "排序",
			"bannerLinkFlag": "banner链接类型: 0:内联,1外联",
			"bannerLink": "banner链接",
			"relationObjId": "关联对象id",
			"columnTypeId":"栏目类型id",
			"bannerTarget": "banner: html页面打开方式"
		}, {
			"bannerContentId":"有就传没有就传空",
			"bannerKeyword": "关键字",
			"bannerName": "banner名称",
			"bannerUrl": "banner图片路径",
			"sort": "排序",
			"bannerLinkFlag": "banner链接类型: 0:内联,1外联",
			"bannerLink": "banner链接",
			"relationObjId": "关联对象id",
			"columnTypeId":"栏目类型id",
			"bannerTarget": "banner: html页面打开方式"
		}]
	  }
	 */
	@ResponseBody
	@RequestMapping(value = "updateBanner")
	public void updateBanner(@RequestParam String bannerJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("bannerJson", bannerJson);
			parameter.put("userId", userVo.getId());
			parameter.put("userName", userVo.getTsBaseUser().getRealname());
			int reslut = bannerService.updateBanner(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改banner异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 删除banner 内容
	 * @param bannerContentId -banner内容Id
	 * @param bannerFlag - banner 是否关联了新闻 0:否 1: 是
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBannerContent")
	public void deleteBannerContent(@RequestParam String bannerContentId,@RequestParam int bannerFlag,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 删除banner
			Map<String, Object> parameter = new HashMap();
			parameter.put("bannerContentId", bannerContentId);
			parameter.put("bannerFlag", bannerFlag);
			int reslut = bannerService.deleteBanner(parameter);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除banner内容异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 删除栏目banner
	 * @param columnId - 栏目id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "deleteColumnBanner")
	public void deleteColumnBanner(@RequestParam String columnId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 删除栏目banner
			Map<String, Object> parameter = new HashMap();
			parameter.put("columId", columnId);
			int reslut = bannerService.deleteColumnBanner(parameter);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除banner内容异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 校验banner 是否关联新闻 , 学习资料 ,视频
	 * @param bannerContentId - banner 内容id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "verifyBannerAssociatedFlag")
	public void verifyBannerAssociatedFlag(@RequestParam String bannerContentId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 校验banner 是否关联新闻 , 学习资料 ,视频
			Map<String, Object> parameter = new HashMap();
			parameter.put("bannerContentId", bannerContentId);
			Map<String,Object> bannerNews= bannerNewsService.verifyBannerAssociatedFlag(parameter);
			returnDate.setObj(bannerNews);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("校验banner是否存在内容异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	

}
