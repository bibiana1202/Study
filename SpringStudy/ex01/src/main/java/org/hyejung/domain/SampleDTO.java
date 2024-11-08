package org.hyejung.domain;

import lombok.Data;

@Data // getter,setter,equals,toString 등의 메서드를 자동 생성
public class SampleDTO {
	private String name;
	private int age;
}
