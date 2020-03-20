package com.dome.util.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dome.util.quartz.CommonUtil;

import redis.clients.jedis.ShardedJedisPool;

/**
 * 定时器 执行类
 * @author Administrator
 *
 */
public class DomeJob implements Job{

	/*private ShardedJedisPool shardedJedisPool; */

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// 获取实体类对象  比如 Service 
		/*shardedJedisPool = CommonUtil.getCurrentApplicationContext().getBean(ShardedJedisPool.class);*/
		
	}
	

}
