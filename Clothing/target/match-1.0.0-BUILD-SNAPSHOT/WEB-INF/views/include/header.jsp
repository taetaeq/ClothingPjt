<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value='/resources/css/include/header.css' />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.3.js" integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM=" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function() {
	    console.log('HEADER DOCUMENT READY!!');
	    
	    let pathname = $(location).attr('pathname');
	    let isIncludeUser = pathname.includes('/user');
	    
	    if (isIncludeUser) {
	        $('#header_wrap .menu ul li a.user').css('text-decoration', 'green wavy underline');
	    } else {
	        $('#header_wrap .menu ul li a.recommend').css('text-decoration', 'blue wavy underline');
	    }
	});		
</script>

<header>

        <!-- 사이트 제목 -->
        <div class="title">
            <h3>옷장 관리 서비스</h3>
        </div>
    </div>
</header>