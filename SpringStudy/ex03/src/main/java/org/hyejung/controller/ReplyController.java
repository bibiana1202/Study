package org.hyejung.controller;

import java.util.List;

import org.hyejung.domain.Criteria;
import org.hyejung.domain.ReplyVO;
import org.hyejung.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 스프링의 빈으로 인식할수 있게
@Log4j  
@RequestMapping("/replies/*") // /board로 시작하는 모든 처리
@AllArgsConstructor // ReplyController는 ReplyService에 대해서 의존적이므로 @AllArgsConstructor를 이용해서 생성자를 만들고 자동으로 주입하도록 한다!
public class ReplyController {

	/*
	 * ReplyController는 ReplyService라는 인터페이스에 의존합니다.
	 * ReplyServiceImpl은 ReplyService 인터페이스를 구현한 클래스입니다.
	 * 스프링 컨테이너는 @Service 어노테이션을 사용해서 ReplyServiceImpl을 빈으로 등록합니다.
	 * ReplyController의 생성자에서 ReplyService 타입을 요구할 때, 스프링은 자동으로 해당 타입의 빈(ReplyServiceImpl)을 찾아 주입합니다.
	 */
	private ReplyService service;
	
	// 등록 작업과 테스트
	@PostMapping(value = "/new", consumes ="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		//ResponseEntity는 Spring Framework에서 제공하는 클래스이며, HTTP 응답을 보다 세밀하게 구성할 수 있도록 도와줍니다.
		log.info("ReplyVO" + vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT: " + insertCount);
		
		return insertCount == 1 ? new ResponseEntity<String>("success",HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); // 삼항 연산자 처리 
	}
	
	// 특정 게시물의 댓글 목록 확인
	@GetMapping(value ="/pages/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page , @PathVariable("bno") Long bno){
		//@PathVariable은 Spring MVC에서 RESTful 웹 서비스를 구현할 때 URL 경로의 일부를 변수로 매핑하기 위해 사용되는 어노테이션입니다. URL 경로의 특정 값을 메소드 파라미터로 받아들여 처리하는 데 사용됩니다. 
		log.info("getList............");
		Criteria cri = new Criteria(page,10);
		log.info(cri);
		
		return new ResponseEntity<>(service.getList(cri,bno),HttpStatus.OK);
	}
	
	// 댓글 조회
	@GetMapping(value ="/{rno}", produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("get: " + rno);
		return new ResponseEntity<>(service.get(rno),HttpStatus.OK);
	}
	
	// 댓글 삭제
	@DeleteMapping(value="/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove (@PathVariable("rno") Long rno){
		log.info("remove: " + rno);
		return service.remove(rno)==1 ? new ResponseEntity<String>("success",HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 댓글 수정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value ="/{rno}",consumes ="application/json",produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		vo.setRno(rno);
		log.info("rno  :"+rno);
		
		log.info("modify: "+vo);
		
		return service.modify(vo) == 1 ? new ResponseEntity<>("success",HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
