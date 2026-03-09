--liquibase formatted sql
--changeset RSergey:1
INSERT INTO group_course (group_id, course_id)
SELECT
    g.group_id,
    c.course_id
FROM (
         SELECT group_id
         FROM student_groups
         ORDER BY RANDOM()
             LIMIT 4
     ) g
         CROSS JOIN (
    SELECT course_id
    FROM courses
    ORDER BY RANDOM()
        LIMIT 8
) c
WHERE NOT EXISTS (
    SELECT 1
    FROM group_course gc
    WHERE gc.group_id = g.group_id
      AND gc.course_id = c.course_id
);
