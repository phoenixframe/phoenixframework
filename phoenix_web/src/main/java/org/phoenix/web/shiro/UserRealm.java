package org.phoenix.web.shiro;

import java.util.HashSet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.phoenix.web.filter.InitServlet;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IUserService;

import com.google.common.collect.Lists;

public class UserRealm extends AuthorizingRealm {
	

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = ((User)principals.getPrimaryPrincipal());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//在配置文件中授权
/*		List<String> perms = Lists.newArrayList();
		if(user.getRole() == 0){
			perms.add("/user/list/**");
			perms.add("/user/add/**");
			perms.add("/usr/update/**");
			perms.add("/usr/delete/**");
		}
		info.setStringPermissions(new HashSet<String>(perms));
		*/

		info.setRoles(new HashSet<String>(Lists.newArrayList(user.getRole()+"")));
		return info;
	}
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		IUserService userService = (IUserService)InitServlet.getBean("userService");
		String username = token.getPrincipal().toString();
		String password = new String((char[])token.getCredentials());
		User user = userService.login(username, password);
		System.out.println(username+","+password+","+user.getPassword());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		info.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
		SecurityUtils.getSubject().getSession().setAttribute("loginUser", user);
		return info;
	}

}
