-- Customers
INSERT INTO customer (id, name, email, phone_number) VALUES
(1, 'Alice', 'alice@example.com', '+1-314-555-0101'),
(2, 'Bob', 'bob@example.com', '+1-314-555-0102');

-- Products
INSERT INTO product (id, name, price, unit) VALUES
(1, 'T-shirt', 25.50, 'each'),
(2, 'Some kind of fabric', 30.89, 'meter'),
(3, 'Pair of Shoes', 60, 'pair');

-- Purchases
INSERT INTO purchase (id, customer_id, purchase_date) VALUES
(1, 1, TIMESTAMP WITH TIME ZONE '2025-07-01 10:00:00-05:00'),
(2, 1, TIMESTAMP WITH TIME ZONE '2025-07-10 14:30:00-05:00'),
(3, 1, TIMESTAMP WITH TIME ZONE '2025-08-15 18:45:00-05:00'),
(4, 1, TIMESTAMP WITH TIME ZONE '2025-09-12 09:15:00-05:00'),
(5, 2, TIMESTAMP WITH TIME ZONE '2025-07-01 10:00:00-05:00'),
(6, 2, TIMESTAMP WITH TIME ZONE '2025-08-10 14:30:00-05:00'),
(7, 2, TIMESTAMP WITH TIME ZONE '2025-09-15 18:45:00-05:00'),
(8, 2, TIMESTAMP WITH TIME ZONE '2025-09-12 09:15:00-05:00');

-- Purchase Details
INSERT INTO purchase_details (id, purchase_id, product_id, quantity) VALUES
    -- Purchase #1 (Alice), total $182.84, 224 points (50 + 164)
    (1, 1, 3, 1.0000), -- 1 pair of shoes
    (2, 1, 1, 3.0000), -- 3 T-shirts
    (3, 1, 2, 1.5000), -- 1.5 meter of fabric

    --- Purchase #2 (Alice): total $60.00, 10 points
    (4, 2, 3, 1.0000),

    -- Purchase #3 (Alice): total $112.78, 74 points
    (5, 3, 1, 2.0000),
    (6, 3, 2, 2.0000),

    -- Purchase #4 (Alice): total $116.39, 82 points
    (7, 4, 1, 1.0000),
    (8, 4, 3, 1.0000),
    (9, 4, 2, 1.0000),

    -- Purchase #1 (Bob): total $25.5, 0 points
    (11, 5, 1, 1.0000),

    -- Purchase #2 (Bob): total $171, 192 points
    (13, 6, 1, 2.0000),
    (14, 6, 3, 2.0000),

    -- Purchase #3 (Bob): total $21.62, 0 points
    (15, 7, 2, 0.7000),

    -- Purchase #4 (Bob): total $136.47, 122 points
    (16, 8, 1, 1.0000),
    (17, 8, 3, 1.0000),
    (18, 8, 2, 1.6500)
;

-- Rewards Rules
INSERT INTO rewards_rule
    (id, description, points_per_dollar, minimum_total_purchase, maximum_total_purchase, start_date, end_date)
VALUES
(1, '1 point per $ between $50 and $100', 1, 50, 100,
TIMESTAMP WITH TIME ZONE '2025-06-01 00:00:00-05:00', NULL),
(2, '2 points per $ over $100', 2, 100, 9223372036854775807,
TIMESTAMP WITH TIME ZONE '2025-06-01 00:00:00-05:00', NULL);