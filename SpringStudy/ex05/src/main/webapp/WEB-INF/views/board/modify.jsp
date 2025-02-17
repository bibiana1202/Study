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
				<form role="form" action="/board/modify" method ="post">
						<!-- 추가 -->
						<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
						<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
						<input type='hidden' name='type'    value='<c:out value="${cri.type}" />'>
					    <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}" />'>					
						<div class="form-group">
							<label>Bno</label> 
							<input class="form-control" name='bno' value='<c:out value="${board.bno}"/>' readonly="readonly">
						</div>
						
						<div class="form-group">
							<label>Title</label> 
							<input class="form-control" name='title' value='<c:out value="${board.title}"/>' >
						</div>
						
						<div class="form-group">
							<label>Text area</label> 
							<textarea class ="form-control" rows="3" name='content' ><c:out value="${board.content}"/></textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' readonly="readonly">
						</div>

						<div class="form-group">
							<label>RegDate</label>
							<input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate}"/>' readonly="readonly">
						</div>

						<div class="form-group">
							<label>Update Date</label>
							<input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate}"/>' readonly="readonly">
						</div>
						
						<button type='submit' data-oper='modify' class="btn btn-default">Modify</button>
						<button type='submit' data-oper='remove' class="btn btn-danger">Remove</button>
						<button type='submit' data-oper='list' class="btn btn-info">List</button>
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
	 let formObj = $("form");  // jQuery 선택자를 사용하여 HTML 페이지에서 <form> 요소를 선택하고, 이를 변수에 할당
	 	
	$('button').on("click",function(e){
		e.preventDefault(); // 기본 동작을 막기(폼제출방지,페이지이동방지,기타기본동작방지)
		let operation = $(this).data("oper"); // javascript 에서는 <button>태그의 'data-oper' 속성을 이용해서 원하는 기능을 동작하도록 처리
		console.log(operation);
		
		if(operation === 'remove'){
			formObj.attr("action","/board/remove")
		}else if(operation === 'list'){
			// move to list
			formObj.attr("action","/board/list").attr("method","get");
			// 수정/삭제 페이지에서 목록 페이지로 이동
			// form 태그에서 필요한 부분만 잠시 복사(clone)해서 보관
			let pageNumTag = $("input[name='pageNum']").clone();
			let amountTag = $("input[name='amount']").clone();
			let typeTag = $("input[name='type']").clone();
			let keywordTag = $("input[name='keyword']").clone();
			// form 태그내의 모든 내용은 지워버리고			
			formObj.empty();
			// 다시 필요한 태그만 추가해서 /board/list를 호출하는 형태
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(typeTag);
			formObj.append(keywordTag);
		}
		formObj.submit(); // 마지막에 직접 submit() 수행
	});
  });
  </script>
<%@include file="../includes/footer.jsp"%>