<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/user/closet/detail_clothing.css' />" rel="stylesheet" type="text/css">

<jsp:include page="../include/detail_clothing_js.jsp" />

</head>
<body>
	 <jsp:include page="../../include/header.jsp" />
   	<jsp:include page="../include/nav.jsp" />
   	
   	<div class="container">
        <h2>상세보기</h2>

        <!-- 상세정보 표시 영역 -->
        <div class="clothing-detail">
            <!-- 아이템 정보 출력 -->
            <c:if test="${not empty clothing}">
                <div class="clothing-image">
                    <img src="${pageContext.request.contextPath}${clothing.imageUrl}" alt="${clothing.itemName}" />
                </div>
                <div class="clothing-info">
                    <h3>${clothing.itemName}</h3>
                    <p><strong>카테고리:</strong> ${clothing.category}</p>
                    <p><strong>색상:</strong> ${clothing.color}</p>
                    <p><strong>핏:</strong> ${clothing.fit}</p>
                    <p><strong>패턴:</strong> ${clothing.pattern}</p>
                </div>
            </c:if>
            <!-- 삭제 버튼 -->
			<c:if test="${not empty clothing}">
			    <a href="<c:url value='/user/closet/delete/${clothing.closetId}' />" class="delete-link" onclick="return confirmDelete();">
			        <button type="button" class="delete-btn">삭제</button>
			    </a>
			    <!-- 옷 리스트 보기 버튼 -->
                <a href="<c:url value='/user/closet/viewClothing' />" class="view-list-link">
                    <button type="button" class="delete-btn view-list-btn">옷 리스트 보기</button>
                </a>
			</c:if>
        </div>

    </div>
    <jsp:include page="../../include/footer.jsp" />
</body>
</html>