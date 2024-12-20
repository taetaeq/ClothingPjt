# ClothingPjt - 옷장 관리 및 추천 웹 서비스

## 📖 프로젝트 설명
ClothingPjt는 사용자가 보유한 옷을 관리하고, 상황에 맞는 코디를 추천받을 수 있는 웹 애플리케이션입니다.  
MZ세대 사용자층을 겨냥하여 간단하고 직관적인 UI를 제공하며, 트렌디한 스타일을 반영한 추천 기능이 특징입니다.

---

## 🔑 주요 기능
1. **옷장 관리**  
   - 사용자가 보유한 옷을 옷장에 등록, 수정, 삭제 가능.  
   - 옷 종류별(모자, 상의, 하의, 신발 등)로 분류하여 직관적으로 관리.  

2. **옷 추천**  
   - 사용자의 옷장 데이터를 활용한 추천.  
   - 외부 데이터(API)를 기반으로 새로운 옷 추천.  
   - 사용자 옷장과 외부 데이터를 혼합한 코디 추천.  

3. **회원 관리**  
   - 회원가입, 로그인, 로그아웃 기능.  
   - 비밀번호 찾기(임시 비밀번호 이메일 발송).  
   - 회원 정보 수정(이름, 이메일, 전화번호, 비밀번호 등).  

4. **기타**
   - 반응형 UI를 지원하여 다양한 디바이스에서 최적화된 화면 제공.
   - 옷장 열림/닫힘 애니메이션 등 동적 UI 제공.

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
- **Postman**: API 테스트.

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
