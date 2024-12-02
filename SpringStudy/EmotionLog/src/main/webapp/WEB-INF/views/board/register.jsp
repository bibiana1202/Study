<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp" %>

<div class ="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Register</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- ./row -->

<div class ="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-heading">Board Register</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<!-- form 태그를 이용해서 필요한 데이터를 전송!! -->
				<!-- form-group : 폼 요소를 그룹화 하여 정돈된 레이아웃 유지 -->
				<!-- form-control : 개별 폼 요소에 일관된 스타일을 적용 -->
				<form role="form" action="/board/register" method="post">
					<div class="form-group">
						<label>Title</label> 
						<input class="form-control" name='title'>
					</div>
					<div class="form-group">
						<lable>Test area</lable> 
						<textarea class ="form-control" rows="3" name='content'></textarea>
					</div>
					<div class="form-group">
						<lable>Writer</lable>
						<input class="form-control" name='writer'>
					</div>
					<button type="submit" class="btn btn-default">Submit Button</button>
					<button type="reset"  class="btn btn-default">Reset Button</button>
				</form>
			</div>
			<!-- end panel-body -->
		
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->
<%@include file="../includes/footer.jsp"%>