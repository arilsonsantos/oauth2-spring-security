insert into customer values (1, 'Joao');
insert into customer values (2, 'Maria');
insert into customer values (3, 'Jose');

INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLE) VALUES (1, 'joao',  '{bcrypt}$2a$10$42B0dm6qcfpojx7bbIjGYOjNJbEzc.MeWozTkhCHRKyKXKXUvxmwm', true);
INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLE) VALUES (2, 'maria', '{bcrypt}$2a$10$42B0dm6qcfpojx7bbIjGYOjNJbEzc.MeWozTkhCHRKyKXKXUvxmwm', true);
INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLE) VALUES (3, 'jose', '{bcrypt}$2a$10$42B0dm6qcfpojx7bbIjGYOjNJbEzc.MeWozTkhCHRKyKXKXUvxmwm', false);
INSERT INTO ROLE VALUES (1,  'ADMIN');
INSERT INTO ROLE VALUES (2, 'USER');
INSERT INTO USER_ROLE VALUES (1,1);
INSERT INTO USER_ROLE VALUES (1,2);
INSERT INTO USER_ROLE VALUES (2,2);
INSERT INTO USER_ROLE VALUES (3,2);