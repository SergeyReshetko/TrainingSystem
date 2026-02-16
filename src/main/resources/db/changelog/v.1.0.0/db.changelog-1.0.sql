--liquibase formatted sql
--changeset RSergey:1
CREATE TABLE IF NOT EXISTS student_groups
(
    group_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    group_number INT NOT NULL,
    CONSTRAINT PK_student_groups_group_id PRIMARY KEY (group_id),
    CONSTRAINT UQ_student_groups_group_number UNIQUE (group_number)

);
--changeset RSergey:2
CREATE TABLE IF NOT EXISTS students
(
    student_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    group_id BIGINT NOT NULL,
    CONSTRAINT PK_students_student_id PRIMARY KEY (student_id),
    CONSTRAINT FK_students_group_id FOREIGN KEY (group_id) REFERENCES student_groups (group_id) ON DELETE CASCADE
);
--changeset RSergey:3
CREATE TABLE IF NOT EXISTS teachers
(
    teacher_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    CONSTRAINT PK_teachers_teacher_id PRIMARY KEY (teacher_id)
);
--changeset RSergey:4
CREATE TABLE IF NOT EXISTS courses
(
    course_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    teacher_id INT NOT NULL,
    course_name VARCHAR(64) NOT NULL UNIQUE,
    CONSTRAINT PK_courses_course_id PRIMARY KEY (course_id),
    CONSTRAINT FK_courses_teacher_id FOREIGN KEY (teacher_id)
    REFERENCES teachers (teacher_id) ON DELETE CASCADE,
    CONSTRAINT UQ_courses_teacher_id UNIQUE (teacher_id)
);
--changeset RSergey:5
CREATE TABLE IF NOT EXISTS schedules
(
    schedule_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    course_date DATE NOT NULL,
    group_id BIGINT NOT NULL,
    teacher_id INT NOT NULL,
    course_id INT NOT NULL,
    CONSTRAINT PK_schedule_schedule_id PRIMARY KEY (schedule_id),
    CONSTRAINT FK_schedule_group_id FOREIGN KEY (group_id)
    REFERENCES student_groups (group_id) ON DELETE CASCADE,
    CONSTRAINT FK_schedule_teacher_id FOREIGN KEY (teacher_id)
    REFERENCES teachers (teacher_id) ON DELETE CASCADE,
    CONSTRAINT FK_schedule_course_id FOREIGN KEY (course_id)
    REFERENCES courses (course_id) ON DELETE CASCADE
);