package org.hyejung.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.hyejung.domain.Criteria;
import org.hyejung.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet 의 ServletContext 를 이용하기 위해서, 스프링에서는 WebApplicationContext 라는 존재를 이용하기 위함.
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class ReplyMapperTests {
	
	@Autowired
	private ReplyMapper mapper;
	
	// ReplyMapper 가 사용가능한지에 대한 테스트 작업
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	
	// 등록
	// 테스트 전에 해당 번호의 게시물이 존재하는지 반드시 확인할 것
	private Long[] bnoArr = {106L,105L,104L,103L,102L};
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			// 게시물의 번호
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);		
		});
	}
	
	// 조회
	@Test
	public void testRead() {
		Long targetRno =5L;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}
	
	//삭제
	@Test
	public void testDelete() {
		Long targetRno = 2L;
		mapper.delete(targetRno);
	}  
	
	// 수정
	@Test
	public void testUpdate() {
		Long targetRno = 21L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT: "+count);
	}
	
	// @Param어노테이션과 댓글 목록
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
}
