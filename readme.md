# QnA

## 1단계 - 엔티티 매핑

### 요구사항
QnA 서비스를 만들어 가면서 JPA로 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑하는지 알아본다.
- DDL을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- @DataJpaTest 를 사용하여 학습 테스트를 해 본다.

### 구현할 기능 목록
- 엔티티 매핑
  - [x] answer
  - [x] delete_history
  - [x] question
  - [x] user
- 리포지토리 테스트
  - [x] answer
  - [x] delete_history
  - [x] question
  - [x] user

## 2단계 - 연관 관계 매핑

### 요구사항
QnA 서비스를 만들어가면서 JPA 로 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

## 3단계 - 질문 삭제하기 리팩터링

### 요구사항
Qna 서비스를 만들어가면서 JPA 로 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

### 구현할 기능 목록
- [ ] 질문 데이터 삭제
  - [ ] 데이터의 상태를 삭제 상태로 변경
  - [ ] 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능
  - [ ] 답변이 없는 경우 삭제 가능
  - [ ] 질문자와 답변 글의 모든 답변자가 같은 경우 삭제 가능
  - [ ] 질문자와 답변자가 다른 경우 답변 삭제 불가능
  - [ ] 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태를 변경해야함
  - [ ] 질문과 답변 삭제시 이력을 DeleteHistory 에 저장
