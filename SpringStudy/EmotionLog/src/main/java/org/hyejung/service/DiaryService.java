package org.hyejung.service;

import java.util.List;

import org.hyejung.domain.DiaryVO;

public interface DiaryService {

	public void register(DiaryVO diary) throws Exception;
	
	public DiaryVO get(DiaryVO diary) throws Exception;
	
	public boolean remove (DiaryVO diary) throws Exception;
	public boolean modify(DiaryVO diary) throws Exception;
	
	
	public List<DiaryVO> getList(DiaryVO diary) throws Exception;
//	public List<BoardVO> getList(Criteria cri) throws Exception;
//	
//	// 전체 페이지 개수
//	public int getTotal(Criteria cri);
}
