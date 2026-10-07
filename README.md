# 🛒 Supermarket Management System

A desktop-based **Supermarket Management System** developed using **Java Swing, MySQL, JDBC, 
NetBeans, and JasperReports**. The system is designed to support essential supermarket operations 
including product management, customer management, supplier management, sales transactions, 
user authentication, dashboard monitoring, and report generation.

---

## 📌 Project Overview

The **Supermarket Management System** is a Java desktop application developed to computerize and
simplify day-to-day supermarket operations.

The application provides a centralized system for managing products, customers, suppliers, 
sales transactions, and users while maintaining the relevant information in a MySQL relational 
database.

The system also includes **JasperReports** for generating management-oriented reports from 
multiple database tables.

This project was developed as part of the **Enterprise Application Development 1 (EAD1)** 
coursework.



## 🎯 Objectives

The main objectives of this project are to:

- Develop a user-friendly supermarket management application.
- Manage supermarket products and stock information efficiently.
- Maintain customer and supplier records.
- Process and record sales transactions.
- Provide secure user login and role-based access.
- Store application data using a MySQL relational database.
- Generate useful reports for management decision-making.
- Apply object-oriented programming and enterprise application development concepts.
- Provide a structured and maintainable Java application.



## ✨ Key Features

### 🔐 User Authentication

- User login system.
- Username and password authentication.
- Role-based user access.
- Supports administrator and cashier roles.

### 📊 Dashboard

- Centralized application dashboard.
- Provides access to the major system modules.
- Organizes supermarket operations through a simple desktop interface.

### 📦 Product Management

- Add new products.
- View product information.
- Update product details.
- Delete products.
- Manage product prices.
- Manage available stock quantities.
- Associate products with categories and suppliers.

### 👥 Customer Management

- Add customer records.
- View customer information.
- Update customer details.
- Maintain customer contact information.
- Support walk-in customers.

### 🚚 Supplier Management

- Add supplier records.
- View supplier information.
- Update supplier details.
- Maintain supplier phone, email, and address information.

### 🧾 Sales Transactions

- Create sales transactions.
- Select products for a sale.
- Enter required quantities.
- Calculate item subtotals.
- Calculate transaction totals.
- Associate sales with customers.
- Record cashier information.
- Store completed sales in the database.

### 📈 Reporting

- Generate reports using **JasperReports**.
- Use information from multiple database tables.
- Support management-oriented analysis of sales information.
- Provide a structured format for viewing and printing reports.

### 🗄️ Database Management

The application uses a relational MySQL database named:

```text
supermarket_db
```

The database contains the following main tables:

```text
category
customer
product
sale
sale_item
supplier
users
```



## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| **Java** | Application development |
| **Java Swing** | Graphical User Interface |
| **MySQL** | Database management |
| **JDBC** | Java–MySQL database connectivity |
| **NetBeans IDE** | Development environment |
| **JasperReports** | Report generation |
| **Apache Ant** | Project build management |
| **Git & GitHub** | Version control and source code management |



## 🏗️ Application Structure

The project follows a structured Java application organization with separate areas for 
application source code, project configuration, build files, compiled output, 
and database resources.

```text
SupermarketSystem/
│
├── src/
│   └── supermarket/
│       ├── dao/
│       ├── model/
│       └── view/
│
├── nbproject/
│
├── dist/
│
├── build.xml
├── manifest.mf
├── supermarket_db.sql
└── README.md
```

### Main Package Responsibilities

#### `model`

Contains Java model classes representing application data and entities.

Examples include:

- Product
- Customer
- Supplier
- Sale
- Other system entities

#### `dao`

Contains Data Access Object classes responsible for communicating with the MySQL database.

The DAO layer helps separate database operations from the user interface.

#### `view`

Contains the Java Swing forms used to interact with the system.

Examples include:

- Login
- Dashboard
- Product Management
- Customer Management
- Supplier Management
- Sales Transaction
- Reports



## 🗃️ Database Design

The application uses a relational database called `supermarket_db`.

### Database Tables

