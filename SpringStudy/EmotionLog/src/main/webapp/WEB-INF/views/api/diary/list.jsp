<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.hyejung.domain.DiaryVO"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EmotionLog_list</title>
    <!-- 부트스트랩 CSS 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" integrity="sha512-mSYUmp1HYZDFaVKK//63EcZq4iFWFjxSL+Z3T/aCt4IO9Cejm03q3NKKYN6pFQzY0SBOr8h+eCIAZHPXcpZaNw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<style>
    /* 전체 달력 중앙 정렬 */
    .calendar-wrapper {
        display: flex;
        justify-content: center; /* 가로 중앙 정렬 */
        align-items: center;
        margin: 0 auto;
        padding: 20px;
    }

    /* 테이블 스타일 */
    .calendar-table {
        table-layout: fixed;
        width: auto;
        margin: 0 auto; /* 테이블 자체 중앙 정렬 */
    }

    .calendar-table th,
    .calendar-table td {
        text-align: center; /* 날짜 번호 중앙 정렬 */
        vertical-align: top; /* 내용 위쪽 정렬 */
        height: 150px; /* 셀 높이 고정 */
        width: 150px; /* 셀 너비 고정 */
    }

    .calendar-card {
        display: flex;
        flex-direction: column;
        justify-content: flex-start; /* 카드 내부 내용 위쪽 정렬 */
        align-items: flex-start; /* 텍스트 왼쪽 정렬 */
        height: 100%;
        width: 100%;
        border: 1px solid #ddd;
        border-radius: 8px;
        background-color: #f8f9fa;
        padding: 10px;
        overflow: hidden;
    }
    
    
    .calendar-card .date {
        font-size: 18px;
        font-weight: bold;
        text-align: center; /* 날짜 번호는 중앙 정렬 */
        width: 100%;
        margin-bottom: 8px;
    }

    .calendar-card .card-text {
        font-size: 14px;
        line-height: 1.5;
        text-align: left; /* 텍스트 왼쪽 정렬 */
        overflow-wrap: break-word;
        word-break: break-word;
        overflow-y: auto; /* 내용이 넘치면 스크롤 */
        max-height: calc(100% - 30px); /* 날짜 번호를 제외한 높이 제한 */
    }
</style>





</head>
<body>
<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-md-8" id="calendar-container">
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

					<!-- JSP 내에서 변수 출력 -->
					<input type="text" id="date-picker" class="form-control" value="${pick_date}" />

					<h1 class="text-left">Emotion_log : ${year}-${month}</h1>
					<h1 class="text-left">${diary.userid}</h1>
					                    
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
					                        	<%--조회 페이지 이동 --%>
												<c:set var="ymd" value="${year}-${month}-${date}" />
												<div class="calendar-card" onclick="handleClick('${ymd}')">
										            <div class ="cart-header">
										               ${date} <!-- 날짜 출력 -->										            
										            </div>
										            <div class="card-text">
										                <ul>
														    <!-- diaryList에서 현재 날짜와 같은 제목 출력 -->
														    <c:forEach var="diary" items="${diary}">
														        <!-- diary.regdate를 yyyy-MM-dd 형식으로 변환 -->
														        <fmt:formatDate value="${diary.regdate}" pattern="dd" var="formattedDate" />														        
								 						        <c:if test="${formattedDate == date}">
														            <li>${diary.title}</li>
														        </c:if> 
														    </c:forEach>
										                </ul>
										            </div>
										        </div>
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
        //alert(day + "일 버튼이 클릭되었습니다!");
        const da = new Date(day); // String을 Date로 변환
        alert(da); // Date 형태로 출력
  		
/*         e.preventDefault();
		 //  게시물의 제목을 클릭하면 form 태그에 추가로 bno값을 전송하기 위해서 input 태그를 만들어 추가
 		actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
		 // form 태그의 action을 '/board/get'으로 변경
		actionForm.attr("action","/board/get");// <-- 여기서 action이 변경됨(동적)
		actionForm.submit(); */
        
    }

    $(document).ready(function () {
        $('#date-picker').datepicker({
            format: "yyyy-mm",
            minViewMode: 1,
            language: "ko",
            autoclose: true
        }).on('changeDate', function () {
            const selectedDate = $('#date-picker').val(); // 선택된 날짜 (예: 2024-11)
            console.log("Selected Date: ", selectedDate);

            // AJAX 요청 보내기
            $.ajax({
                url: '/api/diary/list', // 서버에서 데이터를 가져올 URL
                type: 'GET',
                data: { date: selectedDate },
                success: function (response) {
                    // 서버에서 받은 데이터로 달력 업데이트
                    $('#calendar-container').html(response);
                },
                error: function (error) {
                    console.error('데이터를 가져오는 중 오류 발생:', error);
                }
            });
        });
    });


</script>
</body>
</html>
