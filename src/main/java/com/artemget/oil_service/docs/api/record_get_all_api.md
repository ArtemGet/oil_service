# Record get API

__Admin roots required!__

__This api provides all stored records__

## Request

__Request format:__

```
GET [domain]:[port]/api/records
Authorization: Bearer some-valid.JWT.token
Accept: application/json
```

__Request example:__

```
GET https://127.0.0.1:8080/api/records
Authorization: Bearer some-valid.JWT.token
Accept: application/json
```

## Response

__Request parameter description:__

| Field name    | Field value    | Field description                      |
|:-------------:|:--------------:|:--------------------------------------:|
| id             | long          | record id                              |
| user_name      | string        | user who created record                |
| inserted       | long          | number of stored oils                  |
| corrupted      | long          | number of corrupted(not stored) oils   |
| insertion_date | string        | date in format: "yyyy-MM-dd HH:mm:ss"  |

__Response example:__

```json
{
  "records": [
    {
      "id": 12,
      "user_name": "admin1",
      "inserted": 440,
      "corrupted": 10,
      "insertion_date": "2021-07-19 17:07:40"
    },
    {
      "id": 13,
      "user_name": "admin2",
      "inserted": 10,
      "corrupted": 45,
      "insertion_date": "2021-07-19 17:08:03"
    }
  ]
}
```

## Status codes

| Status code | Caused by                                    |
|:-----------:|----------------------------------------------|
| 200         | records found, request succeed               |
| 401         | unauthorized                                 |
| 403         | forbidden                                    |
| 404         | no such record/s                             |
| 500         | internal                                     |