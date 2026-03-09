--liquibase formatted sql
--changeset RSergey:1
INSERT INTO student_groups(group_number) VALUES
                                             (3),
                                             (5),
                                             (7),
                                             (2),
                                             (1),
                                             (4),
                                             (6),
                                             (8);

--changeset RSergey:2
INSERT INTO students(first_name, last_name, group_number) VALUES
                                                              ('Александр', 'Пушкин', floor(random() * 8 + 1)::int),
                                                              ('Михаил', 'Лермонтов', floor(random() * 8 + 1)::int),
                                                              ('Лев', 'Толстой', floor(random() * 8 + 1)::int),
                                                              ('Федор', 'Достоевский', floor(random() * 8 + 1)::int),
                                                              ('Иван', 'Тургенев', floor(random() * 8 + 1)::int),
                                                              ('Антон', 'Чехов', floor(random() * 8 + 1)::int),
                                                              ('Анна', 'Ахматова', floor(random() * 8 + 1)::int),
                                                              ('Марина', 'Цветаева', floor(random() * 8 + 1)::int),
                                                              ('Сергей', 'Есенин', floor(random() * 8 + 1)::int),
                                                              ('Владимир', 'Маяковский', floor(random() * 8 + 1)::int),
                                                              ('Осип', 'Мандельштам', floor(random() * 8 + 1)::int),
                                                              ('Николай', 'Гоголь', floor(random() * 8 + 1)::int),
                                                              ('Михаил', 'Булгаков', floor(random() * 8 + 1)::int),
                                                              ('Александр', 'Блок', floor(random() * 8 + 1)::int),
                                                              ('Иван', 'Бунин', floor(random() * 8 + 1)::int),
                                                              ('Борис', 'Пастернак', floor(random() * 8 + 1)::int),
                                                              ('Александр', 'Солженицын', floor(random() * 8 + 1)::int),
                                                              ('Варлам', 'Шаламов', floor(random() * 8 + 1)::int),
                                                              ('Андрей', 'Платонов', floor(random() * 8 + 1)::int),
                                                              ('Василий', 'Шукшин', floor(random() * 8 + 1)::int),
                                                              ('Виктор', 'Пелевин', floor(random() * 8 + 1)::int),
                                                              ('Людмила', 'Улицкая', floor(random() * 8 + 1)::int),
                                                              ('Татьяна', 'Толстая', floor(random() * 8 + 1)::int),
                                                              ('Евгений', 'Замятин', floor(random() * 8 + 1)::int),
                                                              ('Владимир', 'Набоков', floor(random() * 8 + 1)::int),
                                                              ('Михаил', 'Шолохов', floor(random() * 8 + 1)::int),
                                                              ('Константин', 'Паустовский', floor(random() * 8 + 1)::int),
                                                              ('Валентин', 'Распутин', floor(random() * 8 + 1)::int),
                                                              ('Виктор', 'Астафьев', floor(random() * 8 + 1)::int),
                                                              ('Белла', 'Ахмадулина', floor(random() * 8 + 1)::int);

--changeset RSergey:3
INSERT INTO teachers(first_name, last_name) VALUES
                                                ('Иван', 'Петров'),
                                                ('Мария', 'Иванова'),
                                                ('Алексей', 'Сидоров'),
                                                ('Елена', 'Смирнова'),
                                                ('Дмитрий', 'Кузнецов'),
                                                ('Анна', 'Попова'),
                                                ('Сергей', 'Васильев'),
                                                ('Ольга', 'Павлова'),
                                                ('Андрей', 'Соколов'),
                                                ('Наталья', 'Михайлова');


