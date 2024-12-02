package org.hyejung.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.hyejung.domain.DiaryVO;
import org.hyejung.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 스프링의 빈으로 인식할수 있게
@Log4j  
@RequestMapping("/api/*") 
@AllArgsConstructor // BoardController는 BoardService에 대해서 의존적이므로 @AllArgsConstructor를 이용해서 생성자를 만들고 자동으로 주입하도록 한다!
public class DiaryController {
	private DiaryService service;
	
	//1. 목록에 대한 처리와 테스트
	@GetMapping("/list")
	public void list(Model model, HttpSession session) {
		session.setAttribute("userid" , 101L);
		try {
			log.info("list.......");
	        // 세션에서 userid 값 가져오기
	        Long userid = (Long) session.getAttribute("userid"); // 세션에 저장된 userid
	        if (userid == null) {
	            throw new IllegalArgumentException("userid is not available in the session");
	        }

			//
			DiaryVO diary = new DiaryVO();
			diary.setUserid(userid);
		    diary.setRegdate(new Date()); // 오늘 날짜
		    //
		    
		     // Model에 DiaryVO 추가
	        model.addAttribute("diary", diary);
	        log.info("DiaryVO: " + diary);

			model.addAttribute("list",service.getList(diary));
			
			
			
		} catch (Exception e) {
			// 유저에게 보여줄 메시지 리턴
			// 이동 할 곳도 지정할수도
			e.printStackTrace();
			
		}
	}
	
//	// 페이징 처리!!
//	@GetMapping("/list")
//	public void list(Criteria cri ,Model model) {
//		try {
//			log.info("list......."+cri);
//			model.addAttribute("list",service.getList(cri));
//			// BoardController 에서는 PageDTO 를 사용할 수 있또록 Model에 담아서 화면에 전달해줄 필요가 있씁니당~
//			// 페이징 처리를 위한 클래스 설계
////			model.addAttribute("pageMaker",new PageDTO(cri,123)); // 전체페이지 123
//			
//			// 전체 페이지 개수
//			int total = service.getTotal(cri);
//			log.info("total: "+total);
//			model.addAttribute("pageMaker",new PageDTO(cri,total));
//		} catch (Exception e) {
//			// 유저에게 보여줄 메시지 리턴
//			// 이동 할 곳도 지정할수도
//			e.printStackTrace();
//			
//		}
//	}
//	
//	//2. 등록 처리와 테스트
//	// addFlashAttribute 의 경우 일회성으로만 데이터를 전달, 보관된 데이터는 단 한번만 사용할수 있게 보관
//	@PostMapping("/register")
//	public String register(BoardVO board, RedirectAttributes rttr) {
//		try {
//			log.info("register: "+board);
//			service.register(board);
//			rttr.addFlashAttribute("result",board.getBno()); // 값 유지 하기 위해서 RedirectAttributes -> 세션 사용
//			return "redirect:/board/list"; // 새로운 화면으로 ,url 재이동
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	// 게시물의 등록 작업은 POST 방식으로 처리하지만, 화면에서 입력을 받아야하므로
//	// GET방식으로 입력 페이지를 볼 수 있도록~
//	@GetMapping("/register")
//	public void register() {
//		
//	}
//	
//	// 3. 조회 처리와 테스트
//	@GetMapping({"/get","/modify"})
//	public void get (@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, Model model) {
//		try {
//			log.info("/get");
//			model.addAttribute("board",service.get(bno));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	// 4. 수정 처리와 테스트
//	@PostMapping("/modify")
//	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
//		try {
//			log.info("modify:" + board);
//			
//			if(service.modify(board)) {
//				rttr.addFlashAttribute("result","success");
//			}
//			// 수정 처리후 이동
////			rttr.addAttribute("pageNum",cri.getPageNum());
////			rttr.addAttribute("amount",cri.getAmount());
////			rttr.addAttribute("type",cri.getType());
////			rttr.addAttribute("keyword",cri.getKeyword());
////			
//			// UriComponentsBuilder 사용 
//			return "redirect:/board/list" + cri.getListLink();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	// 5. 삭제 처리와 테스트
//	@PostMapping("/remove")
//	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
//		try {
//			log.info("remove...."+bno);
//			if(service.remove(bno)) {
//				rttr.addFlashAttribute("result","success");
//			}
//			
//			// 삭제 처리후 이동
////			rttr.addAttribute("pageNum",cri.getPageNum());
////			rttr.addAttribute("amount",cri.getAmount());
////			rttr.addAttribute("type",cri.getType());
////			rttr.addAttribute("keyword",cri.getKeyword());
////			
//			// UriComponentsBuilder 사용 
//			return "redirect:/board/list" + cri.getListLink();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
