package org.hyejung.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component("customAccessDenied")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("Access Denied Handler");
		log.error("Redirect...");
		// 접근 제한이 걸리는 경우 리다이렉트 하는 방식
		response.sendRedirect("/accessError");
	}
	
}
