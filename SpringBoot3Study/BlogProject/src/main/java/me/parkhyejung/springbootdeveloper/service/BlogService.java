package me.parkhyejung.springbootdeveloper.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.Article;
import me.parkhyejung.springbootdeveloper.dto.AddArticleRequest;
import me.parkhyejung.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkhyejung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
// 빈을 생성자로 생성하는 롬복에서 지원하는 애너테이션 final 키워드나 @NotNull 이 붙은 필드로 생성자
@Service // 해당 클래스를 빈 으로 서블릿 컨테이너에 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    // JpaRepository 에서 지원하는 저장메서드 save() 로 AddArticleRequest 클래스에 저장된 값들을
    // article 데이터베이스에 저장
    public Article save(AddArticleRequest request,String userName){
        return blogRepository.save(request.toEntity(userName));
    }

    // 데이터베이스에 저장되어 있는 글을 모두 가져오는 메소드
    public List<Article> findAll(){
        return blogRepository.findAll(); // JPA 지원 메서드인 findAll()을 호출해 article 테이블에 저장되어 있는 모든 데이터를 조회
    }

    // 데이터베이스에 저장되어 있는 글의 ID를 이용해 글을 조회
    public Article findById(long id){
        // JPA에서 제공하는 findById() 메서드를 사용해 ID를 받아 엔티티를 조회하고 없으면 IllegalArugumentException 예외를 발생
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
    }

    // 블로그의 글의 ID를 받은뒤 JPA 에서 제공하는 deleteById() 메서드를 이용해 데이터베이스에서 삭제
    public void delete(long id){
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    // 리포지터리를 사용해 글을 수정하는 update 메서드
    @Transactional // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:" + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(),request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}


