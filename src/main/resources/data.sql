INSERT INTO role (description, name) VALUES ('Admin role', 'ADMIN');
INSERT INTO role (description, name) VALUES ('User role', 'USER');

-- password -> user123
INSERT INTO user(username, email, password) values ('user1', 'user1@user.com', '$2a$10$WpjF2PY7ihbyxESmxrjqgu.N5lsjuUAv.nE9uQQ4Za/OB.VSnx1Xe');
INSERT INTO user_role(user_id, role_id) values (1, 2);

-- password -> admin123
INSERT INTO user(username, email, password) values ('admin', 'admin@admin.com', '$2a$10$8b8PO4ElYf3CKhuSSSYXMekiFAievM0mwptaCqpS8euJyV7K4zQhi');
INSERT INTO user_role(user_id, role_id) values (2, 1);
