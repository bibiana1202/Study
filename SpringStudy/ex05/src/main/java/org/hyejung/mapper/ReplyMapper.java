package org.hyejung.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hyejung.domain.Criteria;
import org.hyejung.domain.ReplyVO;

public interface ReplyMapper {

	// 등록
	public int insert(ReplyVO vo);

	// 조회
	public ReplyVO read(Long rno); // 특정 댓글 읽기
	
	// 삭제
	public int delete (Long rno); 
	
	// 수정
	public int update(ReplyVO reply);
	
	// @Param어노테이션과 댓글 목록
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
		
}
