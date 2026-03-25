--liquibase formatted sql
--changeset RSergey:1
CREATE TABLE IF NOT EXISTS student_groups
(
    id SERIAL PRIMARY KEY,
    group_number INT NOT NULL UNIQUE
);
--changeset RSergey:2
CREATE TABLE IF NOT EXISTS students
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    group_number INT,
    FOREIGN KEY (group_number)
    REFERENCES student_groups (group_number)
    ON UPDATE CASCADE
);
--changeset RSergey:3
CREATE TABLE IF NOT EXISTS teachers
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL
);
--changeset RSergey:4
CREATE TABLE IF NOT EXISTS courses
(
    id SERIAL PRIMARY KEY,
    course_name VARCHAR(64) NOT NULL UNIQUE,
    teacher_id INT UNIQUE,
    FOREIGN KEY (teacher_id)
    REFERENCES teachers(id)
    ON DELETE SET NULL
);
--changeset RSergey:5
    CREATE TABLE IF NOT EXISTS group_course
(
    group_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (group_id, course_id),
    FOREIGN KEY (group_id) REFERENCES student_groups(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
--changeset RSergey:6
CREATE TABLE IF NOT EXISTS schedules
(
    id BIGSERIAL PRIMARY KEY,
    course_id INT NOT NULL,
    teacher_id INT,
    group_id INT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (teacher_id) REFERENCES teachers (id),
    FOREIGN KEY (group_id) REFERENCES student_groups (id)
);
