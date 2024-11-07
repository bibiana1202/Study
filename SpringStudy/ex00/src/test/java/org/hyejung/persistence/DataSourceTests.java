package org.hyejung.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:**/*-context.xml")
@Log4j
public class DataSourceTests {
	//스프링에 빈으로 등록된 DataSource를 이용해서 Conncetion 을 제대로 처리할 수 있는지 확인해보는 용도
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try(Connection con = dataSource.getConnection()){
			log.info(""+con);
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	//SqlSessionFactoyBean을 이용해서 SqlSession을 사용해보는 테스트
	@Setter(onMethod_ = {@Autowired})
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void testMyBatis() {
		// 설정된 SqlSessionFactory 인터페이스 타입의 SqlSessionFactoryBean을 이용해서 생성하고, 이를 이용해서 Connection까지 테스트
		try(SqlSession session = sqlSessionFactory.openSession();
			Connection con = session.getConnection();){
			log.info(session);
			log.info(con);
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
