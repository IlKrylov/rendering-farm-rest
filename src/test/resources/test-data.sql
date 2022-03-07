INSERT INTO users (id, username, password) VALUES
(1,'user1', '$2a$10$Ht1HSQHjLfF/jcWe.L35zuZYhvPS2N7.A.8fk94C/TyLYSBeSI1Ey'),
(2,'user2', '$2a$10$Ht1HSQHjLfF/jcWe.L35zuZYhvPS2N7.A.8fk94C/TyLYSBeSI1Ey'),
(3,'user3', '$2a$10$Ht1HSQHjLfF/jcWe.L35zuZYhvPS2N7.A.8fk94C/TyLYSBeSI1Ey');

INSERT INTO tasks (id, title, description, user_id) VALUES
(1, 'Task1','Task1 description', 1),
(2, 'Task2','Task2 description', 1),
(3, 'Task3','Task3 description', 1),
(4, 'Tas4','Task4 description', 2),
(5, 'Task5','Task5 description', 2);

INSERT INTO task_states (id, status, created, task_id) VALUES
(1, 'NEW','2021-10-27 08:57:08', 1),
(2, 'RENDERING','2021-10-27 08:57:08', 1),
(3, 'COMPLETE','2021-10-27 08:57:08', 1),
(4, 'NEW','2021-10-27 08:57:08', 2),
(5, 'RENDERING','2021-10-27 08:57:08', 2),
(6, 'COMPLETE','2021-10-27 08:57:08', 2),
(7, 'NEW','2021-10-27 08:57:08', 3),
(8, 'RENDERING','2021-10-27 08:57:08', 3),
(9, 'COMPLETE','2021-10-27 08:57:08', 3),
(10, 'NEW','2021-10-27 08:57:08', 4),
(11, 'RENDERING','2021-10-27 08:57:08', 4),
(12, 'COMPLETE','2021-10-27 08:57:08', 4),
(13, 'NEW','2021-10-27 08:57:08', 5),
(14, 'RENDERING','2021-10-27 08:57:08', 5),
(15, 'COMPLETE','2021-10-27 08:57:08', 5);




