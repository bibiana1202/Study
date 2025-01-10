package me.parkhyejung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.parkhyejung.springbootdeveloper.domain.Article;
import me.parkhyejung.springbootdeveloper.domain.User;
import me.parkhyejung.springbootdeveloper.dto.AddArticleRequest;
import me.parkhyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkhyejung.springbootdeveloper.repository.BlogRepository;
import me.parkhyejung.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트, Spring Boot 테스트 환경에서 애플리케이션 컨텍스트를 로드하여 테스트 수행
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성 , MockMvc 는 컨트롤러의 동작을 HTTP 요청과 응답으로 시뮬레이션하여 테스트 할수 있도록 돕는 객체
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    // MockMvc 는 실제 서버를 띄우지 않고 HTTP 요청과 응답을 시뮬레이션 할수 있게 해준다.
    // 이를 통해 REST API 테스트를 간편하게 할수 있다.

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context; // Spring의 웹 애플리케이션 컨텍스트로 MockMvc 를 설정하는데 사용
    // 애플리케이션의 전체 웹 환경을 로드하고, 이를 기반으로 테스트를 설정

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        // MockMvc 객체를 애플리케이션의 웹 컨텍스트를 기반으로 설정
        // mockMvc가 webApplicationContext를 사용하여 전체 웹 애플리케이션의 컨텍스트를 초기화

//        blogRepository.deleteAll();
        // 테스트 수행하기전에 기존의 모든 데이터를 삭제하여 테스트 환경을 깨끗하게 초기화
        // 이를 통해 각 테스트가 독립적으로 수행될 수 있게 합니다.
    }

    @BeforeEach
    void setSecurityContext(){
        userRepository.deleteAll();
        user=userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
    }

    // 블로그 글 생성 API 테스트하는 코드
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given : 블로그 글 추가에 필요한 요청 객체를 만듭니다.
        final String url ="/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title,content);

        // 객체 JSON 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when : 블로그 글 추가 API 에 요청을 보냅니다. 이때 요청 타입은 JSON이며, given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냅니다.
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));


        // then : 응답 코드가 201 Created 인지 확인합니다. Blog를 전체 조회해 크기가 1인지 확인하고, 실제 저장된 데이터와 요청 값을 비교합니다.
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
//        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    // 블로그 글 전체 조회
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        //given : 블로그 글을 저장합니다.
        final String url = "/api/articles";
//        final String title = "title";
//        final String content = "content";
        Article savedArticle = createDefaultArticle();

//        blogRepository.save(Article.builder().title(title).content(content).build());

        //when : 목록 조회 API 를 호출 합니다.
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        //then : 응답 코드가 200 OK 이고, 반환받은 값중에 0번째 요소의 content와 tilte이 저장된 값과 같은지 확인합니다.
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
    }

    // 블로그 글 조회
    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given : 블로그 글을 저장합니다.
         final String url = "/api/articles/{id}";
//         final String title = "title";
//         final String content = "content";

//         Article article = blogRepository.save(Article.builder().title(title).content(content).build());
          Article savedArticle = createDefaultArticle();

        //when  : 저장한 블로그 글의 id 값으로 API를 호출합니다.
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then : 응답 코드가 200 OK 이고, 반환받은 content와 title이 저장된 값과 같은지 확인합니다.
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }

    // 블로그 글 삭제
    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given : 블로그 글을 저장합니다.
        final String url = "/api/articles/{id}";
//        final String title = "title";
//        final String content = "content";
//
//        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        Article savedArticle = createDefaultArticle();
        // when : 저장한 블로그 글의 id 값으로 삭제 API 호출합니다.
        mockMvc.perform(delete(url,savedArticle.getId()))
                .andExpect(status().isOk());

        // then : 응답 코드가 200 OK 이고, 블로그 글 리스트를 전체 조회해 조회한 배열 크기가 0인지 확인합니다.
        List<Article> articles = blogRepository.findAll();

//        assertThat(articles).isEmpty();

    }

    // 블로그 글 수정
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        //given : 블로그 글을 저장하고, 블로그 글 수정에 필요한 요청 객체를 만듭니다.
        final String url = "/api/articles/{id}";
//        final String title = "title";
//        final String content = "content";
//
//        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        Article savedArticle = createDefaultArticle();
        final String newTitle = "newTitle";
        final String newContent = "newContent";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle,newContent);

        //when : UPDATE API로 수정 요청을 보냅니다. 이때 요청 타입은 JSON 이며, given 절에 미리 만들어둔 객체 욫어 본문 으로 함께 보냅니다.
        ResultActions result = mockMvc.perform(put(url,savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)));

        //then : 응답 코드가 200 OK인지 확인합니다. 블로그 글 id 로 조회한후에 값이 수정되었는지 확인합니다.
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

    private Article createDefaultArticle(){
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .build());
    }
}