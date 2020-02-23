# shortening url service

URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service


1. 설치 및 빌드 방법 (리눅스 기준)
   - 해당 프로젝트는 gradle wrapper로 구성되어 있기 때문에 아래의 명령어를 통해 프로젝트를 빌드할 수 있다.
   _$ ./gradlew build_
   
   - 그 후 아래의 명령어를 통해 서버구동을 한다.
     => jdk 1.8 이상의 환경 필요!!
   _java -jar build/libs/shortening_url-1.0.jar_
  
   
2. 서비스 이용방법
   - 1번을 통해 서버가 기동되면 아래의 2가지 화면을 이용할 수 있음.
   
   2.1 shortening url 요청 리스트 (지금까지 요청된 url의 리스트와 요청수 등을 확인)
      => http://localhost:8080/service/urlList
      
   2.2 shortening url 요청 페이지 (새로운 shortening url을 만들기 위한 페이지)
      => http://localhost:8080/service/convertUrl