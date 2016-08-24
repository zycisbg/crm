package com.zyc.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zyc.crm.bean.Authority;
import com.zyc.crm.bean.User;
import com.zyc.crm.model.Navigation;
import com.zyc.crm.service.UserService;

@Controller
public class UserHandler {

	@Autowired
	private UserService userService;
	// 自动装箱国际化配置文件
	// 利用国际换配置文件来显示错误消息
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**
	 * 获取用户所有的权限  
	 * 因为用户中只有子权限
	 * 所有要先把父权限set进去  
	 * 再把当前用户的自权限set进去
	 * @param session
	 * @return
	 */
	@RequestMapping("/user/navigate")
	@ResponseBody
	public List<Navigation> navigate(HttpSession session){
		
		User user = (User) session.getAttribute("user");
		
		//创建一个导航的集合
		List<Navigation> navigations = new ArrayList<>();
		
		//
		String contextPath = session.getServletContext().getContextPath();
		
		Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
		navigations.add(top);
		
		Map<Long, Navigation> parentNavigations = new HashMap<Long, Navigation>();
		//循环把权限的url设置到导航中
		//在这里只把当前用户的子权限全部搞定
		for(Authority authority: user.getRole().getAuthorities()){
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			navigation.setUrl(contextPath+authority.getUrl());
			
			//设置父权限
			Authority parentAuthority = authority.getParentAuthority();
			
			Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
			if(parentNavigation==null){
				parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
				top.getChildren().add(parentNavigation);
			}
			
			parentNavigation.getChildren().add(navigation);
		}
		
		
		
		return navigations;
	}
	

	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {

		session.removeAttribute("user");

		return "redirect:/index";
	}

	@RequestMapping("/user/shiro-login")
	public String login2(@RequestParam("name") String name,
			@RequestParam("password") String password, HttpSession session,
			RedirectAttributes attributes, Locale locale) {
		
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		
		//验证当前用户是否登录
		if(!currentUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			token.setRememberMe(true);
			
			try {
				currentUser.login(token);
			} catch (AuthenticationException e) {
				String code = "error.user.login";
				String message = messageSource.getMessage(code, null, locale);
				
				attributes.addFlashAttribute("message", message);

				return "redirect:/index";
			}
		}
		//可以通过调用 Subject 的 .getPrincipals().getPrimaryPrincipal() 获取到
		//在 realm 中创建 SimpleAuthenticationInfo 对象时的 principal 实例. 
		session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}

	
}
