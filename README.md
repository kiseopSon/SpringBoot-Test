# SpringBoot-Test - 이메일 테스트
- 전송한도 (2023.05.30 기준)
  1. Gmail (개인계정)
      - 1명이 최대 100명까지
      - 24시간 한도 : 500개
      - 수신(외부) : 500개
  2. 네이버
      - 1명이 최대 100명까지
      - 시간당 한도 : 30회
      - host : smtp.naver.com
      - port : 587
      - TLS 또는 SSL설정 필요
  3. 다음
      - 1명이 최대 150명까지
      - 24시간 한도 : 1만개

- 전송이슈 -> 이메일 대량 발송 및 스팸분류 (2023.05.30 기준)
  1. Gmail은 대량 이메일에 대한 엄격한 가이드라인이 있다. 참고자료 : https://support.google.com/mail/answer/81126
  2. 혹여, 수신자에 의해 스팸으로 분류되어있거나, 잘못된 주소로 수없이 발송할 경우 일시적으로 계정중지가 된다.
  3. @gmail.com으로 끝나는 주소인경우 이메일이 수신자의 받은 편지함으로 전송되지 않을 경우가 많다
      - 웹을 제공하는곳은 개인 사용 목적으로 만들어진 서비스가 많기에 대량인경우 스팸으로 분류되기 쉽기때문이다.
      - 비지니스 목적이라면 자주보이는 웹 사이트의 주소보단 기업 도메인의 이름을 포함한 발신 이메일의 사용을 권장한다.
  4. 네이버인 경우 host와 송신자의 주소가 같아야지만 스팸으로 처리되지 않는다. != 달라도 보내지긴 한다.
