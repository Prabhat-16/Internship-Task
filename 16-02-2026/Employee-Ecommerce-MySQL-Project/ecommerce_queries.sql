USE EcommerceAnalytics;

-- ============================================
-- Advanced Analytics Queries
-- ============================================

-- 1️⃣ Top 5 Selling Products
SELECT p.product_name,
       SUM(oi.quantity) AS total_sold
FROM Products p
JOIN Order_Items oi ON p.product_id = oi.product_id
GROUP BY p.product_id
ORDER BY total_sold DESC
LIMIT 5;


-- 2️⃣ Monthly Sales Trend
SELECT DATE_FORMAT(o.order_date, '%Y-%m') AS month,
       SUM(oi.quantity * p.price) AS total_sales
FROM Orders o
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
GROUP BY month
ORDER BY month;


-- 3️⃣ Customer Purchase Pattern (Total Spending)
SELECT c.customer_name,
       SUM(oi.quantity * p.price) AS total_spent
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
GROUP BY c.customer_id
ORDER BY total_spent DESC;


-- 4️⃣ Product Rating Analysis
SELECT p.product_name,
       AVG(r.rating) AS avg_rating,
       COUNT(r.review_id) AS total_reviews
FROM Products p
LEFT JOIN Reviews r ON p.product_id = r.product_id
GROUP BY p.product_id
ORDER BY avg_rating DESC;


-- 5️⃣ Most Loyal Customer (Maximum Orders)
SELECT c.customer_name,
       COUNT(o.order_id) AS total_orders
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id
ORDER BY total_orders DESC
LIMIT 1;
