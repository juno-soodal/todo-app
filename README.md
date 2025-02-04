일정 관리 앱

사용자가 자신의 할 일을 등록하고, 수정 및 삭제할 수 있습니다.

주요 기능

일정 관리
일정 등록(Create)
일정 조회(Read)
일정 수정(Update)
일정 삭제(Delete)
페이네이션(Pagination) 적용

사용자 관리
작성자 등록 (중복 가능)

API 명세서

## POST /api/authors 작성자 생성
request body
```json
{
   "authorName": "string",
   "email": "string",
   "password": "string"
}
```
response 200
```json


{
   "id": 9007199254740991,
   "authorName": "string",
   "email": "string",
   "createdAt": "2025-02-04T00:50:11.336Z",
   "modifiedAt": "2025-02-04T00:50:11.336Z"
}
```
response 400
```json
{
   "error": {
      "message":""
   }
}
```
## 일정 관리

#### GET /api/authors/{authorId}/schedules 일정 전체 조회
#####  Path Variables
authorId : 3, scheduleId : 133
#### Parameters
```json
{
  "modifiedAt": "2025-02-04",
  "page": 1,
  "size": 10
}
```
response 200
```json
{
  "page": 1,
  "size": 10,
  "totalPages": 6,
  "totalElements": 51,
  "data": [
    {
      "id": 133,
      "toDo": "string",
      "authorName": "string",
      "createdAt": "2025-02-04T01:00:46.826Z",
      "modifiedAt": "2025-02-04T01:00:46.826Z"
    }
  ]
}
```
response 400
```json
{
  "error": {
    "message": ""
  }
}
```
#### GET /api/authors/{authorId}/schedules/{scheduleId}
##### parameters authorId : 3, scheduleId: 133
response 200
```json
{
  "data": {
    "id": 9007199254740991,
    "toDo": "string",
    "authorName": "string",
    "createdAt": "2025-02-04T01:03:19.374Z",
    "modifiedAt": "2025-02-04T01:03:19.374Z"
  }
}
```
response 400
```json
{
  "error": {
    "message": ""
  }
}
```
#### PUT /api/authors/{authorId}/schedules/{scheduleId} 일정 수정
##### parameters authorId : 3, scheduleId: 133
parameters
```json
{
  "toDo": "string",
  "password": "string"
}
```
response 200
```json
{
  "data": {
    "id": 9007199254740991,
    "toDo": "string",
    "authorName": "string",
    "createdAt": "2025-02-04T01:05:19.495Z",
    "modifiedAt": "2025-02-04T01:05:19.495Z"
  }
}
```
response 400
```json
{
  "error": {
    "message": ""
  }
}
```
#### DELETE /api/authors/{authorId}/schedules/{scheduleId} 일정 삭제
##### path authorId : 3, scheduleId: 133
##### query password: testasd
response 200 "삭제 성공"
#### POST /api/authors/{authorId}/schedules 일정 생성
paramters
```json
{
  "toDo": "string",
  "password": "string"
}
```
response 200
```json
{
  "data": {
    "id": 9007199254740991,
    "toDo": "string",
    "authorName": "string",
    "createdAt": "2025-02-04T01:08:36.828Z",
    "modifiedAt": "2025-02-04T01:08:36.828Z"
  }
}
```
response 400
```json
{
  "error": {
    "message": ""
  }
}
```