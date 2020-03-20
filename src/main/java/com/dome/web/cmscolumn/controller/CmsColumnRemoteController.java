package com.dome.web.cmscolumn.controller;

import java.io.IOException;
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

import com.dome.bean.vo.CmsColumnVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmscolumn.service.CmsColumnService;
import com.dome.web.cmscolumntype.service.CmsColumnTypeService;

/**
 * 栏目 远程 调用 控制层
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("columnRemote")
public class CmsColumnRemoteController {

	@Resource(name = "columnService")
	private CmsColumnService columnService;

	@Resource(name = "columnTypeService")
	private CmsColumnTypeService columnTypeService;

	/**
	 * 远程查看 - 查询 医院 栏目
	 * 
	 * @param hospitalId
	 *            - 医院id -必传
	 * @param columnPId
	 *            - 栏目父id 没有就不传
	 * @param platformId
	 *            - 平台id -必传
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("findColumnsRemote")
	public void findRemoteColumns(String hospitalId, @RequestParam String platformId, String columnPId,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(!StringUtils.hasText(hospitalId)) { // 如果医院id不存在
				hospitalId = "4028810267ddd9370167dddc6d1f0001"; // 就默认为 本平台最外层
			}
			Map<String, Object> par = new HashMap();
			par.put("columnPId", columnPId);
			par.put("hospitalId", hospitalId);
			par.put("platformId", platformId);
			List<CmsColumnVo> columns = columnService.findRemoteColumns(par);
			returnDate.setObj(columns);
		} catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询医院栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
}
