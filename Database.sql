create database petshop;
-- drop database petshop;
use petshop;

create TABLE role(
	role_id int AUTO_INCREMENT primary key NOT NULL,
    role_name varchar(10)
)character set utf8mb4;

create TABLE gender(
	gender_id int AUTO_INCREMENT primary key NOT NULL,
    gender_name varchar(10)
)character set utf8mb4;

create TABLE user(
	user_id int AUTO_INCREMENT NOT NULL primary key,
    username varchar(32) NOT NULL UNIQUE,
    password varchar(32) NOT NULL,
	name varchar(100) NOT NULL,
	avatar varchar(500) NULL,
    gender_id int NOT NULL,
	email varchar(100) UNIQUE,
	phone varchar(20) NULL,
	role_id int default 2,
	addresses varchar(200) NULL,
	createdAt datetime default now(),
	updatedAt datetime default now(),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
)character set utf8mb4;

create TABLE status(
	status_id int AUTO_INCREMENT primary key NOT NULL,
	status_name varchar(255) not null
)character set utf8mb4; 

-- danh mục : chó, mèo, thức ăn 
CREATE TABLE category ( 
	category_id  int AUTO_INCREMENT primary key NOT NULL,
	category_name varchar(100) NOT NULL,
	-- category_image varchar(500) NULL,
	isDeleted bool NULL,
	createdAt datetime default now(),
	updatedAt datetime default now()
)character set utf8mb4;



CREATE TABLE styles (
	style_id int AUTO_INCREMENT primary key NOT NULL,
	style_name varchar(100) not null,
	category_id int ,
    createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (category_id) references category(category_id)
)character set utf8mb4;

-- 1, mèo anh lông ngắn, mèo có ....., 6 cá, 7, image,id(mèo),
CREATE TABLE pets (
	pets_id int AUTO_INCREMENT primary key NOT NULL,
	pets_name varchar(100) NOT NULL,
	weight double not null,
    age varchar(50) NOT NULL,
    gender boolean not null,
	price decimal(18, 0) NOT NULL,
	count int NOT NULL,
	isActive bool NULL,
	isDeleted bool NULL,
	createdAt datetime default now(),
	updatedAt datetime default now(),
    rated double NULL,
    numOfRate int default 0
)character set utf8mb4;

create table pet_style(
	pets_id int,
    pets_style int,
    primary key(pets_id, pets_style),
    foreign key (pets_id) references pets(pets_id),
    foreign key (pets_style)  references styles(style_id)
)character set utf8mb4;

-- create table pet_foods(
-- 	food_id int AUTO_INCREMENT primary key not null,
-- 	food_name varchar(100) not null,
-- 	style_id int ,
-- 	description text NOT NULL,
-- 	price decimal(18, 0) NOT NULL,
-- 	size double not null,
-- 	count int NOT NULL,
-- 	isActive bool NULL,
-- 	isDeleted bool NULL,
-- 	createdAt datetime default now(),
-- 	updatedAt datetime default now(),
--     rated double NULL,
--     numOfRate int default 0,
--     foreign key(style_id) references styles(style_id)
-- )character set utf8mb4;
 
CREATE TABLE image_list_pets(
	image_id int AUTO_INCREMENT primary key NOT NULL,
    pets_id int,
    imageLink varchar(10000) NOT NULL,
    createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (pets_id) references pets(pets_id)
)character set utf8mb4;

-- CREATE TABLE image_list_food(
-- 	image_id int AUTO_INCREMENT primary key NOT NULL,
--     food_id int,
--     imageLink varchar(10000) NOT NULL,
--     createdAt datetime default now(),
-- 	updatedAt datetime default now(),
--     foreign key (food_id)  references pets(pets_id)
-- )character set utf8mb4;


CREATE TABLE `order` (
	order_id int AUTO_INCREMENT primary key NOT NULL,
	user_id int,
	address varchar(1000) NOT NULL,
	phone varchar(20) NOT NULL,
	status_order int references status(status_id),
	createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (user_id)  references user(user_id)
)character set utf8mb4;


CREATE TABLE order_detail (
	orderdetail_id int AUTO_INCREMENT primary key NOT NULL,
	order_id int NOT NULL,
	pets_id int NULL,
-- 	food_id int NULL,
	count_SP int NULL,
	createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (order_id) references `order` (order_id),
    foreign key (pets_id) references pets(pets_id)
    -- foreign key (food_id) references pet_foods(food_id)
)character set utf8mb4;


CREATE TABLE cart (
	cart_id int AUTO_INCREMENT primary key NOT NULL,
	user_id int unique,
	createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (user_id) references user(user_id)
)character set utf8mb4;


