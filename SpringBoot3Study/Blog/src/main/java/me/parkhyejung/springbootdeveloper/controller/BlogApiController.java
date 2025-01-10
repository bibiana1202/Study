package me.parkhyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.Article;
import me.parkhyejung.springbootdeveloper.dto.AddArticleRequest;
import me.parkhyejung.springbootdeveloper.dto.ArticleResponse;
import me.parkhyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkhyejung.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor //final로 선언된 필드 또는 NonNull로 표시된 필드들을 생성자의 매개변수로 포함하는 생성자를 자동으로 생성
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // 블로그 글 추가
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        // RequestBody로 요청 본문 값 매핑
        // 어노테이션을 사용하여 요청 본문(body)에 있는 JSON 데이터를 Java 객체로 매핑한다는 의미
        // 이를 통해 클라이언트가 서버로 HTTP POST 요청을 할때 본문에 담아서 보낸 JSON 데이터를 Spring이 자동으로 자바 객체로 변환해 주는 역할
        Article savedArticle = blogService.save(request,principal.getName());

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle); //Article 객체인 savedArticle을 **응답 본문(body)**에 담습니다
        // savedArticel 객체는 Java 객체이지만, Spring이 자동으로 JSON 형식으로 변환하여 응답 본문에 포함시킨다.

    }

    // 블로그 글 목록 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll() // 글 전체를 조회
                .stream()
                .map(ArticleResponse::new) // 응답 객체인 ArticleResponse로
                .toList();

        return ResponseEntity.ok()
                .body(articles); // 파싱해 body 에 담아 클라이언트에게 전송
    }

    // 블로그 글 조회
    @GetMapping("/api/articles/{id}") // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) { // URL에서 {id}에 해당하는 값이 id로 들어옴
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 블로그 글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 블로그 글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
