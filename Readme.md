# **API 명세서**

## **캘린더 API**

| **기능**                  | **URL**                             | **Method** | **Path/Query Params**                                                                                  | **Request Body**                                                                                                                                           | **Response**                         | **예외**                                 |
|---------------------------|-------------------------------------|------------|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|-----------------------------------------|
| **캘린더 전체 조회**       | `/api/schedule/getAll/{date}/{id}` | `GET`      | `id` (Long): 사용자 ID<br>`date` (String): 조회할 날짜                                                 | 없음                                                                                                                                                       | `List<Calendar>`: 캘린더 리스트       | `ClassNotFoundException`, `SQLException`|
| **캘린더 일부 조회**       | `/api/schedule/getPortion`         | `GET`      | 없음                                                                                                  | ```json<br>{<br>  "id": 1,<br>  "name": "John Doe",<br>  "password": "password123"<br>}```                                                                  | `Calendar`: 특정 캘린더 정보          | `ClassNotFoundException`, `SQLException`|
| **캘린더 페이지 조회**     | `/api/schedule/pageGet`            | `GET`      | `page` (int): 페이지 번호 (기본값 1)<br>`pageSize` (int): 페이지 크기 (기본값 10)                      | 없음                                                                                                                                                       | `List<Calendar>`: 페이지별 캘린더     | `ClassNotFoundException`, `SQLException`|
| **캘린더 추가**            | `/api/schedule/addCalendar`        | `POST`     | 없음                                                                                                  | ```json<br>{<br>  "userId": 1,<br>  "calendar": {<br>    "date": "2023-12-01",<br>    "details": "Meeting"<br>  }<br>}```                                   | 없음                                 | `ClassNotFoundException`, `SQLException`|
| **캘린더 세부내용 변경**    | `/api/schedule/changeDetails/{detail}` | `PUT`      | `detail` (String): 변경할 세부내용                                                                    | ```json<br>{<br>  "userId": 1,<br>  "calendar": {<br>    "id": 1,<br>    "details": "Updated details"<br>  }<br>}```                                        | `Calendar`: 변경된 캘린더 정보         | `ClassNotFoundException`, `SQLException`|
| **캘린더 삭제**            | `/api/schedule/delete/{details}`   | `DELETE`   | `details` (String): 삭제할 캘린더의 세부내용                                                          | 헤더: `name` (String): 사용자 이름<br>`password` (String): 사용자 비밀번호                                                                                   | 없음                                 | `ClassNotFoundException`, `SQLException`|

---

## **유저 API**

| **기능**                  | **URL**                             | **Method** | **Path/Query Params** | **Request Body**                                                                                                                                           | **Response**                         | **예외**                                 |
|---------------------------|-------------------------------------|------------|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|-----------------------------------------|
| **유저 로그인**            | `/api/user/login`                  | `POST`     | 없음                  | ```json<br>{<br>  "name": "John Doe",<br>  "password": "password123"<br>}```                                                                               | `User`: 로그인된 유저 정보            | `ClassNotFoundException`, `SQLException`|
| **유저 추가**              | `/api/user/addUser`                | `POST`     | 없음                  | ```json<br>{<br>  "name": "John Doe",<br>  "password": "password123"<br>}```                                                                               | `User`: 추가된 유저 정보              | `ClassNotFoundException`, `SQLException`|
| **유저 이름 변경**         | `/api/user/changeUserName`         | `PUT`      | 없음                  | ```json<br>{<br>  "userId": 1,<br>  "newName": "Jane Doe"<br>}```                                                                                           | `User`: 변경된 유저 정보              | `ClassNotFoundException`, `SQLException`|
