package org.hyejung.controller;

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
		try {
			log.info("list.......");
			model.addAttribute("list",service.getList());
		} catch (Exception e) {
			// 유저에게 보여줄 메시지 리턴
			// 이동 할 곳도 지정할수도
			e.printStackTrace();
			
		}
	}
	
	//2. 등록 처리와 테스트
	// addFlashAttribute 의 경우 일회성으로만 데이터를 전달, 보관된 데이터는 단 한번만 사용할수 있게 보관
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		try {
			log.info("register: "+board);
			service.register(board);
			rttr.addFlashAttribute("result",board.getBno()); // 값 유지 하기 위해서 RedirectAttributes -> 세션 사용
			return "redirect:/board/list"; // 새로운 화면으로 ,url 재이동
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 게시물의 등록 작업은 POST 방식으로 처리하지만, 화면에서 입력을 받아야하므로
	// GET방식으로 입력 페이지를 볼 수 있도록~
	@GetMapping("/register")
	public void register() {
		
	}
	
	// 3. 조회 처리와 테스트
	@GetMapping("/get")
	public void get (@RequestParam("bno") Long bno, Model model) {
		try {
			log.info("/get");
			model.addAttribute("board",service.get(bno));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 4. 수정 처리와 테스트
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		try {
			log.info("modify:" + board);
			
			if(service.modify(board)) {
				rttr.addFlashAttribute("result","success");
			}
			return "redirect:/board/list";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 5. 삭제 처리와 테스트
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		try {
			log.info("remove...."+bno);
			if(service.remove(bno)) {
				rttr.addFlashAttribute("result","success");
			}
			return "redirect:/board/list";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
