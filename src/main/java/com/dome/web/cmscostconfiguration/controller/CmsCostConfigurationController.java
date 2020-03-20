package com.dome.web.cmscostconfiguration.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmscostconfiguration.service.CmsCostConfigurationService;

/**
 * 医生服务配置
 * @author admin
 *
 */
@Controller
@RequestMapping("costConfiguration")
public class CmsCostConfigurationController {
	
	
	@Resource(name="costConfigurationService")
	private CmsCostConfigurationService costConfigurationService;
	
	
	/**
	 * 保存医生平台服务
	 * @param platformServiceJson - 平台服务Json
	 * {
			"state": "0:发布:1:草稿",
			"doctorId": "医生Id",
			"doctorName": "医生名称",
			"hospitalId":"医院",
			"hospitalName":"医院名称",
			"platformService": [{
				"platformId": "平台名称",
				"columnId": "栏目id",
				"serviceId": "服务id",
				"comboId": [{
					"id": "套餐Id"
				}, {
					"id": "套餐Id"
				}],
				"publicTime": "发布时间",
				"expireTime": "修改时间",
				"sort": 权重
			}]
		}
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("saveDoctorPlatformService")
	public void saveDoctorPlatformService(String platformServiceJson,@RequestParam String roleId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			String userId = userVo.getId();
			// 查询平台服务信息
	    	Map<String,Object> par = new HashMap();
	    	par.put("userId", userId); // 用户id
	    	par.put("realname", realname); // 真实姓名
	    	par.put("roleId", roleId);
	    	par.put("platformServiceJson", platformServiceJson);
	    	
	    	int reslut = costConfigurationService.saveDoctorPlatformService(par);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("保存医生平台服务异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
}
