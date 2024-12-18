<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
function filterCategory(category) {
    const sections = document.querySelectorAll(".closet-category-section");

    sections.forEach(section => {
        // data-category 속성으로 필터링
        if (category === "all" || section.dataset.category === category) {
            section.style.display = "block"; // 선택한 카테고리만 보이기
        } else {
            section.style.display = "none"; // 다른 카테고리 숨기기
        }
    });
}

</script>