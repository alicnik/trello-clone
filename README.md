# Trello Clone - Server

## 💻 Local Development

### Requirements
- JDK 17
- Postgres

### Instructions
1. Create a local postgres database;
2. Set the following environment variables:
   1. `PGUSER` - your postgres username;
   2. `PGPASSWORD` - your postgres user's password;
   3. `PGHOST` - local postgres instance host (default`localhost`)
   4. `PGPORT` - local postgres instance port (default `5432`)
   5. `PGDATABASE` - the name of the database created at step 1;
3. Install Maven dependencies;
4. Run app;
5. Log in by sending a `POST` request to `http://localhost:8080/api/v1/login` with the `JSON` body:
   ```json
   {
      "username": "alicnik",
      "password": "alicnik"
   }
   ```
6. The response will contain an `access_token` which can be used as a `Bearer` token in the `Authorization` header on requests to any other endpoints.

## 🚀 Deployment

1. Run `./mvnw clean package -DskipTests`
2. Run `fly deploy`