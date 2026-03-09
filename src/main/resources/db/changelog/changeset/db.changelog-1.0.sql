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
    group_number INT,
    FOREIGN KEY (group_number)
    REFERENCES student_groups (group_number)
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
    teacher_id INT UNIQUE,
    FOREIGN KEY (teacher_id)
    REFERENCES teachers(teacher_id)
    ON DELETE SET NULL
);
--changeset RSergey:5
    CREATE TABLE IF NOT EXISTS group_course
(
    group_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (group_id, course_id),
    FOREIGN KEY (group_id) REFERENCES student_groups(group_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
--changeset RSergey:6
CREATE TABLE IF NOT EXISTS schedules
(
    schedule_id BIGSERIAL PRIMARY KEY,
    course_id INT NOT NULL,
    teacher_id INT,
    group_id INT,
    start_time TIME NOT NULL,
    end_time TIME,
    schedule_date date  NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses (course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers (teacher_id),
    FOREIGN KEY (group_id) REFERENCES student_groups (group_id)
);
