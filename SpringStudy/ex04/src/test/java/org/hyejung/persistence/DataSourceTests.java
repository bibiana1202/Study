package org.hyejung.persistence;
import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

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
public class DataSourceTests {
	   @Autowired
	   private DataSource dataSource;

	   @Test
	   public void testConnection() {
	      try (Connection con = dataSource.getConnection()){
	         log.info(""+con);
	      } catch(Exception e) {
	         fail(e.getMessage());
	      }
	   }
}
