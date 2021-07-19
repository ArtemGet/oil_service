# Record/s delete api API

__Admin roots required!__

__This api deletes record/s by id__

## Request

__Request format:__

```
DELETE [domain]:[port]/api/records
Authorization: Bearer some-valid.JWT.token
Content-Type: application/json
```

__Request parameter description:__

| Field name       | Field value    | Possible values                 |
|:----------------:|:--------------:|:-------------------------------:|
| record_id_array  | arr[long]      | only positive numbers allowed   |

__Request example:__

```
DELETE https://127.0.0.1:8080/api/records
Authorization: Bearer some-valid.JWT.token
Content-Type: application/json
{
    "record_id_array": [3, 7, 8, 9, 11]
}
```

## Response
       
__only status code__

## Status codes

| Status code | Caused by                                    |
|:-----------:|----------------------------------------------|
| 201         | record/s successfully deleted                |
| 400         | invalid/empty parameters                     |
| 401         | unauthorized                                 |
| 403         | forbidden                                    |
| 500         | internal                                     |