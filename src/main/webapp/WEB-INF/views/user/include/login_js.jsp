<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	function loginForm() {
		console.log('loginForm() CALLED!!');
		
		let form = document.login_form;
		
		if (form.username.value == '') {
			alert('아이디를 입력해주세요.');
			 form.username.focus();
			
		} else if (form.password.value == '') {
			alert('비밀번호를 입력해주세요.');
			form.password.focus();
			
		} else {
			form.submit();
			
		}
		
	}

</script>
