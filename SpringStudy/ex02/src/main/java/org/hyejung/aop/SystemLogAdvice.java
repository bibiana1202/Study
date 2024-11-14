package org.hyejung.aop;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class SystemLogAdvice {
	// 부가코드(advice) : SystemLogAdivce
	// 결합식(pointcut) : BoardServiceImpl => exception 코드 간결화 됨!!
	// pointcut = 어떤 위치에 Advice를 적용할 것인지 결정!!!!
	@AfterThrowing(pointcut = "execution(* org.hyejung.service.BoardService*.*(..))",
			       throwing="exception")
	public void logException(Exception exception) {		
		log.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.error("@@@@@AfterThrowing adive@@@@");
		log.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.error("exception: "+exception.getMessage());
	}
}
