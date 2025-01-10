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
@EntityListeners(AuditingEntityListener.class) //엔터티 생성 및 수정시간을 자동으로 감시하고 기록
@Entity // JPA에 이 클래스를 데이터베이스 테이블에 매핑해야 한다고 알림.
@Getter // 클래스 필드에 대해 별도 코드 없이 모든 필드에 대한 접근자 메서드를 만든다.
@NoArgsConstructor(access= AccessLevel.PROTECTED) // 접근제어자가 protected 인 기본 생성자 생성
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name ="id",updatable = false)
    private Long id;

    @Column(name="title", nullable =false) // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name="content",nullable = false)
    private String content;

    @CreatedDate // 엔티티가 생성될 때 생성 시간 지정
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="author", nullable = false)
    private String author;

    @Builder // 빌더 패턴으로 객체 생성
    // 빌더 패턴을 사용하면 어떤 필드에 어떤 값이 들어가는지 명시적으로 파악 가능
    public Article(String author,String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }

    //엔터티에 요청받은 내용으로 값을 수정하는 메서드
    public void update(String title,String content){
        this.title  = title;
        this.content = content;
    }


}
