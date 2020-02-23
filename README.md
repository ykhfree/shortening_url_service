# shortening url service

URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

실행 시 필요환경 : jdk 1.8

1. 설치 및 빌드 방법 (리눅스 기준)
   - 해당 프로젝트는 gradle wrapper로 구성되어 있기 때문에 아래의 명령어를 통해 프로젝트를 빌드할 수 있다.    
   ```
   $ ./gradlew build
   ```
   
   - 그 후 아래의 명령어를 통해 서버구동을 한다.  
        1. 주의사항  
          - 8080 포트를 사용하므로 해당 포트가 사용중이면 안됨  
          - H2 내장DB를 메모리가 아닌 파일방식으로 사용하므로 아래 명령어를 실행하는 계정이 jar가 존재하는 폴더의 읽기,쓰기권한을 가져야함.
          - jdk 1.8 환경에서 실행해야함.    
   ```
   java -jar build/libs/shortening_url-1.0.jar
   ```
  
   
2. 서비스 이용방법
   - 서버가 기동되면 아래의 2가지 화면을 이용할 수 있음.
   
   2.1 shortening url 요청 리스트 (지금까지 요청된 url의 리스트와 요청수 등을 확인)  
        http://localhost:8080/service/urlList
      
   2.2 shortening url 요청 페이지 (새로운 shortening url을 만들기 위한 페이지)  
        http://localhost:8080/service/convertUrl