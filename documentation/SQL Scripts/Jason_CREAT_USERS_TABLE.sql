-- Purpose: This script creates tables for system user database
-- with related foreign key

CREATE TABLE IF NOT EXISTS system_user (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	user_name TEXT NOT NULL UNIQUE,
	password TEXT NOT NULL,
	user_region INTEGER NOT NULL,
	user_job_type INTEGER NOT NULL,
	create_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_region (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	region_title TEXT NOT NULL,
	creat_at TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE system_user
ADD CONSTRAINT system_user_region_fkey
FOREIGN KEY (user_region)
REFERENCES user_region(id);

INSERT INTO user_region(region_title,creat_at) VALUES('Airpot',now());
INSERT INTO user_region(region_title,creat_at) VALUES('Airline',now());

INSERT INTO system_user(user_name,password,user_region,user_job_type,create_at) VALUES('Jason','111',1,1,now());
INSERT INTO system_user(user_name,password,user_region,user_job_type,create_at) VALUES('uwp','111',2,3,now());
