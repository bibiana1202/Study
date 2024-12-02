package org.hyejung.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hyejung.domain.DiaryVO;
import org.hyejung.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 스프링의 빈으로 인식할수 있게
@Log4j  
@RequestMapping("/api/diary/*") 
@AllArgsConstructor // BoardController는 BoardService에 대해서 의존적이므로 @AllArgsConstructor를 이용해서 생성자를 만들고 자동으로 주입하도록 한다!
public class DiaryController {
	private DiaryService service;
	/**
	 * 작성자    : 박혜정
	 * 작성일    : 2024-11-22
	 * 내용       : 1. 목록에 대한 처리와 테스트
	 * updated  : 2024-11-23 박혜정 뭐 수정함
	 */
	@GetMapping("/list")
	public String list(@RequestParam(value = "date", required = false) String date,Model model, HttpSession session) {
		session.setAttribute("userid" , 101L);
		try {
			log.info("list.......");
	        // 세션에서 userid 값 가져오기
	        Long userid = (Long) session.getAttribute("userid"); // 세션에 저장된 userid
	        if (userid == null) {
	            throw new IllegalArgumentException("userid is not available in the session");
	        }
	        if (date == null) {
	            // 오늘 날짜를 "yyyy-MM" 형식으로 설정
	            LocalDate today = LocalDate.now();
	            date = today.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	        }
	        log.info("Received date: " + date);

	        // 입력받은 date 파싱 (예: 2024-11)
	        String[] parts = date.split("-");
	        int year = Integer.parseInt(parts[0]);
	        int month = Integer.parseInt(parts[1]);

	        // Calendar 객체 생성
	        Calendar c = Calendar.getInstance();
	        c.set(Calendar.YEAR, year);
	        c.set(Calendar.MONTH, month - 1); // 0부터 시작
	        c.set(Calendar.DAY_OF_MONTH, 1);

	        // 달력 관련 데이터 계산
	        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	        int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

	        model.addAttribute("year", year);
	        model.addAttribute("month", month);
	        model.addAttribute("dayOfWeek", dayOfWeek);
	        model.addAttribute("endDay", endDay);
	        model.addAttribute("pick_date", date); // date도 추가
		    log.info("date어디야야아아아앙"+date);


	        // getlist.....
			DiaryVO diary = new DiaryVO();
			diary.setUserid(101L);
			// Date 객체로 변환
			Date date_regdate = c.getTime();
		    diary.setRegdate(date_regdate); 
		    List<DiaryVO> dia = service.getList(diary);
		    log.info(dia);
		    for (DiaryVO d : dia) {
		        if (d.getRegdate() != null) {
		        	log.info("regdate type: " + d.getRegdate().getClass().getName());
		        }
		        log.info("개짜증난다널임?");

		    }
		    model.addAttribute("diary",dia);

	        // AJAX 요청에 응답할 JSP 반환
	        return "api/diary/list"; // JSP의 특정 부분만 반환
			
			
			
		} catch (Exception e) {
			// 유저에게 보여줄 메시지 리턴
			// 이동 할 곳도 지정할수도
	        e.printStackTrace();
	        return "error"; // 오류 페이지 반환
			
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
