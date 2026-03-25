# 🏢 Enterprise Resource Planning (ERP) - HR & Payroll System

## 📖 Project Description
This ERP system is a robust backend solution for managing **Human Resources** and **Automated Payroll**. It allows institutions to manage employee data, compute complex salary structures based on current regulations, and automate notifications via email when salaries are approved.

### 🔑 Key Features
*   **Employee Management:** Full CRUD functionality for employee profiles and employment details.
*   **Automated Payroll:** One-click payroll generation for all active employees.
*   **Approval Workflow:** Secure process where Managers generate and Admins approve salary payments.
*   **Dynamic Calculations:** Automatically computes Housing (14%), Transport (14%), Pension (6%), and Taxes.
*   **Notification System:** Automated Database and Email triggers upon salary approval.
*   **Security:** Role-Based Access Control (RBAC) using JWT (JSON Web Tokens).

---

## 🛠️ Tech Stack
*   **Backend:** Spring Boot 3.4.5
*   **Language:** Java 25 (LTS)
*   **Database:** MariaDB / MySQL (via XAMPP)
*   **Security:** Spring Security with JWT Authentication
*   **Documentation:** Swagger UI (OpenAPI 3.0)
*   **Build Tool:** Maven

---

## 🚀 Getting Started

### 1. Prerequisites
*   **JDK 25** or higher.
*   **XAMPP** (with MySQL/MariaDB running).
*   **Maven** (included via `./mvnw`).

### 2. Database Setup
1.  Open **phpMyAdmin** (`http://localhost/phpmyadmin`).
2.  Create a new database named `ERP`.
3.  Import the provided `ERP.sql` file to set up the tables and initial data.

### 3. Environment Configuration
Create a `.env` file in the project root folder and add your credentials:

```env
# Database
DB_USER=root
DB_PASSWORD=your_password

# JWT Security
JWT_SECRET=your_super_secret_random_key_at_least_32_characters_long
JWT_EXPIRATION=86400000

# Email (Gmail App Password)
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_gmail_app_password
```

### 4. Running the Application
Open your terminal in the project root and run:
```bash
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```
The server will start on **`http://localhost:8000`**.

---

## 📑 API Documentation
Once the server is running, you can access the interactive API docs here:
👉 **[http://localhost:8000/swagger-ui/index.html](http://localhost:8000/swagger-ui/index.html)**

---

## 🧮 Salary Logic & Deductions
The system calculates salaries based on the following rules:

| Item | Type | Rate |
| :--- | :--- | :--- |
| **Housing Allowance** | Addition | 14% of Base Salary |
| **Transport Allowance** | Addition | 14% of Base Salary |
| **Employee Tax** | Deduction | 30% of Gross Salary |
| **Pension** | Deduction | **6% of Gross Salary** |
| **Medical Insurance** | Deduction | 5% of Gross Salary |
| **Others** | Deduction | 5% of Gross Salary |

---

## 🔐 Roles & Permissions
*   **ROLE_MANAGER:** Can register employees, manage deductions, and generate monthly payroll.
*   **ROLE_ADMIN:** Can view all records and has the exclusive right to **Approve Payroll**.
*   **ROLE_EMPLOYEE:** Can view their own personal profile and their personal payslip history.

*   **N.B:** the password for all users is *Password123!*.

---

## 📩 Automated Messaging
When a payroll is approved by an **Admin**:
1.  The Payslip status changes from `PENDING` to `PAID`.
2.  A record is created in the `message` table.
3.  An email is sent to the employee with the format:
    > *"Dear [First Name], Your salary for [Month/Year] from ERP Corp amounting to [Amount] has been successfully credited to your account ([ID]) successfully."*

---

## 📜 License
This project was developed as part of a technical assessment for an ERP solution.

**Developed by:** Musisi ☺️.