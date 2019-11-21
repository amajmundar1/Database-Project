SELECT count(p.part_number) AS PartsCount
FROM part_nyc p
WHERE p.on_hand > 70;

SELECT SUM(SumOnHand) 
FROM (
	SELECT ((sum(p.on_hand) - sum(s.on_hand)) + sum(s.on_hand)) SumOnHand 
	FROM part_nyc p, part_sfo s 
	WHERE p.part_number = s.part_number AND p.color = 0
	GROUP BY p.part_number 
     )
AS OnHand;

SELECT DISTINCT s.supplier_name  
FROM supplier s 
INNER JOIN part_nyc n 
	ON s.supplier_id=n.supplier 
INNER JOIN part_sfo ps 
	ON s.supplier_id=ps.supplier
GROUP BY s.supplier_name 
HAVING sum(n.on_hand)>sum(ps.on_hand);

SELECT s.supplier_name
FROM supplier s
WHERE s.supplier_id
	IN (SELECT supplier FROM part_nyc) AND s.supplier_id NOT IN (SELECT supplier FROM part_sfo);

/*
UPDATE part_nyc 
SET on_hand = 10;

DELETE FROM part_nyc
WHERE on_hand < 30;
*/
