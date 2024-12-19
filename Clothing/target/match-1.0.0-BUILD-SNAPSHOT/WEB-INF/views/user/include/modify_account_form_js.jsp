<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function modifyAccountForm() {
		console.log('modifyAccountForm() 호출됨!!');
		
		let form = document.modify_account_form;
		
		if (form.name.value == '') {
			alert('사용자 이름을 입력하세요.');
			form.name.focus();
			
		} else if (form.gender.value == '') {
			alert('사용자 성별을 선택하세요.');
			form.gender.focus();
			
		} else if (form.email.value == '') {
			alert('사용자 이메일을 입력하세요.');
			form.email.focus();
			
		} else if (form.phone.value == '') {
			alert('사용자 전화번호를 입력하세요.');
			form.phone.focus();
			
		} else {
			form.submit();
		}
		
	}

</script>
