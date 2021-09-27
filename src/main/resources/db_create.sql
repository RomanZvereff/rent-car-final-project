-- ****************************************************************
-- create and use new DB for project
drop schema if exists RENT_CAR_DB;
create schema if not exists RENT_CAR_DB character set 'utf8mb4';

use RENT_CAR_DB;

-- ****************************************************************
-- MANUFACTURERS table
create table if not exists MANUFACTURERS (
                                             ID                int auto_increment    not null,
                                             MANUF_NAME varchar(50)           not null,
                                             primary key(ID)
);

insert into MANUFACTURERS(MANUF_NAME) values
                                          ('SKODA'),
                                          ('KIA'),
                                          ('VOLKSWAGEN'),
                                          ('HYUNDAI'),
                                          ('TOYOTA'),
                                          ('BMW'),
                                          ('MERCEDES-BENZ'),
                                          ('AUDI');

create index MANUFACTURER_ID_INDEX ON MANUFACTURERS (ID);

-- ****************************************************************
-- MODELS table
create table if not exists MODELS (
                                      ID              int auto_increment  not null,
                                      MODEL_NAME      varchar(50)         not null,
                                      PASSENG_NUM		int unsigned        not null,
                                      MANUF_ID		int                 not null,
                                      primary key(ID),
                                      constraint `FK_MODELS_MANUFACTURERS` foreign key(MANUF_ID) references MANUFACTURERS(ID)
);

create index MODEL_ID_INDEX ON MODELS (ID);

insert into MODELS values
                       (null, 'FABIA',   4, 1),
                       (null, 'PICANTO', 3, 2),
                       (null, 'POLO',    3, 3),
                       (null, 'ACCENT',  4, 4),
                       (null, 'ELANTRA', 4, 4),
                       (null, 'OCTAVIA', 4, 1),
                       (null, 'PASSAT',  4, 3),
                       (null, 'CAMRY',   4, 5),
                       (null, 'TOUAREG', 4, 3),
                       (null, 'RAV4',    4, 5),
                       (null, '520',     4, 6),
                       (null, 'S550',    4, 7),
                       (null, 'A7',      4, 8);

-- ****************************************************************
-- CAR_LEVELS_S1 table
create table if not exists CAR_LVLS_S1 (
                                           ID       int auto_increment   not null,
                                           LVL_NAME varchar(20)          not null,
                                           primary key(ID)
);

insert into  CAR_LVLS_S1(LVL_NAME) values
                                       ('ECONOM'),
                                       ('STANDARD'),
                                       ('BUSINESS');

-- ****************************************************************
-- BODY_TYPES_S1 table
create table if not exists BODY_TYPES_S1 (
                                             ID              int auto_increment  not null,
                                             BODY_TYPE_NAME  varchar(20)         not null,
                                             primary key(ID)
);

insert into BODY_TYPES_S1(BODY_TYPE_NAME) values
                                              ('SEDAN'),
                                              ('HATCHBACK'),
                                              ('STATION WAGON'),
                                              ('MINI VAN'),
                                              ('PICKUP'),
                                              ('SUV');

-- ****************************************************************
-- TRANSMISSION_TYPES_S1 table
create table if not exists TRANSM_TYPES_S1 (
                                               ID                int auto_increment  not null,
                                               TRANSM_TYPE_NAME  varchar(20)         not null,
                                               primary key(ID)
);

insert into TRANSM_TYPES_S1(TRANSM_TYPE_NAME) values
                                                  ('MANUAL'),
                                                  ('AUTOMATIC');

-- ****************************************************************
-- FUEL_TYPES_S1 table
create table if not exists FUEL_TYPES_S1 (
                                             ID              int auto_increment  not null,
                                             FUEL_TYPE_NAME  varchar(20)         not null,
                                             primary key(ID)
);

insert into FUEL_TYPES_S1(FUEL_TYPE_NAME) values
                                              ('PETROL'),
                                              ('DIESEL'),
                                              ('ELECTRICAL'),
                                              ('HYBRID');

-- ****************************************************************
-- USER_ROLES table
create table USER_ROLES (
                            ID          int auto_increment  not null,
                            ROLE_NAME   varchar(20)         not null,
                            primary key(ID)
);

insert into USER_ROLES(ROLE_NAME) values
                                      ('ADMIN'),
                                      ('MANAGER'),
                                      ('CUSTOMER');

