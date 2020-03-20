package com.dome.web.login.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.TSRole;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.password.PasswordUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.verification.VerificationUtils;
import com.dome.web.sysrole.service.TSRoleService;

@Controller
@RequestMapping("login")
public class LoginController {

	
	@Resource(name = "tSRoleService")
	private TSRoleService tSRoleService;
	
	// log 日志
	private  Log log = LogFactory.getLog(LoginController.class);

	// 登录跳转
/*
	@RequestMapping(value = "login", method = { RequestMethod.GET })
	public String loginUI() throws Exception {
		return "redirect:/exhibition/jsp/login.jsp";
	}
*/

	/**
	 * shiro 登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public void shiroLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			String user = request.getParameter("userName");
			String pass = request.getParameter("passWord");
			String remember = request.getParameter("remember");
			Subject currentUser = SecurityUtils.getSubject();
			// 是否登录
			if (!currentUser.isAuthenticated()) {
				String code = (String) session.getAttribute("validateCode");
				String submitCode = WebUtils.getCleanParam(request, "validateCode");
				if(!StringUtils.hasText(submitCode)) {
					returnDate.setFlag("error");
					returnDate.setMsg("请输入验证码!");
					AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
					return;
				}else if (!code.equals(submitCode)) {
					returnDate.setFlag("error");
					returnDate.setMsg("验证码输入错误!");
					AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
					return;
				} else {
		        	// 把用户名和密码封装为 UsernamePasswordToken 对象
					// Shiro实现登录
			    	// Object passObj = new SimpleHash("MD5", pass, null, 1024); // 将密码 进行MD5 1024 转换
			    	
					// 加密
					String password = PasswordUtil.encrypt(user, pass, PasswordUtil.getStaticSalt());
					UsernamePasswordToken token = new UsernamePasswordToken(user, password);
					// 是否勾选了记住我
					if (StringUtils.hasText(remember) && remember.equals("true")) {
						token.setRememberMe(true);
					}

					try {
						// 执行登录. 会跳到我们自定义的realm中
						currentUser.login(token);
						// 权限登录
						if (currentUser.hasRole("admin")) {
							System.out.println("== admin 权限==");
						}
					} catch (UnknownAccountException e) {
						// 未知的账号
						returnDate.setFlag("error");
						returnDate.setMsg("账号不存在!");
						AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
						return;
					} catch (IncorrectCredentialsException e) {
						System.out.println("== 用户名或密码错误 ==");
						returnDate.setFlag("error");
						returnDate.setMsg("用户名或密码错误!");
						AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
						return;
					} catch (AuthenticationException e) {
						// 所有认证时异常的父类. 凭据异常
						returnDate.setFlag("error");
						returnDate.setMsg("账号不存在!");
						AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
						return;
					} catch (Exception e) {
						e.printStackTrace();
						returnDate.setFlag("error");
						returnDate.setMsg("登录异常!");
						AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
						return;
					}
				}
			}
			Map<String, Object> parameter = new HashMap();
			parameter.put("loginName", user);
			List<TSRole> tRoles = tSRoleService.findUserRoleInfo(parameter);
			TSUserVo  sysUser = (TSUserVo)session.getAttribute("sysUser");
			Map<String,Object> data = new HashMap();
			data.put("sysRoles", tRoles);
			data.put("sysUser", sysUser);
			returnDate.setObj(data);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("登录异常!");
		}
		
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
	public void shiroLogout(HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			System.err.println("++++++++++++++++ 准备退出shiro");
			Session session = SecurityUtils.getSubject().getSession();
			Subject subject = SecurityUtils.getSubject();
			session.setAttribute("sysUser", null);
			session.setAttribute("sysRoles", null);
			subject.logout();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("退出登录异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		String verifyCode = VerificationUtils.generateTextCode(VerificationUtils.TYPE_NUM_ONLY, 4, null);
		request.getSession().setAttribute("validateCode", verifyCode);
		response.setContentType("image/jpeg");
		BufferedImage bim = VerificationUtils.generateImageCode(verifyCode, 110, 41, 3, true, Color.decode("#43b1f1"), Color.WHITE,
				null);
		ImageIO.write(bim, "JPEG", response.getOutputStream());
	}

}
