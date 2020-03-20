package com.dome.web.util.fileoperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dome.util.ajax.AjaxUtil;
import com.dome.util.oss.OSSUtils;
import com.dome.util.reverseback.ReverseBackData;

/**
 * 文件操作控制层
 * 
 * @author admin
 * 
 */
@Controller
@RequestMapping("fileOperate")
public class FileOperateController {

	/**
	 * 文件上传到阿里云
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "fileUpload")
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) {
		// 转换request，解析出request中的文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		Map<String,String> map ;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		// 获取文件map集合
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 文件名
		String fileName = null;
		// 循环遍历，取出单个文件
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取单个文件
			MultipartFile file = entity.getValue();
			// 获得原始文件名
			fileName = file.getOriginalFilename();
			// 截取文件类型; 这里可以根据文件类型进行判断
			String fileType = fileName.substring(fileName.lastIndexOf('.')+1);
			Map<String, String> reslut = OSSUtils.mpFputObject(file, fileType);
			// 返回地址
			map  = new HashMap<String, String>();
			map.put("fileName", fileName);
			map.put("ossUrl",reslut.get("ossUrl"));
			list.add(map);
		}
		ReverseBackData returnDate = new ReverseBackData();
		returnDate.setObj(list);
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
		
	}

	
	
	
	
	
	
	
}