-- ****************************************************************
-- USERS table
create table if not exists USERS (
                                     ID          bigint auto_increment   not null,
                                     USER_LOGIN  char(20)                not null unique,
                                     USER_PASS   char(255)               not null,
                                     ROLE_ID     int                     not null default 3,
                                     primary key(ID),
                                     constraint `FK_USERS_USER_ROLE` foreign key (ROLE_ID) references USER_ROLES(ID)
);

create index USER_ID_INDEX ON USERS (ID);
create index USER_LOGIN_INDEX ON USERS (USER_LOGIN);
create index USER_PASS_INDEX ON USERS (USER_PASS);

insert into USERS values
                      (null, 'admin', '1000:19a8b39701e30bc01ce2bb83e09540b6:cba25bc9943d961215695b1b9e43279bd94f2fee732c57496835d2a0df4562a9901e8731c92fdccd3ecaacd03b09c4b7840bbe43238b904e6db45cf4e6d9d6d0', 1),
                      (null, 'conor_123', '1000:465ba9c098ab114b503f0961e71ccb53:c75d1a2efba08c3222b8599d3261d1c2d93c04d7fd3c8606e487792d9755d9403cd2d28a2c1a2b7cb7ad8e46dae4587267bede7771f9bd7ca651f3e9f9003d42', 2),
                      (null, 'black_123', '1000:d0b483ebbbe9a2bf73807f143af220d0:3272e103180984da6a8c4d222eeb068b01c7c0916fa8ed4a381ee2af921081107ad1108336bab4e294dca02724f0b13cfa29939969614d0a6cfe18c0fd49760b', 2);

-- ****************************************************************
-- PROFILES table
create table if not exists `PROFILES` (
                                          ID          bigint auto_increment   not null,
                                          FRST_NAME   varchar(50)             not null,
                                          LAST_NAME   varchar(50)             not null,
                                          PHONE_NUM   varchar(20)             not null,
                                          EMAIL       varchar(50)             not null,
                                          USER_ID     bigint                  not null,
                                          primary key(ID),
                                          constraint `FK_PROFILES_USERS` foreign key(USER_ID) references USERS(ID) ON DELETE CASCADE
);

create index PROFILE_ID_INDEX ON `PROFILES` (ID);
create index PROFILE_FIRST_NAME_INDEX ON `PROFILES` (FRST_NAME);
create index PROFILE_LAST_NAME_INDEX ON `PROFILES` (LAST_NAME);

insert into `PROFILES` values
                           (null, 'Admin', 'Admin', 'null', 'admin@rentcar.com', 1),
                           (null, 'Sarah', 'Conor', '+380971234567', 'manager_sarah@rentcar.com', 2),
                           (null, 'Jack', 'Black', '+380979876543', 'manager_bob@rentcar.com', 2);

-- ****************************************************************
-- BRANCHES table
create table if not exists BRANCHES (
                                        ID          int auto_increment  not null,
                                        BRANCH_NAME varchar(50)         not null,
                                        CITY_NAME   varchar(30)         not null,
                                        ADDRESS     varchar(100)        not null,
                                        MANAGER_ID  bigint              not null,
                                        primary key(ID),
                                        constraint `FK_BRANCHES_PROFILES` foreign key(MANAGER_ID) references `PROFILES`(ID)
);

insert into BRANCHES values
                         (null, 'Boryspil Airport', 'Kyiv', 'Borsypil, Kyiv Oblast, Ukraine', 2),
                         (null, 'Railway station', 'Kyiv', 'Vokzalna Ploshcha, Kyiv, Ukraine', 3);

-- ****************************************************************
-- CARS table
create table if not exists CARS (
                                    ID                      int auto_increment  not null,
                                    MANUF_ID		        int                 not null,
                                    MODEL_ID                int                 not null,
                                    CAR_LVL_ID              int                 not null,
                                    BODY_TYPE_ID            int                 not null,
                                    TRANSM_TYPE_ID		    int                 not null,
                                    FUEL_TYPE_ID            int                 not null,
                                    `ENGINE`                decimal(2,1)        not null,
                                    FUEL_CONSUM		        decimal(3,1)        not null,
                                    PROD_YEAR		        int unsigned        not null,
                                    BRANCH_ID               int                 not null,
                                    PRICE                   decimal(10,2)       not null,
                                    IMG_NAME                varchar(65)         null,
                                    primary key (ID),
                                    constraint `FK_CARS_MANUFACTURERS` foreign key (MANUF_ID) references MANUFACTURERS(ID),
                                    constraint `FK_CARS_MODELS` foreign key (MODEL_ID) references MODELS(ID),
                                    constraint `FK_CARS_CAR_LVLS_S1` foreign key (CAR_LVL_ID) references CAR_LVLS_S1(ID),
                                    constraint `FK_CARS_BODY_TYPES_S1` foreign key (BODY_TYPE_ID) references BODY_TYPES_S1(ID),
                                    constraint `FK_CARS_TRANSM_TYPES_S1` foreign key (TRANSM_TYPE_ID) references TRANSM_TYPES_S1(ID),
                                    constraint `FK_CARS_FUEL_TYPES_S1` foreign key (FUEL_TYPE_ID) references FUEL_TYPES_S1(ID),
                                    constraint `FK_CARS_BRANCHES` foreign key (BRANCH_ID) references BRANCHES(ID)
);

