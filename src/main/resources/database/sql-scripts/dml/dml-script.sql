create database classifiedapi;

-- ROLE
create table role
(
    id   bigserial
        primary key,
    name varchar(255) not null
        constraint uk_8sewwnpamngi6b1dwaa88askk
            unique
);

alter table role
    owner to postgres;


-- LOCATION
create table location
(
    id    bigserial
        primary key,
    city  varchar(255) not null,
    state varchar(255) not null
);

alter table location
    owner to postgres;


-- PROFILE
create table profile
(
    id      bigserial
        primary key,
    name    varchar(255),
    phone   varchar(255),
    rating  double precision not null,
    balance numeric(19, 2)
);

alter table profile
    owner to postgres;


-- ACCOUNT
create table account
(
    id         bigserial
        primary key,
    email      varchar(255) not null
        constraint uk_q0uja26qgu1atulenwup9rxyr
            unique,
    password   varchar(255) not null,
    profile_id bigint
        constraint uk_lx5o8xa39akufx3vih7fto4gj
            unique
        constraint fklelt1dhpyk7dqxdco3x3cd5ub
            references profile,
    role_id    bigint       not null
        constraint fkd4vb66o896tay3yy52oqxr9w0
            references role
);

alter table account
    owner to postgres;


-- MESSAGE
create table message
(
    id           bigserial
        primary key,
    delivered    boolean,
    read         boolean,
    sent_at      timestamp,
    text         varchar(255),
    recipient_id bigint
        constraint fkhklxu5jy1xjfeglvrut8whuy8
            references profile,
    sender_id    bigint
        constraint fkblqrqr1tbir5m5dp5ke7m2fhm
            references profile
);

alter table message
    owner to postgres;


-- ADVERTISEMENT
create table advertisement
(
    id          bigserial
        primary key,
    active      boolean,
    brand       varchar(255),
    condition   varchar(255),
    description varchar(255),
    price       numeric(19, 2),
    title       varchar(255),
    type        varchar(255),
    location_id bigint
        constraint fkg80ce77s6wrpa3n9jsf6e6kd6
            references location,
    profile_id  bigint
        constraint fkgvxr0tmfju8mjynnjcpwhdl5q
            references profile,
    vip         boolean
);

alter table advertisement
    owner to postgres;


-- ADVERTISEMENT_COMMENT
create table advertisement_comment
(
    id               bigserial
        primary key,
    body             varchar(255) not null,
    header           varchar(255) not null,
    pasted_at        timestamp    not null,
    advertisement_id bigint
        constraint fkf7ysj9tcpcidc4tekk2mfo1bn
            references advertisement,
    posted_by        bigint
        constraint fkdeuthfpullaqixmaeli8qr7fc
            references profile
);

alter table advertisement_comment
    owner to postgres;




