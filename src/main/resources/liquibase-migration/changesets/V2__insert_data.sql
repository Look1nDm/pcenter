insert into users (surname, name, lastname, username, email, password)
values ('Lukin', 'Dmitriy','Alekssandrovich','+79992097901','dimka41184@gmail.com','$2a$12$TsqmXxvOObz1dF4ugre6reFNw6XVxQ2O/kHe1Zv1vHbObai68TqL6'),
       ('Pupkin','Vasiliy','Petrovich','+79992097902','pupkin@gmail.com','$2a$12$TsqmXxvOObz1dF4ugre6reFNw6XVxQ2O/kHe1Zv1vHbObai68TqL6'),
       ('Urban','Galina','Dmitrievna','+79992097903','urban@mail.ru','$2a$12$TsqmXxvOObz1dF4ugre6reFNw6XVxQ2O/kHe1Zv1vHbObai68TqL6');

insert into doctors (surname, name, lastname, room)
values ('Usmanova','Veronika','Viktorovna', 23),
       ('Meshina','Galina','Petrovna', 25);

insert into appointments (status, date_of_receipt, doctor_id)
values ('FREE', '2023-01-29 9:00', 1),
       ('FREE', '2023-01-29 9:20', 1),
       ('BUSY', '2023-01-29 9:40', 1),
       ('FREE', '2023-01-29 10:00', 2),
       ('BUSY', '2023-01-29 10:20', 2),
       ('FREE', '2023-01-29 10:40', 2);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_PATIENT'),
       (3, 'ROLE_PATIENT');

-- insert into doctors_appointments (doctor_id, appointment_id)
-- values (1,1),
--        (1,2),
--        (1,3),
--        (2,1),
--        (2,4),
--        (2,5);
--
-- insert into users_appointments (user_id, appointment_id)
-- values (1,3),
--        (2,5);

