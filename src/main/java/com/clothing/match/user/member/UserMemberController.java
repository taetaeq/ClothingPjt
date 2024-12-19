package com.clothing.match.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {

	@Autowired
	UserMemberService userMemberService;

	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[UserMemberController] createAccountForm()");

		String nextPage = "user/member/create_account_form";

		return nextPage;
	}

	/*
	 * 회원 가입 확인
	 */
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberController] createAccountConfirm()");

		String nextPage = "user/member/create_account_ok";

		int result = userMemberService.createAccountConfirm(userMemberVo);

		if (result <= 0)
			nextPage = "user/member/create_account_ng";

		return nextPage;
	}

	/*
	 * 로그인
	 */
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[UserMemberController] loginForm()");

		String nextPage = "user/member/login_form";

		return nextPage;

	}

	/*
	 * 로그인 확인
	 */
	@PostMapping("/loginConfirm")
	public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) {
		System.out.println("[UserMemberController] loginConfirm()");

		String nextPage = "user/member/login_ok";

		UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo);

		if (loginedUserMemberVo == null) {
			nextPage = "user/member/login_ng";

		} else {
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);

		}

		return nextPage;
	}

	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserMemberController] logoutConfirm()");

		String nextPage = "redirect:/user";

		session.removeAttribute("loginedUserMemberVo");
		session.invalidate();

		return nextPage;

	}

	/*
	 * 계정 수정
	 */
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountForm()");

		String nextPage = "user/member/modify_account_form";

		UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
		if (loginedUserMemberVo == null)
			nextPage = "redirect:/user/member/loginForm";

		return nextPage;

	}

	/*
	 * 회원 정보 수정 확인
	 */
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountConfirm()");

		String nextPage = "user/member/modify_account_ok";

		int result = userMemberService.modifyAccountConfirm(userMemberVo);

		if (result > 0) {
			UserMemberVo loginedUserMemberVo = userMemberService.getLoginedUserMemberVo(userMemberVo.getUserId());


			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);

		} else {
			nextPage = "user/member/modify_account_ng";

		}

		return nextPage;
	}
	
	// 마이페이지
	@GetMapping("/myPage")
    public String myPage(HttpSession session) {
        System.out.println("[UserMemberController] myPage()");

        String nextPage = "user/member/mypage";  // 마이페이지로 이동

        // 로그인 확인
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo == null) {
            System.out.println("[UserMemberController] 로그인 정보 없음, 로그인 페이지로 리다이렉트");
            nextPage = "redirect:/user/member/loginForm";
        }

        return nextPage;
    }
    
	
	// 개인상세정보 입력 폼
    @GetMapping("/myDetailForm")
    public String myDetailForm(HttpSession session) {
        System.out.println("[UserMemberController] myDetailForm()");

        String nextPage = "user/member/my_detail_form";  // 개인 상세 정보 입력 폼

        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        }

        return nextPage;
    }
    
    @PostMapping("/saveDetail")
    public String saveDetail(UserMemberVo userMemberVo, HttpSession session) {
        System.out.println("[UserMemberController] saveDetail()");

        String nextPage = "user/member/my_detail_ok";
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "user/member/login_form"; // 로그인되지 않은 경우 로그인 페이지로 이동
        } else {
            // 로그인된 사용자의 userId를 설정
            userMemberVo.setUserId(loginedUserMemberVo.getUserId());
            int result = userMemberService.saveUserDetail(userMemberVo);

            if (result <= 0) {
                nextPage = "user/member/my_detail_ng"; // 저장 실패 시
            }else {
                // 상세정보 저장 후 최신 정보를 세션에 반영
                loginedUserMemberVo.setHeight(userMemberVo.getHeight()); // 최신 height 저장
                loginedUserMemberVo.setWeight(userMemberVo.getWeight()); // 최신 weight 저장
                session.setAttribute("loginedUserMemberVo", loginedUserMemberVo); // 세션 업데이트
            }
        }

        return nextPage;
    }
    
    // 상세정보 보기
    @GetMapping("/viewDetail")
    public String viewDetail(HttpSession session, Model model) {
        System.out.println("[UserMemberController] viewDetail()");
        
        String nextPage = "user/member/view_detail";  // 개인 상세 정보 조회 페이지

        // 로그인 상태 확인
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        } else {
            // 로그인된 사용자의 ID를 사용하여 상세 정보를 조회
            try {
                UserMemberVo userMemberVo = userMemberService.getUserDetail(loginedUserMemberVo.getUserId());
                if (userMemberVo != null) {
                	// BMI 계산
                    userMemberVo.calculateBmi();  // BMI 계산 메서드 호출
                	
                	session.setAttribute("userMemberVo", userMemberVo);  // 세션에 저장하여 마이페이지에서 사용할 수 있도록 설정
                } else {
                    model.addAttribute("message", "사용자 상세 정보를 찾을 수 없습니다.");
                }
            } catch (Exception e) {
                model.addAttribute("message", "상세 정보 조회에 실패했습니다. 다시 시도해주세요.");
                e.printStackTrace();
            }
        }

        return nextPage;
    }

}
