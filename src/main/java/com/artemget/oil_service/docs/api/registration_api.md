# Registration API

## Request

__Request format:__

```
{domain}:{port}/register
```

__Request example:__

```
https://127.0.0.1:8080/register 

body:
{
    "name": "user",
    "password": "123",
    "email": "user@user.com"
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
400 - empty/null registration fields

409 - user with such nickname/email alredy exists
```