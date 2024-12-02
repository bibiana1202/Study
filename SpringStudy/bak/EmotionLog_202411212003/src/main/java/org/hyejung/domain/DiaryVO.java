package org.hyejung.domain;

import java.util.Date;

import lombok.Data;

@Data
public class DiaryVO {
	private Long userid;
	private Long dno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	private String emotionStatus;
	private String useyn;
}
