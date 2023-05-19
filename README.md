# Habit@


![Thank You For Watching Youtube Closing Video.gif](https://file.notion.so/f/s/40a49bb1-9e3f-4b0f-b1d0-f53e03e2864f/Thank_You_For_Watching_Youtube_Closing_Video.gif?id=1e8b3191-af4d-4c4e-8069-997995f4d3ae&table=block&spaceId=b695a4dd-9156-4652-b078-f5a0d5c2ec97&expirationTimestamp=1682135610636&signature=i_YEl0l3WdI9bhyq3RnHt8SyYwwy6-jYdbQFPxlWmh4&downloadName=Thank+You+For+Watching+Youtube+Closing+Video.gif)

## 프로젝트 개요


물 마시기 습관을 쉽게 기를 수 있는 블루투스 연결형 스마트 컵받침 및 기기 연동 어플리케이션

개발기간 : 2023.04.10 ~2023.5.19 (6주)

## 개발팀 소개


| 김민규 | 박병우 | 심정윤 | 원영현 | 유소영 | 이동민 |
| --- | --- | --- | --- | --- | --- |
| FE | FE | EB | FE | BE | BE |

## 기술스택


### **Embedded**

ESP32, Bluetooth, C/C++, 3d print

### **Front-End**

Flutter, FlutterFire, Web Socket

### **Back-End**

Springboot, Redis, MySQL, Web Socket

## 기획 및 설계 산출물


### 기획


<details>
<summary>회의록</summary>
<div markdown="1">

[회의록 Notion Link](https://www.notion.so/32997005b151415a975d3260a76d8a5a) 

</div>
</details>

<details>
<summary>기능 기획</summary>
<div markdown="1">
### 메인기능

1. 수분 섭취량 추척
    - 실시간 섭취량 전송
    - 일일 섭취량 기록
    - 섭취 타입(물/커피/음료수) 기록
    - 주간/월간 리포트
    - 섭취 현황에 대한 피드백 제공

### 세부기능

p1. 소셜 로그인

p2. 기기등록(페어링) / 해제

p3. 물마시기 시작

p4. 코스터 → 앱/서버로 데이터 전송

p5. 실시간 섭취량 페이지

p6. 경험치 증가

p7. 마스코트 키우기 페이지

p8. 물마시기 끝

p9. 마이페이지(체중/키/목표설정/프로필사진/닉네임)

p10. 주간/월간 리포트 페이지

### 코스터

1. 통신방법
    1. 블루투스? 와이파이?
2. 구조 설계
    1. 3d 모델
    2. 코르크
    3. 레진
    4. LED

### 세부기획

- 리워드는 언제 제공할 것인가?
    - 달성여부 / 달성률 / **섭취량** / 섭취횟수

# 기능 정리

1. 앱 로딩페이지(1~2초)
2. **회원가입** : 소셜로그인
3. **기본설정** : 성별, 나이, 키, 체중, 닉네임, (운동빈도, 활동량, 기후, 물섭취빈도, 커피섭취빈도) 일일 목표 물 섭취량(1000미리)
    1. 쏘영쏘님의 추천 일일 목표 섭취량은 **1.5L** 입니다. **50ml** 컵 기준 약 **35잔** 분량입니다. (쏘주잔이네요?)
    2. 추천 목표량 기준으로 나의 목표 수정/설정
4. **알림설정** : 물 마시기 알림
    1. 알림을 원하시나요?
    2. **10:00** ~ **18:00** 사이에 **1시간 간격으로**
    3. 앱 - 음성으로 알림
5. **기기등록**(건너뛰기 가능) : 블루투스(와이파이) 페어링
6. **수분섭취 / 캐릭터 / 리포트/ 설정페이지 / 챌린지 / 음료 기록**
    1. **수분섭취** : 물 마신 거 실시간 기록
        1. 음료 타입(물/커피/음료수) 지정
        2. 오늘 마신 총량, 목표의 00%
        3. 현재 연동된 기기 정보(연동 된 기기가 있음을 알려줌)
        4. 수동으로 추가
        5. 수동으로 편집
        6. 그래프 등 시각적 표현
        7. 마신 종류에 따라 그래프 색깔을 달리 하자(커피-갈색, 물-파랑)
    2. **캐릭터** : 경험치로 성장한 식물/캐릭터
        1. 내 경험치 & 현재 레벨
        2. 식물 사진
        3. 꽃 다 키우면 컬렉션으로 보내기
        4. 트리에 오너먼트처럼 달자
        5. 랜덤 뽑기 형식? 씨앗 고르기 형식? (처음엔 고르고 이후엔 안 겹치게 랜덤 어떤지) → 사용자가 선택하는 액션을 취할 수 있게
    3. **리포트**
        1. 오늘 섭취량 그래프
        2. 주간 섭취량 그래프
        3. 공유하기
    4. **설정페이지**
        1. 기본설정(닉네임, 체중, 키 등)
        2. 알림설정
        3. 목표설정
        4. 기기설정
    5. **음료기록(추가)**
        1. 오늘 
    6. **챌린지(추가)**
        1. 커스텀X 제안(5일 챌린지! 일주일 챌린지! 한달 챌린지!)
        2. 추적X 본인이 달성 여부 기록 : 양심에 맡기기~
        3. 카페인 덜먹기
        4. 액상과당 마시지 않기
        5. 금주(주류 마시지 않기)
7. **코스터**
    1. 음료 무게 변화량 측정
    2. 설정 시간마다 LED 알림
    3. 목표 진행률을 코스터에서 시각적으로 보여주자
8. **위젯(최우선 추가)**
    1. 홈화면/잠금화면 위젯 → 음료 변경 설정 가능하면 좋겠음
    2. 오늘 섭취량 실시간으로 보여주기
    3. 현재 마시고 있는 음료 타입 변경
9. **건강루틴 관리(추가?)**
    1. 스트레칭 등의 건강습관도 같이 관리할 수 있었으면 좋겠다
    2. 추후 고민, 우선 물에 집중하자

</div>
</details>
    



### 설계

<details>
<summary>기능명세서</summary>
<div markdown="1">

[기능 명세서 Notion Link](https://www.notion.so/b68fcf0bd1a341c5b2abe2848d9c209b) 
    
![Untitled](https://file.notion.so/f/s/87db618b-4b16-4dfa-92e8-05ca71cd20ef/Untitled.png?id=a26528c3-74c3-4066-8064-59d8f0ae0e47&table=block&spaceId=b695a4dd-9156-4652-b078-f5a0d5c2ec97&expirationTimestamp=1682135646116&signature=uu96CQMmQuG7_eQSs8ZCJO_pM7tBmn_njVF_iZGFSd0&downloadName=Untitled.png)
    
![Untitled](https://file.notion.so/f/s/3ae32224-92b4-47c7-82c6-edf63b1cbcc0/Untitled.png?id=f697bf91-bdff-4ca9-a76d-75e603d334de&table=block&spaceId=b695a4dd-9156-4652-b078-f5a0d5c2ec97&expirationTimestamp=1682135670537&signature=uJmGX8S3DQl3Bm95YgNGzhVURfvVHLt8j8k8eJ7YJ5A&downloadName=Untitled.png)
    
![Untitled](https://file.notion.so/f/s/3ecfeffb-1e50-43de-b4e5-65e75492f429/Untitled.png?id=7766ede2-b05b-4080-a4bd-4919b8b6dad3&table=block&spaceId=b695a4dd-9156-4652-b078-f5a0d5c2ec97&expirationTimestamp=1682135681503&signature=zIClNepLwHFW9BybV2ene0bJ8HxJWYrAhfex12kU_Bg&downloadName=Untitled.png)

</div>
</details>

<details>
<summary>와이어프레임</summary>
<div markdown="1">

[와이어프레임 Notion Link](https://www.notion.so/f7c8d9d2f925437fa1b3effeae20d225)

</div>
</details>

<details>
<summary>Figma 목업</summary>
<div markdown="1">
    
**[Figma 목업 Link](https://www.figma.com/file/SWtYxLTgxZgvKJia8mw4IK/HabitAt?node-id=0%3A1&t=YYyM33G88LBbHTvc-1)**

</div>
</details>


<details>
<summary>ERD</summary>
<div markdown="1">

[ERD Notion Link](https://www.notion.so/ERD-32d2971c12314d50a82f2404caa6a8b3) 

</div>
</details>

<details>
<summary>API 명세</summary>
<div markdown="1">
[API 명세 Notion Link](https://www.notion.so/API-b4239e489dc94dac9d77ed37f3df7ea9) 

</div>
</details>
<details>
<summary>아키텍처</summary>
<div markdown="1">
    
[아키텍처 Notion Link](https://www.notion.so/450f8e45975c42379795c892392d9257) 

</div>
</details>

    

### 일정관리(Link)

[프로젝트 일정 Notion Link](https://www.notion.so/43b2f340530c4de696e0b30515dd3fc4) 

[지라 Jira Link](https://ssafy.atlassian.net/jira/software/c/projects/S08P31A704/boards/1883)

# 서비스 소개

---

개발 이후 추가 예정
