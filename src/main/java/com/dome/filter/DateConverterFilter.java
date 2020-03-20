package com.dome.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * spring过滤器，预处理日期格式
 * 
 * @author vbon.hxp
 * 
 */
public class DateConverterFilter implements WebBindingInitializer {
	
	private static SimpleDateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	private static SimpleDateFormat df3 = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat df4 = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public void initBinder(WebDataBinder binder, WebRequest request) {
		Iterator<String> keys = request.getParameterNames();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			//匹配所有时间类型的字符串，对其进行java.util.Date类型注册
			if(key.matches(".*Time") || key.matches(".*time")||key.matches("brithday") || key.matches("birthday")|| key.matches("arrayDate")){
				String value = request.getParameter(key);
				if(StringUtils.hasLength(value)){
					if(value.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
						binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
					}else if(value.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}")){
						binder.registerCustomEditor(Date.class, new CustomDateEditor(df1, false));
					}else if(value.matches("\\d{4}年\\d{1,2}月\\d{1,2}日\\s\\d{1,2}时\\d{1,2}分\\d{1,2}\\秒")){
						binder.registerCustomEditor(Date.class, new CustomDateEditor(df2, false));
					}else if(value.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")){
						binder.registerCustomEditor(Date.class, new CustomDateEditor(df3, false));
					}else{
						binder.registerCustomEditor(Date.class, new CustomDateEditor(df4, false));
					}
				}
			}
		}
	}

}
