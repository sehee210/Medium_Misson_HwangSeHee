### 미디엄 클론코딩 미션 - first

---

### 필수미션 1 : 회원기능

#### 엔드 포인트
가입
- [x] GET /member/join : 가입 폼
- [x] POST /member/join : 가입 폼 처리


로그인
- [x] GET /member/login : 로그인 폼
- [x] POST /member/login : 로그인 폼 처리

로그아웃
- [x] POST /member/logout : 로그아웃

<br>

#### 폼
회원가입 폼
- [x] username
- [x] password
- [x] passwordConfirm

로그인 폼
- [x] username
- [x] password

<br>
---

### 필수미션 2 : 글 CRUD

#### 엔드 포인트
홈
- [x] GET / : 홈
    - [x] 최신글 30개 노출

글 목록 조회
- [x] GET /post/list : 전체 글 리스트
    - [x] 공개된 글만 노출

내 글 목록 조회
- [x] GET /post/myList : 내 글 리스트

글 상세내용 조회
- [x] GET /post/1 : 1번 글 상세보기

글 작성
- [x] GET /post/write : 글 작성 폼
- [x] POST /post/write : 글 작성 처리

글 수정
- [x] GET /post/1/modify : 1번 글 수정 폼
- [x] PUT /post/1/modify : 1번 글 수정 폼 처리

글 삭제
- [x] DELETE /post/1/delete : 1번 글 삭제

특정 회원의 글 모아보기
- [x] GET /b/user1 : 회원 user1 의 전체 글 리스트
- [x] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기

### 폼
글 쓰기 폼
- [x] title
- [x] body
- [x] isPublished
    - [x] 체크박스
    - [x] value="true"

글 수정 폼
- [x] title
- [x] body
- [x] isPublished
    - [x] 체크박스
    - [x] value="true"

---

### 선택미션 1 : 조회수

#### 조회수 증가
- [ ] POST /post/5/increaseHit : 5번글에 대한 조회수 증가
  - [x] GET 방식으로 구현

---

### 선택미션 2 : 추천

#### 추천
- [x] POST /post/5/like : 5번글을 추천
  - [x] GET 방식으로 구현
- [ ] DELETE /post/5/canCellike : 5번글을 추천취소
  - [x] GET 방식으로 구현

---

### 선택미션 3 : 댓글
댓글 목록
- [x] 글 상세페이지 하단 : 5번글에 대한 댓글 목록

댓글 작성
- [x] 글 상세페이지 하단 : 5번글에 대한 댓글 작성 폼
- [x] POST /post/5/comment/write : 5번글에 대한 댓글 작성 폼 처리

댓글 수정
- [x] GET /post/5/comment/6/modify : 5번글에 대한 6번 댓글 수점 폼
- [x] POST /post/5/comment/6/modify : 5번글에 대한 6번 댓글 수점 폼 처리

댓글 삭제
- [x] DELETE /post/5/comment/6/delete : 5번글에 대한 6번 댓글 삭제


### 폼
댓글 작성 폼
- [x] body

댓글 수정 폼
- [x] body