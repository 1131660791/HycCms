package com.dome.util.oss;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.dome.util.uuid.UUIDUtil;

/**
 * 阿里云工具
 * @author admin
 *
 */
public class OSSUtils {
	
	private static OSSConfig config = new OSSConfig();

	// log 日志
	private static Log log = LogFactory.getLog(OSSUtils.class);

	/**
	 * 创建存储空间
	 * 
	 * @param ossClient
	 *            OSS连接
	 * @param bucketName
	 *            存储空间
	 * @return
	 */
	public static String createBucketName(String bucketName) {
		config = config == null ? new OSSConfig() : config;
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		// 存储空间
		final String bucketNames = bucketName;
		if (!ossClient.doesBucketExist(bucketName)) {
			// 创建存储空间
			Bucket bucket = ossClient.createBucket(bucketName);
			log.info("创建存储空间成功");
			return bucket.getName();
		}
		return bucketNames;
	}

	/**
	 * 
	 * @Title: saveFileName @Description: TODO(生成阿里云文件保存唯一保存名称) @param @param
	 *         fileCategory @param @param originalFilename @param @return 参数 @return
	 *         String 返回类型 @throws
	 */
	private static String saveFileName(String fileCategory, String originalFilename) {
		StringBuilder sb = new StringBuilder("");
		// 基础保存路径
		sb.append(config.getPicLocation());
		sb.append("cms/");
		// 文件所属类别
		sb.append(fileCategory);
		// 文件分隔
		sb.append("/"); // File.separator.toString()
		// 文件保存唯一标示ID UUID UUID.randomUUID().toString().toUpperCase().replace("-", "")
		sb.append(UUIDUtil.getUUID());
		// 文件保存真实名称
		sb.append("/");
		sb.append(originalFilename);
		return sb.toString();
	}

	/**
	 * 获取上传文件的类型
	 * @param fileType - 文件类型
	 * @return
	 */
	private static String contentType(String fileType) {
		fileType = fileType.toLowerCase();
		String contentType = "";
		switch (fileType) {
		case "bmp":
			contentType = "image/bmp";
			break;
		case "gif":
			contentType = "image/gif";
			break;
		case "png":
		case "jpeg":
		case "jpg":
			contentType = "image/jpeg";
			break;
		case "html":
			contentType = "text/html";
			break;
		case "txt":
			contentType = "text/plain";
			break;
		case "vsd":
			contentType = "application/vnd.visio";
			break;
		case "ppt":
		case "pptx":
			contentType = "application/vnd.ms-powerpoint";
			break;
		case "doc":
		case "docx":
			contentType = "application/msword";
			break;
		case "xml":
			contentType = "text/xml";
			break;
		case "mp4":
			contentType = "video/mp4";
			break;
		default:
			contentType = "application/octet-stream";
			break;
		}
		return contentType;
	}

	/**
	 * 
	 * @Title: putObject 流方式上传文件
	 * @Description: TODO(文件上传到OSS云端)
	 * @param file
	 *            文件流对象
	 * @param fileCategory
	 *            文件分类类别
	 * @param 参数
	 * @return Map<String,String> 返回类型 key:上传到云端后返回的key值(后面下载查询用) url:文件上传到后的云端访问路径
	 *         fileType:文件扩展类型(如果:jpg PDF DOC TXT....) fileName:文件上传时名称 @throws
	 */
	public static Map<String, String> mpFputObject(MultipartFile file, String fileCategory) {
		createBucketName(config.getBucketName());
		Map<String, String> result = new HashMap<String, String>();
		config = config == null ? new OSSConfig() : config;
		String url = null; // 默认null
		OSSClient ossClient = null;
		try {
			String originalFilename = file.getOriginalFilename();
			// 上传文件扩展类型
			String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
			// 上传文件的真实名称--如果文件保存名称有修改需求以修改后名称为真实名称--阿里云保存的文件名称
			String filePathName = saveFileName(fileCategory, originalFilename);
			// 上传文件流对象
			InputStream input = file.getInputStream();
			ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
			ObjectMetadata meta = new ObjectMetadata(); // 创建上传Object的Metadata
			meta.setContentType(OSSUtils.contentType(fileType)); // 设置上传内容类型
			meta.setCacheControl("no-cache"); // 被下载时网页的缓存行为
			PutObjectRequest request = new PutObjectRequest(config.getBucketName(), filePathName, input, meta);
			// 创建上传请求
			ossClient.putObject(request);
			result.put("key", request.getKey());
			url = config.getEndpoint().replaceFirst("http://", "http://" + config.getBucketName() + ".") + "/" + filePathName; // 上传成功再返回的文件路径
			result.put("ossUrl", url);
		} catch (OSSException oe) {
			oe.printStackTrace();
			return null;
		} catch (ClientException ce) {
			ce.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			ossClient.shutdown();
		}
		return result;
	}
	
	/**
	 * 根据key删除OSS服务器上的文件
	 * 
	 * @param ossClient
	 *            oss连接
	 * @param bucketName
	 *            存储空间
	 * @param folder
	 *            模拟文件夹名 如"qj_nanjing/"
	 * @param key
	 *            Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	 */
	public static void deleteOssFile(String key) {
		config = config == null ? new OSSConfig() : config;
		OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		ossClient.deleteObject(config.getBucketName(), key);
		log.info("删除" + config.getBucketName() + "下的文件" + key + "成功");
	}
}
