# Auth API     

## Registration

### Request

__Request format:__

```
POST [domain]:[port]/users
Content-Type: application/json
Accept: application/json
```

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| name          | string        |
| password      | string        |
| email         | string        |

__Request example:__

```
POST https://127.0.0.1:8080/users
Content-Type: application/json
Accept: application/json
{
    "name": "user",
    "password": "123",
    "email": "user@user.com"
}
```

### Response

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| token         | string        |

__Response example:__

```json
{
  "token": "some.JWT.token"
}
```

### Status codes

| Status code | Caused by                                    |
|:-----------:|----------------------------------------------|
| 201         | user registered successfully                 |
| 400         | empty/null registration fields               |
| 409         | user with such nickname/email already exists |
| 500         | internal                                     |


## Login

### Request

__Request format:__

```
GET [domain]:[port]/users/:name
Content-Type: application/json
Accept: application/json
```

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| name          | string        |
| password      | string        |

__Request example:__

```
GET https://127.0.0.1:8080/users/user
Content-Type: application/json
{
    "password": "123"
}
```

### Response

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| token         | string        |

__Response example:__

```json
{
  "token": "some.JWT.token"
}
```

### Errors

| Error code  | Caused by                 |
|:-----------:|---------------------------|
| 200         | user exists               |
| 400         | empty/null login fields   |
| 404         | no such user/unregistered |
| 500         | internal                  |