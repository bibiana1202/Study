package me.parkhyejung.springbootdeveloper.repository;

import me.parkhyejung.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article,Long> {
// JpaRepository 클래스를 상속받을 때 엔티티 Article과 엔티티의 PK 타입 Long 을 인수로 넣는다.
}
