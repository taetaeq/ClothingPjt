<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

function findPasswordForm() {
    console.log('findPasswordForm() CALLED!!');
    
    let form = document.forms['find_password_form'];
    
    if (form.u_m_id.value.trim() === '') { // 'u_m_id' 확인
        alert('아이디를 입력하세요.');
        form.u_m_id.focus();
    } else if (form.u_m_mail.value.trim() === '') { // 'u_m_mail' 확인
        alert('이메일을 입력하세요.');
        form.u_m_mail.focus();
    } else {
        form.submit(); // 폼 전송
    }
}

</script>

