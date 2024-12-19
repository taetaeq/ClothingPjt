<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/view_detail.css' />" rel="stylesheet" type="text/css">

</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	
	<div class="container">
        <h2>개인 상세 정보</h2>
        
        <table class="detail-table">
                <tr>
                    <th>아이디</th>
                    <td>${userMemberVo.username}</td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>${userMemberVo.name}</td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>${userMemberVo.gender == 'M' ? '남성' : '여성'}</td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>${userMemberVo.email}</td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>${userMemberVo.phone}</td>
                </tr>
                <tr>
                    <th>키</th>
                    <td>${userMemberVo.height} cm</td>
                </tr>
                <tr>
                    <th>체중</th>
                    <td>${userMemberVo.weight} kg</td>
                </tr>
                <tr>
                    <th>BMI</th>
                    <td>${userMemberVo.bmi != null ? userMemberVo.bmi : '계산되지 않음'}</td>
                </tr>
            </table>
    </div>
    <div class="mypage">
				
				<a href="<c:url value='/user/member/myPage' />">마이페이지</a>
	</div>
</body>
</html>