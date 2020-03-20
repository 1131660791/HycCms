package com.dome.web.util.mongodb;

import java.util.HashMap;
import java.util.Map;
import com.mongodb.DBObject;
import com.dome.config.mongodb.MongoManager;
import com.dome.util.mongodb.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="mongoDBUtil")
public class MongoDBController {
	
	/**
	 * 下载 MongoDB 文件 
	 * @param fileUuid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/downMongofileByUuid")
	public Map<String, Object> downMongofileByUuid(String fileUuid,HttpServletRequest request ,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 创建 MongoDB 连接
			GridFS gridFS = MongoManager.getMongoDBConnect();
			
			DBObject query = new BasicDBObject("UUID", fileUuid);
			// 获取文件信息
    		GridFSDBFile gridFSDBFileList = gridFS.findOne(query);
    		// 文件下载 - 输出文件流
    		MongoDBUtil.download(request, response, gridFSDBFileList.get("_id").toString(),gridFS);
    		
    		map.put("data",gridFSDBFileList);
			map.put("message", "下载文件成功");
			map.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("data", "");
			map.put("message", "下载文件出现异常");
			map.put("status", "failure");
		}
		return map;
	}
	
	
	
}
