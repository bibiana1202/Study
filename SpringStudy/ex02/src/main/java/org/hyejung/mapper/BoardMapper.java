package org.hyejung.mapper;

import java.util.List;

import org.hyejung.domain.BoardVO;
import org.hyejung.domain.Criteria;

public interface BoardMapper {

	//@Select("select * from tbl_board where bno >0")
	public List<BoardVO> getList();
	
	// 페이징 처리 : Criteria 타입을 파라미터로 사용하는 메서드
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// 1. create(select) 처리
	// insert 만 처리되고 생성된 pk 값은 알 필요가 없는 경우
	public void insert(BoardVO board);
	
	// insert문이 실행되고 생성된 pk 값을 알아야 하는 경우
	public void insertSelectKey(BoardVO board);
	
	// 2. read(select) 처리
	public BoardVO read(Long bno);
	
	// 3. delete 처리
	public int delete(Long bno);
	
	// 4.update 처리
	public int update(BoardVO board);
}
