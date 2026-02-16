-- ============================================
-- Employee Management System - Schema
-- ============================================

DROP DATABASE IF EXISTS EmployeeManagement;
CREATE DATABASE EmployeeManagement;
USE EmployeeManagement;

-- ----------------------------
-- Departments Table
-- ----------------------------
CREATE TABLE Departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL
);

-- ----------------------------
-- Employees Table (Self Referencing for Hierarchy)
-- ----------------------------
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    dept_id INT,
    manager_id INT,
    FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES Employees(emp_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ----------------------------
-- Projects Table
-- ----------------------------
CREATE TABLE Projects (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE
);

-- ----------------------------
-- Employee_Projects (Many-to-Many)
-- ----------------------------
CREATE TABLE Employee_Projects (
    emp_id INT,
    project_id INT,
    PRIMARY KEY (emp_id, project_id),
    FOREIGN KEY (emp_id) REFERENCES Employees(emp_id)
        ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES Projects(project_id)
        ON DELETE CASCADE
);

-- ----------------------------
-- Salaries Table
-- ----------------------------
CREATE TABLE Salaries (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT,
    base_salary DECIMAL(10,2) NOT NULL,
    bonus DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (emp_id) REFERENCES Employees(emp_id)
        ON DELETE CASCADE
);
