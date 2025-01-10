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

@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@RestController // HTTP Response Body 에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러, 클래스에 붙이면 HTTP 응답으로 객체 데이터를 JSON 형식으로 반환
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST 일때 전달 받은 URL 과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    //@RequestBody 로 요청 본문 값 매핑
    // HTTP를 요청할때 응답에 해당하는 값을 @RequestBody 이 붙은 대상 객체인 AddArticleRequest 에 매핑
    // ResponseEntity.status().body() 는 응답코드로 201 , 즉 Created 를 응답하고 테이블에 저장된 객체를 반환
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        // 1. request 객체 확인
        System.out.println("Request Title: " + request.getTitle());
        System.out.println("Request Content: " + request.getContent());

        // 2. principal 값 확인
        System.out.println("Principal Name: " + (principal != null ? principal.getName() : "null"));


        Article savedArticle = blogService.save(request,principal.getName());

        // 4. 반환되는 Article 객체 확인
        System.out.println("Saved Article: " + savedArticle);

        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응담 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // 전체 글을 조회한뒤 반환하는 메소드
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    //URL 경로에서 값 추출
    // @PathVariable : URL 에서 값을 가져오는 애너테이션
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    //URL 에서 {id} 에 해당하는 값이 id로 들어옴, {id}에 해당하는 값이 @PathVariable 어노테이션을 통해 들어온다.
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id){
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    // PUT 요청이 오면 글을 수정하기 위한 메서드
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id,request);// PUT 요청이 오면 Request Body 정보가 request로 넘어오고, update메소드에 id와 request를 넘겨준다.

        return ResponseEntity.ok()
                .body(updatedArticle); // 응답값은 body에 담아 전송

    }

}
