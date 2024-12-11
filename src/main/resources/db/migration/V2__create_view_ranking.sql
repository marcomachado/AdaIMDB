/*
CREATE OR REPLACE VIEW RANKING_VIEW AS
select ROW_NUMBER() OVER() as id, s1.user_id, total*(s1.total_1*100/s2.total) as score_final
from (select user_id, count(*) as total_1
      from rounds where points=1 group by user_id) s1,
     (select user_id, count(*) as total
      from rounds group by user_id) s2
where s1.user_id = s2.user_id
order by score_final desc;*/
CREATE VIEW RANKING_VIEW
AS
WITH TotalPoints AS (
    SELECT r.user_id, COUNT(*) AS total_1
    FROM Rounds r
    WHERE r.points = 1
    GROUP BY r.user_id
),
TotalRounds AS (
 SELECT r.user_id, COUNT(*) AS total
 FROM Rounds r
 GROUP BY r.user_id
)
SELECT ROW_NUMBER() OVER() as id_fake, s1.id_user as user_id, s1.total_1 * 100.0 / tr.total as score_final
FROM (
         SELECT u.id as id_user, COALESCE(tp.total_1, 0) AS total_1
         FROM Users u
              LEFT JOIN TotalPoints tp ON u.id = tp.user_id
     ) s1
         JOIN TotalRounds tr ON s1.id_user = tr.user_id
ORDER BY score_final DESC;