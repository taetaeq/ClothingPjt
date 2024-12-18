<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
    function confirmDelete() {
        // 삭제 확인을 위한 팝업
        var result = confirm("옷을 삭제하시겠습니까?");
        return result;  // 사용자가 확인을 누르면 true, 취소를 누르면 false
    }
</script>