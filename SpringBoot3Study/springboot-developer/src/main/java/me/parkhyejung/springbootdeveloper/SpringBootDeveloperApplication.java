package me.parkhyejung.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스트링 부트 사용에 필요한 기본 설정
public class SpringBootDeveloperApplication {
    // 자바의 main 메서드와 같은 역할. 즉, 여기서 스트링부트가 시작
    public static void main(String[] args){
        SpringApplication.run(SpringBootDeveloperApplication.class,args); // 애플리케이션 실행
        // 첫번째 인수는 스트링부트3 애플리케이션의 매인 클래스로 사용할 클래스
        // 두번째 인수는 커맨드 라인의 인수들을 전달
    }


}
