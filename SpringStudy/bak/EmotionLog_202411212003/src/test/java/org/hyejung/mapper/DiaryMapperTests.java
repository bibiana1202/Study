package org.hyejung.mapper;

import java.util.Date;

import org.hyejung.domain.DiaryVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class DiaryMapperTests {

	@Autowired
	private DiaryMapper mapper;
	
	@Test
	public void testGetList() {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(112L);
	    diary.setRegdate(new Date()); // 오늘 날짜
		
		
		mapper.getList(diary).forEach(d -> log.info(d));
//		assertNotEquals(null,mapper.getList());
	}
	
	// 1. create(insert) 처리
	@Test
	public void testInsert() {
		DiaryVO diary = new DiaryVO();
		diary.setDno(1L);
		diary.setUserid(118L);
		diary.setTitle("새로 작성하는 제목2");
		diary.setContent("새로 작성하는 내용2");
		diary.setWriter("박혜쩜");
		diary.setEmotionStatus("슬픔");
		
		
		mapper.insert(diary);
		
		log.info(diary);
	}

	@Test
	public void testInsertSelectKey() {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(116L);
		diary.setTitle("새로 작성하는 제목2");
		diary.setContent("새로 작성하는 내용2");
		diary.setWriter("박혜쩜");
		diary.setEmotionStatus("슬픔");
		
		mapper.insertSelectKey(diary);
		
		log.info(diary);
	}
	
	// 2. read(select) 처리
	@Test
	public void testRead() {
		// 존재하는 게시물 번호로 테스트
		
		DiaryVO diary = new DiaryVO();
		diary.setUserid(101L);
		diary.setDno(2L);

		mapper.read(diary);
		
		//log.info(diary);
	}
	
	// 3. delete 처리
	@Test
	public void testDelete() {
		DiaryVO diary = new DiaryVO();
		diary.setUserid(113L);
		diary.setDno(1L);
		log.info("DELETE COUNT:" + mapper.delete(diary));
	}
	
	// 4. update 처리
		@Test
		public void testUpdate() {
			
			DiaryVO diary = new DiaryVO();
			// 실행 전 존재하는 번호 인지 확인 할 것
			diary.setUserid(113L);
			diary.setDno(1L);
			diary.setTitle("수정된 제목");
			diary.setContent("수정된 내용");
			diary.setWriter("user00");
			
			int count = mapper.update(diary);
			log.info("UPDATE COUNT:" + count);
		}
	
}
