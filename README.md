# GaraMan Java web application

This project implements the two GaraMan use cases with Jakarta Servlet 6, JSP, JDBC, MySQL 8, and Tomcat 10.1. Login checks credentials in `User_0` and routes customers to catalog search and managers to revenue statistics. The salesperson ID remains in historical invoice rows because it is part of the approved database schema; no sales-staff workflow is implemented.

Demo accounts after running `database/garaman.sql`:

- Customer: `customer1` / `12345`
- Statistics staff (`Manager0`): `manager1` / `12345`

These credentials are intended for the classroom demonstration.

## Run with Docker Desktop

From this directory, run:

```powershell
docker compose up --build
```

Then open [http://localhost:8080](http://localhost:8080). Compose starts MySQL, creates the schema and sample data on the first database initialization, builds the WAR with Maven, and starts Tomcat. Keep the terminal open while demonstrating; stop with `Ctrl+C`, then run `docker compose down`.

The application uses local demo credentials defined in `compose.yaml`. Do not reuse them for a real deployment.

## Run with an existing MySQL and Tomcat installation

1. Create the database and tables by running `database/garaman.sql` in MySQL Workbench or the MySQL command line.
2. Build the WAR from this directory:

   ```powershell
   mvn package
   ```

3. Copy `target/garaman.war` into Tomcat 10.1's `webapps` directory.
4. Configure the connection before starting Tomcat. The defaults in `Database0.java` are URL `jdbc:mysql://localhost:3306/garaman_db`, user `root`, and an empty password. Set these environment variables to override them:

   ```powershell
   $env:GARAMAN_DB_URL = 'jdbc:mysql://localhost:3306/garaman_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh'
   $env:GARAMAN_DB_USER = 'root'
   $env:GARAMAN_DB_PASSWORD = 'your-local-mysql-password'
   ```

5. Start Tomcat and open its root URL, normally `http://localhost:8080/`.

## Implemented flows

### Login and role access

- `POST /login` checks the supplied username and password in `User_0` through `UserDao0`.
- Customers (`Customer0`) are redirected to catalog search; managers (`Manager0`) are redirected to statistics.
- `RoleFilter0` requires a session and restricts customer and manager routes to their own module. `POST /logout` clears the session.

### Module 1 — Search service/part information

- `GET /search?keyword=...` searches `Service_0` and `Part_0` by name with the entered substring.
- `GET /item?type=service|part&id=...` loads one result's details from its corresponding table.
- Empty results show the not-found message.

### Module 2 — Customer revenue statistics

- `GET /statistics?startDate=...&endDate=...` validates the range and aggregates `Invoice_0.total_amount` by customer for the selected payment dates.
- `GET /history?customerId=...&startDate=...&endDate=...` shows that customer's repair invoices in the selected period.
- `GET /invoice?invoiceId=...` loads invoice data and service/part lines from `Invoice_0`, `InvoiceServiceDetail_0`, `InvoicePartDetail_0`, `Service_0`, and `Part_0`.
- Invalid ranges are rejected; empty result sets show a no-data message.

## Database alignment

`database/garaman.sql` creates the eleven tables in the approved ERD: `User_0`, `Customer_0`, `Supplier_0`, `Service_0`, `Part_0`, `Appointment_0`, `Invoice_0`, `InvoiceServiceDetail_0`, `InvoicePartDetail_0`, `ImportInvoice_0`, and `ImportDetail_0`. It keeps the ERD's primary keys, foreign keys, and attributes. `DECIMAL` is used for money columns to avoid floating-point rounding; the conceptual price attributes map to these SQL money types.

The seed data is fictional and exists only to demonstrate both modules. The customer revenue list and all detail pages are queried from MySQL; no business result is hard-coded into the JSP pages. Login is checked against `User_0`, and access to the customer and manager routes is restricted by role.

## Project structure

```text
java-web/
├── database/garaman.sql
├── src/main/java/vn/garaman/
│   ├── dao/       # Prepared JDBC queries
│   ├── db/        # MySQL connection
│   ├── model/     # Entity and result models
│   └── web/       # Servlet handlers for the screens
├── src/main/webapp/
│   ├── WEB-INF/views/  # JSP screens
│   └── assets/app.css
├── compose.yaml
├── Dockerfile
└── pom.xml
```
