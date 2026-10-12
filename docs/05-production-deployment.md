# 05 — Production Deployment (192.168.0.57)

## 1. Build

```bash
./mvnw clean verify          # compiles and runs tests
./mvnw clean package -DskipTests
```

The jar is `target/PerformanceTracker-0.0.1-SNAPSHOT.jar`.

## 2. Environment variables

`application.properties` defaults to the `dev` profile. Production **must** set
`SPRING_PROFILES_ACTIVE=prod`; otherwise TRACE security logging, SQL bind-value logging and
`ddl-auto=update` are active.

| Variable | Example | Notes |
|----------|---------|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` | required |
| `DB_URL` | `jdbc:postgresql://localhost:5432/performance_tracker` | |
| `DB_USERNAME` / `DB_PASSWORD` | | |
| `KEYCLOAK_ISSUER_URI` | `http://192.168.0.57:8081/realms/rkt` | must equal the `iss` claim the **browser** receives — same host/port the frontend uses to reach Keycloak |
| `KEYCLOAK_JWK_SET_URI` | `http://192.168.0.57:8081/realms/rkt/protocol/openid-connect/certs` | |
| `KEYCLOAK_SERVER_URL` | `http://192.168.0.57:8081` | |
| `KEYCLOAK_ADMIN_REALM` / `KEYCLOAK_TARGET_REALM` | `rkt` | |
| `KEYCLOAK_ADMIN_CLIENT_ID` | `performance-tracker-admin` | |
| `KEYCLOAK_ADMIN_CLIENT_SECRET` | *(from Keycloak → Clients → performance-tracker-admin → Credentials)* | rotate it — the old one was committed |
| `CORS_ALLOWED_ORIGINS` | `http://192.168.0.57:3000` | optional; default already allows `http(s)://192.168.0.57` on any port |

### systemd example (`/etc/systemd/system/performance-tracker.service`)

```ini
[Unit]
Description=Performance Tracker API
After=network.target postgresql.service

[Service]
User=pt
EnvironmentFile=/etc/performance-tracker/env
ExecStart=/usr/bin/java -jar /opt/performance-tracker/PerformanceTracker.jar
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

Put the variables above in `/etc/performance-tracker/env` (`chmod 600`).

## 3. Keycloak — changes to make in the running admin console

The realm JSON in `docker/keycloak/realm-data` is only read on a fresh import, so the running
Keycloak must be changed by hand:

1. **Clients → performance-tracker-frontend**
   - Web origins: add the frontend origin, e.g. `http://192.168.0.57:3000`
   - Valid redirect URIs and Valid post logout redirect URIs: add `http://192.168.0.57:3000/*`
2. **Clients → performance-tracker-admin → Credentials → Regenerate** the client secret, then
   update `KEYCLOAK_ADMIN_CLIENT_SECRET` and restart the API.
3. **Realm settings → General → Require SSL**: `External requests` (private 192.168.x.x addresses
   still work over HTTP).
4. **Realm settings → Security defenses → Brute force detection**: enable.
5. Because user password hashes and signing keys were in the committed export, ask users to reset
   passwords, and rotate realm keys (**Realm settings → Keys → Providers → add new rsa-generated
   provider with higher priority, then disable the old one**).

## 4. Reverse proxy

If nginx/Apache fronts the API, do **not** add `Access-Control-*` headers there — the application
already sends them, and duplicate headers are rejected by browsers as a CORS error.

## 5. Smoke test

```bash
curl -s -o /dev/null -w '%{http_code}\n' http://192.168.0.57:8080/actuator/health   # 200
curl -s -i -X OPTIONS http://192.168.0.57:8080/departments \
  -H 'Origin: http://192.168.0.57:3000' \
  -H 'Access-Control-Request-Method: GET' \
  -H 'Access-Control-Request-Headers: authorization' | grep -i access-control
# expect Access-Control-Allow-Origin: http://192.168.0.57:3000
```
