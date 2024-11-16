<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL의 출력과 포맷을 적용할수 있는 태그 라이브러리 추가~! -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp" %>
  <div class="row">
      <div class="col-lg-12">
          <h1 class="page-header">Tables</h1>
      </div>
      <!-- /.col-lg-12 -->
  </div>
  <!-- /.row -->
  <div class="row">
      <div class="col-lg-12">
          <div class="panel panel-default">
              <div class="panel-heading">
        			Board List Page
               <button id ='regBtn' type="button" class="btn btn-xs pull-right">Register New Board</button>
              </div>
              <!-- /.panel-heading -->
              <div class="panel-body">
                  <table class="table table-striped table-bordered table-hover" >
                      <thead>
                          <tr>
                              <th>#번호</th>
                              <th>제목</th>
                              <th>작성자</th>
                              <th>작성일</th>
                              <th>수정일</th>
                          </tr>
                      </thead>
						<!-- Model을 이용해서 게시물의 목록을 list라는 이름으로 담아서 전달~ -->
						<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}"/></td>
							<td> <!-- 조회 페이지 이동 -->
								<a class='move' href ='<c:out value="${board.bno}"/>'>
									<c:out value="${board.title}"/>
								</a>
							</td>
							<td><c:out value="${board.writer}"/></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}"/></td>
						</tr>
						</c:forEach>	                  
                  </table>

				 <!-- 목록 화면에서의 검색 처리 -->
                 <div class ='row'>
                 	<div class="col-lg-12">
	                 <form id ='searchForm' action ="/board/list" method ='get'>
	                 	<!-- select 태그의 내부는 삼항 연산자를 이용해서 해당 조건으로 검색 되었으면 selected라는 문자열을 출력하게해서 화면에서 선택된 항목으로 보이도록 한다. -->
	                 	<select name='type'>
	                 		<option value ="" <c:out value="${pageMaker.cri.type==null?'selected':''}" />>--</option>
	                 		<option value ="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}" />>제목</option>
	                 		<option value ="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}" />>내용</option>
	                 		<option value ="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':''}" />>작성자</option>
	                 		<option value ="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}" />>제목 or 내용</option>
	                 		<option value ="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}" />>제목 or 작성자</option>
	                 		<option value ="TWC" <c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}" />>제목 or 내용 or 작성자</option>
	                 	</select>
	                 	<input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' > 
	                 	<input type='hidden' name='pageNum' value ='<c:out value="${pageMaker.cri.pageNum}"/>'>
	                 	<input type='hidden' name='amount' value ='<c:out value="${pageMaker.cri.amount}"/>'>
	                 	<button class ='btn btn-default'>Search</button>
	                 </form>                 	
                 	</div>
                 </div>
                 
                  
                 <!-- 실제 페이지 동작 form 태그 : 페이지 번호 전송용 form 추가 -->
                 <!-- 
                 	pageNum과 amount 파라미터가 추가로 전달되는 이유는 actionForm에 이미 이 값들이 hidden 필드로 포함되어 있기 때문입니다. 
                 	코드에서 actionForm을 사용하여 게시물의 bno 값을 추가할 때, pageNum과 amount는 이미 actionForm 안에 있기 때문에 함께 전송됩니다.                 
                  -->
  				 <form id="actionForm" action="/board/list" method="get">
				    <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
				    <input type="hidden" name="amount"  value="${pageMaker.cri.amount}">
				    <input type='hidden' name='type'    value='<c:out value="${pageMaker.cri.type}" />'>
				    <input type='hidden' name='keyword' value='<c:out value="${pageMaker.cri.keyword}" />'>
				 </form>

                  <!-- 페이지 번호 출력 -->
                  <div class ='pull-right'>
                  	<ul class='pagination'>
                  		<!-- 이전 버튼 -->
                  		<c:if test="${pageMaker.prev}">
                  			<li class ="paginate_button previous">
                  				<a href="${pageMaker.startPage-1}">Previous</a>
                  			</li>
                  		</c:if>
                  		<!-- 번호 버튼 -->
                  		<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                  			<li class="paginate_button ${pageMaker.cri.pageNum==num ?"active":"" }">
                  				<a href="${num}">${num}</a>
                  			</li>
                  		</c:forEach>
                  		<!-- 다음 버튼 -->
                  		<c:if test="${pageMaker.next}">
                  			<li class="paginate_button next">
                  				<a href="${pageMaker.endPage+1}">Next</a>
                  			</li>
                  		</c:if>
                  	</ul>
                  </div>
                  <!-- end Pagination -->
                  
                  <!-- Modal 추가 -->
                  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  	<div class ="modal-dialog">
                  		<div class="modal-content">
                  			<div class ="modal-header">
                  				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  				<h4 class="modal-title" id="myModalLabel">Modal title</h4>
                  			</div>
                  			<div class="modal-body">처리가 완료되었습니다.</div>
                  			<div class="modal-footer">
                  				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  				<button type="button" class="btn btn-primary">Save changes</button>
                  			</div>
                  		</div>
                  		<!-- /.modal-content -->
                  	
                  	</div>
                  	<!-- /.modal-dialog -->
                  </div>
                  <!-- /.modal -->
              </div>
              <!-- end panel-body -->
          </div>
          <!-- end panel-->
      </div>
  </div>
  <!-- /.row -->
  
  <script type = "text/javascript">
  $(document).ready(function(){//dom 구조가 만들어져 준비되어진 상태 -> ready -> call back function
  	var result = '<c:out value="${result}"/>';
  	
  	checkModal(result);
  	
  	function checkModal(result){
  		if(result === ''){
  			return;
  		}
  		if(parseInt(result)>0){
  			$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");
  		}// remove 일경우 성공시 "success"가 반환된다!
  		$("#myModal").modal("show"); // aria-hidden = false 
  	}
  	
  	// 등록 버튼
  	$("#regBtn").on("click",function(){
  		self.location ="/board/register";
  	});
  	
  	// 페이지 번호 클릭
  	var actionForm = $("#actionForm");
  	
  	$(".paginate_button a").on("click",function(e){
  		e.preventDefault(); // a 태그를 클릭해도 페이지 이동 없게
  		console.log('paginate_button click');
  		// form 태그 내의 pageNum값은 href 속성값으로 변경
  		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
  		actionForm.submit();
  	});
  	
  	// 조회 페이지 이동시 이벤트
  	$(".move").on("click",function(e){
  		e.preventDefault();
		 //  게시물의 제목을 클릭하면 form 태그에 추가로 bno값을 전송하기 위해서 input 태그를 만들어 추가
  		actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
		 // form 태그의 action을 '/board/get'으로 변경
 		actionForm.attr("action","/board/get");// <-- 여기서 action이 변경됨(동적)
		actionForm.submit();
  	});
  	
  	// 검색 버튼의 이벤트 처리
  	let searchForm = $("#searchForm");
  	
  	$("#searchForm button").on("click",function(e){
  		// 검색 종류를 입력하지 않았을때
  		if(!searchForm.find("option:selected").val()){
  			alert("검색 종류를 선택하세요");
  			return false;
  		}
  		// 키워드를 입력하지 않았을때
  		if(!searchForm.find("input[name='keyword']").val()){
  			alert("키워드를 입력하세요");
  			return false;
  		}
  		
  		//form 태그의 전송은 막고 페이지의 번호는 1이 되도록 처리
  		searchForm.find("input[name='pageNum']").val("1");
  		e.preventDefault();
  		
  		searchForm.submit();
  	});
  	
  });
  </script>
<%@include file="../includes/footer.jsp"%>