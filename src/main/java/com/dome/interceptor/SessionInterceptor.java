package com.dome.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dome.config.constant.SessionVariable;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.properties.PropertiesUtil;

/**
 * session过滤 
 * @author lenovo
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {  
  
	Logger logger = Logger.getRootLogger();
    /* 
     * (non-Javadoc) 
     * @see 
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle 
     * (javax.servlet.http.HttpServletRequest, 
     * javax.servlet.http.HttpServletResponse, java.lang.Object) 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	//加载配置文件配置信息
    	PropertiesUtil.loadSettingFilalValuesProperties();
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8");  
        // 后台session控制  
        String[] noFilters = new String[] {
        		"login.jsp", 
        		"login.action",
        		"findSysUserList.action",
        		"finSysStateInfo.action",
        		"downWorkExcel.action",
        		"findRedisData.action"
        		
        };
        
        String uri = request.getRequestURI();  
      /* 
        if(uri.indexOf("Remote.action") != -1){
        	 return super.preHandle(request, response, handler);  
        }
      */
        
        
        if (uri.indexOf("action") != -1) {  
            boolean beFilter = true;  
            for (String s : noFilters) {  
                if (uri.indexOf(s) != -1) { 
                    beFilter = false;  
                    break;  
                }  
            } 
           // 过滤远程调用
           /* 
            if (uri.lastIndexOf("Remote") != -1) {  
                beFilter = false;  
            } 
           */
            if (beFilter) {  
                Object obj = request.getSession().getAttribute(  
                        SessionVariable.USER_ID);  
                //System.out.println("userID=="+request.getSession().getValue(SessionVariable.USER_ID));
                if (null == obj) {  
                	logger.debug("登录失效...");
                	if (uri.lastIndexOf("Remote")>0) {
                		//学生端android session失效
                       AjaxUtil.ajaxResponse("{\"error\":\"登录失效...\"}", response);
                       return false;
                    } 
                    // 未登录  或者session失效
                    PrintWriter out = response.getWriter();  
                    StringBuilder builder = new StringBuilder();  
                    builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
                    builder.append("try{");  
                    builder.append("reLoginWin();");  
                    builder.append("}catch(err){");  
                    builder.append("alert(\"页面过期,请重新登录\");");//android会取用 关键字“页面过期”。  
                    builder.append("window.top.location.href=\"");  
                    builder.append(request.getContextPath());  
                    builder.append("/login/login.action\";");  
                    builder.append("}");  
                    builder.append("</script>");  
                    out.print(builder.toString());  
                    out.close();  
                    return false;  
                } else {  
                	String  nav_weight =  request.getParameter("nav_weight");
                    if(nav_weight!=null&&!"".endsWith(nav_weight)){
                     	request.getSession().setAttribute(SessionVariable.NAVIGATION_WEIGHT, nav_weight);
                    }
                }  
            }  
        }  
        
        /*
		  if("admin".equals(request.getParameter("accountName")) ||"admin".equals(SessionUtil.getUserName(request))
				  ||uri.indexOf("findSoftwareNewVersionRemote")>1 ){
			  DataSourceContextHolder.setDbType("htDataSource"); 
		  }else{
			  DataSourceContextHolder.setDbType("zhjyDataSource"); 
		  }
		 */
         
        return super.preHandle(request, response, handler);  
    }  
}
