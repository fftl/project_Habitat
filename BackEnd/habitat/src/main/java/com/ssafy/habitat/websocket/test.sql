-- 시연용 계정 입력.sql

-- 코스터 생성(coaster)
insert into coaster values("coaster", now(), now());

-- 유저 코스터 등록
insert into user_coaster(created_at, modified_at, coaster_coaster_key, user_user_key) values (now(), now(), "coaster", "kH0SB1qybMCfUd4");

-- 유저 섭취데이터 생성
-- DAY8 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -8 DAY), DATE_ADD(NOW(), INTERVAL -8 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY7 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -7 DAY), DATE_ADD(NOW(), INTERVAL -7 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY6 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 160, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -6 DAY), DATE_ADD(NOW(), INTERVAL -6 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY5 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 90, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -5 DAY), DATE_ADD(NOW(), INTERVAL -5 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY4 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -4 DAY), DATE_ADD(NOW(), INTERVAL -4 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY3 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 80, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 80, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 80, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 80, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 80, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 120, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -3 DAY), DATE_ADD(NOW(), INTERVAL -3 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY2 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 110, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 110, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 110, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 100, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 80, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 70, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -2 DAY), DATE_ADD(NOW(), INTERVAL -2 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");

-- DAY1 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 100, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 120, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 120, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "c", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "n", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");
insert into drink_log(created_at, modified_at, drink, drink_type, is_coaster, is_removed, user_user_key) values(DATE_ADD(NOW(), INTERVAL -1 DAY), DATE_ADD(NOW(), INTERVAL -1 DAY), 150, "w", 0, 0, "kH0SB1qybMCfUd4");

-- 친구 요청(friend_request)
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "8h7WKwavB2NfnaM");
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "cFdOUGT8GQLgVre");
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "erMEpur5ahnMnSy");
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "kFjy9WBXcxQMFpf");
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "mpsT6vtwWtsJgzn");
Insert into friend_request(created_at, modified_at, status, from_user_key, to_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4", "PNVS2Z17RyxQqlY");

-- 친구 생성(friend)
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "8h7WKwavB2NfnaM", "kH0SB1qybMCfUd4");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "cFdOUGT8GQLgVre", "kH0SB1qybMCfUd4");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "erMEpur5ahnMnSy", "kH0SB1qybMCfUd4");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kFjy9WBXcxQMFpf", "kH0SB1qybMCfUd4");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "mpsT6vtwWtsJgzn", "kH0SB1qybMCfUd4");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "PNVS2Z17RyxQqlY", "kH0SB1qybMCfUd4");

Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "8h7WKwavB2NfnaM");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "cFdOUGT8GQLgVre");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "erMEpur5ahnMnSy");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "kFjy9WBXcxQMFpf");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "mpsT6vtwWtsJgzn");
Insert into friend(created_at, modified_at, friend_id_user_key, my_id_user_key) values(now(), now(), "kH0SB1qybMCfUd4", "PNVS2Z17RyxQqlY");

-- 꽃 성장 기록 생성(planting)
insert into planting(create_at, modified_at, exp, flower_cnt, lv, flower_flower_key, user_user_key) values(now(), now(),  700, 1, 7, 1, "kH0SB1qybMCfUd4");
insert into planting(create_at, modified_at, exp, flower_cnt, lv, flower_flower_key, user_user_key) values(now(), now(),  700, 2, 7, 4, "kH0SB1qybMCfUd4");
insert into planting(create_at, modified_at, exp, flower_cnt, lv, flower_flower_key, user_user_key) values(now(), now(),  700, 3, 7, 7, "kH0SB1qybMCfUd4");
insert into planting(create_at, modified_at, exp, flower_cnt, lv, flower_flower_key, user_user_key) values(now(), now(),  0, 4, 0, 2, "kH0SB1qybMCfUd4");

-- 컬렉션 생성(collection)
insert into collection(created_at, modified_at, flower_flower_key, user_user_key) values(now(), now(), 1, "kH0SB1qybMCfUd4");
insert into collection(created_at, modified_at, flower_flower_key, user_user_key) values(now(), now(), 4, "kH0SB1qybMCfUd4");
insert into collection(created_at, modified_at, flower_flower_key, user_user_key) values(now(), now(), 7, "kH0SB1qybMCfUd4");

-- 유저 플라워 해금 정보(user_flower)