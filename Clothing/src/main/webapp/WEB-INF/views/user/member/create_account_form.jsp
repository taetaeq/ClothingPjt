<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/create_account_form.css' />"rel="stylesheet" type="text/css">

<jsp:include page="../include/create_account_form_js.jsp" />

</head>
<body>

	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />

	<section>

		<div id="section_wrap">

			<div class="word">

				<h3>회원가입</h3>

			</div>
			<div class="create_account_form">
			
				<form action="<c:url value='/user/member/createAccountConfirm' />"
					name="create_account_form" method="post">

					<!-- 사용자 아이디 -->
					<input type="text" name="username" placeholder="아이디를 입력해주세요.">
					<br>

					<!-- 비밀번호 -->
					<input type="password" name="password" placeholder="비밀번호를 입력해주세요.">
					<br>

					<!-- 비밀번호 확인 -->
					<input type="password" name="password_again"
						placeholder="한번 더 입력해주세요."> <br>

					<!-- 사용자 이름 -->
					<input type="text" name="name" placeholder="이름을 입력해주세요."> <br>

					<!-- 성별 -->
					<select name="gender">
						<option value="">성별 선택</option>
						<option value="M">남자</option>
						<option value="F">여자</option>
					</select> <br>

					<!-- 이메일 -->
					<input type="email" name="email" placeholder="이메일을 입력해주세요.">
					<br>

					<!-- 전화번호 -->
					<input type="text" name="phone" placeholder="전화번호를 입력해주세요.">
					<br>

					<!-- 계정 생성 버튼 -->
					<input type="button" value="Create Account"
						onclick="createAccountForm();">

					<!-- 리셋 버튼 -->
					<input type="reset" value="Reset">

				</form>
			</div>
		</div>
		<div class="login">

			<a href="<c:url value='/user/member/loginForm' />">로그인</a>

		</div>
	</section>

	<jsp:include page="../../include/footer.jsp" />

</body>
</html>