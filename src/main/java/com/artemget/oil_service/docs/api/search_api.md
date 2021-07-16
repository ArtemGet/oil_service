# Oil search API

__This api search for oil by one of the density20/density50/viscosity20/viscosity50 parameter,    
the backend will find the closest oils limited by request__

## Request

__Request format:__

```
GET [domain]:[port]/api/oils/:param/:limit
Authorization: Bearer some-valid.JWT.token
Accept: application/json
```

__Request parameter description:__

| Field name    | Field value  | Possible values                                 |
|:-------------:|:------------:|:-----------------------------------------------:|
| param          | string      | density20, density50, viscosity20, viscosity50  |
| value          | double      | only positive                                   |
| limit          | long        | only positive                                   |

__Request example:__

```
GET https://127.0.0.1:8080/api/oils/density20/1
Authorization: Bearer some-valid.JWT.token
Accept: application/json
{
value = 0.123
}
```

## Response

__Request parameter description:__

| Field name    | Field value   |
|:-------------:|:-------------:|
| name          | string        |
| output_place  | string        |
| p20           | double        |
| p50           | double        |
| v20           | double        |
| v50           | double        |
| hk_350        | double        |

__Response example:__

```json
{
  "oils": [
    {
      "name": "some oil",
      "output_place": "some place",
      "p20": 0.8573,
      "p50": 0.83633,
      "v20": 10.23,
      "v50": 4.87,
      "hk_350": 52.6
    },
    {
      "name": "some oil 2",
      "output_place": "some place 2",
      "p20": 0.8567,
      "p50": 0.83573,
      "v20": 17.39,
      "v50": 7.19,
      "hk_350": 49.0
    }
  ]
}
```

## Status codes

| Status code | Caused by                                    |
|:-----------:|----------------------------------------------|
| 200         | oil found, request succeed                   |
| 400         | invalid/empty parameters                     |
| 401         | unauthorized                                 |
| 403         | forbidden                                    |
| 404         | no such oil                                  |
| 500         | internal                                     |