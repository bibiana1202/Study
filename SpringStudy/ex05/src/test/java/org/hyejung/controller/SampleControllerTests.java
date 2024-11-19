package org.hyejung.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hyejung.domain.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet 의 ServletContext 를 이용하기 위해서, 스프링에서는 WebApplicationContext 라는 존재를 이용하기 위함.
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class SampleControllerTests {
	
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	// convert() 메서드 테스트
	@Test
	public void testConvert() throws Exception{
		Ticket ticket = new Ticket();
		ticket.setTno(123);
		ticket.setOwner("Admin");
		ticket.setGrade("AAA");
		
		String jsonStr = new Gson().toJson(ticket); //Gson 라이브러리는 Java의 객체를 JSON 문자열로 변환하기 위해 사용
		
		log.info("convert전.......jsonStr"+jsonStr);
		
		mockMvc.perform(post("/sample/ticket")
				.contentType(MediaType.APPLICATION_JSON) // 전달하는 데이터가 json
				.content(jsonStr))
		.andExpect(status().is(200));
		
		// JSON 문자열이 Ticket 타입의 객체로 변환된다!!
	}
}
