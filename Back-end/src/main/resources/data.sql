-- Insert role
insert into role (name) values ('ROLE_USER');

-- Insert User test
insert into user (enabled, full_name, password, username, role_id) VALUES (1, 'KF', '$2a$10$V.QDKYG4G/OjuSPUMIFtveYRzUOqF5nz4KY62AR6MKkSUl6lY5Gzu', 'kev.flamand@gmail.com', '1');

-- Insert Token test
INSERT INTO registration_token (id) VALUES (1);
