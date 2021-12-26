# Database-Web-Programming

## Background
    - 택시 승강장은 유동 인구가 밀집된 구역에 설치될 필요성을 파악.
    - 인스타그램 크롤링을 통해 핫 플레이스를 시각화하여 택시 승강장 위치와 비교 분석.
    
## Process
- Collecting Data
    - 장소 정보를 포함하는 인스타그램 게시글들을 특정 키워드들로 수집 (selenium)
    - 수집된 게시글 목록은 hdfs에 파일로 저장
- Processing Data
    - 수집된 장소 정보를 좌표변환 API를 이용하여 지도 좌표 (위도, 경도)로 변환
    - 서울시 전체를 100m 간격의 정사각형으로 구역을 나눔
    - ImpalaQL을 사용하여 각 구역에 언급된 게시글의 수를 카운트
- Providing Data
    - 특정 위치를 중심으로 주변 구역의 게시글 수 정보를 제공하는 API
    - php3 + MySQL을 이용하여 REST API 형태로 제공
    - 택시 승강장의 지도 좌표 정보 제공
- Visualization
    - Naver Map API로 지도 인터페이스로 데이터 시각화
    - GPS정보를 받아와서 내 주변 위치의 정보만 요청
    - 구역별 게시글의 수와 택시 승강장의 위치를 마커로 표기


## Sturcture
![image](https://user-images.githubusercontent.com/84901368/142994046-9b65dc25-bd90-4344-9199-e9ca196bd509.png)
