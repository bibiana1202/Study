package org.hyejung.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class SampleTests {

    @Autowired
    private Restaurant restaurant;

    @Test
    public void testExist() {
        // restaurant가 null이 아닌지 확인
        assertNotNull(restaurant);

        // restaurant 객체 정보 로그 출력
//        Log.info("Restaurant: {}", restaurant);
//        Log.info("Chef: {}", restaurant.getChef());
    }
}