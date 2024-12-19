<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />
<link href="<c:url value='/resources/css/user/closet/search_clothing.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="../../include/header.jsp" />
    <jsp:include page="../include/nav.jsp" />
    
    <div class="results-container">
        <h2>검색 결과</h2>
        <div class="results-list">
            <!-- 카테고리별 검색 결과 렌더링 -->
            <c:forEach var="item" items="${searchResults}">
                <c:choose>
                    <c:when test="${item.category == '아우터'}">
                        <div class="result-item outer">
                    </c:when>
                    <c:when test="${item.category == '상의'}">
                        <div class="result-item top">
                    </c:when>
                    <c:when test="${item.category == '드레스'}">
                        <div class="result-item dress">
                    </c:when>
                    <c:when test="${item.category == '하의'}">
                        <div class="result-item bottom">
                    </c:when>
                    <c:when test="${item.category == '신발'}">
                        <div class="result-item shoes">
                    </c:when>
                    <c:otherwise>
                        <div class="result-item other">
                    </c:otherwise>
                </c:choose>

                    <img src="${pageContext.request.contextPath}${item.imageUrl}" alt="${item.itemName}" class="result-image">
                    <div class="result-info">
                        <h3>${item.itemName}</h3>
                        <p>카테고리: ${item.category}</p>
                        <p>색상: ${item.color}</p>
                        <!-- 상세보기 링크 추가 -->
                        <a href="<c:url value='/user/closet/details/${item.closetId}' />" class="details-link">상세보기</a>
                    </div>
                </div>
            </c:forEach>

            <!-- 검색 결과가 없을 때 -->
            <c:if test="${searchResults.isEmpty()}">
                <p class="no-results">검색 결과가 없습니다.</p>
            </c:if>
        </div>
    </div>
    <jsp:include page="../../include/footer.jsp" />
</body>
</html>
