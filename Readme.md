# Calendar API 명세서

## Calendar API

| 기능 | URL | Method | Path/Query Params | Request Body | Response | 예외 |
|-----|-----|--------|-------------------|--------------|----------|------|
| **전체 일정 조회** | `/api/schedule/all/{id}` | `GET` | `id` (Long): 사용자 ID | - | `List<Calendar>` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **단일 일정 조회** | `/api/schedule/portion` | `GET` | - | `User`: { id, name, password, email } | `Calendar` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **페이지별 일정 조회** | `/api/schedule/page-nation` | `GET` | `page` (기본값: 1)<br>`pageSize` (기본값: 10) | - | `List<Calendar>` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **일정 추가** | `/api/schedule` | `POST` | - | `AllRounder`: { calendar, user } | `Calendar` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **일정 수정** | `/api/schedule` | `PATCH` | - | `AllRounder`: { calendar, user } | `Calendar` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **일정 삭제** | `/api/schedule/{details}` | `DELETE` | `details` (String): 삭제할 일정 내용 | - | - | `ClassNotFoundException`, `SQLException`, `IdException` |

## User API

| 기능 | URL | Method | Path/Query Params | Request Body | Response | 예외 |
|-----|-----|--------|-------------------|--------------|----------|------|
| **로그인** | `/api/user/login` | `POST` | - | `User`: { name, password } | `User` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **회원가입** | `/api/user/new` | `POST` | - | `User`: { name, password, email } | `User` | `ClassNotFoundException`, `SQLException`, `IdException` |
| **사용자명 변경** | `/api/user/change-username` | `PATCH` | - | `AllRounder`: { user, userNameChange } | `User` | `ClassNotFoundException`, `SQLException`, `IdException` |

## Request/Response DTO

### Calendar