| Table | Purpose |
|---|---|
| `users` | Stores login credentials and user roles |
| `category` | Stores product categories |
| `product` | Stores product details, prices and stock |
| `customer` | Stores customer information |
| `supplier` | Stores supplier information |
| `sale` | Stores sales transaction headers |
| `sale_item` | Stores individual items belonging to sales |

### Main Relationships

```text
Category
   │
   └────────────── Product
                       │
                       │
Supplier ─────────────┘

Customer
   │
   └────────────── Sale
                       │
                       └──────────── Sale_Item
                                           │
                                           └──── Product

Users
   │
   └──── Authentication / Roles
```

The `sale` and `sale_item` tables separate transaction-level information from individual 
purchased items, allowing sales information to be stored in a structured relational form.



## 💻 System Requirements

Before running the application, make sure the following software is installed:

- **JDK**
- **Apache NetBeans**
- **MySQL Server / XAMPP**
- **MySQL JDBC Connector**
- **JasperReports libraries**

A Java Development Kit compatible with the project's configured Java version should be used.



# 🚀 Installation & Setup

Follow the steps below to run the application locally.

## Step 1 — Clone the Repository

Open Git Bash or a terminal and run:

```bash
git clone https://github.com/kavya-liyanage433/SupermarketSystem.git
```

Then move into the project directory:

```bash
cd SupermarketSystem
```



## Step 2 — Open the Project in NetBeans

1. Open **Apache NetBeans**.
2. Select:

```text
File → Open Project
```

3. Select the cloned `SupermarketSystem` folder.
4. Open the project.
5. Allow NetBeans to load the project configuration.

The project is configured as a NetBeans/Ant-based Java project.



## Step 3 — Create the Database

Open **MySQL**, **phpMyAdmin**, or another MySQL-compatible database management tool.

Create the database:

```sql
CREATE DATABASE supermarket_db;
```

Select the database:

```sql
USE supermarket_db;
```



## Step 4 — Import the Database

The repository contains:

```text
supermarket_db.sql
```

Import this SQL file into the `supermarket_db` database.

### Using phpMyAdmin

1. Open phpMyAdmin.
2. Create/select:

```text
supermarket_db
```

3. Select the **Import** tab.
4. Choose:

```text
supermarket_db.sql
```

5. Click **Import**.

The SQL script creates the required tables and includes sample data.



## Step 5 — Configure the Database Connection

Open the database connection class inside the project.

Make sure the connection settings match your local MySQL configuration.

Typical configuration:

```java
String url = "jdbc:mysql://localhost:3306/supermarket_db";
String username = "root";
String password = "";
```

If your MySQL installation uses a different username or password, update the values accordingly.

> **Important:** Do not upload real database passwords, production credentials, or other
> sensitive information to GitHub.



## Step 6 — Check the MySQL JDBC Driver

Make sure the project has the **MySQL Connector/J** library available.

The JDBC driver is required for communication between the Java application and MySQL database.

If NetBeans displays an error such as:

```text
No suitable driver found
```

or:

```text
ClassNotFoundException: com.mysql.cj.jdbc.Driver
```

check that the MySQL Connector/J library has been added to the project's Libraries.



## Step 7 — Build the Project

In NetBeans:

```text
Right-click Project
        ↓
Clean and Build
```

Resolve any missing libraries before running the application.

---

## Step 8 — Run the Application

After a successful build:

```text
Right-click Project
        ↓
Run
```

The application should open with the login interface.



# 🔑 Login Credentials

The database currently contains sample users for testing.

### Administrator

```text
Username: admin
Password: admin123
Role: admin
```

### Cashier

```text
Username: cashier
Password: cashier123
Role: cashier
```

> These credentials are included for development/testing purposes only. They should be changed
> or removed before deploying the system in a real production environment.



# 🧑‍💼 User Roles

The system supports different user roles.

### Administrator

The administrator can access management-oriented functionality such as:

- Dashboard
- Product management
- Customer management
- Supplier management
- Sales information
- Reports

### Cashier

The cashier role is intended for operational activities such as:

- Accessing the system
- Processing sales transactions
- Selecting products
- Recording customer information
- Completing sales

The exact permissions available to each role depend on the application's implemented 
access-control logic.



# 📊 Reporting

