<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
    // 옷장 열기/닫기 토글 함수
    function toggleCloset() {
        const closetContent = document.querySelector('.closet-content');
        
        // closet-content가 열렸는지 체크하여 열기/닫기 처리
        if (closetContent.classList.contains('open')) {
            closetContent.classList.remove('open');
        } else {
            closetContent.classList.add('open');
        }
    }
</script>

