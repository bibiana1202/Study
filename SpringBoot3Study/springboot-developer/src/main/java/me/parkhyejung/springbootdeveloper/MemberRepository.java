package me.parkhyejung.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 엔티티에 있는 데이터들을 조회하거나 저장,변경,삭제를 할때 사용하는 인터페이스
// 스프링데이터 JPA 에서 제공하는 인터페이스인 JpaRepository 클래스를 상속받아 간단하게 구현할수 있다.
// JpaRepository 클래스를 상속받을때 ,엔티티 Member 와 엔티티의 기본키 타입 Long을 인수로 넣어준다.
// 해당 리포지터리를 사용할때 JpaRepository에서 제공하는 여러 메서드를 사용할수 있게 된다.
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}