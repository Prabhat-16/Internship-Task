USE EmployeeManagement;

-- ============================================
-- Complex Queries
-- ============================================

-- 1️⃣ Employees Working on Multiple Projects
SELECT e.emp_id,
       e.emp_name,
       COUNT(ep.project_id) AS total_projects
FROM Employees e
JOIN Employee_Projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id, e.emp_name
HAVING COUNT(ep.project_id) > 1;


-- 2️⃣ Total Salary per Department
SELECT d.dept_name,
       SUM(s.base_salary + s.bonus) AS total_salary
FROM Departments d
JOIN Employees e ON d.dept_id = e.dept_id
JOIN Salaries s ON e.emp_id = s.emp_id
GROUP BY d.dept_name;


-- 3️⃣ Manager → Subordinates
SELECT m.emp_name AS Manager,
       e.emp_name AS Subordinate
FROM Employees e
JOIN Employees m ON e.manager_id = m.emp_id;


-- 4️⃣ Full Hierarchy (Recursive CTE - MySQL 8+)
WITH RECURSIVE EmployeeHierarchy AS (
    SELECT emp_id, emp_name, manager_id
    FROM Employees
    WHERE manager_id IS NULL

    UNION ALL

    SELECT e.emp_id, e.emp_name, e.manager_id
    FROM Employees e
    INNER JOIN EmployeeHierarchy eh
        ON e.manager_id = eh.emp_id
)
SELECT * FROM EmployeeHierarchy;
