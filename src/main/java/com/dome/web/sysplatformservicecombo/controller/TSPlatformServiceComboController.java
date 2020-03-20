package com.dome.web.sysplatformservicecombo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dome.web.sysplatformservicecombo.service.TSPlatformServiceComboService;

/**
 * 平台服务套餐控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("platformServiceCombo")
public class TSPlatformServiceComboController {
	
	@Resource(name="platformServiceComboService")
	private TSPlatformServiceComboService platformServiceComboService;

	
	
	
	
	
	
	
	
}
