package org.hyejung.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class SampleTxServiceTests {
	// 서비스 주입
	@Autowired
	private SampleTxService service;
	
	@Test
	public void testLong() {
		String str = "012345678901234567890123456789012345678901234567890";
		
		log.info(str.getBytes().length);
		
		service.addData(str);
				
	}
}
