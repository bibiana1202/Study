<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello Spring~!
</h1>

<P>  The time on the server is ${serverTime}. </P>
<ol>
	<li><a href="/board/list">board list</a>
	<li><a href="/api/list">diary list</a>
</ol>
<img src="/resources/img/a.jpg"/>
</body>
</html>
