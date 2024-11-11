package org.hyejung.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet 의 ServletContext 를 이용하기 위해서, 스프링에서는 WebApplicationContext 라는 존재를 이용하기 위함.
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class BoardControllerTests {
	
	@Autowired
	private WebApplicationContext ctx;
	
	// 가짜 mvc
	private MockMvc mockMvc;
	
	@Before // 모든 테스트 전에 매번 실행되는 메서드
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	// 1. 목록에 대한 처리와 테스트
	@Test
	public void testList() throws Exception{
		//MockMvcRequestBuilders라는 존재를 이용해서 GET 방식의 호출을 한다.
		// BoardController의 getList를 반환한 결과를 이용해서 Model에 어떤 데이터들이 담겨있는지 확인
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				 .andReturn()
				 .getModelAndView()
				 .getModelMap());
	}
	
	// 2.등록 처리와 테스트
	@Test
	public void testRegister() throws Exception{
		String resultPage= mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title","테스트 새글 제목")
				.param("content","테스트 새글 내용")
				.param("writer","user00")
				).andReturn().getModelAndView().getViewName();
		log.info("resultPage: "+resultPage);
	}
	
	// 3. 조회 처리와 테스트
	@Test
	public void testGet() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
								.param("bno", "5"))
						.andReturn()
						.getModelAndView()
						.getModelMap());
	}
	
	// 4. 수정 처리와 테스트
	@Test
	public void testModify() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
											.param("bno", "1")
											.param("title", "수정된 테스트 새글 제목")
											.param("content", "수정된 테스트 새글 내용")
											.param("writer","user00"))
									.andReturn()
									.getModelAndView()
									.getViewName();
		log.info(resultPage);
	}
	
	//5. 삭제 처리와 테스트
	@Test
	public void testRemove() throws Exception{
		//삭제 전 데이터베이스에 게시물 번호 확인할것
		String resultPage= mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
											.param("bno", "21"))
									.andReturn()
									.getModelAndView()
									.getViewName();
		log.info(resultPage);
				
	}
	
	
	
	
}
