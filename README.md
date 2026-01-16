# JSL-Cinema
ＪＳＬ人材開発院　Cloud連携Web開発者課程26期　個人プロジェクト

Project: JSL Cinema
"사용자에게는 최고의 감동을, 관리자에게는 효율적인 운영을." MVC 패턴 기반의 영화 예매 및 멤버십 관리 시스템

1. 프로젝트 개요 (Overview)
프로젝트명: JSL Cinema

개발 기간: 2026.01.12 ~ 2026.01.16 (5일간 집중 개발 / 총 기획 포함 6주)

개발 인원: 1인 (Full Stack)

개발 목적:

Model 2 MVC 패턴을 적용하여 유지보수가 용이한 웹 애플리케이션 구축.

복잡한 비즈니스 로직(좌석 예매, 포인트 결제, 상영 일정 관리)을 DB와 연동하여 구현.

최신 트렌드인 Glassmorphism(유리 질감) UI를 적용하여 차별화된 사용자 경험 제공.

2. 기술 스택 (Tech Stack)
🖥️ Backend
Language: Java 11

Web Server: Apache Tomcat 9.0

Database: Oracle DB 11g/18c (OJDBC8)

Framework/Lib: JSP & Servlet, JSTL, COS (File Upload), BCrypt (Security)

🎨 Frontend
Language: HTML5, CSS3, JavaScript (ES6)

Library: Bootstrap 5.3 (Grid System), jQuery (AJAX), Bootstrap Icons

Design Pattern: Glassmorphism UI (Custom CSS)

🛠️ Tools & Environment
IDE: Eclipse IDE 2023-09

VCS: Git & GitHub

DB Tool: SQL Developer

3. 시스템 아키텍처 (System Architecture)
[MVC Design Pattern]

Controller (*.do):

MovieController 하나가 모든 요청을 받아 Command Pattern으로 분기 처리.

URL 관리가 용이하며, 유지보수성을 극대화함.

Model (DAO/DTO):

DB 접근 로직(DAO)과 데이터 객체(DTO)를 철저히 분리.

Connection Pool(JNDI) 혹은 DBManager 싱글톤 패턴을 사용하여 리소스 효율성 확보.

View (JSP):

JSTL/EL을 사용하여 Java 코드를 뷰에서 제거, 디자인과 로직의 분리 실현.

4. 핵심 기능 (Key Features)
👤 사용자 (User)
영화 예매 시스템 (Reservation):

영화 선택 → 날짜/시간 선택 → 좌석 선택 → 결제(포인트)로 이어지는 원스톱 프로세스.

DB 트랜잭션: 좌석 예매와 포인트 차감이 동시에 이루어지도록 처리.

마이 티켓 (My Ticket UX 개선):

지난 관람 내역을 기본적으로 숨기고(d-none), 토글 버튼으로 조회 가능하도록 개선하여 가독성 증대.

관람 완료/예매 완료 상태에 따른 직관적인 배지(Badge) UI 제공.

멤버십 & 포인트:

회원 등급(Member~MVP)에 따른 차등 적립률 및 할인 혜택 적용.

⚙️ 관리자 (Admin)
영화 등록 (File Upload):

multipart/form-data 요청 처리 및 서버 경로 파일 업로드 구현.

개발 환경(Eclipse)과 배포 환경(Tomcat)의 경로 차이를 고려한 유연한 업로드 로직.

상영 일정 관리 (Scheduling):

영화와 상영관 정보를 조회하여 날짜별 상영 스케줄(datetime-local) 등록.

5. 트러블 슈팅 (Troubleshooting & Challenges)
면접에서 가장 중요한 "문제를 해결한 경험"입니다.

🔥 Issue 1: Eclipse 배포 경로와 파일 업로드 증발
문제: 관리자 모드에서 포스터 이미지를 업로드했으나, 서버 재시작 시 이미지가 사라지는 현상 발생.

원인: Eclipse는 src 폴더가 아닌 .metadata 내부의 가상 배포 폴더에서 서버를 구동하기 때문.

해결: 개발 단계에서는 업로드 경로를 **프로젝트의 실제 로컬 경로(Absolute Path)**로 하드코딩하여 소스 폴더에 파일이 저장되도록 조치, 데이터 영속성 확보.

🔥 Issue 2: JSTL 날짜/문자열 처리 오류
문제: JSP EL 표기법 내에서 자바 메서드(.substring()) 호출 시 이클립스 유효성 검사 오류(빨간 줄) 발생.

해결: JSTL 표준 함수 라이브러리(xmlns:fn)를 도입하여 ${fn:substring()} 문법으로 대체, 표준 준수 및 오류 해결.

🔥 Issue 3: 외부 라이브러리(JBCrypt) 빌드 패스 충돌
문제: 프로젝트 위치 이동 후 라이브러리를 찾지 못해 빌드 실패.

해결: .classpath 설정 파일을 분석하여 잘못된 경로를 제거하고, 프로젝트 내부 WEB-INF/lib 경로로 재설정하여 의존성 문제 해결.

6. 프로젝트 후기 (Conclusion)
단순한 CRUD를 넘어, 실제 영화관에서 사용되는 예매 로직을 구현하며 DB 설계의 중요성을 체감했습니다. 특히 사용자 경험(UX)을 고려한 UI 개선과 관리자 기능을 통한 데이터 제어까지, 웹 서비스의 A to Z를 경험한 뜻깊은 프로젝트였습니다.
