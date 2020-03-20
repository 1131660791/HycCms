package com.dome.web.cmsbanner.controller;

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

import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsbanner.service.CmsBannerService;

/**
 * 远程调用 banner 控制层
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("bannerRemote")
public class CmsBannerRemoteController {

	@Resource(name = "bannerService")
	private CmsBannerService bannerService;

	/**
	 * 查询banners
	 * 
	 * @param columnId
	 *            - 栏目id
	 * @param hospitalId
	 *            - 医院id
	 * @param platformId
	 *            - 平台id
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("findBannersRemote")
	public void findBanners(@RequestParam String columnId, String hospitalId,
			@RequestParam String platformId,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			if(!StringUtils.hasText(hospitalId)) { // 如果医院id不存在
				hospitalId = "4028810267ddd9370167dddc6d1f0001"; // 就默认为 本平台最外层
			}
			Map<String, Object> par = new HashMap();
			par.put("columnId", columnId);
			par.put("hospitalId", hospitalId);
			par.put("platformId", platformId);
			par.put("time", time);
			
			List<Map<String, Object>> banners = bannerService.findBannersRemote(par);
			returnDate.setObj(banners);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目失败");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

}
