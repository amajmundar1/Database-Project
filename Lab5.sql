CREATE TABLE Suppliers
	(
		sid integer,
		sname char(40),
		address char(80),
		PRIMARY KEY (sid)
	};
	
CREATE TABLE Parts
	(
		pid integer,
		pname char(40),
		color char(20),
		PRIMARY KEY (pid)
	);
	
CREATE TABLE Catalog
	(
		sid integer,
		pid integer,
		cost real,
		PRIMARY KEY (sid, pid),
		FOREIGN KEY (sid) REFERENCES Suppliers(sid),
		FOREIGN KEY (pid) REFERENCES Parts(pid)
	);
	
SELECT pid FROM Catalog WHERE Catalog.cost < 10;
SELECT pname FROM Parts WHERE Parts.pid = (SELECT pid from Catalog WHERE Catalog.cost < 10);
SELECT address FROM Suppliers WHERE Suppliers.sid = (SELECT sid from Catalog WHERE Catalog.pid = (SELECT pid from Parts WHERE Parts.pname = 'Fire Hydrant Cap'));
SELECT Suppliers.sname FROM Suppliers, Catalog, Parts WHERE Suppliers.sid = Catalog.sid AND Parts.pid = Catalog.pid AND Parts.color = 'green';