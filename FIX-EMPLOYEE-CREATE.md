# Employee Create Fix

The employee `password` field was marked with `@JsonIgnore` on the getter.
That can cause Jackson to ignore the property during request-body binding,
so `EmployeeService.save()` received `password == null` and threw
`Password is required`.

The entity now uses:

```java
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;
```

This accepts `password` from POST/PUT JSON but does not serialize it in API
responses.

Restart Spring Boot after replacing the backend.

Example request:

```json
{
  "name": "Siva",
  "employeeId": "E1",
  "username": "SivaE1",
  "email": "sivaE1@gmail.com",
  "phone": "9098909890",
  "password": "siva123"
}
```

The frontend already sends these exact property names.
