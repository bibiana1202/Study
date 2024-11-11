package org.hyejung.service;

import java.util.List;

import org.hyejung.domain.BoardVO;
import org.hyejung.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 비즈니스 영역을 담당하는 객체임을 표시, 작성된 어노테이션은 패키지를 읽어 들이는 동안 처리
@AllArgsConstructor // 모든 파라미터를 이용하는 생성자
public class BoardServiceImpl implements BoardService {
	
	// spring 4.3 이상에서 자동 처리
	// BoardServiecImpl 가 정상적으로 작동하기 위해서는 BoardMapper 객체가 필요
	private BoardMapper mapper;
	
	// 1. 등록 작업의 구현과 테스트
	@Override
	public void register(BoardVO board) {
		log.info("register...." + board); 
		mapper.insertSelectKey(board);
	}

	// 2. 목록(리스트) 작업의 구현과 테스트
	@Override
	public List<BoardVO> getList() {
		log.info("getList......");
		return mapper.getList();
	}

	// 3. 조회 작업의 구현과 테스트
	@Override
	public BoardVO get(Long bno) {
		log.info("get....."+bno);
		return mapper.read(bno);
	}

	// 4. 삭제/수정 구현과 테스트
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify....."+board);
		return mapper.update(board)== 1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("remove....."+bno);
		return mapper.delete(bno)==1;
	}


}
