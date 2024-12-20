package com.clothing.match.user.member;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {

	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	UserMemberDao userMemberDao;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	public int createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] createAccountConfirm()");

		// 이미 회원인지 확인
		boolean isMember = userMemberDao.isUserMember(userMemberVo.getUsername());

		if (!isMember) {
			int result = userMemberDao.insertUserAccount(userMemberVo);

			if (result > 0)
				return USER_ACCOUNT_CREATE_SUCCESS;

			else
				return USER_ACCOUNT_CREATE_FAIL;

		} else {
			return USER_ACCOUNT_ALREADY_EXIST;

		}
	}

	public UserMemberVo loginConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] loginConfirm()");

		UserMemberVo loginedUserMemberVo = userMemberDao.selectUser(userMemberVo);
		if (loginedUserMemberVo != null)
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		else
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");

		return loginedUserMemberVo;
	}

	public int modifyAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] modifyAccountConfirm()");
		
		// 비밀번호 암호화 처리
	    if (userMemberVo.getPassword() != null && !userMemberVo.getPassword().isEmpty()) {
	        //1String encryptedPassword = passwordEncoder.encode(userMemberVo.getPassword());
	        //userMemberVo.setPassword(encryptedPassword);
	    }
	    
		return userMemberDao.updateUserAccount(userMemberVo);

	}

	public UserMemberVo getLoginedUserMemberVo(int userId) {
		System.out.println("[UserMemberService] getLoggedInUser()");

		return userMemberDao.selectUser(userId);
	}
	
	
	public int saveUserDetail(UserMemberVo userMemberVo) {
	    System.out.println("[UserMemberService] saveUserDetail()");

	    // 사용자 상세 정보 업데이트 (존재하면 업데이트, 없으면 추가)
	    int result = userMemberDao.updateUserDetail(userMemberVo);

	    // 만약 기존에 상세 정보가 없으면 추가하는 로직
	    if (result <= 0) {
	        result = userMemberDao.insertUserDetail(userMemberVo);
	    }

	    if (result > 0) {
	        System.out.println("[UserMemberService] 사용자 상세 정보 저장 성공");
	    } else {
	        System.out.println("[UserMemberService] 사용자 상세 정보 저장 실패");
	    }

	    return result;
	}
    
    public UserMemberVo getUserDetail(int userId) {
        System.out.println("[UserMemberService] getUserDetail()");

        // 사용자 상세 정보를 UserDetailDao에서 조회
        return userMemberDao.selectUserDetail(userId);
    }
    
    
    // 임시 비밀번호 생성 메서드
    public String generateTemporaryPassword() {
        int length = 10; // 비밀번호 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }

        return tempPassword.toString();
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(String username, String newPassword) {
        System.out.println("[UserMemberService] updatePassword()");

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(newPassword);
        userMemberDao.updateUserPasswordByUsername(username, encryptedPassword);
    }
    
    
    public UserMemberVo getUserByIdAndEmail(String username, String email) {
        return userMemberDao.findUserByUsernameAndEmail(username, email);
    }
    
    // 이메일 전송 로직
    public void sendTemporaryPasswordEmail(String email, String tempPassword) {
        // Spring Email 혹은 Java Mail로 구현
        System.out.println("[UserMemberService] sendTemporaryPasswordEmail()");
        System.out.println("To: " + email);
        System.out.println("Temporary Password: " + tempPassword);
    }
    
    public void processFindPassword(String username, String email) throws Exception {
        System.out.println("[UserMemberService] processFindPassword()");

        // 사용자 확인
        UserMemberVo user = getUserByIdAndEmail(username, email);
        if (user == null) {
            throw new Exception("아이디와 이메일이 일치하지 않습니다.");
        }

        // 임시 비밀번호 생성
        String tempPassword = generateTemporaryPassword();
        System.out.println("Generated Temporary Password: " + tempPassword);

        // 비밀번호 업데이트
        updatePassword(username, tempPassword);

        // 이메일 전송 로직 (구현 필요)
        sendTemporaryPasswordEmail(email, tempPassword);
    }
}

