# Amortisation Calculator - Invoker Propagation + PostgreSQL + Capacitor

## What was added

1. **Invoker propagation**
   - Frontend captures an email address.
   - Frontend sends:
     - `X-Invoker-Id: amort-capacitor-app`
     - `X-Invoker-Username: <email address>`
   - Backend reads those headers and creates an `InvokerContext`.

2. **Automated request ID**
   - `RequestIdFilter` checks for `X-Request-Id`.
   - If missing, it generates a UUID.
   - The same request ID is returned in the response header and saved to PostgreSQL.

3. **PostgreSQL calculation history**
   - Added JPA entity: `CalculationHistory`.
   - Added repository: `CalculationHistoryRepository`.
   - The service saves each calculation with:
     - request ID
     - invoker ID
     - invoker username
     - IP address
     - timestamp
     - principal
     - annual rate
     - period months
     - inception date
     - monthly payment
     - total repayment
     - total interest

4. **Capacitor container**
   - Mobile project lives in `mobile-app`.
   - The web app lives in `mobile-app/www/index.html`.
   - Add Android/iOS platforms with Capacitor commands.

## PostgreSQL setup

Create the database:

```sql
CREATE DATABASE amortisation_db;
```

Default backend config is in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/amortisation_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

Change the username/password to match your local PostgreSQL credentials.

## Run backend

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Test API with curl

```bash
curl -X POST http://localhost:8080/api/amortisation \
  -H "Content-Type: application/json" \
  -H "X-Invoker-Id: amort-capacitor-app" \
  -H "X-Invoker-Username: user@example.com" \
  -d '{
    "principal": 70000,
    "annualRate": 10.5,
    "periodMonths": 48,
    "inceptionDate": "2026-06-25"
  }'
```

## Capacitor setup

From the `mobile-app` folder:

```bash
npm install
npx cap add android
npx cap add ios
npx cap sync
```

Open Android:

```bash
npx cap open android
```

Open iOS, macOS required:

```bash
npx cap open ios
```

## Important API URL for mobile

Inside `mobile-app/www/index.html`, update this line before testing on mobile:

```javascript
const API_URL = '/api/amortisation';
```

Use one of these values:

```javascript
// Android emulator
const API_URL = 'http://10.0.2.2:8080/api/amortisation';

// Real Android/iPhone on same Wi-Fi
const API_URL = 'http://YOUR_PC_WIFI_IP:8080/api/amortisation';

// Deployed backend
const API_URL = 'https://your-backend-url.com/api/amortisation';
```


## Latest UI/DB formatting update

- The `principal` field is now a text input with `inputmode="decimal"` so commas can be displayed while typing, for example `70,000.00`.
- Before sending to the API, commas are removed and the backend still receives a numeric `principal` value.
- `CalculationHistory` now stores money/rate values as `BigDecimal` with scale `2`, mapped to PostgreSQL `numeric(..., 2)`.
- The service rounds values using `RoundingMode.HALF_UP` before saving to `calculation_history`.

If your existing PostgreSQL table was created with `double precision`, run this once before retesting:

```sql
ALTER TABLE calculation_history
  ALTER COLUMN principal TYPE numeric(19,2) USING ROUND(principal::numeric, 2),
  ALTER COLUMN annual_rate TYPE numeric(10,2) USING ROUND(annual_rate::numeric, 2),
  ALTER COLUMN monthly_payment TYPE numeric(19,2) USING ROUND(monthly_payment::numeric, 2),
  ALTER COLUMN total_repayment TYPE numeric(19,2) USING ROUND(total_repayment::numeric, 2),
  ALTER COLUMN total_interest TYPE numeric(19,2) USING ROUND(total_interest::numeric, 2);
```
