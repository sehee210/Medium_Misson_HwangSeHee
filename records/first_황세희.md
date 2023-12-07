### 미디엄 클론코딩 미션 - first

---

### 필수미션 1 : 회원기능

#### 엔드 포인트
가입
- [ ] GET /member/join : 가입 폼
- [ ] POST /member/join : 가입 폼 처리


로그인
- [ ] GET /member/login : 로그인 폼
- [ ] POST /member/login : 로그인 폼 처리

로그아웃
- [ ] POST /member/logout : 로그아웃

<br>

#### 폼
회원가입 폼
- [ ] username
- [ ] password
- [ ] passwordConfirm

로그인 폼
- [ ] username
- [ ] password

<br>
---

### 필수미션 2 : 글 CRUD

#### 엔드 포인트
홈
- [ ] GET / : 홈
    - [ ] 최신글 30개 노출

글 목록 조회
- [ ] GET /post/list : 전체 글 리스트
    - [ ] 공개된 글만 노출

내 글 목록 조회
- [ ] GET /post/myList : 내 글 리스트

글 상세내용 조회
- [ ] GET /post/1 : 1번 글 상세보기

글 작성
- [ ] GET /post/write : 글 작성 폼
- [ ] POST /post/write : 글 작성 처리

글 수정
- [ ] GET /post/1/modify : 1번 글 수정 폼
- [ ] PUT /post/1/modify : 1번 글 수정 폼 처리

글 삭제
- [ ] DELETE /post/1/delete : 1번 글 삭제

특정 회원의 글 모아보기
- [ ] GET /b/user1 : 회원 user1 의 전체 글 리스트
- [ ] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기

### 폼
글 쓰기 폼
- [ ] title
- [ ] body
- [ ] isPublished
    - [ ] 체크박스
    - [ ] value="true"

글 수정 폼
- [ ] title
- [ ] body
- [ ] isPublished
    - [ ] 체크박스
    - [ ] value="true"