package org.hyejung.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 비어 있는 생성자 만들기
@NoArgsConstructor // 모든 속성을 사용하는 생성자
public class SampleVO {

	private Integer mno;
	private String firstName;
	private String lastName;
}
