package org.hyejung.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.hyejung.domain.SampleDTO;
import org.hyejung.domain.SampleDTOList;
import org.hyejung.domain.TodoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller // -> servlet-context.xml
@RequestMapping("/sample/*") // 현재 클래스의 모든 메서드들의 기본적인 URL경로
@Log
public class SampleController {

	// 1) @Controller, @RequestMapping
	@RequestMapping("")
	public void basic() {
		log.info("basic.......");
	}
	
	// 2) @RequestMapping 의 변화
	@RequestMapping(value="/basic", method= {RequestMethod.GET,RequestMethod.POST})
	public void basicGet() {
		log.info("basic get.......");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get.....");
	}
	
	//3) Controller의 파라미터 수집
	@GetMapping("/ex01")
	public String ex01(@ModelAttribute SampleDTO dto) {
		log.info(""+dto);
		return "ex01";
	}
	
	// @RequestParam : 파라미터로 사용된 변수의 이름과 전달되는 파라미터의 이름이 다른 경우에 유용하게 사용
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name,
			@RequestParam("age") int age) {
		log.info("name:"+name);
		log.info("age:"+age);
		return "ex02";
	}
	
	// 리스트 처리
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids:"+ids);
		return "ex02List";
	}
	
	// 배열 처리
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids: " + Arrays.toString((ids)));
		return "ex02Array";
	}
	
	// 객체 리스트
	@GetMapping("/ex02Bean")
	public String ex02Bean(@ModelAttribute SampleDTOList list) {
		log.info("list dtos:" + list);
		return "ex02Bean";
	}
	
	//@InitBinder : 변환 처리
//	@InitBinder 
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
//	}
	
	// simpleDataFormat 으로 변환
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo:"+todo);
		return "ex03";
	}
	 
	// 4) Model 이라는 데이터 전달자
	// @ModelAttribute : 강제로 전달받은 파라미터를 Model에 담아서 전달하도록 할때 필요한 어노테이션!
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto:"+dto);
		log.info("page:"+page);
		return "/sample/ex04";
	}
	
	//5) Controller 의 리턴타입
	
	// void: 해당 URL의 경로를 그대로 jsp 파일의 이름으로 사용!
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05.....");
	}
	
	// String : 화면
	
	// 객체 : VO,DTO
	// 스프링 MVC 는 자동으로 브라우저에 JSON 타입으로 객체를 변환해서 전달!!
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06.....");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		return dto;
	}
	
	// ResponseEntity 타입
	// 원하는 헤더 정보나 데이터를 전달
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07.....");
		String msg = "{\"name\":\"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(msg,header,HttpStatus.OK);
	}
}
