<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	function createAccountForm() {
		console.log('createAccountForm() CALLED!!');
		
		let form = document.create_account_form;
		
		if (form.username.value == '') {
			alert('아이디를 입력하세요.');
			form.username.focus();
			
		} else if (form.password.value == '') {
			alert('비밀번호를 입력하세요.');
			form.password.focus();
			
		} else if (form.password_again.value == '') {
			alert('비밀번호를 한번 더 입력하세요.');
			form.password_again.focus();
			
		} else if (form.password.value != form.password_again.value) {
			alert('비밀번호가 일치하지 않습니다. 다시 확인하세요.');
			form.password.focus();
			
		} else if (form.name.value == '') {
			alert('이름을 입력하세요.');
			form.name.focus();
			
		} else if (form.gender.value == '') {
			alert('성별을 선택하세요.');
			form.gender.focus();
			
		} else if (form.email.value == '') {
			alert('이메일을 입력하세요.');
			form.email.focus();
			
		} else if (form.phone.value == '') {
			alert('전화번호를 입력하세요.');
			form.phone.focus();
			
		} else {
			form.submit();
		}
		
	}

</script>
