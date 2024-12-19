package com.clothing.match.user.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMemberVo {
	private int userId;          // 고유 사용자 ID
    private String username;     // 사용자 아이디
    private String password;     // 비밀번호
    private String name;         // 사용자 이름
    private String email;        // 이메일
    private String gender;         // 성별 ('M' 또는 'F')
    private String phone;        // 전화번호
    
    private int height;		//키
	private int weight;		//몸무게
	private double bmi;       // BMI 값
	
	// BMI 계산 메서드 추가
    public void calculateBmi() {
        if (height > 0 && weight > 0) {
            double heightInMeters = height / 100.0; // 키를 미터 단위로 변환
            this.bmi = weight / (heightInMeters * heightInMeters); // BMI 계산 공식
            
         // 소수점 첫째 자리까지 반올림
            this.bmi = Math.round(this.bmi * 10.0) / 10.0;
        }
    }
}
