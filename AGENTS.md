# Contributor Guide

## Dev Environment Tips
- application-secret.yml 내용은 다음과 같다 추가하여 사용하여야 한다.
```yaml
MONGODB_URI: mongodb://localhost:27017/mydb

jwt:
  secret: no-one-knows-about-my-secret-key-maybe
  access-expiration: 3600000
  refresh-expiration: 604800000
```
- mongodb가 설치되어있어야 한다.

## Testing Instructions
- 테스트를 할때 2 계정으로 한다
  1. username: wolfbone0304, password: 1234, classes: 11회차
  2. username: sver0314, password:1234, classes: 11회차
- confirm register login을 모두 사용해본다.
- 1번계정의 경우 모두 성공해야 하고 2변 계정의 경우 confirm에서 실패하여야한다.
## PR instructions
Title format: [<project_name>] <Title>