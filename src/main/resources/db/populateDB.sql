DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM locations;
DELETE
FROM devices;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO locations (name, city, street, building, room)
VALUES ('ats_peterhoff', 'Petrodvorets', 'Botanicheskaya', '27B', 16),
       ('germozona', 'Saint-Petersburg', 'Universitetskaya', '7-9-11', 115),
       ('admin', 'Saint-Petersburg', 'Himichesky', '5P', 114);

INSERT INTO devices (location_id, dnsname, model, serial, inventory, manufactured, description, contacts)
VALUES (100002, 'ats.ad.pu.ru', 'DELL RX750', 'dls8989008kkh788', 'IN77/34.99899873', '2013-06-01 00:00:00', 'punk ats',
        '+78123334455 nik@pu.ru'),
       (100002, 'ns05.ad.pu.ru', 'SUPERMICRO N560', 'sm876fff98789799', 'IN45/34.87457376', '2010-05-01  00:00:00',
        'punk dns', 'adminpunk@pu.ru'),
       (100003, 'srvsyn01.ad.pu.ru', 'SYNOLOGY RS2416RP', 'sy127834587875', 'IN37/45.6575675', '2015-04-01  00:00:00',
        'thirst storage', '+78125555555 admin@pu.ru'),
       (100003, 'srvqnap01.ad.pu.ru', 'QNAP TVS-EC1271U-RP', 'q127834dfjh587875', 'IN33/234.234234',
        '2016-07-01 00:00:00', 'second storage', 'admin@pu.ru'),
       (100004, 'huawei01.ad.pu.ru', 'HUAWEI RH2288H', 'hw989879sr98473', 'IN37/54.8734875', '2017-09-01  00:00:00',
        'vsan 01', 'admin@pu.ru'),
       (100004, 'node3.pu.ru', 'CISCO 3750', '830845874HH345', 'IN77/33.3453543', '2012-03-01  00:00:00',
        'core network 3750sw', 'nocadm@pu.ru');