The application uses **JasperReports** to generate reports.

Reports are designed to provide useful information for supermarket management and decision-making.

The database supports report generation using information from multiple related tables, 
including:

```text
Customer
     ↓
Sale
     ↓
Sale Item
     ↓
Product
     ↓
Category / Supplier
```

This relational structure allows meaningful sales information to be retrieved and presented in 
report format.



# ✅ Validation & Exception Handling

The application is designed to validate user input before performing important operations.

Examples of validation areas include:

- Required fields
- Numeric values
- Product quantities
- Prices
- Customer information
- Database operations
- Sales transaction data

Database-related exceptions are handled to prevent unexpected application termination and to 
provide meaningful feedback to the user.



# 🔄 Sales Transaction Workflow

The main sales process can be represented as follows:

```text
Login
  │
  ▼
Dashboard
  │
  ▼
Sales Transaction
  │
  ▼
Select Customer
  │
  ▼
Select Product
  │
  ▼
Enter Quantity
  │
  ▼
Calculate Subtotal
  │
  ▼
Calculate Total
  │
  ▼
Save Sale
  │
  ├──────────────► Sale
  │
  └──────────────► Sale Item
                         │
                         ▼
                      Product
```



# 🧩 Design & Development Concepts

The project demonstrates important enterprise application development concepts including:

- Object-Oriented Programming
- Separation of responsibilities
- DAO-based database access
- Model classes
- GUI-based application development
- Relational database design
- CRUD operations
- JDBC connectivity
- Input validation
- Exception handling
- Report generation
- User authentication
- Role-based functionality



# 📁 Important Project Files

| File / Folder | Description |

| `src/` | Java source code |
| `src/.../dao/` | Database access classes |
| `src/.../model/` | Application model classes |
| `src/.../view/` | Java Swing user interfaces |
| `nbproject/` | NetBeans project configuration |
| `dist/` | Build/distribution output |
| `build.xml` | Apache Ant build configuration |
| `manifest.mf` | Application manifest |
| `supermarket_db.sql` | Database structure and sample data |
| `README.md` | Project documentation |



# 🔒 Security Considerations

This project is intended primarily for academic and development purposes.

For production deployment, the following improvements are recommended:

- Hash passwords instead of storing plain-text passwords.
- Use environment variables for database credentials.
- Implement stronger role-based access control.
- Validate and sanitize all user input.
- Use prepared statements consistently.
- Add audit logging for important operations.
- Protect sensitive configuration information.
- Use secure database credentials instead of default credentials.



# 🚧 Future Improvements

Possible future enhancements include:

- Advanced inventory management.
- Low-stock notifications.
- Barcode scanning.
- Product search and filtering.
- Advanced sales analytics.
- Profit and revenue analysis.
- Printable customer invoices.
- Password hashing and improved authentication.
- User management interface.
- Backup and restore functionality.
- Advanced dashboard charts.
- More comprehensive JasperReports.
- Improved error logging.
- Automated testing.
- Production deployment support.



# 🎓 Academic Context

This project was developed as part of the **Enterprise Application Development 1 (EAD1)** 
coursework.

The project focuses on developing an enterprise-style desktop application with:

- User interfaces
- Transaction functionality
- Database operations
- Design and development practices
- Dashboard functionality
- Management reports
- Validation and exception handling
- Deployment/build support

The project scope is based on the **Sales** domain.



# 👩‍💻 Author

**Kavya Liyanage**

GitHub:  
https://github.com/kavya-liyanage433

Project Repository:  
https://github.com/kavya-liyanage433/SupermarketSystem



# 📜 License

This project was developed for **academic/educational purposes** a
s part of the Enterprise Application Development 1 coursework.

Unless otherwise specified, the source code should not be treated as an open-source project 
with permission for unrestricted commercial redistribution.



## ⭐ Project Summary

The **Supermarket Management System** provides a structured desktop solution 
for managing essential supermarket activities. By combining **Java Swing**, **MySQL**, **JDBC**, 
and **JasperReports**, the application demonstrates the development of a database-driven 
enterprise application with user authentication, product management, 
customer and supplier management, sales processing, and reporting capabilities.

