package org.hyejung.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스의 객체가 Aspect(모음)을 구현한것임을 나타낸다~!!
@Log4j
@Component
public class LogAdvice {
	// Advice(부가코드) 는 Aspect(모음)을 구현한 코드
	// aop 할시 protected 이상이여야 함.
	// spring aop 에 execution 표현식 : 메서드 기준으로 PointCut을 설정
	@Before( "execution(* org.hyejung.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.info("@@@@@@@@@before adviece@@@@@@@@@@@");
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	}
	
	// args 를 이용한 파라미터 추적
		@Before( "execution(* org.hyejung.service.SampleService*.doAdd(String ,String))&& args(str1,str2)")
		public void logBeforeWithParam(String str1, String str2) {
			log.info("str1:"+str1);
			log.info("str2:"+str2);
			
		}
		
		// 예외를 발생한후에 동작
		@AfterThrowing(pointcut = "execution(* org.hyejung.service.SampleService*.*(..))",throwing="exception")
		public void logException(Exception exception) {		
			log.info("Exception....!!!!");
			log.info("exception: "+exception);
		}
		
		
		// @Around 와 ProceedingJoinPoint
		@Around( "execution(* org.hyejung.service.SampleService*.*(..))")
		public Object logTime(ProceedingJoinPoint pjp) {
			// ProceedingJoinPoint : Around 어드바이스에서 사용되는 객체로, 실제 메서드를 호출하거나 호출을 가로채 추가 작업
			
			long start = System.currentTimeMillis();
			
			log.info("[Around] Target : " + pjp.getTarget()); // 실행 대상 객체(즉, 실제 메서드가 있는 객체)를 반환 -> 어떤 객체에서 메서드가 호출됬는지 알수 있따.
			log.info("[Around] Param" + Arrays.toString(pjp.getArgs())); // 메서드 호출시 전달된 파라미터를 배열로 반환
			
			// invoke method
			Object result = null;
			
			try {
				result = pjp.proceed(); // proceed() : 실제 대상 메서드가 실행
			} catch(Throwable e){ // 예외 최상위
				e.printStackTrace();
			}
			
			long end = System.currentTimeMillis();
			log.info("[Around] TIME : "+(end-start));
			
			return result;
			
		}
}
