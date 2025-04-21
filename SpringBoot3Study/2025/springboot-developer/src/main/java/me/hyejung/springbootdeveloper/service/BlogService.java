package me.hyejung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hyejung.springbootdeveloper.domain.Article;
import me.hyejung.springbootdeveloper.dto.AddArticleRequset;
import me.hyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.hyejung.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor// final 이나 @NOTNULL이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequset request){
        return blogRepository.save(request.toEntity());
    }

    // 데이터베이스에 저장되어있는 글을 모두 가져오는 메서드
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 블로그 글 하나를 조회하는 메서드
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found"+ id));
    }

    // 블로그 글 하나를 삭제하는 메서드
    public void delete(long id){
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정하는 메서드
    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found"+id));
        article.update(request.getTitle(),request.getContent());
        return article;
    }
}
