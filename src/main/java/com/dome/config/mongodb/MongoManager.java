package com.dome.config.mongodb;

import java.io.IOException;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.gridfs.GridFS;
import com.dome.util.properties.PropertiesUtil;

/**
 * MongoDB 管理
 * @author Administrator
 * 
 */
public class MongoManager {
	
	private static Mongo mongo = null;
	
	private MongoManager() {
		
	}

	/**
	 *  获得 mongo 文件集合连接
	 * @param  DBName： 为mongo文件存储集合名字
	 * @param  filetype: 为分类文件存储
	 * @return 
	 * @throws IOException
	 */
	public static GridFS getMongoDBConnect() throws IOException {
		PropertiesUtil propertyUtil = new PropertiesUtil();
		// 获取 mongo 集合名
		String DBName = propertyUtil.getProperties("mongo.DBname", "/database.properties");
		String filetype = propertyUtil.getProperties("mongo.filetype", "/database.properties");
		DB db = MongoManager.getDB(DBName);
		GridFS gridFS = new GridFS(db, filetype);
		return gridFS;
	}
	
	/**
	 * 根据名称获取DB，相当于是连接
	 * @param dbName 
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static DB getDB(String dbName) {
		if (mongo == null) {
			// 初始化
			try {
				initMongoDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mongo.getDB(dbName);
	}

	/**
	 * 初始化连接池，设置参数。
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	private static void initMongoDB() throws NumberFormatException, IOException {
		PropertiesUtil propertyUtil = new PropertiesUtil();
		String host = propertyUtil.getProperties("mongo.IP", "/application.properties");// 主机地址
		int port = new Integer(propertyUtil.getProperties("mongo.port", "/application.properties"));// 端口
//		int poolSize = new Integer(propertyUtil.getProperties("mongo.poolSize", "/application.properties"));// 连接数量
//		int blockSize = new Integer(propertyUtil.getProperties("mongo.blockSize", "/application.properties")); // 等待队列长度
		// 其他参数根据实际情况进行添加
		try {
			mongo = new Mongo(host, port);
			MongoOptions opt = mongo.getMongoOptions();
//			opt.connectionsPerHost = poolSize;
//			opt.threadsAllowedToBlockForConnectionMultiplier = blockSize;
		} catch (MongoException e) {
			// log error
			
		}
	}
	
	
}
