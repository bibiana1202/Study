package me.parkhyejung.springbootdeveloper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //테스트용 어플리케이션 컨텍스트 생성
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성 : 애플리케이션을 서버에 배포하지 않고도 테스트용 MVC 환경을 만들어 요청 및 전송,응답 기능을 제공하는 유틸리티 클래스
class TestControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach // 테스트 실행후 실행하는 메서드
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
        // given
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L,"홍길동"));

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // perform() : 요청을 전송하는 역할을 하는 메소드
        // ResultActions 객체 : 반환값을 검증하고 확인하는 andExcpet() 메서드를 제공
        // accept() : 요청을 보낼때 무슨 타입으로 응답을 받을지 결정하는 메서드
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
        // andExpect 메서드 는 응답을 검증, TestController에서 만든 api는 응답으로 ok(200)을 반환하므로 이에 해당하는 메서드인 isOk를 사용해 응답코드가 ok(200)인지 확인
        //jsonPath("${필드명}") 은 JSON 응답값의 값을 가져오는 역할을 하는 메서드

    }

}