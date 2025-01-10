package me.parkhyejung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) //Spring Data JPA에서 엔티티(Entity)의 생성 및 수정 시각을 자동으로 기록하기 위해 사용하는 어노테이션
@Entity // 엔티티로 지정
@Getter // 클래스 필드에 대해 별도 코드 없이 모든 필드에 대한 접근자 메서드를 만들수 있다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근 제어자가 protected인 기본 생성자를 별도의 코드 없이 생성
public class Article {

    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id",updatable = false)
    private Long id ;

    @Column(name="title",nullable = false) // 'title'이라는 not null 컬럼과 매핑
    private String title ;

    @Column(name="content" ,nullable = false)
    private String content ;

    @CreatedDate // 엔티티가 생성될때 생성 시간 저장
    @Column(name="create_at")
    private LocalDateTime createAt;

    @LastModifiedDate //엔티티가 수정될때 수정 시간 저장
    @Column(name="update_at")
    private LocalDateTime updateAt;

    @Column(name="author" ,nullable = false)
    private String author;


    // 빌더 패턴으로 객체 생성
    @Builder
    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
    // 엔티티에 요청받은 내용으로 값을 수정하는 메서드

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
