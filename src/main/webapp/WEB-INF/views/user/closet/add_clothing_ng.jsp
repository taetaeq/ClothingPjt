<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />

<link href="<c:url value='/resources/css/user/closet/add_clothing_result.css' />" rel="stylesheet" type="text/css">

</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	
	<jsp:include page="../include/nav.jsp" />
	
	<section>
		
		<div id="section_wrap">
		
			<div class="word">
			
				  <h3>옷등록 실패했습니다.</h3> 
				
			</div>
			
			<div class="others">
				
				<div class="others">
		    <a href="<c:url value='/user/closet/addClothingForm' />" class="btn-retry">옷 다시 등록하기</a>
		    <a href="<c:url value='/user/closet/myCloset' />" class="btn-back">나의 옷장으로 가기</a>
		</div>

				
			</div>
		
		</div>
		
	</section>
	
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>