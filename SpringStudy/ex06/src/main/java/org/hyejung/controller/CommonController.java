package org.hyejung.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {

		// 접근 제한 페이지
		@GetMapping("/accessError")
		public void accessDenied(Authentication auth, Model model) {
			log.info("access Denied:"+auth);
			model.addAttribute("msg","Acess Denied");
		}
		
		// 커스텀 로그인 화면 페이지
		@GetMapping("/customLogin")
		public void loginInput(String error,String logout, Model model) {
			log.info("error: " + error);
			log.info("logout: "+logout);
			
			if(error!= null) {
				model.addAttribute("error","Login Error Check Your Account");
			}
			
			if(logout != null) {
				model.addAttribute("logout","Logout!!");
			}
		}
		
		// 로그아웃
		@GetMapping("/customLogout")
		public void logoutGET() {
			log.info("custom logout");
		}
}