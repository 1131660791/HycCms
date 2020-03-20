package com.dome.util.uuid;

import java.util.UUID;

/**
 * UUID 工具类
 * @author hzw
 */
public class UUIDUtil {

	/**
	 * 创建UUId
	 * @return
	 */
	public static String getUUID(){
	 	UUID uuid= UUID.randomUUID();
	 	String id = uuid.toString().replace("-", "");
		return id ;
	}
	
}
