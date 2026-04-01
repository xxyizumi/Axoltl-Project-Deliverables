-- ユーザーデータ
INSERT INTO users (name, email) VALUES 
    ('山田太郎', 'yamada@example.com'),
    ('鈴木花子', 'suzuki@example.com'),
    ('田中一郎', 'tanaka@example.com'),
    ('佐藤美咲', 'sato@example.com'),
    ('高橋健二', 'takahashi@example.com');

-- 注文データ
INSERT INTO orders (user_id, status, total_amount, ordered_at) VALUES 
    (1, 'DELIVERED', 15000, '2024-12-01 10:00:00'),
    (1, 'SHIPPED', 8000, '2024-12-15 14:30:00'),
    (2, 'CONFIRMED', 25000, '2024-12-20 09:00:00'),
    (2, 'PENDING', 12000, '2024-12-25 16:00:00'),
    (3, 'CANCELLED', 5000, '2024-12-10 11:00:00'),
    (3, 'DELIVERED', 18000, '2024-11-20 13:00:00'),
    (4, 'SHIPPED', 9500, '2024-12-22 10:30:00'),
    (5, 'CONFIRMED', 32000, '2024-12-24 15:00:00');

-- 注文明細データ
INSERT INTO order_details (order_id, product_name, quantity, unit_price) VALUES 
    (1, 'プログラミング入門書', 2, 3000),
    (1, 'ノートPC', 1, 9000),
    (2, 'キーボード', 1, 8000),
    (3, 'モニター', 1, 25000),
    (4, 'マウス', 3, 4000),
    (5, 'USBケーブル', 5, 1000),
    (6, 'ヘッドセット', 2, 5000),
    (6, 'Webカメラ', 1, 8000),
    (7, 'USBハブ', 1, 3500),
    (7, 'LANケーブル', 3, 2000),
    (8, '4Kモニター', 1, 30000),
    (8, 'モニターアーム', 1, 2000);

-- 商品データ（STEP 4で使用）
INSERT INTO products (name, price, stock) VALUES 
    ('プログラミング入門書', 3000, 50),
    ('ノートPC', 120000, 10),
    ('キーボード', 8000, 30),
    ('マウス', 4000, 100),
    ('モニター', 25000, 15);
