package me.hyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.domain.Article;
import me.hyejung.springbootdeveloper.dto.AddArticleRequset;
import me.hyejung.springbootdeveloper.dto.ArticleResponse;
import me.hyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hyejung.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController// Http Response Body 에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {
    private final BlogService blogService;

    /*블로그 글 작성*/
    // HTTP 메소드가 POST 일때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody 로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequset request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    /*블로그 글 목록 조회*/
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }

    /*블로그 글 조회*/
    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    /*블로그 글 삭제*/
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    /*블로그 글 수정*/
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id,request);
        return ResponseEntity.ok()
                .body(updatedArticle);
    }

}
