package org.hyejung.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링을 실행하는 역할을 할것
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // 필요한 객체들을 스프링 내에 객체로 등록(빈으로 등록)
@Log4j
public class SampleTests {


	@Setter(onMethod_ = {@Autowired})
	private Restaurant restaurant;
	
    @Test
    public void testExist() {
        // restaurant가 null이 아닌지 확인
        assertNotNull(restaurant);

        // restaurant 객체 정보 로그 출력
        log.info(restaurant);
        log.info(restaurant.getChef());
    }
}
