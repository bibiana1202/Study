<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
	<div class ='uploadDiv'>
		<input type ='file' name='uploadFile' multiple>
	</div>
	
	<button id='uploadBtn'>Upload</button>
	
	<!-- jQuery 라이브러리 추가 -->
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	
	<!-- jQuery를 이용하는 경우에 파일 업로드는 FormData 객체를 이용하게 된다. 가상의 <form> 태그 -->
	<script>
		$(document).ready(function(e){
			$("#uploadBtn").on("click",function(e){
				let formData = new FormData();
				let inputFile = $("input[name='uploadFile']");
				let files = inputFile[0].files;
				console.log(files);				
				
				// add File Data to formData
				for(var i =0; i < files.length; i++){
					formData.append("uploadFile",files[i]);
				}
				
				$.ajax({
					url : '/uploadAjaxAction',
					processData : false,
					contentType: false,
					data : formData,
					type: 'POST',
					success : function(result){
						alert("Uploaded");
					}
				}); // $.ajax
			});
		});
	</script>
</body>
</html>