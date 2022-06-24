# 인수 테스트 기반 TDD

## 1단계 - 인수 테스트 기반 리팩터링
### 필요기능
- [x] LineService 리팩터링
  - [x] Domain 으로 옮길 로직을 찾기
  - [x] Domain 의 단위 테스트를 작성하기
  - [x] 기존 로직을 지우지 말고 새로운 로직을 만들어 수행
  - [x] 정상 동작 확인 후 기존 로직 제거
- [x] LineSectionAcceptanceTest 리팩터링

## 2단계 - 경로 조회 기능
### 필요기능
- [x] 코드리뷰 피드백 반영
- [x] 최단 경로 조회 인수 테스트 만들기
- [x] 최단 경로 조회 기능 구현하기
  - [x] jgrapht 라이브러리 적용
  - [x] Station 과 Section 정보를 정점(vertext)과 간선(edge), 그리고 가중치 개념으로 이용