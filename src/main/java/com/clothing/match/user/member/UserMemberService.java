package com.clothing.match.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {

	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	UserMemberDao userMemberDao;

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
}
