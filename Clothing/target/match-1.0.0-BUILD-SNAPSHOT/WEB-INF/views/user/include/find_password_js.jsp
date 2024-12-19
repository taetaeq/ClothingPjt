<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

    function findPasswordForm() {
        console.log('findPasswordForm() CALLED!!');
        
        let form = document.find_password_form;
        
        if (form.username.value == '') {
            alert('아이디를 입력하세요.');
            form.username.focus();
            
        } else if (form.email.value == '') {
            alert('이메일을 입력하세요.');
            form.email.focus();
            
        } else {
            form.submit();
        }
    }

</script>

