<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>]
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
document.addEventListener('DOMContentLoaded', () => {
    console.log("DOM fully loaded and parsed");

    // 폼 유효성 검사
    function validateForm(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        const form = document.forms["add_clothing_form"];
        const requiredFields = {
            "itemName": "옷 이름",
            "category": "카테고리",
            "detail": "세부 카테고리",
            "color": "색상",
            "imageFile": "이미지 파일",
            "fit": "핏",
            "pattern": "패턴"
        };
        const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

        // 필수 입력 확인
        for (let field in requiredFields) {
            if (form[field].value.trim() === "") {
                alert(`${requiredFields[field]}를 입력해주세요.`);
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

    // 이미지 미리보기 및 API 연동
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
     	// 이미지 미리보기
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

        // API 호출
        let formData = new FormData();
        formData.append("imageFile", file);

        fetch("/user/closet/analyzeImage", {
            method: "POST",
            body: formData
        })
        .then(response => {
	    if (!response.ok) throw new Error("Network response was not ok");
	    return response.json();
	})
	.then(data => {
	    console.log("API 응답 데이터:", data); // 콘솔에 데이터 출력 (확인용)
	    document.getElementById("color").innerHTML = `<option value="${data.color}" selected>${data.color}</option>`;
	    document.getElementById("pattern").innerHTML = `<option value="${data.pattern}" selected>${data.pattern}</option>`;
	})
	.catch(error => {
        console.error("Error during analysis:", error);
        alert("이미지 분석 중 오류가 발생했습니다. 다시 시도해주세요.");
    });
}
	
    // 파일 입력 이벤트
    const fileInput = document.getElementById("imageFile");
    if (fileInput) {
        fileInput.addEventListener("change", handleImageChange);
    } else {
        console.error("File input element with id 'imageFile' not found.");
    }

    // 카테고리 선택 이벤트
    const categorySelect = document.getElementById('category');
    const subcategorySelect = document.getElementById('detail');
    const fitSelect = document.getElementById('fit');

    if (categorySelect && subcategorySelect && fitSelect) {
        categorySelect.addEventListener('change', function () {
            const category = this.value;
            subcategorySelect.innerHTML = ''; // 초기화
            fitSelect.innerHTML = ''; // 초기화

            const options = {
                "상의": {
                    subcategory: ['맨투맨/스웨트', '셔츠/블라우스', '후드 티셔츠', '니트/스웨터', '카라티셔츠', '긴소매 티셔츠', '반소매 티셔츠', '민소매 티셔츠', '기타 상의'],
                    fit: ['슬림 핏', '레귤러 핏', '오버 핏']
                },
                "하의": {
                    subcategory: ['데님팬츠', '코튼팬츠', '슈트팬츠/슬랙스', '트레이닝/조거 팬츠', '숏 팬츠', '레깅스', '점프 슈트/오버올', '기타 하의'],
                    fit: ['레귤러핏', '슬림 핏', '와이드핏', '스트레이트핏', '스키니핏', '크롭핏', '베기핏', '루즈핏', '오버 핏']
                },
                "아우터": {
                    subcategory: ['후드집업', '블루종/MA-1', '레더/라이더스 재킷', '무스탕/퍼', '트러커 재킷', '슈트/블레이저재킷', '가디건', '아노락 재킷', '후리스', '트레이닝 재킷', '스타디움 재킷', '환절기 코드', '겨울 코트', '롱패딩', '숏패딩', '패딩 베스트', '베스트', '기타 아우터'],
                    fit: ['없음']
                },
                "드레스": {
                    subcategory: ['미니 원피스', '미디 원피스', '맥시 원피스', '미니 스커트', '미디 스커트', '롱 스커트', '기타 원피스'],
                    fit: ['슬림 핏', '플레어 핏']
                },
                "신발": {
                    subcategory: ['스니커즈', '구두', '샌들/슬리퍼', '부츠/워커', '스포츠화', '패딩/퍼신발', '기타 신발'],
                    fit: ['사이즈 선택 없음']
                }
            };

            const selectedOptions = options[category] || { subcategory: [], fit: [] };
            selectedOptions.subcategory.forEach(option => {
                const opt = document.createElement('option');
                opt.value = option;
                opt.textContent = option;
                subcategorySelect.appendChild(opt);
            });
            selectedOptions.fit.forEach(option => {
                const opt = document.createElement('option');
                opt.value = option;
                opt.textContent = option;
                fitSelect.appendChild(opt);
            });
        });
    }
});

</script>
