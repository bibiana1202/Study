package me.parkhyejung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name ="id",updatable = false)
    private Long id;

    @Column(name="title", nullable =false) // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name="content",nullable = false)
    private String content;

    @Builder // 빌더 패턴으로 객체 생성
    // 빌더 패턴을 사용하면 어떤 필드에 어떤 값이 들어가는지 명시적으로 파악 가능
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    //엔터티에 요청받은 내용으로 값을 수정하는 메서드
    public void update(String title,String content){
        this.title  = title;
        this.content = content;
    }
}
