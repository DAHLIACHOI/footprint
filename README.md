### 🐾 당신의 발자취 리펙토링 
[이전 프로젝트 repository](https://github.com/UMC-Foot-Step/Server)
- SpringBoot 3.2.1
- Java 17
- Gradle

### ⚙️ 개선해야겠다고 느낀 점
> 첫 스프링 프로젝트였어서 코드가 깔끔하지 않고 가독성이 낮았던 것 같았다. <br>
> 또한, 코드의 중복이 많고, 성능 관련 고민을 해본 적이 없어서 리팩토링을 통해 테스트 코드 추가, 전체적인 로직 개선, 성능 테스트를 진행해보려고 한다.

<br>

<b> 🌱 이전 프로젝트와 다른 것 & 프로젝트 관련 개발 블로그 (개발하면서 추가 예정) </b>
- [글로벌 메시지 적용 (i18n 사용)](https://dahliachoi.tistory.com/105)
- [@PrePersist와 @PreUpdate](https://dahliachoi.tistory.com/111)
- 반환형식 정의 (CommonResult, SingleResult, ListResult)
- [@ExceptionHandler를 통한 예외 처리](https://dahliachoi.tistory.com/106)
- 메서드 분리 및 중복 코드 제거
- [@AllArgsConstructor, @RequiredArgsConstructor 사용 지양](https://dahliachoi.tistory.com/107)
- [JWT accessToken/RefreshToken 구현 (with. Redis)](https://dahliachoi.tistory.com/108)
- [게시글 등록하면서 리팩토링한 부분](https://github.com/DAHLIACHOI/TIL/blob/main/%EA%B0%9C%EB%B0%9C/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81/'%EB%8B%B9%EC%8B%A0%EC%9D%98%20%EB%B0%9C%EC%9E%90%EC%B7%A8'%20%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%97%85%EB%A1%9C%EB%93%9C%20%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81.md)
- [Optional 클래스의 orElse와 orElseGet 차이](https://dahliachoi.tistory.com/109)
- [N+1 문제와 fetch join 해결 및 테스트](https://dahliachoi.tistory.com/110)
- [HashCode와 equals 오버라이딩](https://dahliachoi.tistory.com/113)
- [Spring Cache 적용(조회 성능 개선)](https://dahliachoi.tistory.com/112)
- [enum을 사용한 하드 코딩 개선](https://github.com/DAHLIACHOI/TIL/blob/main/%EA%B0%9C%EB%B0%9C/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81/%ED%95%98%EB%93%9C%20%EC%BD%94%EB%94%A9%20%EA%B0%9C%EC%84%A0.md)


<br>

### ⚒️ 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> 
<br>

### 📄 API 명세서
| 기능 | Method | URI |
| :---: | :---: | :---: |
| 회원가입 | POST | /v1/join |
| 로그인 | POST | /v1/login |
| 토큰재발급 | POST | /v1/reissue |
| 게시글 등록 | POST | /v1/posting |
| 게시글 등록 | POST | /v2/posting |
| 게시글 등록 | POST | /v3/posting |
| 좋아요 등록 및 삭제 | POST | /v1/like/{posting-id} |
| 댓글 등록 | POST | /v1/comment/{posting-id} |
| 댓글 삭제 | DELETE | /v1/comment/{comment-id} |
| 모든 발자취(위치) 조회 | GET | /v1/places |
| 모든 발자취(위치) 조회 | GET | /v2/places |
| 특정 지역 발자취(위치) 조회 |  GET | /v1/places/{city} |

<br>

### 🫧 Git Convention
| keyword | description |
| :---:| :---:|
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 |
| style | 코드 스타일 변경 (기능 수정이 없는 경우) |
| design | 사용자 UI 디자인 변경 |
| test | 테스트 코드 추가 |
| refactor | 코드 리팩토링 |
| build | 빌드 파일 수정 |
| perf | 성능 개선 |
| del | 불필요한 코드 삭제 |
| chore | 빌드 업무 수정, 패키지 매니저 수정(gitignore...) |
| remove | 파일만 삭제 |
| rename | 파일 또는 폴더명만 수정 |