create index CAR_ID_INDEX ON CARS (ID);
create index MANUFACT_ID_INDEX ON CARS (MANUF_ID);
create index MODEL_ID_INDEX ON CARS (MODEL_ID);

insert into CARS values
                     (null, 1, 1,  1, 2, 1, 1, 1.2, 6.5,  2005, 1, 15, 'skoda_fabia.jpg'),
                     (null, 1, 6,  2, 1, 2, 2, 2.0, 8.5,  2012, 2, 25, 'skoda-octavia.jpg'),
                     (null, 2, 2,  1, 2, 2, 1, 1.0, 5.5,  2008, 1, 12, 'kia_picanto.jpg'),
                     (null, 3, 3,  1, 2, 2, 1, 1.4, 6.5,  2011, 1, 17, 'volkswagen_polo.jpg'),
                     (null, 3, 7,  2, 1, 2, 2, 1.8, 10.5, 2013, 2, 28, 'volkswagen_passat.jpg'),
                     (null, 3, 9,  3, 6, 2, 2, 3.0, 12.5, 2014, 2, 55, 'volkswagen-touareg.jpg'),
                     (null, 4, 4,  1, 1, 1, 1, 1.4, 9.5,  2011, 1, 16, 'hyundai_accant.jpg'),
                     (null, 4, 5,  2, 1, 2, 1, 1.6, 9.0,  2013, 1, 26, 'hyundai_elantra.jpg'),
                     (null, 5, 8,  2, 1, 2, 1, 2.4, 13.0, 2011, 1, 27, 'toyota_camry.jpg'),
                     (null, 5, 10, 3, 6, 2, 4, 2.0, 11.5, 2015, 2, 60, 'toyota_rav4.jpg'),
                     (null, 6, 11, 3, 1, 2, 1, 2.2, 12.5, 2016, 2, 58, 'bmw_520.jpg'),
                     (null, 7, 12, 3, 1, 2, 2, 3.2, 16.5, 2016, 1, 65, 'mercedes-benz-s550.jpg'),
                     (null, 8, 13, 3, 1, 2, 1, 3.5, 18.5, 2017, 1, 68, 'audi-a7.jpg');

-- ****************************************************************
-- ORDERS table
create table ORDERS (
                        ID              bigint auto_increment   not null,
                        ORDER_NUM       varchar(20)             not null,
                        PROFILE_ID      bigint                  not null,
                        RENT_START      date                    not null,
                        RENT_END        date                    not null,
                        CAR_ID          int                     not null,
                        BRANCH_ID       int                     not null,
                        NEED_DRIVER     char(3)                 not null,
                        TOTAL_COST      decimal(7,2)            not null,
                        ORDER_STATUS    varchar(45)             not null,
                        primary key(ID),
                        constraint `FK_ORDERS_PROFILES` foreign key (PROFILE_ID) references `PROFILES`(ID),
                        constraint `FK_ORDERS_CARS` foreign key (CAR_ID) references CARS(ID),
                        constraint `FK_ORDERS_BRANCHES` foreign key (BRANCH_ID) references BRANCHES(ID)
);

-- ****************************************************************
-- INVOICE table
create table if not exists INVOICE (
                                       ID          bigint auto_increment   not null,
                                       INVOICE_NUM varchar(10)             not null,
                                       CAR_ID      int                     not null,
                                       PROFILE_ID  bigint                  not null,
                                       DEMG_DESC   varchar(255)            not null,
                                       AMOUNT      decimal(15,2)           not null,
                                       IBAN_ACC    varchar(35)             not null,
                                       ORDER_ID    bigint                  not null,
                                       primary key(ID),
                                       constraint `FK_INVOICE_CARS` foreign key (CAR_ID) references CARS(ID),
                                       constraint `FK_INVOICE_PROFILES` foreign key (PROFILE_ID) references `PROFILES`(ID)
);