package me.parkhyejung.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자(엔티티는 반드시 기본 생성자가 있어야하고 접근제어자는 public 또는 protected 여야 한다.)
@AllArgsConstructor
@Getter
@Entity // 엔티티로 지정(Member 객체를 JPA가 관리하는 엔티티로 지정)
public class Member {
    @Id // Long 타입의 id 필드를 테이블의 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키의 생성 방식을 결정, 자동으로 기본키가 증가되도록 지정
    // 기본키를 자동으로 1씩 증가
    // IDENTITY : 기본키 생성을 데이터베이스에 위임
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false) // 데이터 베이스의 컬럼과 필드를 매핑, name이라는 not null 컬럼과 매핑
    private String name;

    public void changeName(String name){
        this.name = name;
    }
}
