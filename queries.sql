SELECT pid FROM catalog c WHERE c.cost < 10;
SELECT pname FROM parts p, catalog c WHERE p.pid = c.pid AND c.cost < 10;
SELECT address FROM suppliers s, parts p, catalog c WHERE s.sid = c.sid AND c.pid = p.pid AND p.pname = 'Fire Hydrant Cap';
SELECT s.sname FROM suppliers s, catalog c, parts p WHERE s.sid = c.sid AND c.pid = p.pid AND (p.color = 'green' or p.color = 'Green');
SELECT DISTINCT s.sname, p.pname FROM suppliers s, catalog c, parts p WHERE s.sid = c.sid AND c.pid = p.pid;
