<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />
<link href="<c:url value='/resources/css/user/mydetail.css' />" rel="stylesheet" type="text/css">
<jsp:include page="../include/mydetail_js.jsp" />
</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	<jsp:include page="../include/nav.jsp" />
	
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>상세정보 입력</h3>
			</div>

			<div class="my_detail_form">
				<form action="<c:url value='/user/member/saveDetail' />" 
						name="my_detail_form" method="post">
					<input type="number" name="height" placeholder="키를 입력하세요." /> <span>cm</span> <br>
					<input type="number" name="weight" placeholder="몸무게를 입력하세요." /> <span>kg</span><br>
					<input type="button" value="상세정보 입력" onclick="myDetailForm();" />
					<input type="reset" value="초기화" />
				</form>
			</div>
			<div class="viewDetail">
				<a href="<c:url value='/user/member/viewDetail' />">상세정보 보기</a>
			</div>
		</div>
	</section>
	
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>
