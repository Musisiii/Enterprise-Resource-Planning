# 🏢 Enterprise Resource Planning (ERP) System

## 📖 Description
The Entreprise Resource Planning (ERP) system is a system for managing *financial* and *human resources* in institutions. It's core modules are:
1. Employee management: storage of employee details including personal information and professional employment details.

2. Payroll management system: managing employee compensation by calculating salary based on rules and regulations indicating taxes, deductions and base salary.
> ⚠️ Update: Pension rate increased from **3% → 6%**  
System must reflect this change in salary computation.

## 🎯 Tasks Overview

### 🧱 Backend Development
Develop backend with the following tables:

#### 1. Employee
- `id`
- `first_name`
- `last_name`
- `email`
- `password`
- `roles`
- `mobile`
- `date_of_birth`
- `status` (active, disabled)

#### 2. Employment
- `id`
- `employee_id`
- `department`
- `position`
- `base_salary`
- `status` (active, inactive)
- `joining_date`

#### 3. Deductions
- `id`
- `deduction_name`
- `percentage`

#### 4. Pay Slip
- `id`
- `employee`
- `housing`
- `transport`
- `employee_tax`
- `pension`
- `medical_insurance`
- `other_tax`
- `gross_salary`
- `net_salary`
- `month`
- `year`
- `status` (pending, paid)

---

### 👤 Employee Management
- Provide **CRUD APIs**
- Each employee is a **system user**

#### 🔐 Security
- Authentication: **Email + Password**
- Authorization: **Role-Based Access**

#### Roles
- `ROLE_MANAGER`
  - Add employees
  - Process payroll  

- `ROLE_ADMIN`
  - Approve salary  

- `ROLE_EMPLOYEE`
  - View personal details  
  - Download payslip  
  - View pending payments 

---

### 💸 Deductions & Taxes Management
Create endpoints to manage deductions.

#### Sample Deductions
- Employee Tax → **30%**
- Pension → **6%**
- Medical Insurance → **5%**
- Others → **5%**
- Housing → **14%**
- Transport → **14%**

---

### 🧮 Payroll Processing
- Manager generates payroll for a given **month/year**
- System computes:
  - Gross salary
  - Total deductions
  - Net salary  

#### Rules
- Only **active employees**
- Prevent deductions > gross salary
- Prevent **duplicate payroll** (same employee, same month/year)

#### Access
- 👤 Employee → view own payslip  
- 👨‍💼 Manager → view all payslips

---

### 📩 Messaging System

#### 1. Database Trigger
- When payroll is approved:
  - Status changes: `pending → paid`
  - Trigger generates message

#### Message Table
- `employee_id`
- `message`
- `month_year`

#### 2. Email Notification
Format:
Dear <First Name>,
Your salary for <Month/Year> from <Institution> amounting to <Amount>
has been successfully credited to your account (<Employee ID>) successfully.


---

## ⚙️ Technical Instructions

### 🛠️ Tech Stack
- Spring Boot
- Spring Data JPA
- JWT Authentication
- Swagger UI (API documentation)

---

## 🔐 Security Requirements
- JWT-based authentication
- Role-based authorization

---

## 🚫 Constraints
- No duplicate payroll per employee/month/year
- Data validation required at all stages

---

## 🧩 System Design Requirements

### 1. Flow Diagram
- Show full system workflow

### 2. Database Design (ERD)
- Entities & relationships

### 3. Architecture
- High-level system structure

### 4. POJOs
- Define all model classes

### 5. API Endpoints
- List and describe endpoints

### 6. Security Design
- Authentication & authorization flow

---

## 🧭 Summary
This system should:
- Manage employees 👥  
- Compute salaries 💰  
- Enforce security 🔐  
- Automate payroll ⚙️  
- Notify users 📩