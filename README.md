# HAEBANG

<br>
<br>

## 🚀[HAEBANG 접속하기](https://www.haebang.site)

## 목차

- [Project Members](#project-members)
- [소개](#소개)
- [개발 환경](#개발-환경)
- [시스템 아키텍처](#시스템-아키텍처)
- [API 명세서](#api-명세서)
- [프로젝트 목적](#프로젝트-목적)
- [화면구성](#화면구성)
- [HAEBANG 회고록](#HAEBANG-회고록)
- [ERD 구조](#ERD-구조)
- [핵심 기능](#핵심-기능)
  - [로그인/회원가입](#로그인회원가입)
  - [아파트 매매 정보](#아파트-매매-정보)
  - [인기 검색 순위](#인기-검색-순위)
  - [매물 CRUD](#매물-CRUD)
  - [관리자 문의](#관리자-문의)
  - [검색어 자동완성](#검색어-자동완성)
- [CI/CD](#CI/CD)
  - [CI](#CI)
  - [CD](#CD)
- [Trouble Shooting](#Trouble-Shooting)

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

**HAEBANG**는 서울시 내 아파트 매매 정보를 제공하고 우리나라 전역의 매물 등록 및 거래 할수 있는 웹서비스를 개발하는 프로젝트입니다. <br>

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
- ElasticSearch 7.17.12

**인프라**

- AWS EC2
- AWS S3
- AWS RDS
- AWS LoadBalancer
- AWS Route 53
- Jenkins
- Docker

**라이브러리**

- [KakaoMap Api](https://apis.map.kakao.com/)
- [공공데이터 Api](https://www.data.go.kr/)

## 시스템 아키텍처

![haebangarchifinal](https://github.com/HaeBangProject/HAEBANG/assets/59076085/ae7dfc0a-3f86-4ae6-8539-ba4eea9d3d97)

## API 명세서

### [Api 명세서 보기](https://documenter.getpostman.com/view/17346598/2s93kz659T)

## 프로젝트 목적

외부 API 활용, 토큰 인증방식, 소켓통신, 분산 아키텍쳐를 적용하는 것과 관련된 전반적인 springboot의 동작을 이해하고 실사용자를 고려한 배포까지 진행하는 것이 주된 목적입니다.

## 화면 구성

| [![main_haebang](https://github.com/HaeBangProject/HAEBANG/assets/59076085/17664fab-72c8-4a5c-b3a4-b7ce603bbb21)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/17664fab-72c8-4a5c-b3a4-b7ce603bbb21) | [![login_haebang](https://github.com/HaeBangProject/HAEBANG/assets/59076085/8de9e11e-f82f-4943-a255-d54f2ddffa16)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/8de9e11e-f82f-4943-a255-d54f2ddffa16) | [![haebang_1](https://github.com/HaeBangProject/HAEBANG/assets/59076085/68a06f62-4e25-4d7d-9351-1b56a850c29a)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/68a06f62-4e25-4d7d-9351-1b56a850c29a) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 메인 페이지                                                  | 로그인/회원가입                                              | 매물보기 페이지                                              |

| [![mypage_1](https://github.com/HaeBangProject/HAEBANG/assets/59076085/54b3109d-430d-4b45-b077-a13447386334)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/54b3109d-430d-4b45-b077-a13447386334) | [![haebang_bookmark](https://github.com/HaeBangProject/HAEBANG/assets/59076085/b69c1a91-13d7-4d22-8957-c986ac37f9fb)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/b69c1a91-13d7-4d22-8957-c986ac37f9fb) | [![crud_haebang](https://github.com/HaeBangProject/HAEBANG/assets/59076085/923d88d4-a3a4-41f0-aa3c-cba9b1a1717e)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/923d88d4-a3a4-41f0-aa3c-cba9b1a1717e) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 마이 페이지->내가 작성한 글                                  | 마이페이지->북마크한 글                                      | 집 내놓기 페이지                                             |

| [![search_2](https://github.com/HaeBangProject/HAEBANG/assets/59076085/f2793768-b763-4eca-9b31-050efdfb77d5)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/f2793768-b763-4eca-9b31-050efdfb77d5) | [![chatroom_haebang](https://github.com/HaeBangProject/HAEBANG/assets/59076085/5e23ed46-2388-4f05-b680-c7773f4f413e)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/5e23ed46-2388-4f05-b680-c7773f4f413e) | [![chat_haebang](https://github.com/HaeBangProject/HAEBANG/assets/59076085/2028042b-a779-4474-8c7d-b223c123a322)](https://github.com/HaeBangProject/HAEBANG/assets/59076085/2028042b-a779-4474-8c7d-b223c123a322) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 메인페이지-> 검색어 자동완성                                 | 문의하기 페이지                                              | 문의하기 페이지 -> 채팅                                      |

## HAEBANG 회고

### [HAEBANG 회고록](https://goofy-hoverfly-fbf.notion.site/fb804fd9cbca4e1298f111f9f21a75a2?v=98ca6cf74bb94745b5e16fd62c8b8bda)

## ERD-구조

![ERD](https://github.com/HaeBangProject/HAEBANG/assets/57720285/a06f6694-f044-4387-9173-84b8636e1234)

## 핵심 기능

### 로그인/회원가입

- 세션로그인 대신 JWT(Json Web Token)을 활용한 토큰인증방식을 구현
- 토큰을 담은 API요청을 필터로 검증 - 접근제한/허용
  - 토큰 역할&만료시간
    - AccessToken( ATK ) = api요청시 헤더에 넣어 보내는 토큰, 만료시간을 보다 짧은 1시간으로 설정
    - RefreshToken( RTK ) = ATK를 재발급하는 용도의 토큰, 만료시간을 보다 긴 2주로 설정
- 과정
  - 최초 로그인 시 AccessToken( ATK )과 RefreshToken( RTK ) 두개를 발급후 Redis에 RTK를 저장
    - key[RTK토큰] value[email] exp[남은시간] 저장
      - 재발급시 RTK 토큰이 존재할때 ATK재발급 (탈취된 RTK를 사용금지하기 위함)
  - 로그아웃시 Redis에서 RTK는 삭제하고 ATK를 저장
    - 기한이 남았을 ATK를 사용금지 위해 key[ATK토큰] value['logout'] exp[남은시간] 저장
    - 기존 등록된 RTK를 사용금지 위해 RTK를 삭제

- Redis를 토큰 관리에 사용
  - 여러대의 서버로 분산될 인메모리 session db를 대비해 인메모리 저장소인 Redis 에서 사용자 토큰을 관리
  - 데이터를 저장할때 만료시간을 지정할 수 있어서 만료시간이 있는 토큰들을 관리하기에 적합하다고 판단
- cookie와 localstorage
  - atk는 자주 사용되기 때문에 자동으로 실리는 Cookie에 저장
  - rtk는 자주 사용되지 않으니 localstorage에 저장
- custom error response
  - 회원가입/로그인/토큰검증 과정에서 발생한 문제를 세분화
  - 401상태코드 일 경우 토큰 재발급
  - 사용자에게 필요한 조치를 안내

### 아파트 매매 정보

- KakaoMap api와 공공데이터 api를 이용해 지도상 아파트 위치 구현과 지역과 년도별 아마트 매매 정보를 제공
- 공공데이터 api를 호출하는 로직을 Service에 구현하고 Controller에서 년,월과 지역을 선택하면 Service에서 해당 매물을 찾아 Dto에 저장해 Controller에 Dto를 받아 결과를 구현

### 인기 검색 순위

- Redis의 자료구조인 Sorted Set을 이용하고 String값을 key,value로 사용하기 위해RedisTemplate의 StringRedisTemplate을 이용
  - Spring Data Redis 라이브러리를 이용해 Lettuce를 사용해 Redis와 연동
- opsForZset()의 incrementScore()를 이용해 key 안에서 검색어(지역)를 value에 저장하고, 검색될때마다 delta에 1을 주어 순위를 구현
- key 안에서 delta 기준으로 0~4순위를 리스트에 담아 Top5 구현

### 매물 CRUD

- 아파트 매물을 작성하고 수정 삭제 조회하는 기능을 RESTful API로 구현
  - entity에서 작성/수정 가능한 정보만 담아 dto로 요청/전달
  - validation을 사용해 정형화된 값을 받음. 조건에 맞지 않을시 message 전달
  - 같은 주소일 경우 1:N (아파트 : 매물)의 관계로 저장
    - [우편번호 서비스](https://postcode.map.daum.net/guide) api를 이용해 도로명 주소를 입력받아 일관되고 정확한 데이터 확보
- 수정, 삭제는 작성자와 토큰에서 도출한 사용자와 일치하는 지 확인 후 접근/제한
- 사진(Photo) 업로드/수정/삭제
  - 리사이징 거쳐 사진 용량 줄임
  - 오브젝트 스토리지인 AWS S3에 사진을 업로드하고 삭제
  - mysql db에는 s3에 업로드하고 받은 URL을 저장해서 사용
    - 가상호스팅주소 접근이 불가해 Path Style로 변경
- 북마크(Bookmark) 기능
  - item_id와 member_id 로 복합키 생성해 boomark:item = N:1, bookmark:member = N:1 관계 형성
  - item에 bookmarks를 함께 보내 프론트에서 북마크 유무 확인
    
    ### 관리자 문의
    
- STOMP와 SockJS를 이용해 채팅을 구현하여 실시간으로 관리자와 1:1 문의가 가능하도록 구현
  - 세션을 직접관리 않고 여러 채팅방을 개설하기 위해 WebSocket 기반의 STOMP 이용
  - WebSocket을 지원하지 않는 브라우저에서 서버-클라이언트 간 통신이 끊기지 않게 하기 위해 SockJS를 이용
- redis를 이용해 관리자 채팅방을 엔티티로 설정해 Scale-out 분산환경에서도 채팅방이 유지되도록 구현
  - redis의 pub/sub 시스템을 구축해 모든 서버들이 해당 시스템을 통해 pub/sub 메세지를 주고받도록 구현

### 검색어 자동완성

- Elasticsearch와 nori 형태소 분석기를 이용해서 @Document로 apt 데이터를 인덱싱해 검색어를 입력했을때 자동완성으로 검색 데이터가 보이게 구현
  - Docker 컨테이너로 elasticsearch를 실행한뒤 , analyis-nori 플러그인을 설치해 apt 인덱스 설정
  - 매물이 생성되거나 삭제될때 apt 인덱스에도 업데이트, ElasticsearchRepository를 이용해 검색 api(_search)로 검색 데이터를 찾음
  - ElasticSearch 도커 컨테이너가 중지되거나 다시 실행해도 데이터를 유지하게 호스트 경로와 컨테이너 내부 경로를 연결하는 볼륨마운트를 설정

## CI/CD

### CI

- Github에서 빌드가 되면 젠킨스 컨테이너에서 젠킨스 파이프라인 설정으로 DockerFile을 통해 jar파일이 생성되고, 빌드한 이미지를 DockerHub에 push하게 설정
  - Github의 Webhook 기능을 이용해 Jenkins에 자동으로 트리거를 유발하게 설정
  - [젠킨스 파이프라인](#https://github.com/HaeBangProject/HAEBANG/blob/6b554b93ec869c785225ab4c8348d0a4c91b9505/jenkins_pipeline#L1)
    - sed 명령어로 설정파일의 secret값들을 파이프라인의 변수로 치환해 secret값들을 주입해줌
    - 젠킨스 도커 컨테이너에서 쓰지않는 데이터들을 삭제하게 만들어 불필요한 용량이 쌓이지 않게 설정

### CD

- 젠킨스 파이프라인을 이용해 배포 서버에서 Dockerhub를 통해 이미지를 pull받아 3대의 서버로 나눠 분산환경으로 실행되게 구현
  - 3개의 서버를 nginx의 라운드로빈 방식으로 로드밸런싱
- https 프로토콜 사용을 위해 aws의 ALB(application load balancer)와 ACM(AWS Certificate Manager)를 생성해 사용
  - Let's Encrypt 인증서를 사용해 SSL까지 nginx에서 수행하는 것보다 AWS에서 편리하게 관리하기 위해 나누어 진행
- 무중단 배포
  - 3개의 서버 중 하나를 backup서버로 두어 나머지 2개의 서버가 동작하지 않을 경우 backup서버로 로드밸런싱한 뒤, backup서버의 업데이트를 30초 늦춰 진행
    
## Trouble Shooting
    
- Redis의 sorted set 자료구조를 이용해 인기 지역 검색 순위를 구현하는 과정에서 동점처리를 못해 공동 순위를 구현하지 못하는 문제가 발생
  - [Map 자료구조를 이용해 score값을 기준으로 다시 검색데이터를 분류해 같은 score값을 가진 데이터를 카운트 해 reverseRange()를 이용해 동점처리 된 데이터도 공동순위에 들어가게 구현](https://github.com/HaeBangProject/HAEBANG/blob/bfe7e36905e51443391b8d21349d4b6a16618360/src/main/java/com/haebang/haebang/service/RedisService.java#L40)
  - 관리자 문의 채팅 기능에서 WebSocket과 Stomp만으로 여러 서버에서 채팅방 접속과 메세지를 주고받지 못하는 문제가 발생
    - Redis를 이용해 채팅방을 엔티티로 구현한 뒤 pub/sub 시스템을 구축해 메세지를 주고받도록 구현해 해결
      - 채팅방 정보가 초기화 되지 않도록 생성시 redis hash에 저장하도록 처리
      - 채팅방 입장시에는 채팅방 id로 redis topic(채팅방)을 조회해 pub/sub메세지 리스너와 연동
- CI/CD가 될때마다 redis 컨테이너의 저장된 데이터 값이 삭제되는 문제가 발생
  - [redis-cli를 설정할때 --requirepass [password] 명령어를 사용해서 비밀번호를 설정해 데이터가 초기화 되는 문제를 해결](#https://lemonade99.tistory.com/8)
- spring Security 5.7 & JWT 변경사항
  
  - [spring boot 2.7.3 은 spring security 5.7 버전을 포함한다. 5.7버전은 어댑터를 사용하지 않고 bean 등록방식을 사용하도록 바뀌었기 때문에 오버라이드 해서 구현했던 방식 대신 bean으로 등록하여 사용하면 된다. WebSecurityConfigurerAdapter (기존) → FilterChain (변경)](https://github.com/HaeBangProject/HAEBANG/blob/bfe7e36905e51443391b8d21349d4b6a16618360/src/main/java/com/haebang/haebang/configuration/SecurityConfig.java#L20))
  - [new release 1.0.x 부터 parse 대신 builder()로, <u>signWith( 알고리즘, 세가지형태시크릿키)</u>에서 <u>signWith( byte[]형만되는시크릿키, 알고리즘 )</u> 으로 변경. ](https://github.com/HaeBangProject/HAEBANG/blob/bfe7e36905e51443391b8d21349d4b6a16618360/src/main/java/com/haebang/haebang/utils/JwtProvider.java#L29))
    signWith에 필요한 시크릿키는 Keys.hmacShaKeyFor('keyBytes')로 인코딩 된 키로 sign하도록 변경됨 ('keybytes'는 32byte보다 길어야 함)
- 웹소켓 통신시 jwt 적용과 유저 구분 문제 - 토큰으로 여부를 구분하기 때문에 토큰을 담아 보내고 검증하는 것에 어려움 발생
  
  - [토큰으로 회원여부를 구분하기 때문에 stomp의 헤더에 토큰을 담아 보내고, 이를 stomp hadler를 거쳐 검증함](https://github.com/HaeBangProject/HAEBANG/blob/bfe7e36905e51443391b8d21349d4b6a16618360/src/main/java/com/haebang/haebang/utils/StompHandler.java#L20)
  - [핸들러를 통해 <u>세션id와 토큰으로 부터 가져온 유저네임을 맵핑시키는 방식</u>에서, 최초 로그인시 토큰과 유저정보를 쿠키에 저장하도록 해 소켓 <u>통신시 토큰과 유저네임을 함께 보내도록해 보다 </u>편리하게 변경](https://github.com/HaeBangProject/HAEBANG/blob/bfe7e36905e51443391b8d21349d4b6a16618360/src/main/java/com/haebang/haebang/utils/StompHandler.java#L20)
- Entity 순환참조 문제
  
  - @JsonIgnore 어노테이션을 추가해 필요하지 않는 객체는 제외
  - [지연로딩 옵션을 선택했을 시 발생하는 문제 해결 위해 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 추가](https://github.com/HaeBangProject/HAEBANG/blob/bf32b9907e2d811f7b2a99d1e651d1f5a0a930b0/src/main/java/com/haebang/haebang/entity/Member.java#L24C63-L24C63)
  - [복잡한 Entity의 경우 @JsonIgnore로 해결이 불가해 새로운 response 용 Dto를 만들어 필요한 정보만 넣어 반환](https://github.com/HaeBangProject/HAEBANG/blob/bf32b9907e2d811f7b2a99d1e651d1f5a0a930b0/src/main/java/com/haebang/haebang/dto/AptItemRes.java#L14)
- 연관된 Entity가 삭제/생성시 연동되지 않는 문제
  
  - [bookmark 에 item과 memeber가 있어서 item 삭제시 bookmark 때문에 지워지지 않는 현상 & item 삭제시 S3File이 남아있는 현상 - item삭제시 관련된 bookmark와 S3File이 지워지도록 설정 `casecade.REMOVE`](https://github.com/HaeBangProject/HAEBANG/blob/bf32b9907e2d811f7b2a99d1e651d1f5a0a930b0/src/main/java/com/haebang/haebang/entity/Item.java#L52)`
  - [Apt가 자식이 없을때 삭제하기 위해 카운트를 만들어 0이 될때 Apt와 AptDocument (elastic search)를 삭제](https://github.com/HaeBangProject/HAEBANG/blob/bf32b9907e2d811f7b2a99d1e651d1f5a0a930b0/src/main/java/com/haebang/haebang/service/AptService.java#L121)
