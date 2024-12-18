<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
   function myDetailForm() {
		console.log('myDetailForm() CALLED!!');
	
	let form = document.my_detail_form;
	
	// 키가 비어있다면 경고 메시지
	if(form.height.value == '') {
		alert('키를 입력하세요.');
		form.height.focus();
	} 
	// 몸무게가 비어있다면 경고 메시지
	else if(form.weight.value == '') {
		alert('몸무게를 입력하세요.');
		form.weight.focus();
	} 
	// 입력이 모두 완료되었으면 폼을 제출
	else {
		form.submit();
	}
  }
</script>
