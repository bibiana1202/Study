package me.hyejung.springbootdeveloper.repository;

import me.hyejung.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email); // email 로 사용자 정보를 가져옴

}