CREATE TABLE cart_detail (
	cartdetail_id int AUTO_INCREMENT primary key NOT NULL,
	cart_id int,
	pets_id int NULL,
-- 	food_id int NULL,
	count_SP int NOT NULL,
	createdAt datetime default now(),
	updatedAt datetime default now(),
    foreign key (cart_id) references cart(cart_id),
    foreign key (pets_id) references pets(pets_id)-- ,
--     foreign key (food_id) references pet_foods(food_id)
)character set utf8mb4;

CREATE TABLE pet_rate(
	user_id int,
    pets_id int,
    rate int check(rate >=1 and rate <=5),
    `comment` text,
    createdAt datetime default now(),
    updatedAt datetime default now(),
    primary key(user_id, pets_id),
    foreign key (user_id) references `user`(user_id),
    foreign key (pets_id) references pets(pets_id)
);

CREATE TABLE pet_favorite(
	user_id int,
    pets_id int,
    primary key(user_id, pets_id),
    foreign key (user_id) references `user`(user_id),
    foreign key (pets_id) references pets(pets_id)
);

-- CREATE TABLE food_favorite(
-- 	user_id int,
--     food_id int,
--     primary key(user_id, food_id),
--     foreign key (user_id) references `user`(user_id),
--     foreign key (food_id) references pet_foods(food_id)
-- );

-- CREATE TABLE petfood_rate(
-- 	user_id int,
--     food_id int,
--     rate int check(rate >=1 and rate <=5),
--     `comment` text,
--     createdAt datetime default now(),
--     updatedAt datetime default now(),
--     primary key(user_id, food_id),
--     foreign key (user_id) references `user`(user_id),
--     foreign key (food_id) references pet_foods(food_id)
-- );

delimiter //
CREATE Trigger insert_pet_rate after insert
on pet_rate
for each row
begin
	update pets
		set pets.rated = if((select pets.rated where pets.pets_id = new.pets_id) is null, new.rate, pets.rated),
			pets.rated = if((select pets.rated where pets.pets_id = new.pets_id) is not null,
					(pets.rated * pets.numOfRate + new.rate)/(pets.numOfRate+1), pets.rated),
			pets.numOfRate = pets.numOfRate + 1
		where pets.pets_id = new.pets_id;
end//
delimiter ;
-- drop trigger insert_pet_rate;

delimiter //
CREATE Trigger update_pet_rate after update
on pet_rate
for each row
begin
	update pets
		set pets.rated = (pets.rated * pets.numOfRate - old.rate + new.rate)/pets.numOfRate
        where pets.pets_id = new.pets_id;
end//
delimiter ;
-- drop trigger update_pet_rate;

delimiter //
CREATE Trigger delete_pet_rate after delete
on pet_rate
for each row
begin
	update pets
		set pets.rated = (pets.rated * pets.numOfRate - old.rate)/(pets.numOfRate - 1),
			pets.numOfRate = pets.numOfRate - 1
		where pets.pets_id = old.pets_id;
end//
delimiter ;
-- drop trigger delete_pet_rate

delimiter //
-- CREATE Trigger insert_petfood_rate after insert
-- on petfood_rate
-- for each row
-- begin
-- 	update pet_foods
--         set pet_foods.rated = if((select pet_foods.rated where pet_foods.food_id = new.food_id) is null, new.rate, pet_foods.rated),
-- 			pet_foods.rated = if((select pet_foods.rated where pet_foods.food_id = new.food_id) is not null,
-- 						(pet_foods.rated * pet_foods.numOfRate + new.rate)/(pet_foods.numOfRate+1), pet_foods.rated),
-- 			pet_foods.numOfRate = pet_foods.numOfRate + 1
--         where pet_foods.food_id = new.food_id;
-- end//
-- delimiter ;
-- -- drop trigger insert_petfood_rate;

-- delimiter //
-- CREATE Trigger update_petfood_rate after update
-- on petfood_rate
-- for each row
-- begin
-- 	update pet_foods
-- 		set pet_foods.rated = (pet_foods.rated * pet_foods.numOfRate - old.rate + new.rate)/pet_foods.numOfRate
--         where pet_foods.food_id = new.food_id;
-- end//
-- delimiter ;
-- -- drop trigger update_petfood_rate;

-- delimiter //
-- CREATE Trigger delete_petfood_rate after delete
-- on petfood_rate
-- for each row
-- begin
-- 	update pet_foods
-- 		set pet_foods.rated = (pet_foods.rated * pet_foods.numOfRate - old.rate)/(pet_foods.numOfRate - 1),
-- 			pet_foods.numOfRate = pet_foods.numOfRate - 1
-- 		where pet_foods.food_id = old.food_id;
-- end//
-- delimiter ;
-- -- drop trigger delete_petfood_rate

