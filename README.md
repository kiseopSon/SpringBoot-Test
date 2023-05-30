# SpringBoot-Test - JWT Test

- 2023.05.30 기록
sts4로 개발되었고,
혹시나 Problems에 소스를 못찾는다고 하면 백발백중 롬복이 제대로 적용안되어 있어서 그러니, sts4.ini맨 밑에 
다음 내용
-javaagent:C:\Users\admin\Downloads\spring-tool-suite-4-4.18.1.RELEASE-e4.27.0-win32.win32.x86_64.self-extracting\sts-4.18.1.RELEASE\plugins\org.projectlombok.agent_1.18.28/lombok.jar
을 추가하고, 롬복파일도 공식홈페이지에서 다운받을 수있으니, 해당 경로에 넣어놓기만 하면 된다.
그리고, 기존 프로젝트가 있는 상태에서 롬복을 설치하려는경우 설치후 기존 프로젝트는 인식이 안될 수 있다.
그럴경우 따로 파일을 뽑아서 다시 import하는 방식으로 추가하면 적상 작동한다.

마지막으로 application.yml의 db경로는 항상 달라질 수 있으며, database는 현재 jwt라는 명칭으로 설정해 놓았고, ddl-auto명령어로 database까지 생성해 주지 않기에 존재하고 있어야 Dialect관련 에러가 나오지 않고 정상 실행된다. 
