package org.hyejung.service;

import java.util.List;

import org.hyejung.domain.BoardVO;
import org.hyejung.domain.Criteria;

public interface BoardService {

	public void register(BoardVO board) throws Exception;
	
	public BoardVO get(Long bno) throws Exception;
	
	public boolean modify(BoardVO board) throws Exception;
	
	public boolean remove (Long bno) throws Exception;
	
//	public List<BoardVO> getList() throws Exception;
	public List<BoardVO> getList(Criteria cri) throws Exception;
	
	// 전체 페이지 개수
	public int getTotal(Criteria cri);
}
