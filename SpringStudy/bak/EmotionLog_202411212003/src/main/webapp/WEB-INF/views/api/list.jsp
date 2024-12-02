<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.hyejung.domain.DiaryVO"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EmotionLog_list</title>
    <!-- 부트스트랩 CSS 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" integrity="sha512-mSYUmp1HYZDFaVKK//63EcZq4iFWFjxSL+Z3T/aCt4IO9Cejm03q3NKKYN6pFQzY0SBOr8h+eCIAZHPXcpZaNw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <style>
        .calendar-table {
            table-layout: fixed;
            width: 100%;
        }
        .calendar-table th, .calendar-table td {
            text-align: center;
            vertical-align: middle;
            height: 80px;
        }
        .calendar-table .btn {
            width: 100%;
        }
    </style>
</head>
<body>
<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <table class="table table-bordered calendar-table">
                <thead>
                    <tr class="bg-light">
                        <th>일</th>
                        <th>월</th>
                        <th>화</th>
                        <th>수</th>
                        <th>목</th>
                        <th>금</th>
                        <th>토</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // DiaryVO에서 날짜 가져오기
                        DiaryVO diary = (DiaryVO) pageContext.findAttribute("diary");
                        Date regdate = diary.getRegdate();

                        // Calendar 객체로 달력 정보 계산
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
                        c.setTime(regdate);
                        c.set(Calendar.DAY_OF_MONTH, 1); // 1일로 설정

                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH) + 1; // 월은 0부터 시작
                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // 1일의 요일
                        int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월의 마지막 날짜

                        // 달력 데이터 JSP로 전달
                        request.setAttribute("year", year);
                        request.setAttribute("month", month);
                        request.setAttribute("dayOfWeek", dayOfWeek);
                        request.setAttribute("endDay", endDay);
                    %>

					<!-- JSP 내에서 변수 출력 -->
			        <input type="text" id="date-picker" class="form-control bg-white border-0 small" aria-label="Search" aria-describedby="basic-addon2" th:value="|${year}-${month}|">
					<h1 class="text-left">Emotion_log : ${year}-${month}</h1>

					                    
                   <c:set var="date" value="1" />
                   <c:forEach var="row" begin="0" end="5">
					    <%-- 날짜가 마지막을 초과했는지 확인 --%>
					    <c:if test="${date > endDay}">
					        <c:set var="finished" value="true" />
					    </c:if>
					
					    <%--날짜가 남아있지 않으면 반복문 종료 --%>
					    <c:if test="${finished != true}">
					        <tr>
					            <c:forEach var="col" begin="1" end="7">
					                <c:choose>
					                    <%--첫 주의 공백 출력 --%>
					                    <c:when test="${row == 0 && col < dayOfWeek}">
					                        <td></td>
					                    </c:when>
					
					                    <%--마지막 날짜 이후 공백 --%>
					                    <c:when test="${date > endDay}">
					                        <td></td>
					                    </c:when>
					
					                    <%--날짜 출력 --%>
					                    <c:otherwise>
					                        <td>
					                            <button class="btn btn-primary" onclick="handleClick(${date})">${date}</button>
					                            <c:set var="date" value="${date + 1}" />
					                        </td>
					                    </c:otherwise>
					                </c:choose>
					            </c:forEach>
					        </tr>
					    </c:if>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- 부트스트랩 JS & JavaScript -->
<!-- jQuery를 가장 먼저 로드 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<!-- Bootstrap JS 및 Datepicker JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.ko.min.js"></script>

<script>
    // 버튼 클릭 이벤트 처리
    function handleClick(day) {
        alert(day + "일 버튼이 클릭되었습니다!");
    }

    $(document).ready(function() {
        $('#date-picker').datepicker({
            format: "yyyy-mm",
            minViewMode: 1,
            language: "ko",
            autoclose: true
        });
    });

</script>
</body>
</html>
