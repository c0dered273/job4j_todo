INSERT INTO users(name, email, password) VALUES ('root', 'root@local', 'a00e4d3b352e9d11979549b9eef5dc951592f594488451e6cd86fdc4bce76a53');
INSERT INTO users(name, email, password) VALUES ('user', 'user@local', '8ac76453d769d4fd14b3f41ad4933f9bd64321972cd002de9b847e117435b08b');

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 01', TO_TIMESTAMP(1618520770), false, (SELECT id FROM users WHERE users.name = 'root'));

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 02', TO_TIMESTAMP(1614520770), true, (SELECT id FROM users WHERE users.name = 'root'));

INSERT INTO items(description, created, done, user_id)
VALUES ('Admin item 03', CURRENT_TIMESTAMP, false, (SELECT id FROM users WHERE users.name = 'root'));

INSERT INTO items(description, created, done, user_id)
VALUES ('User item 01', TO_TIMESTAMP(1614520770), true, (SELECT id FROM users WHERE users.name = 'user'));

INSERT INTO items(description, created, done, user_id)
VALUES ('User item 02', CURRENT_TIMESTAMP, false, (SELECT id FROM users WHERE users.name = 'user'));
