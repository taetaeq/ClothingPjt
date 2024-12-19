<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/my_detail_result.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />
	
	<section>
		
		<div id="section_wrap">
		
			<div class="word">
			
				<h3>상세정보 입력에 실패하였습니다</h3>
				
			</div>
			
			<div class="others">
				<a href="<c:url value='/user/member/myPage' />">마이페이지</a>
				<a href="<c:url value='/user/member/myDetailForm' />">상세정보 입력</a>
			</div>
		
		</div>
		
	</section>
	
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>