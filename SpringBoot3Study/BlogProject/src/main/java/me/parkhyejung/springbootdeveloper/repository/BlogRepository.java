package me.parkhyejung.springbootdeveloper.repository;

import me.parkhyejung.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article,Long> {
}
