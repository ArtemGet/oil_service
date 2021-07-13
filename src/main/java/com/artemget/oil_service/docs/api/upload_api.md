# Xlsx upload API    
__Admin roots required!__

## Request

__Request format:__

```
POST [domain]:[port]/api/handbooks/handbook
Authorization: Bearer some-valid.JWT.token
Content-Type: multipart/form-data;  boundary=some-boundary
Connection: keep-alive
```

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| file          | xlsx          |

__Request example:__

```
POST https://127.0.0.1:8080/api/handbooks/handbook
Authorization: Bearer some-valid.JWT.token
Content-Type: multipart/form-data;  boundary=---123
Connection: keep-alive
---123
Content-Disposition: form-data; name="file"
@someFile.xlsx
```

## Response   

Only status code will be sent.

## Status codes

| Status code | Caused by                                    |
|:-----------:|----------------------------------------------|
| 201         | file uploaded successfully                   |
| 400         | more then one file sent/invalid file format  |
| 401         | unauthorized                                 |
| 403         | forbidden                                    |
| 409         | such data already exists                     |
| 500         | internal                                     |