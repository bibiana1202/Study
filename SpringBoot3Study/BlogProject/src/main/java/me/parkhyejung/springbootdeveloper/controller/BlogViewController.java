package me.parkhyejung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkhyejung.springbootdeveloper.domain.Article;
import me.parkhyejung.springbootdeveloper.dto.ArticleListViewResponse;
import me.parkhyejung.springbootdeveloper.dto.ArticleViewResponse;
import me.parkhyejung.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    // /articles GET 요청을 처리, 블로그 글 전체 리스트를 담은 뷰를 반환

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                                                            .stream()
                                                            .map(ArticleListViewResponse::new)
                                                            .toList();
        model.addAttribute("articles", articles); // 1) 블로그 글 리스트 저장 : 모델에 값을 저장 (articles라는 키에 글 리스트를 저장)
        return "articleList"; // 2) articleList.html 라는 뷰 조회 : resource/templates/articleList.html을 찾도록 뷰의 이름

    }

    @GetMapping("/articles/{id}")
    // URL 경로에서 값 추출
    // @PathVariable : URL 에서 값을 가져오는 애너테이션
    public String getArticle(@PathVariable Long id, Model model) {
        // 인자 id 에 url 로 넘어온 값을 받아 findById 메서드로 넘겨 글을 조회하고, 화면에서 사용할 모델에 저장한다음, 보여줄 화면의 템플릿 이름을 반환
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    // 1) id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id 는 없을 수도 있음)
    public String newArticle(@RequestParam(required=false) Long id , Model model){
        if(id == null){ // 2) id가 없으면 생성
            model.addAttribute("article",new ArticleViewResponse()); // id가 없으면 기본 생성자를 이용해 빈 ArticleViewResponse 객체를 만든다.
        }
        else{
            Article article = blogService.findById(id); //id 가있으면 기존 값을 가져오는 findById 메소드 호출
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }

}
