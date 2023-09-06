# <img width="60" alt="bemo_icon" src="https://github.com/HaeBangProject/HAEBANG/assets/59076085/128deff0-b54c-46aa-ba85-130f52dba780"> HAEBANG

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
- [화면 구성](#화면-구성)
- [HAEBANG 회고](#haebang-회고)
- [ERD 구조](#erd-구조)
- [핵심 기능](#핵심-기능)
  - [로그인/회원가입](#로그인회원가입)
  - [아파트 매매 정보](#아파트-매매-정보)
  - [인기 검색 순위](#인기-검색-순위)
  - [매물 CRUD](#매물-crud)
  - [관리자 문의](#관리자-문의)
  - [검색어 자동완성](#검색어-자동완성)
- [CI/CD](#cicd)
  - [CI](#ci)
  - [CD](#cd)
- [Trouble Shooting](#trouble-shooting)

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
    <td align="center"><b>Backend Developer</b></td>
    <td align="center"><b>Backend Developer</b></td>
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
- [우편번호 서비스 Api](https://postcode.map.daum.net/guide)

## 시스템 아키텍처

![haebang_archi_final](https://github.com/HaeBangProject/HAEBANG/assets/59076085/ae7dfc0a-3f86-4ae6-8539-ba4eea9d3d97)


## API 명세서

### [Api 명세서 보기](https://documenter.getpostman.com/view/17346598/2s93kz659T)



## 프로젝트 목적

외부 API 활용, 토큰 인증방식, 소켓통신, 검색 프로세스를 적용하는 것과 관련된 전반적인 springboot의 동작을 이해하고 실사용자를 고려한 배포까지 진행하는 것이 주된 목적입니다.



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

![ERD](https://github.com/HaeBangProject/HAEBANG/assets/57720285/cd200289-7a10-4cdc-b00f-f860311edd6a)

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
  - 400 - 500 사이의 상태코드일 경우 토큰 재발급
  - 사용자에게 필요한 조치를 안내

### 아파트 매매 정보

- KakaoMap api와 공공데이터 api를 이용해 지도상 아파트 위치 구현과 지역과 년도별 아마트 매매 정보를 제공
  - 마커를 이용해 마커 클릭시 거래금액,계약날짜,건축년도 등의 아파트 매매 정보를 띄움
- 공공데이터 api를 호출하는 로직을 Service에 구현하고 Controller에서 년,월과 지역을 선택하면 Service에서 해당 매물을 찾아 Dto에 저장해 Controller에 Dto를 받아 결과를 구현

### 인기 검색 순위

- Redis의 자료구조인 Sorted Set을 이용하고 String값을 key,value로 사용하기 위해RedisTemplate의 StringRedisTemplate을 이용
  - Spring Data Redis 라이브러리를 이용해 Lettuce를 사용해 Redis와 연동
- opsForZset()의 incrementScore()를 이용해 key 안에서 검색어(지역)를 value에 저장하고, 검색될때마다  delta에 1을 주어 순위를 구현
- key 안에서 delta 기준으로 0~4순위를 리스트에 담아 Top5 구현

### 매물 CRUD

- 아파트 매물을 작성하고 수정 삭제 조회하는 기능을 RESTful API로 구현
  - entity에서 작성/수정 가능한 정보만 담아 dto로 요청/전달
  - validation을 사용해 정형화된 값을 받음. 조건에 맞지 않을시 message 전달
  - 같은 주소일 경우 1:N (아파트 : 매물)의 관계로 저장
    - 우편번호 서비스 api를 이용해 도로명 주소를 입력받아 일관되고 정확한 데이터 확보
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
- Redis를 이용해 관리자 채팅방을 Topic으로 설정해 여러 서버(Scale-out 분산환경)에서도 채팅방이 유지되도록 구현
  - Redis의 공통으로 사용할 수 있는 pub/sub 시스템을 구축해 모든 서버들이 해당 시스템을 통해 pub/sub 메세지를 주고받도록 구현
  - Redis Hash를 이용해 공통 Topic(채팅방)을 생성하고 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있게 구현
  - stomp 헤더에 토큰을 보내면서 stomphandler를 거쳐 유효한 토큰인지 검증해 유저 여부를 구분하며 websocket의 통신 보안을 강화

### 검색어 자동완성

- Elasticsearch와 nori 형태소 분석기를 이용해서 @Document로 apt 데이터를 인덱싱해 검색어를 입력했을때 자동완성으로 검색 데이터가 보이게 구현
  - Apt 엔티티는 JpaRepository를 사용하고 있어 ElasticsearchRepository를 사용하기 위해 ElasticSearch와 매핑할 AptDocument 클래스를 따로 생성해 적용
  - Docker 컨테이너로 elasticsearch를 실행한뒤 , analyis-nori 플러그인을 설치해 apt 인덱스 설정
  - 매물이 생성되거나 삭제될때 apt 인덱스에도 업데이트, ElasticsearchRepository를 이용해 검색 api(_search)로 검색 데이터를 찾음
  - ElasticSearch 도커 컨테이너가 중지되거나 다시 실행해도 데이터를 유지하게 호스트 경로와 컨테이너 내부 경로를 연결하는 볼륨마운트를 설정


## CI/CD

### CI

* Github에서 빌드가 되면 젠킨스 컨테이너에서 젠킨스 파이프라인 설정으로 DockerFile을 통해 jar파일이 생성되고, 빌드한 이미지를 DockerHub에 push하게 설정
  * Github의 Webhook 기능을 이용해 Jenkins에 자동으로 트리거를 유발하게 설정
  * [젠킨스 파이프라인](https://github.com/HaeBangProject/HAEBANG/blob/6b554b93ec869c785225ab4c8348d0a4c91b9505/jenkins_pipeline#L1)
    * sed 명령어로 설정파일의 secret값들을 파이프라인의 변수로 치환해 secret값들을 주입해줌
    * 젠킨스 도커 컨테이너에서 쓰지않는 데이터들을 삭제하게 만들어 불필요한 용량이 쌓이지 않게 설정

### CD

* 젠킨스 파이프라인을 이용해 배포 서버에서 deploy 스크립트 파일을 실행해 이미지를 pull받아 3대의 서버로 나눠 분산환경으로 실행되게 구현
  - 3개의 도커 컨테이너 서버를 nginx의 최소연결(least_conn) 방식으로 로드밸런싱

- https 프로토콜 사용을 위해 aws의 ALB(application load balancer)와 ACM(AWS Certificate Manager)를 생성해 사용
  - Let's Encrypt 인증서를 사용해 SSL까지 nginx에서 수행하는 것보다 AWS에서 편리하게 관리하기 위해 나누어 진행


- 무중단 배포 ( 롤링 배포 )
  - 롤링배포 방식을 위해 [스크립트](https://github.com/HaeBangProject/HAEBANG/blob/5694f437389f620e76f97a57a0180739bc6ec2d2/deploy.sh)로 3개의 서버를 차례로 배포후 health check 에서 up이면 다음 배포 진행
  - 버전 호환을 위해 1 배포 후 up이면 나머지 서버 down 하여 트래픽이 가지 않도록 함

## Trouble Shooting

- 인기 지역 검색 순위를 구현하는 과정에서 Redis의 Zset 자료구조는 동점처리를 지원해주지 않아 공동 순위를 구현하지 못하는 문제가 발생

  - Map.Entity 자료구조를 이용해 score값을 기준으로 다시 검색데이터를 분류한 뒤, 같은 score값을 가진 데이터를 카운트 해 reverseRange()를 이용해 동점처리 된 데이터도 공동순위에 들어가게 구현

  ```java
  List<Map.Entry<Double, Integer>> sortedList = new ArrayList<>(score.entrySet());
          //key인 score값을 기준으로 정렬
          sortedList.sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
          int same_score=0;
  
      
          //score가 TOP 5 안에 들어가고 ,같은 score를 가진 데이터가 2개이상일때
  				//same_score 변수에 카운트
          for (int i = 0; i <= 4 && i < sortedList.size(); i++) {
              Map.Entry<Double, Integer> entry = sortedList.get(i);
              if(entry.getValue()>1) {
                  same_score += entry.getValue() - 1;
                  System.out.println("Score: " + entry.getKey() + ", Value: " + entry.getValue());
              }
          }
          // 공동순위를 포함한 TOP5
          Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,4+same_score);
  ```



- Scale-out 분산환경에서 채팅방의 메인 저장소 없이 DTO로 채팅방을 설정해 서버 재시작할때마다 채팅방이 초기화 되는 문제와 여러 서버에서 채팅방 접속과 메세지를 주고받지 못하는 문제가 발생

  - Redis를 이용해 채팅방을 공통 Topic으로 구현한 뒤 pub/sub 시스템을 구축해 메세지를 주고받도록 구현해 해결
    - 채팅방 정보가 초기화 되지 않도록 생성시 redis HashOperations 구조를 이용해 ‘CHATROOM’을 Hash key값으로 갖고, 채팅방 id을 Hash Field로 , 채팅방 객체를 Hash Value로 저장해 채팅방의 정보를 저장하고 조회
    - 채팅방 입장시에는 채팅방 id로 redis topic(채팅방)을 조회해 pub/sub메세지 리스너와 연동

  ```java
  public class ChatRoomRepository {
      // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
      private final RedisMessageListenerContainer redisMessageListener;
      // 구독 처리 서비스
      private final RedisSubscriber redisSubscriber;
      // Redis
      private static final String CHAT_ROOMS = "CHAT_ROOM";
      private final RedisTemplate<String, Object> redisTemplate;
      private HashOperations<String, String, ChatRoom> opsHashChatRoom;
      // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보
  		// 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 구현
      private Map<String, ChannelTopic> topics;
  
      @PostConstruct
      private void init() {
          opsHashChatRoom = redisTemplate.opsForHash();
          topics = new HashMap<>();
      }
      public List<ChatRoom> findAllRoom() {
          return opsHashChatRoom.values(CHAT_ROOMS);
      }
      public ChatRoom findRoomById(String id) {
          return opsHashChatRoom.get(CHAT_ROOMS, id);
      }
      // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
      public ChatRoom createChatRoom(String name,String username) {
          ChatRoom chatRoom = ChatRoom.create(name,username);
          // CHAT_ROOM 키에 대한 redis hash 구조에 채팅방의 id를 key로 하고 , 해당 채팅방객체(chatRoom)를 value로 저장
          opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
          return chatRoom;
      }
  ```



- 배포가 업데이트 될때마다 redis 컨테이너의 저장된 key 값이 사라지는 문제가 발생

  - redis 기본포트가 열려있고, 인증없이 접근할 수 있는 환경일때, 크롤러봇이 해당되는 redis에 접근하여 모든 키를 지우고 스크립트를 심는 문제가 원인
  - redis-cli를 설정할때 --requirepass [password] 명령어를 사용해서 비밀번호를 설정해 문제를 해결

  ```shell
  docker run -v /path/on/host:/data \ 
  --name my-redis \
  -p 6379:6379 \ 
  -d redis:latest redis-server --appendonly yes --requirepass '비밀번호'
  ```



- spring Security 5.7 & JWT 변경사항

  - spring boot 2.7.3 은 spring security 5.7 버전을 포함
  - 5.7버전은 어댑터를 사용하지 않고 bean 등록방식을 사용하도록 바뀌었기 때문에 오버라이드 해서 구현했던 방식 대신 bean으로 등록하여 사용 WebSecurityConfigurerAdapter (기존) → FilterChain (변경)

  ``` java 
  @Configuration
  @EnableWebSecurity
  @RequiredArgsConstructor
  public class SecurityConfig {
   private final JwtProvider jwtProvider;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
              .httpBasic().disable()
              .csrf().disable() // cross site 도메인 다를때 허용
              .cors() //cross site
              .and()
              .formLogin().disable()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
              // UsernamePasswordAuthenticationFilter 필터를 거치기 전에 jwt필터를 거치도록 설정
      return http.build();
    }
  
  @Bean
  public HttpFirewall defaultHttpFirewall(){
      return new DefaultHttpFirewall();
    }
  }
  ```

  - new release 1.0.x 부터 parse 대신 builder()로, <u>signWith( 알고리즘, 세가지형태시크릿키)</u>에서 <u>signWith( byte[]형만되는시크릿키, 알고리즘 )</u> 으로 변경
  - signWith에 필요한 시크릿키는 Keys.hmacShaKeyFor('keyBytes')로 인코딩 된 키로 sign하도록 변경 ('keybytes'는 32byte보다 길어야 함)

  ``` java 
  public class JwtProvider {
   private final Key key;
   private final RedisService redisService;
   private final MemberRepository memberRepository;
  
      public JwtProvider(@Value("${jwt.secret}") String secretKey, RedisService redisService, MemberRepository memberRepository){
          byte[] keyBytes = Base64.getDecoder().decode(secretKey);
          key = Keys.hmacShaKeyFor(keyBytes);
  
          this.redisService = redisService;
          this.memberRepository = memberRepository;
      }
  }
  ```

- 유효하지 않은 token에 대해서 채팅방접속과 메시지 처리를 하지 못하는 문제 발생해 SpringSecuirty로 접근 권한과 Jwt를 인증해 Websocket 통신 보안을 강화해줌

  - SecurityFilterChain에서 .antMatchers(  "/chat/**").authenticated() 으로 접근 권한을 설정
  - stomp의 헤더에 access_token을 넣어 전송하면 stomp hadler에서 CONNECT, SEND의 경우 토큰을 검증

  ```java
  public class StompHandler implements ChannelInterceptor {
  
      private final JwtProvider jwtProvider;
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
          StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
  		// connect 또는 send이면 유효한 토큰인지 검증
          if(accessor.getCommand() == StompCommand.CONNECT) {
              if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                  throw new AccessDeniedException("");
          }
          else if(accessor.getCommand() == StompCommand.SEND){
              if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                  throw new AccessDeniedException("");
          }
  
          return message;
      }
  
  }
  ```

- JPA 양방향 연관 관계를 가진 객체들을 응답 시에 JSON 직렬화하는 과정에서 순환 참조 문제가 발생

  - @JsonIgnore 어노테이션을 추가해 필요하지 않는 객체는 제외
  - 지연로딩 옵션을 선택했을 경우 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 추가

  ``` java
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @Getter
  @NoArgsConstructor(force = true)
  @AllArgsConstructor
  @Builder
  @Entity
  public class Member implements UserDetails {// user은 ddl예약어로 member로 변경
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;
   @JsonIgnore
   @NotNull
   private String password;
  // 중략
   @JsonIgnore
   @Builder.Default
   @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   List<Item> items = new ArrayList<>(); 
  // 중략
  }
  ```

  - 복잡한 Entity의 경우 @JsonIgnore 만으로 해결이 불가해 새로운 response 용 Dto를 만들어 필요한 정보만 넣어 반환

  ``` java 
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Data
  public class AptItemRes {
  @JoinColumn(name = "data")
  AptItemReq aptItemReq;
  List<S3File> s3Files = new ArrayList<>();
  } 
  ```

- 연관된 Entity가 삭제/생성시 연동되지 않는 문제

  - bookmark 에 item, memeber가 있어 item 삭제시 bookmark 때문에 지워지지 않는 현상 & item 삭제시 S3File이 남아있는 현상
  - item삭제시 관련된 bookmark와 S3File이 지워지도록 설정 cascade = CascadeType.REMOVE, CascadeType.ALL

  ``` java
  @Builder.Default
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  List<S3File> s3Files = new ArrayList<>();
  
  @Builder.Default
  @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
  List<Bookmark> bookmarks = new ArrayList<>(); 
  ```
  - 매물이 존재하지 않는 아파트 데이터가 남아았고 검색어 자동완성에 뜨는 문제
  - Apt에 Item생성/삭제시 증가/감소하는 카운트를 만들어 0이 될때 Apt와 AptDocument (elastic search)를 삭제

  ``` java 
  public boolean deleteItem(String username, Long idx){
      Item item = itemRepository.findById(idx).orElseThrow();
  
        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);
        itemRepository.deleteById(idx);
        item.getApt().decreaseCnt();
  
        if(item.getApt().getCnt() > 0){
            aptRepository.save(item.getApt());
        }else{
            // 더이상 존재하는 item 이없는 apt 일 경우 - db 애서 지우고, 검색에 안뜨게 elastic search 애서도 지우기
            aptRepository.delete(item.getApt());
            aptSearchRepository.delete(AptDocument.from(item.getApt()));
        }
        return true;
    } 
  ```