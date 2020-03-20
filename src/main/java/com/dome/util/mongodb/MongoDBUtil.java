package com.dome.util.mongodb;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.dome.config.mongodb.MongoManager;
import com.dome.util.properties.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB 工具
 * @author hzw
 */
public class MongoDBUtil {
	
	/**
	 * 显示图片
	 * @param request
	 * @param response
	 * @param UUID    - MongoDB 对应id
	 * @param gridFS  
	 */
	public static void showFile(HttpServletRequest request, HttpServletResponse response, String UUID, GridFS gridFS) {
		try {
			BasicDBObject query = new BasicDBObject("UUID", UUID);
			GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(query);
			if (gridFSDBFile != null) {
				OutputStream sos = response.getOutputStream();
				response.setContentType("application/octet-stream");
				// 获取原文件名
				String name = (String) gridFSDBFile.get("fileName");
				/*
				 * String fileName = new String(name.getBytes("GBK"),"ISO8859-1");
				 * if(fileName.contains(".jpg")||fileName.contains(".png")){
				 */
				// 向客户端输出图片
				gridFSDBFile.writeTo(sos);
				sos.flush();
				sos.close();
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件下载 - 输出文件流
	 * 
	 * @author wangt
	 * @Date 2017年6月23日上午10:38:50
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String fileId,
			GridFS gridFS) {
		try {
			ObjectId objId = new ObjectId(fileId);
			GridFSDBFile gridFSDBFile = (GridFSDBFile) gridFS.findOne(objId);
			if (gridFSDBFile != null) {
				OutputStream sos = response.getOutputStream();
				response.setContentType("application/octet-stream");
				// 获取原文件名
				String name = (String) gridFSDBFile.get("filename");
				String fileName = new String(name.getBytes("GBK"), "ISO8859-1");
				// 设置下载文件名
				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName);
				// 向客户端输出文件
				gridFSDBFile.writeTo(sos);
				sos.flush();
				sos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存文件
	 * @param multipartFile
	 * @param gridFS
	 * @param key
	 * @param values
	 * @return
	 */
	public static boolean addFile(MultipartFile multipartFile, GridFS gridFS, String[] key, String[] values) {
		boolean bl = false;
		byte[] bytes = null;
		try {
			bytes = multipartFile.getBytes();
			GridFSFile file = gridFS.createFile(bytes);
			for (int i = 0; i < key.length; i++) {
				file.put(key[i], values[i]);
			}
			file.put("uploadDate", new Timestamp(System.currentTimeMillis()));
			file.save();
			bl = true;
			return bl;
		} catch (IOException e) {
			e.printStackTrace();
			return bl;
		}
	}
	
}
