CREATE DATABASE syntecxhub_payroll;
USE syntecxhub_payroll;
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    base_salary DOUBLE NOT NULL
);

SHOW TABLES;

UPDATE employees
SET
    name = 'Anuj Sharma',
    position = 'Frontend Developer'
WHERE id = 1;

SELECT * FROM employees;


UPDATE employees
SET base_salary = 75000
WHERE id = 1;

SELECT * FROM employees;





