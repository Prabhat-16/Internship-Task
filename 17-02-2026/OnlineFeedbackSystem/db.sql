CREATE DATABASE college_db;

USE college_db;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    prn VARCHAR(20) UNIQUE,
    name VARCHAR(100),
    course VARCHAR(50),
    cet_marks DOUBLE
);
