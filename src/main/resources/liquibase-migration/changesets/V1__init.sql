create table if not exists users
(
    id       bigserial primary key,
    surname  varchar(255) not null,
    name     varchar(255) not null,
    lastname varchar(255),
    username varchar(255) not null unique,
    email    varchar(255) not null,
    password varchar(255) not null
);

create table if not exists doctors
(
    id       bigserial primary key,
    surname  varchar(255) not null,
    name     varchar(255) not null,
    lastname varchar(255),
    room     bigint       not null
);

create table if not exists appointments
(
    id              bigserial primary key,
    status          varchar(255) not null,
    date_of_receipt timestamp    not null,
    user_id         bigint,
    doctor_id       bigint,
    constraint fk_users_appointments_users foreign key (user_id) references users (id),
    constraint fk_doctor_appointments_doctor foreign key (doctor_id) references doctors (id)
);

create table if not exists users_roles
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
);