package com.dome.config.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.util.StringUtils;

import com.dome.bean.TSUser;
import com.dome.config.constant.SessionVariable;

/**
 * Session 监听 移除 
 * @author Administrator
 *
 */
public class SessionListener implements HttpSessionListener {
	
	private HttpServletRequest request;
	
	// log4j 对象  
//	private static Log  log = LogFactory.getLog(LoginAction.class);
	
    // key为sessionId，value为HttpSession，使用static，定义静态变量，使之程序运行时，一直存在内存中。  
    private static java.util.Map<String, HttpSession> sessionMap = new java.util.concurrent.ConcurrentHashMap<String, HttpSession>(500);  
   
    // 
    private static Map<String, String> userSessionMap = new HashMap<String, String>();  
	
    // 用户 信息
    private static Map<String, TSUser> userVoMap = new HashMap<String, TSUser>();  
    
	public void sessionCreated(HttpSessionEvent hse) {
		// TODO Auto-generated method stub
		//设置session失效时间
		hse.getSession().setMaxInactiveInterval(60*30);//单位秒
		ServletContext application = hse.getSession().getServletContext();
//		log.debug("一个浏览器客服端请求链接 session创建...");
	}
	
	/**
	 * session失效时，移除在线人员信息，更新小组累计在线时间
	 */
	public void sessionDestroyed(HttpSessionEvent hse) {
		// TODO Auto-generated method stub
		//这里不能用userId，因为该值已经为空了
		String userId = (String)hse.getSession().getAttribute(SessionVariable.USER_ID);
		
		ServletContext application = hse.getSession().getServletContext();
	    String sessinId = hse.getSession().getId();
		/*//移除用户列表中的用户名  
		Map<String,SysUser> userMembers=(Map<String,SysUser>)application.getAttribute("users");
		if(userMembers!=null){
			SysUser sysUser = userMembers.get(sessinId);
			if(sysUser!=null){
				userMembers.remove(sysUser);
				SysUserState sysUserState = sysUser.getSysUserState();
				Date lastLoginTime = sysUserState.getLastLoginTime();
				// 更新人员登陆时长
				long time  = (new Date().getTime()-lastLoginTime.getTime())/60000;
				sysUserState.setTotalOnlineTime(Integer.parseInt(time+""));
				sysUserState.setIsOnline(0);//离线
				if(sysUserStateService==null){
        			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext
        			(hse.getSession().getServletContext());
        			sysUserStateService =(SysUserStateService)applicationContext.getBean("sysUserStateService");
        		}
				sysUserStateService.updateOnlineState(sysUser);
				log.debug("用户:"+sysUser.getUserName()+"离线....");
			}
			log.debug("sessionId:"+sessinId+"断开连接....");
		}*/
	    removeUserSession(userId);
	}
	
	 /** 
     * 得到在线用户会话集合 
     */  
    public static List<HttpSession> getUserSessions() {  
        List<HttpSession> list = new ArrayList<HttpSession>();  
        Iterator<String> iterator = getSessionMapKeySetIt();  
        while (iterator.hasNext()) {  
            String key = iterator.next();  
            HttpSession session = getSessionMap().get(key);  
            list.add(session);  
        }  
        return list;  
    }  
    
    /** 
     * 得到用户对应会话map，key为用户ID,value为会话ID 
     */  
    public static Map<String, String> getUserSessionMap() {  
       /* 
        Iterator<String> iter = getSessionMapKeySetIt();  
        while (iter.hasNext()) {  
            String sessionId = iter.next();  
            HttpSession session = getSessionMap().get(sessionId);  
            String userId = (String) session.getAttribute(SessionVariable.USER_ID);  
            if (userId != null) {  
            	userSessionMap.put(userId, sessionId);  
            }  
        }  */
        return userSessionMap;  
    }  
  
    /** 
     * 移除用户Session 
     */  
    public synchronized static void removeUserSession(String userId) {  
     // Map<String, String> userSessionMap = getUserSessionMap();  
        if (userSessionMap.containsKey(userId)) {  
            String sessionId = userSessionMap.get(userId);  
            HttpSession httpSession= getSessionMap().get(sessionId);
            getSessionMap().remove(sessionId); 
            if(httpSession!=null){
            	httpSession.removeAttribute(SessionVariable.USER_ID);
				//httpSession.invalidate();
            }
            userSessionMap.remove(userId);
            
            if(userVoMap.containsKey(userId)){
            	TSUser sysUser = userVoMap.get(userId);
//           	SysUserState sysUserState = sysUser.getSysUserState();
            	// 获取登录时间
//				Date lastLoginTime = sysUserState.getLastLoginTime();
				//更新人员登陆时长
//				long time  = (new Date().getTime()-lastLoginTime.getTime())/60000;//分钟
//				sysUserState.setTotalOnlineTime(Integer.parseInt(time+""));
//				sysUserState.setIsOnline(0);//离线
//				if(sysUserStateService!=null){
//					sysUserStateService.updateOnlineState(sysUser);
//        		}
//				log.debug("用户:"+sysUser.getUserName()+"离线....");
            }
            userVoMap.remove(userId);
        }  
    }  
  
    /** 
     * 增加用户到session集合中 
     */  
    public static void addUserSession(String userId,HttpSession session,TSUser sysUser) {  
        getSessionMap().put(session.getId(), session); 
        userSessionMap.put(userId, session.getId());  
        userVoMap.put(userId, sysUser);
    }  
  
    /** 
     * 移除一个session 
     */  
    public static void removeSession(String sessionID) {  
        getSessionMap().remove(sessionID);  
    }  
  
    public static boolean containsKey(String key) {  
        return getSessionMap().containsKey(key);  
    }  
  
    /** 
     * 判断该用户是否已重复登录，使用 
     * 同步方法，只允许一个线程进入，才好验证是否重复登录 
     * @param user 
     * @return 
     */  
    public synchronized static boolean checkIfHasLogin(String userId) {  
        Iterator<String> iter = getSessionMapKeySetIt();  
        while (iter.hasNext()) {  
            String sessionId = iter.next();  
            HttpSession session = getSessionMap().get(sessionId);  
            String userIdTemp = (String) session.getAttribute(SessionVariable.USER_ID);  
            if (StringUtils.hasLength(userIdTemp)&&userIdTemp.equals(userId)){  
                return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * 获取在线的sessionMap 
     */  
    public static Map<String, HttpSession> getSessionMap() {  
        return sessionMap;  
    }  
  
    /** 
     * 获取在线sessionMap中的SessionId 
     */  
    public static Iterator<String> getSessionMapKeySetIt() {  
        return getSessionMap().keySet().iterator();  
    }  
    
    /**
     * 获取在线sessionMap中的SessionId 
     */  
    public static Set<String> getUserSessionMapKeySet() {  
    	return userSessionMap.keySet();
    }  
	
/*
	 private String getIp(){
	        String ip = request.getHeader("x-forwarded-for");
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	        }
	       
	        return ip;
	 }
*/
	
	public static void main(String[] args) throws ParseException {
		Date time1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		Date time2 = DateUtil.parseDateTime("2014-07-21 15:20:33");
		//System.out.println((time2.getTime()-time1.getTime())/60000);
	}

}
