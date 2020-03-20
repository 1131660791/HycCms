package com.dome.util.quartz.test;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import com.dome.config.constant.ConstantVariable;
import com.dome.util.quartz.QuartzManager;
import com.dome.util.quartz.job.DomeJob;

/**
 * ContextRefreshedEvent 事件 在加载完 Spring 框架 时 进行开启 监听 
 * @author hzw
 *
 */
@Controller
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {
	
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
			// 开启 定时器 
			QuartzManager.addJob("data_solrss_job", DomeJob.class, "0 0 0/2 * * ?");
		}
	}
}
