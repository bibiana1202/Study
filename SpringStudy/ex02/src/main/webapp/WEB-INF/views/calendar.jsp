<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<pre>
<% 

	Calendar c = Calendar.getInstance(); // new 가 감춰져 있는것, 내부에서 시스템에 맞는 인스턴스를 생성
	
	int year = 2024;
	int month = 10;
	c.set(year, month-1 ,1);
	
	// 요일 : 1일이 무슨 요일 인가?
	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	
	// 마지막날 : 마지막 날짜는 몇일 인가?
	int endDay = c.getActualMaximum(Calendar.DATE);
%>

<table>
<caption> <%=year %>년 <%=month %>월</caption> <!-- jsp expression -->
<tr>
<td>일</td>
<td>월</td>
<td>화</td>
<td>수</td>
<td>목</td>
<td>금</td>
<td>토</td>
</tr>

<tr align ='right'>
<%
	/*
	jsp의 multi line comment 3 
	*/
	for(int d= 1 ; d< dayOfWeek ; d++){
		out.print("<td></td>");
	}
%>
<%  for(int date =1 , d=dayOfWeek; date <= endDay ; date++, d++){%>
	<td><%=date %></td>
	<%if(d%7==0) out.println("</tr><tr align='right'>");%>
<%  } %>	
</tr>
</table>
</body>
</html>