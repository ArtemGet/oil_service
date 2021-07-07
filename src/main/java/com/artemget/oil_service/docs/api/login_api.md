# Login API

## Request

__Request format:__

```
GET [domain]:[port]/users/user
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
    "name": "user",
    "password": "123"
}
```

## Response

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

## Errors

| Error code  | Caused by                 |
|:-----------:|---------------------------|
| 200         | user exists               |
| 400         | empty/null login fields   |
| 404         | no such user/unregistered |
| 500         | internal                  |