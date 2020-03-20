package com.dome.web.cmscolumntype.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsColumnType;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmscolumntype.service.CmsColumnTypeService;

/**
 * 栏目类型 控制层
 * 
 * @author admin
 */
@Controller
@RequestMapping("columnType")
public class CmsColumnTypeController {

	@Resource(name = "columnTypeService")
	private CmsColumnTypeService columnTypeService;

	/**
	 * 查询banner 内链 栏目类型
	 */
	@ResponseBody
	@RequestMapping(value = "findBannerInternalColumnType")
	public void findBannerInternalColumnType(HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			List<CmsColumnType> columnTypes = columnTypeService.findBannerInternalColumnType();
			returnDate.setObj(columnTypes);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询栏目类型异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

}
