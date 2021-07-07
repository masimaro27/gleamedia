# Gleamedia_backend_과제

---

## Project 구성

- language: java 1.8
- framework: spring boot 2.5.2
- ORM: Hibernate
- Build framework: Gradle
- DB: H2
- QueryDSL

---

# build

### Mac:

1. project root 경로 이동 ( ../gleamedia )
2. Gradle build
    - 실행: ./gradlew build
3. 생성된 jar 실행
    - 위치: ../gleamedia/build/libs
    - 실행: java -jar ./gleamedia-0.0.1-SNAPSHOT.jar

---
## DB 접속정보

- URL - http://localhost:8081/h2-console
- ID - sa
- PW -