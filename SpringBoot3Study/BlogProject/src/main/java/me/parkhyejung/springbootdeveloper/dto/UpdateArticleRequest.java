package me.parkhyejung.springbootdeveloper.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    //블로그 글 수청 요청을 받을 dto
    private String title;
    private String content;
}
