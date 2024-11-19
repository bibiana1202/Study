package org.hyejung.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hyejung.domain.SampleVO;
import org.hyejung.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController // Controller(스프링의 빈으로 인식할수 있게) + ResponseBody(데이터 자체를 전달)
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	// 1. 단순 문자열 반환
	// produces 속성은 해당 메서드가 생산하는 MIME 타입을 의미, 문자열로 직접 지정할수도 있고, 메서드 내의 MediaType이라는 클래스를 이용할수도 있다.
	@GetMapping(value ="/getText", produces = "text/plain ; charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	}
	
	// 2. 객체의 반환
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"스타","로드");
	}
	
	// GetMapping 이나 RequestMapping 의 produces 속성은 반드시 지정해야 하는것이 아니므로 생략하는 것도 가능
	@GetMapping(value ="/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(113,"로켓","라쿤");
	}
	
	// 3. 컬렉션 타입의 객체 반환
	// 여러 데이터를 한번에 전송 하기 위해서 배열 이나 리스트, 맵 타입의 객체들을 전송하는 경우
	@GetMapping(value ="/getList")
	public List<SampleVO> getList(){
		// 내부적으로 1부터 10 미만 까지의 루프를 처리하면서 SampleVO 객체를 만들어서 List<SampleVO>를 만들어 낸다.
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i,i + "First", i+"Last")).collect(Collectors.toList());
	}
	
	// 4. ResponseEntity 타입
	// REST 방식으로 호출하는 경우는 화면 자체가 아니라 데이터 자체를 전송하는 방식으로 처리 되기 때문에
	// 데이터를 요청하는 쪽에서 정상적인 데이터 인지 비정상적인 데이터인지를 구분 할수 있는 확실한 방법을 제공 해야 만한다.
	// ResoponseEntity 는 데이터와 함께 HTTP 헤더 의 상태 메시지등을 같이 전송하는 용도
	@GetMapping(value ="/check", params= {"height","weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0,""+height, ""+weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height<150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}
		else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		return result;
	}
	
	// 1. PathVariable
	// REST 방식에서는 URL 내에 최대한 많은 정보를 담으려고 노력
	// URL에서 {} 로 처리된 부분은 컨트롤러의 메서드에서 변수로 처리가 가능
	// PathVariable은 {}의 이름을 처리할때 사용
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {"category :"+ cat, "productid : "+pid};
	}
	
	// 2. RequestBody
	// 전달된 요청(request)의 내용(body)을 이용해서 해당 파라미터의 타입으로 변환을 요구
	// 내부적으로 HttpMessageConverter 타입의 객체들을 이용해서 다양한 포맷의 입력 데이터를 변환할수 있따.
	@PostMapping("/ticket") 
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert........ticket " + ticket);
		return ticket;
	}
	
}
