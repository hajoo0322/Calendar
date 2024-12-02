# API 명세서

## 일정관리 API

### 1. 일정 생성 (POST /schedule)
- **설명**: 새로운 일정을 생성합니다.
- **요청 URL**: `/api/schedule`
- **요청 메서드**: `POST`
- **요청 헤더**:
    - Content-Type: application/json
- **요청 바디**:
  ```json
  {
    "name": "Ha Joo",
    "day" : "20",
    "detail" : "do my best"
  }

### 2. 일정 조회 (GET /schedule)
- **설명**: 일정을 조회합니다.
- **요청 URL**: `/api/schedule`
- **요청 메서드**: `GET`
- **요청 파라미터**: "day"

### 3. 일정 수정 (PUT /schedule/{masterId})
- **설명**: 일정을 수정합니다.
- **요청 URL**: `/api/schedule/{masterId}`
- **요청 메서드**: `PUT`
- **요청 헤더**:
    - Content-Type: application/json
- **요청 바디**:
  ```json
  {
    "name": "Ha Joo",
    "day" : "20",
    "detail" : "do my best of best"
  }
### 4. 일정 삭제 (DELETE /schedule/{masterId})
- **설명**: 일정을 삭제합니다.
- **요청 URL**: `/api/schedule/{masterId}`
- **요청 메서드**: `DELETE`
- **요청 파라미터**: "day"