<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/login_result.css' />" rel="stylesheet" type="text/css">

<jsp:include page="../include/login_js.jsp" />
</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />
	
	<section>
		
		<div id="section_wrap">
		
			<div class="word">
			
				<h3>로그인 성공</h3>
				
			</div>
			
			<div class="others">
				
				<a href="<c:url value='/user/member/logoutConfirm' />">로그아웃</a>
				<a href="<c:url value='/user/member/modifyAccountForm' />">계정 수정</a>
				
			</div>
		
		</div>
		
	</section>
	
	<jsp:include page="../../include/footer.jsp" />
	
</body>
</html>