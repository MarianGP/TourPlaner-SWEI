-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-05-01 10:46:13.356

-- tables
-- Table: app_log
CREATE TABLE app_log (
    log_id int  NOT NULL,
    dated date  NOT NULL,
    logger varchar(50)  NULL,
    "level" varchar(10)  NULL,
    message varchar(100)  NOT NULL,
    user_id int  NOT NULL,
    CONSTRAINT app_log_pk PRIMARY KEY (log_id)
);

-- Table: tour
CREATE TABLE tour (
    tour_id int  NOT NULL,
    title varchar(255)  NOT NULL,
    description varchar(1000)  NULL,
    origin varchar(255)  NOT NULL,
    destination varchar(255)  NOT NULL,
    author varchar(50)  NOT NULL,
    distance int  NOT NULL,
    duration int  NULL,
    CONSTRAINT tour_pk PRIMARY KEY (tour_id)
);

CREATE INDEX tour_idx_title on tour (title ASC);

-- Table: tour_directions
CREATE TABLE tour_directions (
    tour_id int  NOT NULL,
    step varchar(1000)  NOT NULL,
    CONSTRAINT tour_directions_pk PRIMARY KEY (tour_id)
);

-- Table: tour_img
CREATE TABLE tour_img (
    tour_id int  NOT NULL,
    name varchar(255)  NOT NULL,
    CONSTRAINT tour_img_pk PRIMARY KEY (tour_id)
);

-- Table: tour_log
CREATE TABLE tour_log (
    log_id int  NOT NULL,
    date date  NOT NULL,
    distance int  NOT NULL,
    duration int  NOT NULL,
    rating int  NOT NULL,
    sport varchar(50)  NOT NULL,
    avg_speed int  NULL,
    start time  NOT NULL,
    arrival time  NOT NULL,
    special int  NULL,
    tour_id int  NOT NULL,
    user_id int  NOT NULL,
    CONSTRAINT tour_log_pk PRIMARY KEY (log_id)
);

-- Table: user
CREATE TABLE "user" (
    user_id int  NOT NULL,
    username varchar(50)  NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: Table_5_tour (table: tour_img)
ALTER TABLE tour_img ADD CONSTRAINT Table_5_tour
    FOREIGN KEY (tour_id)
    REFERENCES tour (tour_id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: log_user (table: app_log)
ALTER TABLE app_log ADD CONSTRAINT log_user
    FOREIGN KEY (user_id)
    REFERENCES "user" (user_id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: personal_log_tour (table: tour_log)
ALTER TABLE tour_log ADD CONSTRAINT personal_log_tour
    FOREIGN KEY (tour_id)
    REFERENCES tour (tour_id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: personal_log_user (table: tour_log)
ALTER TABLE tour_log ADD CONSTRAINT personal_log_user
    FOREIGN KEY (user_id)
    REFERENCES "user" (user_id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: tour_steps_tour (table: tour_directions)
ALTER TABLE tour_directions ADD CONSTRAINT tour_steps_tour
    FOREIGN KEY (tour_id)
    REFERENCES tour (tour_id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

INSERT INTO public."user" (user_id, username)
VALUES (1, 'Me');

-- End of file.

