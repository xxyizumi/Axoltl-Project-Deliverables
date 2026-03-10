-- 「email」に「example.com」を含むユーザーを検索するSQLを書いてください。
select * from users where email like '%example.com';

-- 金額が10000円以上の注文を、金額の降順で取得するSQLを書いてください。
select * from orders where amount >= 10000 order by amount desc;

-- 注文を1件以上持つユーザーのみを取得するSQLを書いてください。
select * from users where id in (select user_id from orders where  users.id = orders.user_id)