CREATE TABLE Professor
	(
		ssn char(11) NOT NULL, 
		name, 
		age integer, 
		p_rank, 
		specialty, 
		PRIMARY KEY(ssn)
	);
	
CREATE TABLE Department
	(
		dno Not NULL, 
		dname, 
		office, 
		PRIMARY KEY(dno)
	);


CREATE TABLE Project
	(
		pno NOT NULL, 
		sponsor, 
		start_date DATETIME, 
		end_date DATETIME, 
		budget float, 
		PRIMARY KEY(pno)
	);

CREATE TABLE Graduate
	(
		ssn char(11) NOT NULL, 
		name, 
		age integer, 
		deg_pg, 
		PRIMARY KEY(ssn)
	);

CREATE TABLE Work_Project
	(
		pno, 
		ssn char(11),
		manage char(11),
		since DATETIME, 
		PRIMARY KEY(pno, ssn), 
		FOREIGN KEY(pno) REFERENCES Project(pno), 
		FOREIGN KEY(ssn) REFERENCES GRADUATE(ssn),
		FOREIGN KEY(manage) REFERENCES Professor(ssn)
	);
CREATE TABLE Work_Dept
	(
		ssn char(11),
		dno,
		time_pc integer,
		PRIMARY KEY(ssn, dno),
		FOREIGN KEY(ssn) REFERENCES Professor(ssn),
		FOREIGN KEY(dno) REFERENCES Department(dno)
	);
ALTER TABLE Professor
	ADD COLUMN dept_id NOT NULL,
	ADD CONSTRAINT prof_has_one_dept
		FOREIGN KEY (ssn, dno) REFERENCES Work_Dept(ssn, dno);


