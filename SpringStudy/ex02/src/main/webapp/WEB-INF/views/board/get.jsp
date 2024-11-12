<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp" %>

<div class ="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- ./row -->

<div class ="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-heading">Board Read Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- register.jsp 에 있던 <form>태그는 조회페이지에서는 그다지 필요하지 않으므로 제거~! -->
					<div class="form-group">
						<label>Bno</label> 
						<input class="form-control" name='bno' value='<c:out value="${board.bno}"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>Title</label> 
						<input class="form-control" name='title' value='<c:out value="${board.title}"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>Text area</label> 
						<textarea class ="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}"/></textarea>
					</div>
					<div class="form-group">
						<label>Writer</label>
						<input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' readonly="readonly">
					</div>
					
					<button data-oper='modify' class="btn btn-default">Modify</button>
					<button data-oper='list' class="btn btn-info">List</button> 
					
					<form id ='operForm' action="/board/modify" method="get">
						<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
					</form>
			</div>
			<!-- end panel-body -->
		
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->

  <script type = "text/javascript">
  $(document).ready(function(){//dom 구조가 만들어져 준비되어진 상태 -> ready -> call back function

	  var operForm = $("#operForm");
  
	  // 사용자가 수정 버튼을 누르는 경우에는 bno 값을 같이 전달하고 <form> 태그를 submit 시켜서 처리
  	  $("button[data-oper='modify']").on("click",function(e){
  		  operForm.attr("action","/board/modify").submit();
  	  });
  	  
	  // 아무런 데이터가 필요하지 않으므로 , <form> 태그 내에 bno 태그를 지우고 submit을 통해서 리스트 페이지로 이동
  	  $("button[data-oper='list']").on("click",function(e){
  		  operForm.find("#bno").remove();
  		  operForm.attr("action","/board/list")
  		  operForm.submit();
  	  });
  });
  </script>
<%@include file="../includes/footer.jsp"%>