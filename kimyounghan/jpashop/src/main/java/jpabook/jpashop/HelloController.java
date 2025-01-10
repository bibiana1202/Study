package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    // model 에다 데이터를 실어서 뷰로 넘길수 있다.
    public String hello(Model model) {
        model.addAttribute("data", "data hello 넘긴다아 model로!!!");
        return "hello"; // 화면 이름
    }
}
