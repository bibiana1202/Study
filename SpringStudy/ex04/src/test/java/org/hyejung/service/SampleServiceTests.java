package org.hyejung.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class SampleServiceTests {
	// Spring 프레임워크에서 의존성 주입을 수행하기 위해 사용되는 어노테이션으로, 
	// Spring이 특정 필드, 생성자, 또는 메서드에 필요한
	// 빈을 자동으로 주입해 주도록 합니다.
	/*
	 * SampleService는 인터페이스이고, SampleServiceImpl은 그 인터페이스를 구현한 구체 클래스입니다.
	 * Spring에서 SampleServiceImpl 클래스가 SampleService 인터페이스를 구현하고 있으면, 
	 * SampleService 타입의 필드에는 SampleServiceImpl 인스턴스를 주입할 수 있습니다. 
	 * 이는 업캐스팅(상위 타입으로의 변환)이라고 합니다. 
	 * 즉, SampleServiceImpl 인스턴스가 SampleService 타입으로 취급되는 것입니다
	 * 
	 * Spring 이 빈을 등록 하는 과정
	 * 1. 빈 스캔 및 등록: Spring 애플리케이션이 시작될 때, @ComponentScan 또는 XML 설정 등을 통해 @Service, @Component, @Repository 등으로 표시된 클래스들을 스캔하여 빈으로 등록합니다.
	 * 2. 의존성 주입 : Spring은 @Autowired가 붙은 SampleService 타입의 service 필드를 발견하면, 컨테이너에 등록된 빈 중에서 SampleService 타입 또는 그 하위 타입을 찾습니다.
	 * 3. 자동 매칭 : Spring 컨테이너에는 SampleService 타입의 빈이 존재하지 않지만, SampleService를 구현하는 SampleServiceImpl 클래스의 인스턴스가 있습니다.
	 * 				  Spring은 SampleServiceImpl이 SampleService의 구현체라는 것을 알고 있으므로, SampleService 타입의 service 필드에 SampleServiceImpl 인스턴스를 주입할 수 있습니다.
	 * */
	
	@Autowired // Spring 컨테이너는 @Autowired 를 확인하여, 애플리케이션 컨텍스트에 있는 Samlpleservice타입의 빈을 주입해준다.
	// 이때 구현체가 SampleServiceImpl 하나뿐이라면 Spring은 자동으로 그 구현체를 주입
	private SampleService service; // 업캐스팅되어 자동으로 SampleServiceImpl 클래스의 인스턴스 주입
	
	/*
	 * Spring AOP 설정을 통해 SampleServiceTests 에서 서비스 객체에 대한 프록시 객체가 생성되는 과정!
	 * 1. aop 설정 : <aop:aspectj-autoproxy/> 설정을 통해 spring 에서 AOP 프록시 객체가 생성되도록 한다!
	 * 2. 프록시 객체 : SampleService 인터페이스를 구현하는 프록시 객체가 생성되어 주입,프록시는 SampleServiceImpl 인스턴스를 감싸고 있으며, 이 프록시가 호출을 가로채고 AOP 기능을 적용합니다.
	 *    프록시 객체는 JDK 동적 프록시로 생성된다
	 */
	
	// AOP 설정을 한 Target에 대해서 Proxy 객체가 정상적으로 만들어져 있는지 확인
	@Test
	public void testClass() {
		log.info(service);
		log.info(service.getClass().getName());
	}
	
	// args 를 이용한 파라미터 추적
	@Test
	public void testAdd() throws Exception{
		log.info(service.doAdd("123", "456"));
	}
	
	// 예외를 발생한후에 동작
	@Ignore // 테스트를 임시로 실행하지 않도록 설정
	@Test
	public void testAddError() throws Exception{
		log.info(service.doAdd("123", "ABC"));
	}
}
