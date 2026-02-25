--liquibase formatted sql
--changeset RSergey:1
CREATE TABLE IF NOT EXISTS student_groups
(
    group_id SERIAL PRIMARY KEY,
    group_number INT NOT NULL UNIQUE
);
--changeset RSergey:2
CREATE TABLE IF NOT EXISTS students
(
    student_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    group_number INT NOT NULL UNIQUE,
    FOREIGN KEY (group_number)
    REFERENCES student_groups (group_number)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
--changeset RSergey:3
CREATE TABLE IF NOT EXISTS teachers
(
    teacher_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL
);
--changeset RSergey:4
CREATE TABLE IF NOT EXISTS courses
(
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(64) NOT NULL UNIQUE,
    teacher_id INT NOT NULL UNIQUE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id)
    );
--changeset RSergey:5
CREATE TABLE IF NOT EXISTS schedules
(
    schedule_id BIGSERIAL PRIMARY KEY,
    date_lecture DATE NOT NULL UNIQUE,
    group_number INT NOT NULL UNIQUE,
    teacher_id INT NOT NULL UNIQUE,
    course_id INT NOT NULL UNIQUE,
    FOREIGN KEY (group_number) REFERENCES student_groups (group_number),
    FOREIGN KEY (teacher_id) REFERENCES teachers (teacher_id),
    FOREIGN KEY (course_id) REFERENCES courses (course_id)
    );