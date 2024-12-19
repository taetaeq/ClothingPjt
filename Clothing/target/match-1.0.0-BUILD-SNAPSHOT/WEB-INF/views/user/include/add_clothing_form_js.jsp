<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
// 폼 유효성 검사 함수
function validateForm(event) {
    event.preventDefault(); // 기본 폼 제출 방지
    console.log("Form validation started");

    const form = document.forms["add_clothing_form"];
    const requiredFields = ["itemName", "category", "detail", "color", "imageFile", "fit", "pattern"];
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    // 필수 입력 필드 확인
    for (let field of requiredFields) {
        if (form[field].value.trim() === "") {
            alert("모든 항목을 입력해 주세요.");
            return false;
        }
    }

    // 이미지 파일 확인
    const fileInput = form["imageFile"];
    const filePath = fileInput.value;

    if (filePath && !allowedExtensions.test(filePath)) {
        alert("이미지 파일만 업로드할 수 있습니다. (jpg, jpeg, png, gif)");
        console.error("Invalid file extension");
        fileInput.value = "";
        return false;
    }
    console.log("Form validation passed");
    form.submit(); // 유효성 통과 시 폼 제출
}

// 이미지 미리보기 및 분석 처리
	function handleImageChange(event) {
    const file = event.target.files[0];
    const previewContainer = document.querySelector('.image-preview');
    previewContainer.innerHTML = ''; // 기존 내용 초기화

    if (!file) {
        const placeholderText = document.createElement('span');
        placeholderText.textContent = '이미지를 선택하면 여기에 미리보기가 표시됩니다.';
        previewContainer.appendChild(placeholderText);
        return;
    }

    const reader = new FileReader();
    const previewImage = document.createElement('img');
    previewImage.style.maxWidth = '100%';
    previewImage.style.maxHeight = '100%';
    previewImage.style.borderRadius = '5px';

    reader.onload = () => {
        previewImage.src = reader.result;
        previewContainer.appendChild(previewImage);
    };
    reader.readAsDataURL(file);

    // Google Cloud Storage에 업로드 및 Vision API 호출
    try {
        const formData = new FormData();
        formData.append("imageFile", file);

        // 이미지 업로드 요청
        const uploadResponse = await fetch("/user/closet/uploadTempImage", {
            method: "POST",
            body: formData,
        });

        if (!uploadResponse.ok) {
            throw new Error("이미지 업로드 실패");
        }

        const { imageUrl } = await uploadResponse.json();
        console.log("Uploaded Image URL:", imageUrl);

        // Vision API 호출
        const analyzeResponse = await fetch("/user/closet/analyzeImage", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ imageUrl }),
        });

        if (!analyzeResponse.ok) {
            throw new Error("이미지 분석 실패");
        }

        const { color, pattern } = await analyzeResponse.json();
        console.log("Vision API Results:", { color, pattern });

        // 분석 결과를 드롭다운에 반영
        document.getElementById("color").innerHTML = `<option value="${color}" selected>${color}</option>`;
        document.getElementById("pattern").innerHTML = `<option value="${pattern}" selected>${pattern}</option>`;
    } catch (error) {
        console.error("Error during image processing:", error);
        alert("이미지 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
}

// 파일 입력에 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', () => {
    const fileInput = document.querySelector('input[name="imageFile"]');
    if (fileInput) {
        fileInput.addEventListener('change', handleImageChange);
    }
});

// 카테고리 선택에 따라 옵션 동적 변경
document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded and parsed");

    const categorySelect = document.getElementById("category");
    const subcategorySelect = document.getElementById("detail");
    const fitSelect = document.getElementById("fit");

    // 카테고리 선택 이벤트
    categorySelect.addEventListener("change", function () {
        const category = this.value;

        subcategorySelect.innerHTML = ""; // 서브 카테고리 초기화
        fitSelect.innerHTML = ""; // 핏 옵션 초기화

        let subcategoryOptions = [];
        let fitOptions = [];

        // 카테고리별 옵션 설정
        switch (category) {
            case "상의":
                subcategoryOptions = [
                    "맨투맨/스웨트", "셔츠/블라우스", "후드 티셔츠", "니트/스웨터", "카라티셔츠",
                    "긴소매 티셔츠", "반소매 티셔츠", "민소매 티셔츠", "기타 상의",
                ];
                fitOptions = ["슬림 핏", "레귤러 핏", "오버 핏"];
                break;
            case "하의":
                subcategoryOptions = [
                    "데님팬츠", "코튼팬츠", "슈트팬츠/슬랙스", "트레이닝/조거 팬츠", "숏 팬츠",
                    "레깅스", "점프 슈트/오버올", "기타 하의",
                ];
                fitOptions = ["레귤러핏", "슬림 핏", "와이드핏", "스트레이트핏", "스키니핏", "크롭핏", "베기핏", "루즈핏", "오버 핏"];
                break;
            case "아우터":
                subcategoryOptions = [
                    "후드집업", "블루종/MA-1", "레더/라이더스 재킷", "무스탕/퍼", "트러커 재킷",
                    "슈트/블레이저재킷", "가디건", "아노락 재킷", "후리스", "트레이닝 재킷",
                    "스타디움 재킷", "환절기 코드", "겨울 코트", "롱패딩", "숏패딩", "패딩 베스트", "베스트", "기타 아우터",
                ];
                fitOptions = ["없음"];
                break;
            case "드레스":
                subcategoryOptions = [
                    "미니 원피스", "미디 원피스", "맥시 원피스", "미니 스커트", "미디 스커트",
                    "롱 스커트", "기타 원피스",
                ];
                fitOptions = ["슬림 핏", "플레어 핏"];
                break;
            case "신발":
                subcategoryOptions = [
                    "스니커즈", "구두", "샌들/슬리퍼", "부츠/워커", "스포츠화",
                    "패딩/퍼신발", "기타 신발",
                ];
                fitOptions = ["사이즈 선택 없음"];
                break;
            default:
                subcategoryOptions = [];
                fitOptions = [];
        }

        // 서브 카테고리 추가
        subcategoryOptions.forEach((option) => {
            const optionElement = document.createElement("option");
            optionElement.value = option;
            optionElement.textContent = option;
            subcategorySelect.appendChild(optionElement);
        });

        // 핏 옵션 추가
        fitOptions.forEach((option) => {
            const optionElement = document.createElement("option");
            optionElement.value = option;
            optionElement.textContent = option;
            fitSelect.appendChild(optionElement);
        });
    });
});
</script>
