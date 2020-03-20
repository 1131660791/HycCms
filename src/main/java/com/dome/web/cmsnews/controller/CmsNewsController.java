package com.dome.web.cmsnews.controller;

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

import com.dome.bean.CmsColumn;
import com.dome.bean.CmsNews;
import com.dome.bean.TSDepart;
import com.dome.bean.TSPlatform;
import com.dome.bean.vo.CmsNewsVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsnews.service.CmsNewsService;
import com.dome.web.sysdepart.service.TSDepartService;
import com.dome.web.sysplatform.service.TSPlatformService;

import net.sf.json.JSONArray;

/**
 * 新闻控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("news")
public class CmsNewsController {
	
	@Resource(name="newsService")
	private CmsNewsService newsService;
	
	@Resource(name = "departService")
	private TSDepartService departService;
	
	@Resource(name="platformService")
	private TSPlatformService platformService;
	
	/**
	 *  查询新闻列表
	 * @param currentPage - 显示页
	 * @param showCount  - 每页显示条数
	 * @param hospitalId - 医院id
	 * @param columnId  - 栏目id
	 * @param platformId - 平台id
	 * @param newsName  - 新闻名称
	 * @param state     - 新闻状态,0:发布,1:草稿
	 * @param startDate - 开始时间   yyyy-MM-dd HH:mm:ss
	 * @param endDate   - 结束时间  yyyy-MM-dd HH:mm:ss
	 * @param weightOrder - 按权重排序 1：升序,2:降序
	 * @param dataOrder - 时间排序  1:升序,2：降序
	 * @param sortOrder - 按排序 (按前台显示顺序 - 推荐 权重 时间的优先级排序) 排序  0:不按此排序,1：按此排序
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findNewss")
	public void findNewss(Integer currentPage, Integer showCount, @RequestParam String hospitalId,
			@RequestParam String columnId, String newsName, Integer state,String startDate, String endDate,
			@RequestParam String platformId,Integer weightOrder,Integer dataOrder,int sortOrder,HttpServletResponse response) {
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
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnId", columnId);
			parameter.put("newsName", newsName);
			parameter.put("platformId", platformId); // 平台id
			parameter.put("state", state);
			parameter.put("startDate", startDate);
			parameter.put("endDate", endDate);
			
			parameter.put("dataOrder", dataOrder); //  时间排序  1:升序,2：降序
			parameter.put("weightOrder", weightOrder); //  按权重排序  1:升序,2：降序
			parameter.put("sortOrder", sortOrder); //  按排序 排序  0:不按此排序,1：按此排序
			List<CmsNewsVo> newsVo = newsService.findNewsListPage(parameter);
			page.setList(newsVo);
			returnDate.setObj(page);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻列表!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 添加新闻
	 * @param cmsNews
	 */
	@ResponseBody
	@RequestMapping("addNews")
	public void addNews(CmsNews cmsNews,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			String uuid = UUIDUtil.getUUID();
			cmsNews.setId(uuid);
			cmsNews.setCreateTime(time);
			cmsNews.setUpdateTime(time);
			cmsNews.setCreateUser(userVo.getId());
			cmsNews.setCreateUserName(realname);
			cmsNews.setUpdateUser(userVo.getId());
			cmsNews.setUpdateUserName(realname);
			
			int reslut = newsService.addNews(cmsNews);
			if(reslut < 1) {
				returnDate.setFlag("error");
				returnDate.setMsg("添加新闻错误!");
			}else {
				returnDate.setObj(uuid);
			}
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加新闻异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 删除新闻
	 * @param newsId - 新闻Id
	 * @param bannerFlag - 是否关联banner 0:未关联 1：已关联
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("deleteNews")
	public void deleteNews(@RequestParam String newsId,@RequestParam int bannerFlag,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("newsId", newsId);
			parameter.put("bannerFlag", bannerFlag);
			// 删除新闻
			int reslut = newsService.deleteNews(parameter);
			if(reslut < 1) {
				returnDate.setFlag("error");
				returnDate.setMsg("删除新闻错误!");
			}
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除新闻异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 查询新闻
	 * @param newsId -新闻Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findNews")
	public void findNews(@RequestParam String newsId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> parameter = new HashMap();
			parameter.put("newsId", newsId);
			// 删除新闻
			CmsNewsVo newsVo = newsService.findNews(parameter);
			returnDate.setObj(newsVo);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	/**
	 * 修改新闻
	 * @param newsId
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("updateNews")
	public void updateNews(CmsNews cmsNews,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			
			cmsNews.setUpdateTime(time);
			cmsNews.setUpdateUser(userVo.getId());
			cmsNews.setUpdateUserName(realname);
			int reslut = newsService.updateByPrimaryKeySelective(cmsNews);
			if(reslut < 1) {
				returnDate.setFlag("error");
				returnDate.setMsg("修改新闻失败!");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 发布新闻
	 * @param newsIdJson  新闻Id - Json - [{"newsId":"id值"},{"newsId":"id值"}]
	 */
	@ResponseBody
	@RequestMapping("publishNews")
	public void publishNews(@RequestParam String newsIdJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			JSONArray newsIdArray = JSONArray.fromObject(newsIdJson);
			int reslut = newsService.publishNews(newsIdArray);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("发布新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 转载跳转
	 * @param roleId - 角色id
	 */
	@ResponseBody
	@RequestMapping(value="reshipmentSkip")
	public void reshipmentSkip(@RequestParam String roleId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			
			Map<String,Object> parameter = new HashMap();
			parameter.put("roleId", roleId);
			parameter.put("userId", userVo.getId());
			// 单位信息
			List<TSDepart> departs = departService.findUserDeparts(parameter);
			// 角色平台信息
			List<TSPlatform> platforms = platformService.findRolePlatforms(parameter);
			
			Map<String,Object> reslut = new HashMap();
			reslut.put("departs", departs);
			reslut.put("platforms", platforms);
			returnDate.setObj(reslut);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("转载跳转异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 修改新闻权重
	 * @param newsId - 新闻Id
	 * @param sort   - 权重
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="updateNewsSort")
	public void updateNewsSort(@RequestParam String newsId, int sort, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsNews news = new CmsNews();
			news.setId(newsId);
			news.setSort(sort);
			newsService.updateByPrimaryKeySelective(news);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改新闻权重异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 转载 新闻
	 * @param newsJson - 
	  {
		 "platformId": "转载到的平台Id",
		 "hospitalId": "转载到的机构Id",
		 "columnId": "转载到的栏目Id",
		 "publicTime": "yyyy-MM-dd HH:mm:ss",
		 "expireTime": "yyyy-MM-dd HH:mm:ss",
		 "newsIds": [{
			"newsId": "转载的id值"
		 }, {
			"newsId": "转载的id值"
		}]
	  }
	 */
	@ResponseBody
	@RequestMapping(value="reshipmentNews")
	public void reshipmentNews(@RequestParam String newsJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			
			Map<String,Object> parameter = new HashMap<>();
			parameter.put("newsJson", newsJson);
			parameter.put("userId", userVo.getId());
			int reslut = newsService.reshipmentNews(parameter);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("转载新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	/**
	 * 查询banner 新闻
	 * @param columnId - 栏目id
	 * @param newsName - 新闻名称
	 */
	@ResponseBody
	@RequestMapping(value="findBannerNews")
	public void findBannerNews(Integer currentPage,Integer showCount,@RequestParam String columnId, String newsName, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 8;
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> parameter = new HashMap();
			parameter.put("columnId", columnId);
			parameter.put("newsName", newsName);
			parameter.put("page", page);
			List<CmsNews>  news = newsService.findBannerNewsPage(parameter);
			page.setList(news);
			returnDate.setObj(page);
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目异常!");
			AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		}
	}
	
	
	

}
