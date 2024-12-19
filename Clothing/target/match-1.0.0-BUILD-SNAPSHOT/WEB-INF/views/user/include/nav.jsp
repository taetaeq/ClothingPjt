<%@page import="com.clothing.match.user.member.UserMemberVo"%>
<%@page import="com.clothing.match.closet.ClosetVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value='/resources/css/user/include/nav.css' />"
	rel="stylesheet" type="text/css">

<nav>
	<div id="nav_wrap">
		<%
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo != null) {
        %>
		<div class="search">
			<form action="<c:url value='/user/closet/searchClothingForm' />"
				method="get">
				<div class="search-container">
					<select id="nav_category" name="category">
						<option value="">카테고리 선택</option>
						<option value="상의">상의</option>
						<option value="하의">하의</option>
						<option value="아우터">아우터</option>
						<option value="드레스">원피스</option>
						<option value="신발">신발</option>
					</select> <input type="text" name="itemName" placeholder="옷의 이름을 검색해주세요.">
					<input type="submit" value="검색">
				</div>
			</form>
		</div>


		<div class="menu">
			<ul>
				<li><a href="<c:url value='/user/member/myPage' />">마이페이지</a></li>
				<li><a href="<c:url value='/user/member/logoutConfirm' />">로그아웃</a></li>
				<li><a href="<c:url value='/user/closet/myCloset' />">나의 옷장</a></li>
			</ul>
		</div>
		<%
        } else {
        %>
		<div class="menu">
			<ul>
				<li><a href="<c:url value='/user/member/loginForm' />">로그인</a></li>
				<li><a href="<c:url value='/user/member/createAccountForm' />">회원가입</a></li>
			</ul>
		</div>
		<%
        }
        %>
	</div>
</nav>
