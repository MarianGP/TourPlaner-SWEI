create table "user"
(
    user_id  integer     not null
        constraint user_pk
            primary key,
    username varchar(50) not null
);

alter table "user"
    owner to postgres;

create table app_log
(
    log_id  integer      not null
        constraint app_log_pk
            primary key,
    dated   date         not null,
    logger  varchar(50),
    level   varchar(10),
    message varchar(100) not null,
    user_id integer      not null
        constraint log_user
            references "user"
);

alter table app_log
    owner to postgres;

create table tour
(
    tour_id     serial       not null
        constraint tour_pk
            primary key,
    title       varchar(255) not null,
    description varchar(1000),
    origin      varchar(255) not null,
    destination varchar(255) not null,
    user_id     integer      not null
        constraint tour_user
            references "user",
    distance    integer,
    duration    integer,
    img         varchar(255)
);

alter table tour
    owner to postgres;

create index tour_idx_title
    on tour (title);

create table tour_directions
(
    tour_id integer       not null
        constraint tour_directions_pk
            primary key
        constraint tour_steps_tour
            references tour,
    step    varchar(1000) not null
);

alter table tour_directions
    owner to postgres;

create table tour_img
(
    tour_id integer      not null
        constraint tour_img_pk
            primary key
        constraint table_5_tour
            references tour,
    name    varchar(255) not null
);

alter table tour_img
    owner to postgres;

create table tour_log
(
    log_id    serial  not null
        constraint tour_log_pk
            primary key,
    date      date    not null,
    distance  integer not null,
    duration  integer not null,
    rating    integer,
    sport     varchar(50),
    avg_speed integer,
    start     time,
    arrival   time,
    special   integer,
    tour_id   integer not null
        constraint personal_log_tour
            references tour
            on delete cascade,
    user_id   integer not null
        constraint personal_log_user
            references "user"
);

alter table tour_log
    owner to postgres;


