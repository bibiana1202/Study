package org.hyejung.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.hyejung.domain.DiaryVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

//@WebAppConfiguration은 주로 Spring MVC 컨트롤러, 요청 매핑, 뷰 리졸버 등을 테스트하는 데 유용합니다.
@WebAppConfiguration //웹 애플리케이션 관련 테스트에서만 필요
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml") //웹 애플리케이션 리소스와 관련된 설정(servlet-context.xml 등)을 @ContextConfiguration과 함께 지정해야 웹 컨텍스트가 올바르게 로드
@Log4j
public class DiaryServiceTests {

	@Autowired
	private DiaryService service; // BoardServiceImpl이 자동으로 주입됨
	
	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	
	// 1. 등록 작업의 구현과 테스트
	@Test
	public void testRegister() throws Exception {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(113L);
		diary.setTitle("새로 작성하는 글");
		diary.setContent("새로 작성하는 내용");
		diary.setWriter("newbie");
		diary.setEmotionStatus("기쁨");

		service.register(diary);
		
		log.info("생성된 게시물의 번호:"+diary.getDno());
	}
	
	// 2. 목록(리스트) 작업의 구현과 테스트
	@Test
	public void testGetList() throws Exception {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(108L);
	    diary.setRegdate(new Date()); // 오늘 날짜
	    
		service.getList(diary).forEach(dia -> log.info(dia));
	}
	
	// 3. 조회 작업의 구현 과 테스트
	@Test
	public void testGet() throws Exception {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(108L);
	    diary.setDno(15L);
		log.info(service.get(diary));
	}
	// 4. 삭제 구현과 테스트
	@Test
	public void testDelete() throws Exception {
		// 게시물 번호의 존재 여부를 확인하고 테스트할 것
		DiaryVO diary = new DiaryVO();
		diary.setUserid(108L);
	    diary.setDno(15L);
		log.info("REMOVE RESULT: "+service.remove(diary));
	}
	
	// 4. 수정 구현과 테스트
	@Test
	public void testUpdate() throws Exception{
		DiaryVO diary = new DiaryVO();
		diary.setUserid(108L);
	    diary.setDno(16L);
	    
	    DiaryVO board = service.get(diary);
		
		if(board==null) {
			return;
		}
		
		board.setTitle("제목 수정합니다.");
		log.info("MODIFY RESULT: "+service.modify(board));
	}
}
