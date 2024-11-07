package org.hyejung.persistence;

import org.hyejung.mapper.TimeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.java.Log;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log
public class TimeMapperTests {
	@Autowired
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		// TimeMapper가 정상적으로 사용이 가능한지를 = 스프링이 인터페이스를 이용해서 객체를 생성한다!
		log.info(timeMapper.getClass().getName()); // 실제 동작하는 클래스의 이름확인 -> 개발시 인터페이스만 만들어줬는데 내부적으로 적당한 클래스가 만들어짐 !!
		log.info(timeMapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		// sql 이 길어지는 경우는 어노테이션보다 XML을 이용하는 방식을 선호!
		log.info("getTime2");
		log.info(timeMapper.getTime2());
	}
}
