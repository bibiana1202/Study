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
	public void register(BoardVO board) throws Exception {
		try {
			log.info("register...." + board); 
			mapper.insertSelectKey(board);
		} catch (Exception e) {
			log.error(null);
		}
	}

	// 2. 목록(리스트) 작업의 구현과 테스트
	@Override
	public List<BoardVO> getList() throws Exception {
		try {
			log.info("getList......");
			return mapper.getList();
		} catch (Exception e) {
			log.error(e.getMessage()); // 예외 메시지를 로그에 기록
			throw e; // 예외를 다시 던져 상위 호출자에게 전파
		}
	}

	// 3. 조회 작업의 구현과 테스트
	@Override
	public BoardVO get(Long bno) throws Exception {
		try {
			log.info("get......"+bno);
			BoardVO board = mapper.read(bno);
			if(board == null) throw new RuntimeException(bno + "게시물이 없음");
			return board;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}		
	}

	// 4. 삭제/수정 구현과 테스트
	@Override
	public boolean modify(BoardVO board) throws Exception {
		try {
			log.info("modify...."+board);
			
			if( mapper.update(board) == 0) 
				throw new RuntimeException(board.getBno()+"번 게시물이 수정되지 않음");
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean remove(Long bno) throws Exception {
		try {
			log.info("remove...."+bno);
			
			if( mapper.delete(bno) == 0) 
				throw new RuntimeException(bno+"번 게시물이 삭제되지 않음");
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}


}
