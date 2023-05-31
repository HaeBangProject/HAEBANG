# <img width="60" alt="bemo_icon" src="https://github.com/HaeBangProject/HAEBANG/assets/59076085/128deff0-b54c-46aa-ba85-130f52dba780"> HAEBANG

<br>

<br>

## 목차

- [Project Members](#project-members) 
- [소개](#소개) 
- [개발 환경](#개발-환경)
- [시스템 아키텍처](#시스템-아키텍처)
- [API 명세서](#api-명세서)
- [프로젝트 목적](#프로젝트-목적)
- [HAEBANG 회고록](#HAEBANG-회고록)
- [핵심 기능](#핵심-기능)
  - [로그인/회원가입](#로그인회원가입)
  - [아파트 매매 정보](#아파트-매매-정보)
  - [인기 검색 순위](#인기-검색-순위)
  - [매물 CRUD](#매물-CRUD)
  - [관리자 문의](#관리자-문의)
  - [Error Response](#custom-error-response)
## Project Members

<table>
   <tr>
    <td align="center"><b><a href="https://github.com/jeeyoun-kang">강지윤</a></b></td>
    <td align="center"><b><a href="https://github.com/jisoo615">현지수</a></b></td>
  <tr>
     <td align="center"><a href="https://github.com/jeeyoun-kang"><img src="https://avatars.githubusercontent.com/u/59076085?v=4" width="100px" /></a></td>
    <td align="center"><a href="https://github.com/jisoo615"><img src="https://avatars.githubusercontent.com/u/57720285?v=4" width="100px" /></a></td>
  </tr>
  <tr>
    <td align="center"><b>Web Developer</b></td>
    <td align="center"><b>Web Developer</b></td>
</table>


## 소개

**HAEBANG**는 서울시 내 아파트 매매 정보를 제공하고 매물 등록 및 거래 할수 있는 웹서비스를 개발하는 프로젝트입니다. <br>

## 개발 환경

- Windows
- IntelliJ 
- GitHub
- AWS

## 사용 기술

**백엔드**

- Java 11 openjdk
- SpringBoot 2.7.3
- Spring Security
- Spring Data JPA 
- Lombok
- JWT

**프론트엔드**

- Html5/css3
- Javascript
- Thymeleaf

**빌드 툴**

- Gradle 7.6.1

**데이터베이스**

- Mysql
- Redis

**인프라** 

- AWS EC2
- AWS S3
- AWS RDS
- AWS Codedeploy
- AWS Route 53
- Github Actions

**라이브러리**

- [KakaoMap Api](https://apis.map.kakao.com/)
- [공공데이터 Api](https://www.data.go.kr/)

## 시스템 아키텍처

![haebang_architecture](https://github.com/HaeBangProject/HAEBANG/assets/59076085/f6cc8811-24bf-4b36-bbfe-13b06ce9c506)

## API 명세서

### [Api 명세서 보기](https://documenter.getpostman.com/view/17346598/2s93kz659T)



## 프로젝트 목적

외부 API 활용, 토큰 인증방식, 소켓통신을 적용하는 것과 관련된 전반적인 springboot의 동작을 이해하고 실사용자를 고려한 배포까지 진행하는 것이 주된 목적입니다.



## HAEBANG 회고

### [HAEBANG 회고록](https://goofy-hoverfly-fbf.notion.site/fb804fd9cbca4e1298f111f9f21a75a2?v=98ca6cf74bb94745b5e16fd62c8b8bda)



## 핵심 기능

 - ### 로그인/회원가입

  * 세션로그인 대신 JWT(Json Web Token)을 활용한 토큰인증방식을 구현
  * 최초 로그인 이후, 발급된 토큰을 담은 API요청을 필터로 검증 - 접근제한/허용
  * AccessToken( ATK )과 RefreshToken( RTK ) 두개를 발급해 최초 로그인 시 Redis에 RTK를 저장, 로그아웃시 Redis에 RTK는 삭제하고 ATK를 저장
  * Redis를 토큰 관리에 사용
    * 서버가 여러대라고 가정하여, 분산될 인메모리 session db를 대비해 인메모리 저장소인 Redis 에서 사용자 토큰을 관리
    * 데이터를 저장할때 만료시간을 지정할 수 있어서 만료시간이 있는 토큰들을 관리하기에 적합하다고 판단

- ### 아파트 매매 정보

  - KakaoMap api와 공공데이터 api를 이용해 지도상 아파트 위치 구현과 지역과 년도별 아마트 매매 정보를 제공
  - 공공데이터 api를 호출하는 로직을 Service에 구현하고 Controller에서 년,월과 지역을 선택하면 Service에서 해당 매물을 찾아 Dto에 저장해 Controller에 Dto를 받아 결과를 구현

- ### 인기 검색 순위

  - Redis의 자료구조인 Sorted Set을 이용하고 String값을 key,value로 사용하기 위해RedisTemplate의 StringRedisTemplate을 이용
    - Spring Data Redis 라이브러리를 이용해 Lettuce를 사용해 Redis와 연동
  - opsForZset()의 incrementScore()를 이용해 key 안에서 검색어(지역)를 value에 저장하고, 검색될때마다  delta에 1을 주어 순위를 구현
  - key 안에서 delta 기준으로 0~9순위를 리스트에 담아 Top10 구현

- ### 매물 CRUD

  * 아파트 매물을 작성하고 수정 삭제 조회하는 기능을 RESTful API로 구현
  * 수정, 삭제는 글의 작성자와 토큰에서 도출한 사용자와 일치하는 지 확인 후 접근/제한
  * 글 작성 시 아파트의 도로명 주소를 받아, 같은 주소일 경우 1:N (아파트 : 매물)의 관계로 저장
  * 전체 매물 조회와 아파트 도로명 주소로 조회하는 두가지 방식을 구현

- ### 관리자 문의

  * STOMP와 SockJS를 이용해 채팅을 구현하여 관리자와 1:1 문의가 가능하도록 구현
    * 세션을 직접관리 않고 여러 채팅방을 개설하기 위해 WebSocket 기반의 STOMP 이용
    * WebSocket을 지원하지 않는 브라우저에서 서버-클라이언트 간 통신이 끊기지 않게 하기 위해 SockJS를 이용
  * stomp의 헤더에 토큰을 넣어 소켓통신을 검증
    * 최초 연결 Connect 에서 토큰이 검증되면 연결 허용
    * 이어지는 Send 요청들도 토큰을 검증해 중간에 사용자가 바뀌는지 확인

- ### Custom Error Response

  * 상황에 따른 error code를 작성하여 기존 응답보다 client가 어떤 조치를 취해야 하는지 자세히 안내하는 Exception Handler를 작성
  