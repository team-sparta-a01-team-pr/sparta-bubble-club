# Sparta Bubble Club
- 팀명 : 🫧스파르타버블클럽🫧
- 프로젝트 명 : 🫧스파르타버블클럽🫧
- 프로젝트 소개 : 사용자가 짧은 글을 등록하는 커뮤니티 서비스
- 프로젝트 계기 : 대용량 트래픽을 구현하기 위해 가장 트래픽이 높은 서비스를 생각해보았고, 트위터가 대표적인 서비스라고 생각되어, 이를 기반으로 프로젝트를 구상하였습니다.

## 개발 기간
- `2024-02-14` ~ `2024-02-23`

## 개발 도구
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Data Jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Swagger-6DB33F?style=for-the-badge&logo=swagger&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
<img src="https://img.shields.io/badge/IntelliJ Ultimate Idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">

## 개발 인원

| 최유민                                                       | 백승한                                                      | 박재은                                                        | 곽준선                                                        |
|-----------------------------------------------------------|----------------------------------------------------------|------------------------------------------------------------|------------------------------------------------------------|
| ![](https://avatars.githubusercontent.com/u/115597692?v=4) | ![](https://avatars.githubusercontent.com/u/84169773?v=4) | ![](https://avatars.githubusercontent.com/u/149674839?v=4) | ![](https://avatars.githubusercontent.com/u/120253044?v=4) |
| [@adorecamus](https://github.com/adorecamus)             | [@back-seung](https://github.com/back-seung)             | [@Re-eun](https://github.com/Re-eun)                       | [@rugii913](https://github.com/rugii913)                                              |
| `팀장`, `로컬 캐시 적용`, `인증/인가`                                 | `CRUD`, `성능 테스트`, `인기 검색어 캐시`, `페이지네이션`, `Infra 구성`      | `CRUD`, `성능 테스트`, `인기 검색어 캐시`, `페이지네이션`                    | `로컬 캐시 적용`, `인증/인가`                                        |

## 프로젝트 패키지 구조
├── README.md
├── .github
│   ├── ISSUE-TEMPLATE
│   └── PULL_REQUEST_TEMPLATE
├── .gitignore
├── .build.gradle.kts
├── .idea
├── .gradle
└── src.main.kotlin
    └── com.sparta.bubbleclub
    ├── domain
    │   ├── bubble
    │   │   ├── controller
    │   │   ├── dto
    │   │   │   ├── request
    │   │   │   └── response
    │   │   ├── entity
    │   │   ├── repository
    │   │   └── service
    │   ├── keyword
    │   │   ├── controller
    │   │   ├── dto
    │   │   │   └── response
    │   │   ├── entity
    │   │   ├── repository
    │   │   └── service
    │   ├── member
    │   │   ├── controller
    │   │   ├── dto
    │   │   │   ├── request
    │   │   │   └── response
    │   │   ├── entity
    │   │   ├── repository
    │   │   └── service
    ├── global
    │   ├── config
    │   ├── entity
    │   └── exception
    │       ├── code
    │       ├── common
    │       ├── handler
    │       ├── member
    │       └── response
    └── infra
        ├── jwt
        ├── querydsl
        ├── swagger
        └── redis



