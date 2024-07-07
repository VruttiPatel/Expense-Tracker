CREATE TABLE public."user_mst" (
  "user_id" int8 primary key,
  "user_name" varchar NOT NULL,
  "email" varchar UNIQUE NOT NULL,
  "password" varchar NOT NULL ,
  "dob" timestamp,
  "status" int8 NOT NULL,
  "profile_pic_id" int8 ,
  "created_by" int8 NOT NULL,
  "created_date" timestamp NOT NULL,
  "created_by_ip" varchar NOT NULL,
  "updated_by" int8 ,
  "updated_date" timestamp,
  "updated_by_ip" varchar
);

CREATE TABLE public."cashbook_mst" (
  "cashbook_id" int8 primary key,
  "user_id" int8,
  "cashbook_name" varchar UNIQUE NOT NULL,
  "status" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp NOT NULL,
  "created_by_ip" varchar NOT NULL,
  "updated_by" int8 ,
  "updated_date" timestamp,
  "updated_by_ip" varchar,
  constraint fk_user
  foreign key(user_id)
  references user_mst(user_id)
);


CREATE TABLE public."category_mst" (
  "category_id" int8 primary key,
  "category_name" varchar UNIQUE NOT NULL,
  "status" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp NOT NULL,
  "created_by_ip" varchar NOT NULL,
  "updated_by" int8 ,
  "updated_date" timestamp,
  "updated_by_ip" varchar
);


CREATE TABLE public."payment_mode_mst" (
  "payment_id" int8 primary key,
  "payment_mode" varchar UNIQUE NOT NULL,
  "status" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp NOT NULL,
  "created_by_ip" varchar NOT NULL,
  "updated_by" int8,
  "updated_date" timestamp,
  "updated_by_ip" varchar
);



CREATE TABLE public."expense_mst" (
  "expense_id" int8 primary key,
  "cashbook_id" int8 references cashbook_mst(cashbook_id) not null,
  "category_id" int8 references category_mst(category_id) not null,
  "entry_type" int8 NOT NULL,
  "entry_date_time" timestamp NOT NULL,
  "amount" int8 NOT NULL,
  "remarks" varchar,
  "payment_mode_id" int8 NOT NULL,
  "attachment_id" int8,
  "status" int8 NOT NULL,
  "created_by" int8 NOT NULL,
  "created_date" timestamp NOT NULL,
  "created_by_ip" varchar NOT NULL,
  "updated_by" int8 ,
  "updated_date"  timestamp,
  " updated_by_ip" varchar
);




