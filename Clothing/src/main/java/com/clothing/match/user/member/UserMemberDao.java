package com.clothing.match.user.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemberDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isUserMember(String username) {
	    System.out.println("[UserMemberDao] isUserMember()");
	    
	    String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
	    
	    int result = jdbcTemplate.queryForObject(sql, Integer.class, username);
	    
	    return result > 0 ? true : false;
	}
	
	public int insertUserAccount(UserMemberVo userMemberVo) {
	      System.out.println("[UserMemberDao] insertUserAccount()");
	      
	      String sql = "INSERT INTO user(username, password, name, gender, email, phone) " 
	      + "VALUES(?, ?, ?, ?, ?, ?)";

	    int result = -1;
	    
	    try {
	        result = jdbcTemplate.update(sql,
	        		userMemberVo.getUsername(),                                // 사용자 아이디
	                passwordEncoder.encode(userMemberVo.getPassword()),       // 암호화된 비밀번호
	                userMemberVo.getName(),                                   // 이름
	                userMemberVo.getGender(),                                 // 성별
	                userMemberVo.getEmail(),                                  // 이메일
	                userMemberVo.getPhone());                                 // 전화번호
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public UserMemberVo selectUser(UserMemberVo userMemberVo) {
	      System.out.println("[UserMemberDao] selectUser()");
	      
	      String sql = "SELECT * FROM user WHERE username = ?"; 
	      
	      List<UserMemberVo> userMemberVos = 
		            new ArrayList<UserMemberVo>();
	      
	      try {
	          RowMapper<UserMemberVo> rowMapper = 
		  BeanPropertyRowMapper.newInstance(UserMemberVo.class);
	          userMemberVos = jdbcTemplate.query(sql, rowMapper, 
	        		  userMemberVo.getUsername());
	    
	          if (!passwordEncoder.matches(
        		  userMemberVo.getPassword(), 
        		  userMemberVos.get(0).getPassword())) 
	              userMemberVos.clear();

	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return userMemberVos.size() > 0 ? 
		            userMemberVos.get(0) : null;
      }
	
	public int updateUserAccount(UserMemberVo userMemberVo) {
	      System.out.println("[UserMemberDao] updateUserAccount()");
	      
	      String sql = "UPDATE user SET "
	    		  + "password = ?, "   // 비밀번호도 업데이트
	              + "name = ?, "
	              + "gender = ?, "
	              + "email = ?, "
	              + "phone = ? "
	              + "WHERE user_id = ?";

	   int result = -1;

	   try {
	       result = jdbcTemplate.update(sql,
    		   				passwordEncoder.encode(userMemberVo.getPassword()),  // 암호화된 비밀번호
	                           userMemberVo.getName(), 
	                           userMemberVo.getGender(), 
	                           userMemberVo.getEmail(), 
	                           userMemberVo.getPhone(), 
	                           userMemberVo.getUserId());
	   } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	            
	      return result;
	}
	
	public UserMemberVo selectUser(int userId) {
	    System.out.println("[UserMemberDao] selectUser()");

	    String sql = "SELECT * FROM user WHERE user_id = ?";

	    List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();

	    try {
	        RowMapper<UserMemberVo> rowMapper = 
	        BeanPropertyRowMapper.newInstance(UserMemberVo.class);
	        userMemberVos = jdbcTemplate.query(sql, rowMapper, userId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
	}
	// 상세정보 입력
	public int insertUserDetail(UserMemberVo userMemberVo) {
	    System.out.println("[UserMemberDao] insertUserDetail()");

	    String sql = "INSERT INTO user_detail(user_id, height, weight) VALUES(?, ?, ?)";

	    int result = -1;

	    try {
	        result = jdbcTemplate.update(sql,
	                userMemberVo.getUserId(),   // 로그인된 사용자의 user_id
	                userMemberVo.getHeight(),   // height (키)
	                userMemberVo.getWeight());  // weight (몸무게)
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	// 상세정보 업데이트
	public int updateUserDetail(UserMemberVo userMemberVo) {
	    System.out.println("[UserMemberDao] updateUserDetail()");

	    String sql = "UPDATE user_detail SET height = ?, weight = ? WHERE user_id = ?";

	    int result = -1;

	    try {
	        result = jdbcTemplate.update(sql,
	                userMemberVo.getHeight(),   // height (키)
	                userMemberVo.getWeight(),   // weight (몸무게)
	                userMemberVo.getUserId());  // 로그인된 사용자의 user_id
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	
	public UserMemberVo selectUserDetail(int userId) {
	    System.out.println("[UserMemberDao] selectUserDetail()");

	    String sql = "SELECT u.user_id, u.username, u.name, u.email, u.phone, u.gender, ud.height, ud.weight "
	               + "FROM user u "
	               + "INNER JOIN user_detail ud ON u.user_id = ud.user_id "
	               + "WHERE u.user_id = ?";
	    
	    UserMemberVo userMemberVo = null;  // 단일 객체를 반환하기 위해 초기화

	    try {
	        RowMapper<UserMemberVo> rowMapper = BeanPropertyRowMapper.newInstance(UserMemberVo.class);
	        userMemberVo = jdbcTemplate.queryForObject(sql, rowMapper, userId);  // 단일 결과 반환
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userMemberVo;  // null일 경우에도 null을 반환
	}
	
	// 아이디와 이메일로 사용자 확인
    public UserMemberVo findUserByUsernameAndEmail(String username, String email) {
        System.out.println("[UserMemberDao] findUserByUsernameAndEmail()");

        String sql = "SELECT * FROM user WHERE username = ? AND email = ?";
        UserMemberVo user = null;

        try {
            RowMapper<UserMemberVo> rowMapper = BeanPropertyRowMapper.newInstance(UserMemberVo.class);
            user = jdbcTemplate.queryForObject(sql, rowMapper, username, email);
        } catch (Exception e) {
            System.out.println("사용자를 찾을 수 없습니다: " + e.getMessage());
        }

        return user;
    }

    // 비밀번호 업데이트 (username 기반)
    public int updateUserPasswordByUsername(String username, String encryptedPassword) {
        System.out.println("[UserMemberDao] updateUserPasswordByUsername()");

        String sql = "UPDATE user SET password = ? WHERE username = ?";
        int result = -1;

        try {
            result = jdbcTemplate.update(sql, encryptedPassword, username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }




}
