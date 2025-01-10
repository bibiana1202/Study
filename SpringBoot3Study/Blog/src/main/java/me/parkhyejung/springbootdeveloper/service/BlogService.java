package me.parkhyejung.springbootdeveloper.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.Article;
import me.parkhyejung.springbootdeveloper.dto.AddArticleRequest;
import me.parkhyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkhyejung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 NotNuLL이 붙은 필드의 생성자추가
@Service // 빈으로 등록
public class BlogService {


    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName)); //AddArticleRequest 클래스에 저장된 값들을 article db에 저장
    }

    // 블로그 글 목록 조회를 위한 API
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 블로그 글 조회 API
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
        // JPA에서 제공하는 findById() 메서드를 사용해 ID를 받아 엔티티를 조회하고 없으면 예외 발생
    }

    // 블로그 글 삭제
    public void delete(Long id){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    // 블로그 글 수정
    @Transactional // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    public Article update(Long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
