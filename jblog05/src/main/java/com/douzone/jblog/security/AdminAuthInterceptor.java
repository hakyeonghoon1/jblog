package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.vo.UserVo;

public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {	//컨트롤러가 아니라는 말
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method의 @Admin 받아오기
		Admin admin = handlerMethod.getMethodAnnotation(Admin.class);
		
		//4. HandlerMethod에 @Admin가 없으면 Type에 있는지 확인
		if(admin == null) {
			// 과제
			admin = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Admin.class);
		}
		
		//5. Type과 Method에 @Admin가 적용이 안되어 있는 경우
		if(admin ==null) {
			return true;
		}
		
		//6. @가 적용이 되어 있기 때문에 인증(enfication) 여부 확인
		
		String URI = request.getRequestURI();
		String[] URISplit = URI.split("/");
		String blogId = URISplit[2];
		
		HttpSession session = request.getSession();

		if(session == null) {
			
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 로그인 아이디와 블로그 아이디가 같지 않으면 블로그 메인 페이지로 이동
		if(!blogId.equals(authUser.getId())) {
			response.sendRedirect(request.getContextPath()+"/"+blogId);
			return false;
		}
		
		return true;
	}

}
