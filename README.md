### 프로젝트 소개
파일 확장자 차단 관리 서버입니다.

<br>

배포 주소 <br>
http://ec2-15-165-160-203.ap-northeast-2.compute.amazonaws.com:8080/

Swagger 주소 <br>
http://ec2-15-165-160-203.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html


<br>

### 기술 스택

- 백엔드: Java 17, Spring Boot, Spring MVC, Spring Data JPA
- 데이터베이스: MySQL
- 프론트엔드 Thymeleaf, HTLML, CSS
- 배포: Docker, EC2

<br>

### 기능

- 고정 확장자를 uncheck/check 하여 차단 여부를 결정할 수 있으며, 변경 시 DB에 저장됩니다.
- 커스텀  확장자를 추가/삭제 할 수 있으며, DB에 저장됩니다.
    - 최대 200개 까지 등록 가능합니다.
    - 확장자 이름은 20자로 제한되며, 영문, 숫자, -, _ 만 입력 가능합니다.
    - 모든 확장자는 소문자로 저장됩니다. 
    - 이미 존재하는 확장자와 중복될 수 없습니다.


<br>

### 동시성 고려 사항
커스텀 확장자는 최대 200개 까지 등록가능하나, 여러 사람이 동시에 확장자를 등록하는 경우가 생기면 제한을 초과할 수 있습니다.
이런 동시성 제어까지 구현하지는 못했으나 Pessimistic Write 락을 적용하여 충돌을 방지할 수 있습니다.  



