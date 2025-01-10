package me.parkhyejung.springbootdeveloper.dto;

import lombok.Getter;
import me.parkhyejung.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {
    // 응답을 위한 DTO(글 목록 조회)
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
