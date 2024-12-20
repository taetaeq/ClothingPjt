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
async function handleImageChange(event) {
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
        const uploadResponse = await fetch("/match/user/closet/uploadTempImage", {
            method: "POST",
            body: formData,
        });

        if (!uploadResponse.ok) {
            throw new Error("이미지 업로드 실패");
        }

        const { imageUrl } = await uploadResponse.json();
        console.log("Uploaded Image URL:", imageUrl);

        // Vision API 호출
        const analyzeResponse = await fetch("/match/user/closet/analyzeImage", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ imageUrl }),
        });

        if (!analyzeResponse.ok) {
            throw new Error("이미지 분석 실패");
        }

        const responseData = await analyzeResponse.json();
        const { color } = responseData; // 구조 분해 할당을 사용하여 color 속성 추출
        console.log("Vision API Results:", color);  // '실버'가 출력됩니다.
        
     // 분석 결과를 드롭다운에 반영
        const colorSelect = document.getElementById("color");

        // 기존 내용 초기화 (기존 옵션들 지우기)
        colorSelect.innerHTML = "";

        // 새로운 옵션 추가
        const optionElement = document.createElement("option");
        optionElement.value = color; // 색상 값
        optionElement.textContent = color; // 표시될 텍스트
        optionElement.selected = true; // 기본값으로 선택

        colorSelect.appendChild(optionElement);

      
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
        fitSelect.innerHTML = "";

        let subcategoryOptions = [];
        let fitOptions = [];

     // 카테고리별 옵션 설정
        switch (category) {
            case "상의":
                subcategoryOptions = [
                    "맨투맨/스웨트", "셔츠/블라우스", "후드 티셔츠", "니트/스웨터", "카라티셔츠",
                    "긴소매 티셔츠", "반소매 티셔츠", "민소매 티셔츠", "기타 상의",
                ];
                fitOptions = [
                    "슬림 핏(몸에 딱 맞는 핏)",
                    "레귤러 핏(기본적인 표준 핏)",
                    "오버 핏(크게 입은 듯한 넉넉한 핏)",
                    "크롭 핏(허리가 드러나는 짧은 스타일)",
                    "박스 핏(넉넉하고 사각형 느낌의 핏)",
                    "드롭 숄더 핏(어깨선이 내려간 여유로운 핏)",
                    "라운드 핏(몸을 따라 자연스럽게 흐르는 핏)",
                    "기타(기타 설명)"
                ];
                break;
            case "하의":
                subcategoryOptions = [
                    "데님팬츠", "코튼팬츠", "슈트팬츠/슬랙스", "트레이닝/조거 팬츠", "숏 팬츠",
                    "레깅스", "점프 슈트/오버올", "기타 하의",
                ];
                fitOptions = [
                    "레귤러핏(기본적인 표준 핏)",
                    "슬림 핏(몸에 딱 맞는 핏)",
                    "와이드핏(넓고 여유로운 핏)",
                    "스트레이트핏(일자로 떨어지는 핏)",
                    "스키니핏(몸에 꼭 맞는 핏)",
                    "크롭핏(발목 위로 짧은 핏)",
                    "베기핏(엉덩이와 허벅지가 여유로운 핏)",
                    "루즈핏(넉넉하고 편안한 핏)",
                    "오버 핏(더 큰 사이즈로 입은 핏)",
                    "부츠컷 핏(무릎 아래로 퍼지는 핏)",
                    "테이퍼드 핏(허벅지는 여유롭고 아래로 좁아지는 핏)",
                    "조거 핏(발목에 밴드가 있는 핏)",
                    "슬릿 핏(옆부분에 트임이 있는 핏)",
                    "기타(기타 설명)"
                ];
                break;
            case "아우터":
                subcategoryOptions = [
                    "후드집업", "블루종/MA-1", "레더/라이더스 재킷", "무스탕/퍼", "트러커 재킷",
                    "슈트/블레이저재킷", "가디건", "아노락 재킷", "후리스", "트레이닝 재킷",
                    "스타디움 재킷", "환절기 코드", "겨울 코트", "롱패딩", "숏패딩", "패딩 베스트", "베스트", "기타 아우터",
                ];
                fitOptions = [
                    "라글란 핏(어깨선이 자연스럽게 내려오는 핏)",
                    "트렌치 핏(허리를 묶어 슬림한 실루엣을 강조)",
                    "코쿤 핏(몸을 감싸는 둥근 형태의 핏)",
                    "에이라인 핏(위는 좁고 아래로 넓어지는 핏)",
                    "기타(기타 설명)"
                ];
                break;
            case "드레스":
                subcategoryOptions = [
                    "미니 원피스", "미디 원피스", "맥시 원피스", "미니 스커트", "미디 스커트",
                    "롱 스커트", "기타 원피스",
                ];
                fitOptions = [
                    "슬림 핏(몸에 딱 맞는 핏)",
                    "플레어 핏(아래로 퍼지는 여성스러운 핏)",
                    "엠파이어 핏(가슴 아래부터 시작되는 핏)",
                    "머메이드 핏(무릎 아래로 퍼지는 인어 형태 핏)",
                    "오프숄더 핏(어깨를 드러내는 핏)",
                    "셔츠 핏(셔츠를 변형한 심플한 핏)",
                    "기타(기타 설명)"
                ];
                break;
            case "신발":
                subcategoryOptions = [
                    "스니커즈", "구두", "샌들/슬리퍼", "부츠/워커", "스포츠화",
                    "패딩/퍼신발", "기타 신발",
                ];
                fitOptions = [
                    "로우컷(발목 아래의 낮은 스타일)",
                    "미드컷(발목까지 오는 스타일)",
                    "하이컷(발목 위까지 올라오는 스타일)",
                    "슬림핏(발을 감싸는 슬림한 핏)",
                    "기타(기타 설명)"
                ];
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
     	console.log(fitOptions);  // fitOptions 객체의 값 확인
	     	
    	 // 핏 옵션 추가
     	fitOptions.forEach((fitOption) => {
     	    const optionElement = document.createElement("option");
     	    
     	    // "핏(설명)"에서 "핏" 부분만 DB에 저장하도록 value 설정
     	    const fitValue = fitOption.split("(")[0]; // "(" 이전의 텍스트 추출
     	    optionElement.value = fitValue.trim(); // 공백 제거 후 저장

     	    // 드롭다운에 전체 텍스트를 표시 (핏 + 설명)
     	    optionElement.textContent = fitOption;

     	    fitSelect.appendChild(optionElement);
     	});

    });
});
</script>
