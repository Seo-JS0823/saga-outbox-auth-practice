# saga-outbox-auth-practice

## PhantomToken Pattern
PhantomToken은 사용자 인증 상태를 직접 노출하지 않으면서 클라이언트 요청을 식별•검증하기 위한 토큰 기반 인증 방식입니다.

## 목적
기존 인증 토큰을 그대로 전달할 때 발생할 수 있는 노출 위험을 줄이고, 서비스 내부에서 필요한 인증 정보를 안전하게 처리하기 위해 사용합니다.

## 동장 방식

1. 사용자가 로그인시 인증 서버는 SecureRandom 을 사용한 64자리 무작위 문자열(알파벳 대소문자, 숫자)을 재료로 PhantomToken을 발급합니다.
2. 동시에 로그인 정보를 통해 JWT AccessToken 을 같이 발급합니다.
3. Redis에는 SHA-256 으로 PhantomToken을 Hash하여 Key로 저장하고, JWT AccessToken을 Value로 저장하게 됩니다.
4. 클라이언트(사용자)는 쿠키에 User-Authorization 이라는 이름으로 PhantomToken을 발급받습니다.
5. 이후 클라이언트는 요청에 발급받은 PhantomToken을 User-Authorization 쿠키에 자동 첨부되어 요청을 보내게 됩니다.
6. 게이트웨이는 해당 PhantomToken 정보를 바탕으로 인증 서버에 요청을 보내 JWT AccessToken을 교환받고 내부 서비스에는 이 AccessToken을 파싱 or 원문을 첨부하도록 할 예정입니다.