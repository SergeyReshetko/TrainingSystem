--liquibase formatted sql
--changeset RSergey:1
INSERT INTO group_course (group_id, course_id)
SELECT
    g.id,
    c.id
FROM (
         SELECT id
         FROM student_groups
         ORDER BY RANDOM()
             LIMIT 4
     ) g
         CROSS JOIN (
    SELECT id
    FROM courses
    ORDER BY RANDOM()
        LIMIT 8
) c
WHERE NOT EXISTS (
    SELECT 1
    FROM group_course gc
    WHERE gc.group_id = g.id
      AND gc.course_id = c.id
);
