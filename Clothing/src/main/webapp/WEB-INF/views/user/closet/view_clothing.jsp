<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="../../include/title.jsp" />
    <link href="<c:url value='/resources/css/user/closet/view_clothing.css' />" rel="stylesheet" type="text/css">
    <jsp:include page="../include/view_clothing_js.jsp" />
</head>
<body>
    <jsp:include page="../../include/header.jsp" />
    <jsp:include page="../include/nav.jsp" />

    <div class="container">
        <h2 onclick="filterCategory('all')" class="closet-title">내 옷장</h2>

        <!-- 옷장 아이템 섹션 -->
        <div id="closet-items-container">
            <c:forEach var="category" items="${categories}">
    			<div class="closet-category-section" data-category="${category}">
        			<h3 onclick="filterCategory('${category}')">${category}</h3>
                    <div class="closet-items">
                        <c:forEach var="item" items="${closetItems}">
                            <c:if test="${item.category == category}">
                                <div class="closet-item-card">
                                
                                    <img src="${item.imageUrl}" alt="${item.itemName}" class="closet-item-image">
                                    <div class="closet-item-info">
                                        <h3>${item.itemName}</h3>
                                        <p>색상: ${item.color}</p>
                                        <p>핏: ${item.fit}</p>
                                        <p>패턴: ${item.pattern}</p>
                                        <a href="<c:url value='/user/closet/details/${item.closetId}' />" class="details-link">상세보기</a>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- 옷 추가 버튼 -->
        <div class="closet-content">
            <a href="<c:url value='/user/closet/addClothingForm' />" class="add-button">옷 추가</a>
        </div>
    </div>

    <jsp:include page="../../include/footer.jsp" />
</body>
</html>
