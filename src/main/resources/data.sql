INSERT INTO role (description, name) VALUES ('Admin role', 'ADMIN');
INSERT INTO role (description, name) VALUES ('User role', 'USER');

-- password -> user123
INSERT INTO user (username, email, password) values ('user1', 'user1@user.com', '$2a$10$WpjF2PY7ihbyxESmxrjqgu.N5lsjuUAv.nE9uQQ4Za/OB.VSnx1Xe');
INSERT INTO user_role(user_id, role_id) values (1, 2);

-- password -> admin123
INSERT INTO user (username, email, password) values ('admin', 'admin@admin.com', '$2a$10$8b8PO4ElYf3CKhuSSSYXMekiFAievM0mwptaCqpS8euJyV7K4zQhi');
INSERT INTO user_role(user_id, role_id) values (2, 1);
INSERT INTO user_role(user_id, role_id) values (2, 2);

-- Default Sports
INSERT INTO sport (sport_name) VALUES ('Fútbol');
INSERT INTO sport (sport_name) VALUES ('Baloncesto');
INSERT INTO sport (sport_name) VALUES ('Padel');

-- Default Courts
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Fútbol 1', 1, 10);
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Fútbol 2', 1, 10);
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Baloncesto 1', 2, 10);
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Baloncesto 2', 2, 10);
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Padel 1', 3, 10);
INSERT INTO court (court_name, sport_id, court_price) VALUES ('Padel 2', 3, 10);

-- Default Time Intervals
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('09:00', '10:30', 1);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('10:30', '12:00', 1);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('12:00', '13:30', 1);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('09:00', '10:30', 2);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('10:30', '12:00', 2);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('12:00', '13:30', 2);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('09:00', '10:30', 3);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('10:30', '12:00', 3);
INSERT INTO time_interval (start_time, end_time, court_id) VALUES ('12:00', '13:30', 3);
