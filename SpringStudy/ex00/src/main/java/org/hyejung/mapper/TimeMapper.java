package org.hyejung.mapper;

public interface TimeMapper {
	//@Select("SELECT sysdate FROM dual")
	public String getTime();
	
	//xml 사용
	public String getTime2();
}
