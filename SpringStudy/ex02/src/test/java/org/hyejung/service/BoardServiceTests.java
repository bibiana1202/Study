package org.hyejung.service;

import static org.junit.Assert.assertNotNull;

import org.hyejung.domain.BoardVO;
import org.hyejung.domain.Criteria;
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
public class BoardServiceTests {

	@Autowired
	private BoardService service; // BoardServiceImpl이 자동으로 주입됨
	
	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	
	// 1. 등록 작업의 구현과 테스트
	@Test
	public void testRegister() throws Exception {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("newbie");
		
		service.register(board);
		
		log.info("생성된 게시물의 번호:"+board.getBno());
	}
	
	// 2. 목록(리스트) 작업의 구현과 테스트
	@Test
	public void testGetList() throws Exception {

		//service.getList().forEach(board -> log.info(board));
		// 페이징 처리
		service.getList(new Criteria(2,10)).forEach(board->log.info(board));
	}
	
	// 3. 조회 작업의 구현 과 테스트
	@Test
	public void testGet() throws Exception {
		log.info(service.get(1L));
	}
	
	// 4. 삭제/수정 구현과 테스트
	@Test
	public void testDelete() throws Exception {
		// 게시물 번호의 존재 여부를 확인하고 테스트할 것
		log.info("REMOVE RESULT: "+service.remove(2L));
	}
	
	@Test
	public void testUpdate() throws Exception{
		BoardVO board = service.get(1L);
		
		if(board==null) {
			return;
		}
		
		board.setTitle("제목 수정합니다.");
		log.info("MODIFY RESULT: "+service.modify(board));
	}
}
