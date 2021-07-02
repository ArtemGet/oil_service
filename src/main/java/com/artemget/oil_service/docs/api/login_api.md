# Login API

## Request

__Request format:__

```
{domain}:{port}/login
```

__Request example:__

```
https://127.0.0.1:8080/login 

body:
{
    "name": "user",
    "password": "123"
}
```

## Response

__Response format:__

```json
{
  "token": "some.JWT.token"
}
```

## Errors

__Possible Errors__

```
400 - empty/null login fields

404 - no such user/unregistered
```