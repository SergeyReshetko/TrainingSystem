--liquibase formatted sql
--changeset RSergey:1
DROP TABLE IF EXISTS schedules;
--changeset RSergey:2
DROP TABLE IF EXISTS students;
--changeset RSergey:3
DROP TABLE IF EXISTS group_course;
--changeset RSergey:4
DROP TABLE IF EXISTS courses;
--changeset RSergey:5
DROP TABLE IF EXISTS teachers;
--changeset RSergey:6
DROP TABLE IF EXISTS student_groups