<%@page import="com.clothing.match.user.member.UserMemberVo" %>
<%@page import="com.clothing.match.closet.ClosetVo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/mypage.css' />" rel="stylesheet" type="text/css">
</head>
<body>
<nav>
	<jsp:include page="../../include/header.jsp" />
	
	<div id="nav_wrap">
		
		<div class="menu">
			<ul>
				<li><a href="<c:url value='/user/member/logoutConfirm' />">로그아웃</a></li>
				<li><a href="<c:url value='/user/member/myDetailForm' />">개인 상세 정보 입력</a></li>
				<li><a href="<c:url value='/user/member/viewDetail' />">계정 상세 정보 보기</a></li>
            	<li><a href="<c:url value='/user/member/modifyAccountForm' />">계정 수정</a></li>
            	<li><a href="<c:url value='/user/member/bookMark' />">북마크</a></li>
				<li><a href="<c:url value='/user/closet/myCloset' />">나의 옷장</a></li>
			</ul>
		</div>
	</div>
</nav>

<div class="mypage-content">
	<h3>환영합니다, ${loginedUserMemberVo.name}님!</h3>
     <p>여기서 마이페이지 관련 정보를 확인하고 수정할 수 있습니다.</p>
</div>
</body>
</html>