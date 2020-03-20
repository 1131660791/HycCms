package com.dome.config.shiro.realm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dome.bean.TSBaseUser;
import com.dome.bean.TSRole;
import com.dome.bean.vo.TSUserVo;
import com.dome.web.sysrole.service.TSRoleService;
import com.dome.web.sysuser.service.TSBaseUserService;
import com.dome.web.sysuser.service.TSUserService;

/**
 * 
 * @author admin
 */
public class ShiroRealm extends AuthorizingRealm {
	
	@Autowired
    private TSBaseUserService  tSBaseUserService;
	
	@Autowired
	private TSRoleService tSRoleService;
	
	@Autowired
	private TSUserService tSUserService;
	
	/**
	 * 为当前登录的Subject授予角色和权限
	 * 该方法的调用时机为需授权资源被访问时 每次访问需授权资源时都会执行该方法中的逻辑,如果用户授权内容被缓存，则在不会重复调用该方法
	 * 
	 * PrincipalCollection是一个身份集合，因为我们可以在Shiro中同时配置多个Realm，所以呢身份信息可能就有多个；
	 *   因此其提供了PrincipalCollection用于聚合这些身份信息：
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
    	
        List<String> permissionList = new ArrayList<String>();
        Set<String> roles = new HashSet<String>(); //角色与权限为一对多关系
        Set<String> permission = new HashSet<String>();
        if (null != principals) {
            String loginName = principals.getPrimaryPrincipal().toString(); // 获取用户登录名
            if(loginName!=null){
	        	Map<String,Object> parameter  = new HashMap();
	        	parameter.put("loginName", loginName);
	        	
	        	List<TSRole> tRoles = tSRoleService.findUserRoleInfo(parameter);
	        	
	       // 	SysRoleVo sysRoleMenus = sysRoleService.findSysRoleButtonInfo(parameter);
	        	// 查询用户角色 如 admin ...
	            if(tRoles!=null){
	            	for(int i=0;i < tRoles.size(); i++) {
	            		roles.add(tRoles.get(i).getRolecode());
	            	}
	            }
	           
//             if(sysRoleMenus.getMenus() != null && sysRoleMenus.getMenus().size() > 0) {
//                	// 查询用户权限 如:增加权限 add 删除权限 delete ...
//                	for(int i=0;i < sysRoleMenus.getButtons().size(); i++){
//                		permission.add(sysRoleMenus.getButtons().get(i).getButtonCode());
//                	}
//              }
	            
	            simpleAuthorInfo.addRoles(roles); // 设置角色
	            simpleAuthorInfo.addStringPermissions(permission); // 权限集合
	            
	            // session 登录
	            TSUserVo sysUser = tSUserService.findSysUserInfo(parameter);
	    		Session session = SecurityUtils.getSubject().getSession();
	    		session.setAttribute("sysUser", sysUser);
	    	//	session.setAttribute("sysRoles", tRoles);
            }
        } else {
            throw new AuthorizationException();
        }
        return simpleAuthorInfo;
    }
    
    /**
     * 验证当前登录的Subject 在subject.login(AuthenticationToken)时调用该方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String,Object> parameter =new HashMap<String,Object>();
        
        String  loginName = token.getUsername();
        parameter.put("loginName", loginName);
		// 查询出用户
        TSBaseUser baseUser = tSBaseUserService.findBaseUserInfo(parameter);
        String passWord = baseUser.getPassword();
        // 参数1.用户认证的对象(subject.getPrincipal();返回的对象),
        // 参数2.从数据库根据用户名查询到的用户的密码  
        // 参数3.把当前自定义的realm对象传给SimpleAuthenticationInfo,在配置文件需要注入 
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), passWord,getName());
		return authcInfo;
	}
	
}
