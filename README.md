# springboot-template
Spring Boot Project Template

스프링 부트 프로젝트 템플릿

## 사용된 기술

- Java SDK 17
- Spring Boot 3.2.2
- H2

## 기능 (web-full-stack)
- [x] H2 연결
- [x] CRUD api [<U>Link</U>](web-full-stack/src/main/java/com/example/webfullstack/auth/controller/AuthController.java)
- [x] 동시성 테스트 [<U>Link</U>](web-full-stack/src/test/java/com/example/webfullstack/product/service/ProductServiceConcurrentTest.java)
- [x] 유닛 테스트 [<U>Link</U>](web-full-stack/src/test/java/com/example/webfullstack/auth/controller/AuthControllerUnitTest.java)
- [x] 통합 테스트 [<U>Link</U>](web-full-stack/src/test/java/com/example/webfullstack/auth/controller/AuthControllerIntegrationTest.java)
- [x] e2e 테스트 [<U>Link</U>](web-full-stack/src/test/java/com/example/webfullstack/auth/controller/AuthControllerE2ETest.java)
- [x] 로거
- [x] 커스텀 익셉션 [<U>Link</U>](web-full-stack/src/main/java/com/example/webfullstack/common/exception/CustomException.java)
- [x] 익셉션 핸들러 [<U>Link</U>](web-full-stack/src/main/java/com/example/webfullstack/common/exception/GlobalExceptionHandler.java)
- [x] jwt 기반 인증

## 기능 (web-content)
- `hello, world!`를 출력하는 기본 예제 ([참고](https://spring.io/guides/gs/serving-web-content))