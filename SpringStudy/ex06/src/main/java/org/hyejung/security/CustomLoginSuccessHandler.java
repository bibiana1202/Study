package org.hyejung.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		
	
		log.warn("Login Success");
		// 사용자에게 부여된 권한
		List<String> roleNames = new ArrayList<>();
		auth.getAuthorities().forEach(authority->{
			roleNames.add(authority.getAuthority());
		});
		
		log.warn("ROLE NAMES: "+roleNames);
		// Authentication 객체를 이용하여 사용자가 가진 모든 권한을 문자열로 체크
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/sample/admin");
			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/sample/member");
			return;
		}
		
		response.sendRedirect("/");
	}
	
}
