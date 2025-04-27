
# 🏦 Bank Management System (Spring Boot Project)

A **full-stack backend** for managing bank accounts and transactions with **Spring Boot**, **Spring Security**, and **H2 Database**.

---

## 📖 About

This project supports:
- Creating user accounts
- Performing deposits and withdrawals
- Tracking transaction histories
- Managing user authentication using Spring Security
- Fetching top depositors and transaction statistics

---

## ⚙️ Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 In-Memory Database
- Maven

---

## 📁 Project Structure

```
src/main/java/com/darpan/finalbankmanagement/
├── configuration/
│   └── SecurityConfig.java
├── controller/
│   ├── AccountController.java
│   └── TransactionController.java
├── dto/
│   ├── MaxTransactionCount.java
│   └── TopDepositors.java
├── entity/
│   ├── Transaction.java
│   ├── UserAccount.java
│   └── UserPrincipal.java
├── repository/
│   ├── AccountRepository.java
│   └── TransactionRepository.java
├── services/
│   ├── AccountService.java
│   └── TransactionService.java
├── servicesIml/
│   ├── AccountServiceImp.java
│   └── TransactionServiceImp.java
├── UserPrincipalService.java
└── FinalBankManagementApplication.java
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17
- Maven

### Steps to Run

1. Clone the repository:
    ```bash
    git clone https://github.com/Darpan3011/Bank-Management-System-Java-SpringBoot.git
    cd Bank-Management-System-Java-SpringBoot
    ```

2. Build and run the project:
    ```bash
    mvn spring-boot:run
    ```

3. Open H2 Console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (leave empty)

---

## 📚 API Endpoints

| Method | Endpoint | Description |
|:-------|:---------|:------------|
| POST | `/account/register` | Register new user |
| POST | `/account/login` | Authenticate user |
| POST | `/account/deposit` | Deposit money |
| POST | `/account/withdraw` | Withdraw money |
| GET  | `/account/{accountId}` | Get account details |
| GET  | `/transaction/{accountId}` | Get transaction history |
| GET  | `/transaction/topDepositors` | Top users by deposit |
| GET  | `/transaction/maxTransaction` | User with maximum transaction count |

⚡ **Authentication required** for most APIs (Basic Auth).

---

## 📑 Example JSON Requests

**Register User**
```json
POST /account/register
{
  "accountHolderName": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Deposit**
```json
POST /account/deposit
{
  "accountId": 1,
  "amount": 1000
}
```

**Withdraw**
```json
POST /account/withdraw
{
  "accountId": 1,
  "amount": 500
}
```

---

## 🔐 Security

- Basic Authentication enabled (`username/password`)
- User role management via `UserPrincipal` and `SecurityConfig`
- Password encoding using Spring Security standards

---

## 🔮 Future Enhancements

- JWT Authentication
- Email notification for transactions
- Scheduled bank statement generation
- Deployment to AWS
---

## 🙌 Thank you for visiting!
