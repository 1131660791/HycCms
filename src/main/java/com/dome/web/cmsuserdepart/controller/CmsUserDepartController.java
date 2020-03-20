package com.dome.web.cmsuserdepart.controller;

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

import com.dome.bean.TSDepart;
import com.dome.bean.vo.CmsUserDepartVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsuserdepart.service.CmsUserDepartService;
import com.dome.web.sysdepart.service.TSDepartService;

/**
 * 用户单位管理层
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping(value = "userDepart")
public class CmsUserDepartController {

	@Resource(name = "userDepartService")
	private CmsUserDepartService userDepartService;

	@Resource(name = "departService")
	private TSDepartService departService;

	/**
	 * 查询用户可以管理的医院模块 - 用户 登录业务系统使用
	 */
	@ResponseBody
	@RequestMapping(value = "findUserDepart")
	public void findUserDepart(HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo) session.getAttribute("sysUser");
			// 用户id
			String userId = userVo.getId();
			Map<String, Object> parameter = new HashMap();
			parameter.put("userId", userId);
			List<TSDepart> departs = departService.findUserDeparts(parameter);
			returnDate.setObj(departs);
		} catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 查询用户 基础信息 和 用户能管理的 单位 - 管理员 配置用户 信息时使用
	 * 
	 * @param userId
	 *            - 用户 Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "findUserManageDeparts")
	public void findUserManageDeparts(@RequestParam String userId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("userId", userId);
			List<CmsUserDepartVo> userDeparts = userDepartService.findUserManageDeparts(parameter);
			returnDate.setObj(userDeparts);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询用户管理单位异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 删除用户 配置的 单位
	 * 
	 * @param userId
	 * @param departId
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "deleteUserDepart")
	public void deleteUserDepart(@RequestParam String userId, @RequestParam String departId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("userId", userId);
			parameter.put("departId", departId);
			int reslut = userDepartService.deleteUserDepart(parameter);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除用户配置的单位异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 保存用户 管理单位
	 * @param userDepartJson - 
	 *  [{"userId":"122","departId":"1"},{"userId":"122","departId":"2"}]
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "saveUserManageDepart")
	public void saveUserManageDepart(@RequestParam String userDepartJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("userDepartJson", userDepartJson);
			int reslut = userDepartService.saveUserManageDepart(parameter);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存用户管理单位异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

}
