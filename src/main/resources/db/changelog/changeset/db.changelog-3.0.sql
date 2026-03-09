--liquibase formatted sql
--changeset RSergey:1
INSERT INTO courses(course_name, teacher_id) VALUES
                                                 ('Математика', 1),
                                                 ('Физика', 2),
                                                 ('Химия', 3),
                                                 ('Литература', 4),
                                                 ('История', 5),
                                                 ('Информатика', 6),
                                                 ('Биология', 7),
                                                 ('Английский язык', 8);
