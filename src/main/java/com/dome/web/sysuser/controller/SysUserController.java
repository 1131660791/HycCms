package com.dome.web.sysuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsUserDepartVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.sysuser.service.TSUserService;

/**
 * 用户控制层
 * @author admin
 *
 */
@Controller
@RequestMapping(value="user")
public class SysUserController {
	
	/*
		@Autowired
		private ShardedJedisPool shardedJedisPool;
	*/
	
	@Resource(name="tSUserService")
	private TSUserService tSUserService;
	
	
	
	/**
	 *  查询所有用户
	 * @param currentPage - 显示页
	 * @param showCount   - 每页显示条数
	 * @param userName - 用户名称及手机号
	 * @param startDate - 开始时间  - yyyy-MM-dd HH:mm:ss
	 * @param endDate - 结束时间  - yyyy-MM-dd HH:mm:ss
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findUserss")
	public void findUsers(Integer currentPage, Integer showCount, String userName, String startDate, String endDate,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		Session session = SecurityUtils.getSubject().getSession();
		TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
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
			parameter.put("userName", userName);
			parameter.put("startDate", startDate);
			parameter.put("endDate", endDate);
			parameter.put("userId", userVo.getId());
			List<TSUserVo> users = tSUserService.findUsersPage(parameter);
			page.setList(users);
			returnDate.setObj(page);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
}
