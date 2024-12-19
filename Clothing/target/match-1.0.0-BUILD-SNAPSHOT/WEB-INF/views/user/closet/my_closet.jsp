<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="<c:url value='/resources/css/user/closet/my_closet.css' />" rel="stylesheet" type="text/css">

<jsp:include page="../include/my_closet_js.jsp" />

</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	<jsp:include page="../include/nav.jsp" />
	<div class="mypage-content">
    <h3>나의 옷장</h3>

    <div class="closet-container">
        <button class="closet-handle" onclick="toggleCloset()">옷장 열기</button>

        <div class="closet-content">
            <ul>
                <li><a href="<c:url value='/user/closet/addClothingForm' />">옷 추가</a></li>
                <li><a href="<c:url value='/user/closet/viewClothing' />">옷 리스트 보기</a></li>
            </ul>
        </div>
    </div>
  </div>
   <!-- 여기에 실제 옷장이 열리는 UI를 삽입 -->
    
</body>
</html>