package org.hyejung.service;

import java.util.List;

import org.hyejung.domain.DiaryVO;
import org.hyejung.mapper.DiaryMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 비즈니스 영역을 담당하는 객체임을 표시, 작성된 어노테이션은 패키지를 읽어 들이는 동안 처리
@AllArgsConstructor // 모든 파라미터를 이용하는 생성자
public class DiaryServiceImpl implements DiaryService {
	
	// spring 4.3 이상에서 자동 처리
	// BoardServiecImpl 가 정상적으로 작동하기 위해서는 BoardMapper 객체가 필요
	private DiaryMapper mapper;
	
	// 1. 등록 작업의 구현과 테스트
	@Override
	public void register(DiaryVO diary) throws Exception {

		log.info("register....."+diary);
		mapper.insertSelectKey(diary); 
	}
	// 2. 목록(리스트) 작업의 구현과 테스트
	@Override
	public List<DiaryVO> getList(DiaryVO diary) throws Exception {
		log.info("getList......");
		return mapper.getList(diary);
	}
	// 3. 조회 작업의 구현과 테스트
	@Override
	public DiaryVO get(DiaryVO diary) throws Exception {
		log.info("getList......");
		return mapper.read(diary); 
	}
	// 4. 삭제 구현과 테스트
	@Override
	public boolean remove(DiaryVO diary) throws Exception {
		log.info("remove...."+diary);
		
		if( mapper.delete(diary) == 0) 
			throw new RuntimeException(diary.getDno()+"번 게시물이 삭제되지 않음");
		return true;
	}
	
	// 4. 수정 구현과 테스트
	@Override
	public boolean modify(DiaryVO diary) throws Exception {
		log.info("modify...."+diary);
		
		if( mapper.update(diary) == 0) 
			throw new RuntimeException(diary.getDno()+"번 게시물이 수정되지 않음");
		return true;
	}




}
