package org.hyejung.service;


import org.hyejung.mapper.Sample1Mapper;
import org.hyejung.mapper.Sample2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
//@Transactional // 이 클래스안 모두 다 트랜잭션 먹음
public class SampleTxServiceImpl implements SampleTxService {

	// mybatis spring이 자동으로 구현체 만들어줌.......
	@Autowired
	private Sample1Mapper mapper1;
	@Autowired
	private Sample2Mapper mapper2;
	
	@Transactional // 메소드 단위 별로 트랜잭션!!!!!!!
	@Override
	public void addData(String value) {
		log.info("mapper1.................");
		mapper1.insertCol1(value);

		log.info("mapper2.................");
		mapper2.insertCol2(value);
		
		log.info("end.....................");
		
	}
}
