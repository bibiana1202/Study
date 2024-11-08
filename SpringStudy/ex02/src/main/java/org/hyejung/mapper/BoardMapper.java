package org.hyejung.mapper;

import java.util.List;

import org.hyejung.domain.BoardVO;

public interface BoardMapper {

	//@Select("select * from tbl_board where bno >0")
	public List<BoardVO> getList();
}
