SELECT sid, COUNT(pid) 
	FROM catalog 
	GROUP BY sid;
SELECT sid, COUNT(pid) 
	FROM catalog 
	GROUP BY sid 
	HAVING COUNT(pid) > 2;
SELECT s.sname, COUNT(*) 
	FROM suppliers s, catalog c, parts p 
	WHERE s.sid = c.sid AND p.pid = c.pid 
	GROUP BY s.sname, s.sid 
	HAVING EVERY(p.color = 'GREEN');
SELECT s.sname, MAX(c.cost)
	FROM suppliers s, catalog c, parts p
	WHERE s.sid = c.sid AND p.pid = c.pid
	GROUP BY s.sname, s.sid
	HAVING EVERY(p.color = 'Green' OR p.color = 'Red'); 
