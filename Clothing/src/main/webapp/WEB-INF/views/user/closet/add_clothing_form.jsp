<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />
<link
	href="<c:url value='/resources/css/user/closet/add_clothing_form.css' />"
	rel="stylesheet" type="text/css">

<!-- 스크립트 포함 -->
<jsp:include page="../include/add_clothing_form_js.jsp" />

</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	<jsp:include page="../include/nav.jsp" />

	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>옷 등록</h3>
			</div>
			
			<div class="form-container">
                <!-- 이미지 미리보기 영역 -->
                <div class="image-preview-container">
                    <div class="image-preview">
                        <span>이미지를 선택하면 여기에 미리보기가 표시됩니다.</span>
                    </div>
                </div>
                
				<form action="<c:url value='/user/closet/addClothingConfirm' />"
					name="add_clothing_form" method="post"
					enctype="multipart/form-data" onsubmit="return validateForm(event)">



					<!-- 옷 이름 -->
					<input type="text" name="itemName" placeholder="옷 이름을 입력해주세요."
						required> <br>

					<!-- 카테고리 -->
					<select id="category" name="category" required>
						<option value="">카테고리 선택</option>
						<option value="상의">상의</option>
						<option value="하의">하의</option>
						<option value="아우터">아우터</option>
						<option value="드레스">원피스</option>
						<option value="신발">신발</option>
					</select> <br>

					<!-- 세부 디테일 -->
					<select id="detail" name="detail" required>
						<option value="">세부 카테고리 선택</option>
					</select><br>

					<!-- 이미지 업로드 -->
					<input type="file" id="imageFile" name="imageFile" accept="image/*" required>
					<br>
					
					<!-- 색상 선택 드롭다운 -->
					<select id="color" name="color" required>
					    <option value="">색상 선택</option>
					</select>
					
					<!-- 패턴 선택 드롭다운 -->
					<select id="pattern" name="pattern" required>
					    <option value="">패턴 선택</option>
					</select>


					<!-- 피트감 -->
					<select id="fit" name="fit" required>
						<option value="">핏 선택</option>

					</select> <br>

					
						
					</select> <br> <input type="submit" value="등록"
						onclick="addClothingForm();">

					<!-- 리셋 버튼 -->
					<input type="reset" value="초기화">

				</form>
			</div>
		</div>
	</section>

	<jsp:include page="../../include/footer.jsp" />
</body>
</html>
