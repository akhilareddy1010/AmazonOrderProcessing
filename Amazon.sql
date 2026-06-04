CREATE DATABASE amazon_db;

USE amazon_db;

CREATE TABLE products
(
product_id INT PRIMARY KEY,
product_name VARCHAR(50),
stock INT,
price DOUBLE
);



CREATE TABLE orders
(
order_id INT PRIMARY KEY,
customer_name VARCHAR(50),
product_id INT,
quantity INT,
status VARCHAR(20),

FOREIGN KEY(product_id)
REFERENCES products(product_id)

);



CREATE TABLE shipments
(
shipment_id INT PRIMARY KEY,
order_id INT,
dispatch_status VARCHAR(30),

FOREIGN KEY(order_id)
REFERENCES orders(order_id)

);


INSERT INTO products VALUES
(101,'Laptop',20,55000),
(102,'Mouse',100,700),
(103,'Keyboard',80,1200),
(104,'Monitor',25,10000),
(105,'Printer',15,15000);



INSERT INTO orders VALUES
(1001,'Rahul',101,2,'PENDING'),
(1002,'Anjali',102,5,'PENDING'),
(1003,'Kiran',103,3,'PENDING'),
(1004,'Pooja',104,1,'PENDING'),
(1005,'Arjun',105,2,'PENDING');


