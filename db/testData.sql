INSERT INTO users(name) VALUES ('admin');
INSERT INTO users(name) VALUES ('simple_user');

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 01', TO_TIMESTAMP(1618520770), false, (SELECT id FROM users WHERE users.name = 'admin'));

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 02', TO_TIMESTAMP(1614520770), true, (SELECT id FROM users WHERE users.name = 'admin'));

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 03', CURRENT_TIMESTAMP, false, (SELECT id FROM users WHERE users.name = 'admin'));

INSERT INTO items(description, created, done, user_id)
VALUES ('User item 01', TO_TIMESTAMP(1614520770), true, (SELECT id FROM users WHERE users.name = 'simple_user'));

INSERT INTO items(description, created, done, user_id)
VALUES ('User item 02', CURRENT_TIMESTAMP, false, (SELECT id FROM users WHERE users.name = 'simple_user'));
