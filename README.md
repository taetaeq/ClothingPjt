# ClothingPjt - 옷장 관리 및 추천 웹 서비스

## 📖 프로젝트 설명
ClothingPjt는 사용자가 보유한 옷을 관리하고, 상황에 맞는 코디를 추천받을 수 있는 웹 애플리케이션입니다.  
MZ세대 사용자층을 겨냥하여 간단하고 직관적인 UI를 제공하며, 트렌디한 스타일을 반영한 추천 기능이 특징입니다.

---

## 🔑 주요 기능
1. **옷장 관리**  
   - 사용자가 보유한 옷을 옷장에 등록, 수정, 삭제 가능.  
   - 옷 종류별(모자, 상의, 하의, 신발 등)로 분류하여 직관적으로 관리.  

2. **회원 관리**  
   - 회원가입, 로그인, 로그아웃 기능.  
   - 비밀번호 찾기(임시 비밀번호 이메일 발송).  
   - 회원 정보 수정(이름, 이메일, 전화번호, 비밀번호 등).  

---

## 💻 기술 스택
### **백엔드**
- **Spring Framework**: 프로젝트의 전체 구조 및 컨트롤러, 서비스, DAO 구현.
- **MariaDB**: 사용자 및 옷장 데이터 관리.
- **JDBC Template**: 데이터베이스 접근 및 쿼리 실행.
- **Spring Security**: 비밀번호 암호화 및 인증 관리.

### **프론트엔드**
- **JSP/HTML**: 웹 페이지 렌더링.
- **CSS**: 사용자 친화적인 UI/UX 디자인.
- **JavaScript**: 동적 UI 및 클라이언트 로직.

### **도구**
- **Git/GitHub**: 버전 관리 및 협업.
- **Spring Tools Suite (STS)**: 개발 환경.

---

## 🛠 설치 및 실행 방법
### 1️⃣ 프로젝트 클론
```bash
git clone https://github.com/taetaeq/ClothingPjt.git
cd ClothingPjt

ClothingPjt/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.clothing.match/
│   │   │   │   ├── user/
│   │   │   │   │   ├── controller/   # UserMemberController
│   │   │   │   │   ├── service/      # UserMemberService
│   │   │   │   │   ├── dao/          # UserMemberDao
│   │   │   │   │   ├── vo/           # UserMemberVo
│   │   │   │   ├── closet/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── dao/
│   │   │   │   │   ├── vo/
│   │   │   ├── ClothingApplication.java
│   │   ├── resources/
│   │   │   ├── static/               # CSS, JS, 이미지 파일
│   │   │   ├── templates/            # JSP 파일
│   │   ├── test/
│   │   │   ├── java/                 # 테스트 클래스
│   │   │   ├── resources/
├── pom.xml


CREATE  DATABASE clothing;
USE clothing;

###########################
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,   -- 고유 사용자 ID
    username VARCHAR(50) NOT NULL UNIQUE,     -- 사용자 아이디
    password VARCHAR(255) NOT NULL,           -- 비밀번호
    name VARCHAR(100),                        -- 사용자 이름
    email VARCHAR(100) NOT NULL UNIQUE,       -- 이메일
    gender CHAR(1) NOT NULL,                  -- 성별 (M: 남성, F: 여성)
    phone VARCHAR(15)                         -- 전화번호
);

SELECT * FROM user;

#########################################
DROP TABLE user_detail;
CREATE TABLE user_detail (
    user_id INT NOT NULL,                           -- 사용자 ID (Foreign Key)
    height INT,                            -- 키 (cm)
    weight INT,                            -- 체중 (kg)
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

SELECT * FROM user_detail;

#########################################
DROP TABLE closet;

CREATE TABLE closet (
    closet_id INT AUTO_INCREMENT PRIMARY KEY,  -- 옷장 고유 ID
    user_id INT,                               -- 사용자 ID
    item_name VARCHAR(255) NOT NULL,            -- 옷 이름
    category VARCHAR(100),                     -- 옷 카테고리 (예: 상의, 하의, 아우터)
    detail VARCHAR(100),                       -- 옷 세부 항목 (예: 티셔츠, 반팔, 청바지 등)
    color VARCHAR(50),                         -- 색상
    image_url VARCHAR(255),                    -- 이미지 URL
    fit VARCHAR(50),                           -- 핏 (예: 슬림핏, 루즈핏 등)
    pattern VARCHAR(100),                      -- 패턴/무늬 (예: 스트라이프, 체크 등)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 등록일 (기본값으로 현재 시간)
    FOREIGN KEY (user_id) REFERENCES user(user_id) -- 사용자와 연결
);

DELETE FROM closet
WHERE item_name = "테스트";


SELECT * FROM closet;

######################################
CREATE TABLE recommended_closet (
    recommended_closet_id INT AUTO_INCREMENT PRIMARY KEY,   -- 추천 옷장 고유 ID
    user_id INT,                                            -- 사용자 ID (user 테이블과 연결)
    item_name VARCHAR(255) NOT NULL,                         -- 옷 이름
    category VARCHAR(100),                                   -- 옷 카테고리
    color VARCHAR(50),                                       -- 색상
    image_url VARCHAR(255),                                  -- 이미지 URL
    fit VARCHAR(50),                                         -- 핏 (예: 슬림핏, 루즈핏 등)
    pattern VARCHAR(100),                                    -- 패턴/무늬 (예: 스트라이프, 체크 등)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,          -- 등록일 (기본값으로 현재 시간)
    FOREIGN KEY (user_id) REFERENCES user(user_id)          -- 사용자와 연결
);

SELECT * FROM recommended_closet;

######################################

CREATE TABLE bookmark (
    bookmark_id INT AUTO_INCREMENT PRIMARY KEY,  -- 북마크 고유 ID
    user_id INT,                                 -- 사용자 ID
    closet_ids VARCHAR(255) NOT NULL,            -- 추천받은 옷 ID 리스트 (콤마로 구분)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 북마크 생성 시간
    FOREIGN KEY (user_id) REFERENCES user(user_id) -- 사용자와 연결
);

SELECT * FROM bookmark;

