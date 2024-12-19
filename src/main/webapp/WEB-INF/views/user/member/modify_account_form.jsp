<%@page import="com.clothing.match.user.member.UserMemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<jsp:include page="../../include/title.jsp" />

<link
	href="<c:url value='/resources/css/user/modify_account_form.css' />"
	rel="stylesheet" type="text/css">

<jsp:include page="../include/modify_account_form_js.jsp" />
</head>
<body>

	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />

	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>회원 정보 수정</h3>
			</div>

			<div class="modify_account_form">
				<form action="<c:url value='/user/member/modifyAccountConfirm' />"
					name="modify_account_form" method="post">

					<input type="hidden" name="userId"
						value="${loginedUserMemberVo.userId}"> <input type="text"
						name="username" value="${loginedUserMemberVo.username}" readonly
						disabled> <br> <input type="password" name="password"
						value="******" readonly disabled> <br> <input
						type="text" name="name" value="${loginedUserMemberVo.name}"
						placeholder="사용자 이름을 입력하세요."> <br> <select
						name="gender">
						<option value="">사용자 성별을 선택하세요.</option>
						<option value="M"
							<c:if test="${loginedUserMemberVo.gender eq 'M'}"> selected </c:if>>남성</option>
						<option value="F"
							<c:if test="${loginedUserMemberVo.gender eq 'F'}"> selected </c:if>>여성</option>
					</select> <br> <input type="email" name="email"
						value="${loginedUserMemberVo.email}" placeholder="사용자 이메일을 입력하세요."><br>
					<input type="text" name="phone"
						value="${loginedUserMemberVo.phone}"
						placeholder="사용자 전화번호를 입력하세요."> <br> <input
						type="button" value="회원 정보 수정" onclick="modifyAccountForm();">
					<input type="reset" value="초기화">

				</form>
			</div>
		</div>
	</section>

	<jsp:include page="../../include/footer.jsp" />

</body>
</html>