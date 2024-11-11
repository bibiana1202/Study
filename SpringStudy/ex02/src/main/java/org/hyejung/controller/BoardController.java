package org.hyejung.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.hyejung.domain.BoardVO;
import org.hyejung.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 스프링의 빈으로 인식할수 있게
@Log4j  
@RequestMapping("/board/*") // /board로 시작하는 모든 처리
@AllArgsConstructor // BoardController는 BoardService에 대해서 의존적이므로 @AllArgsConstructor를 이용해서 생성자를 만들고 자동으로 주입하도록 한다!
public class BoardController {
	private BoardService service;
	
	//1. 목록에 대한 처리와 테스트
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list",service.getList());
	}
	
	//2. 등록 처리와 테스트
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register:"+board);
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		return "redirect:/board/list";
	}
	
	// 3. 조회 처리와 테스트
	@GetMapping("/get")
	public void get (@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		model.addAttribute("board",service.get(bno));
	}
	
	// 4. 수정 처리와 테스트
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","sucess");
		}
		
		return "redirect:/board/list";
	}
	
	// 5. 삭제 처리와 테스트
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove...."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","suceess");
		}
		return "redirect:/board/list";
	}
}
