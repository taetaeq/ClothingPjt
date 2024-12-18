<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/user/bookmark/bookmark_list.css' />"rel="stylesheet" type="text/css">

</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />
	
	<h2>내 북마크</h2>
    <div>
        <c:forEach var="bookmark" items="${bookmarks}">
            <div>
                <p>북마크 ID: ${bookmark.bookmarkId}</p>
                <p>저장된 옷 IDs: ${bookmark.closetIds}</p>
                <p>생성일: ${bookmark.createdAt}</p>
            </div>
        </c:forEach>
    </div>
    
    <jsp:include page="../../include/footer.jsp" />
</body>
</html>