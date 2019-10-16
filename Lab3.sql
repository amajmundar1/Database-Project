DROP TABLE IF EXISTS Professor CASCADE;
DROP TABLE IF EXISTS Department CASCADE;
DROP TABLE IF EXISTS Project CASCADE;
DROP TABLE IF EXISTS Graduates CASCADE;
DROP TABLE IF EXISTS Work_Project;
DROP TABLE IF EXISTS Work_Dept;
DROP TABLE IF EXISTS Runs;
DROP TABLE IF EXISTS Advisor;
DROP TABLE IF EXISTS Manages;
DROP TABLE IF EXISTS Works_In;
DROP TABLE IF EXISTS Musicians CASCADE;
DROP TABLE IF EXISTS Instrument CASCADE;
DROP TABLE IF EXISTS Album CASCADE;
DROP TABLE IF EXISTS Songs CASCADE;
DROP TABLE IF EXISTS Telephone CASCADE;
DROP TABLE IF EXISTS Place CASCADE;
DROP TABLE IF EXISTS Home;
DROP TABLE IF EXISTS Plays;
DROP TABLE IF EXISTS Producer;
DROP TABLE IF EXISTS Perform;
DROP TABLE IF EXISTS Appears;

CREATE TABLE Professor
	(
		ssn char(11) NOT NULL, 
		name char(40), 
		age integer, 
		p_rank char(40), 
		specialty char(40), 
		PRIMARY KEY (ssn)
	);
	
CREATE TABLE Department
	(
		dno char(40) NOT NULL, 
		dname char(40), 
		office char(40), 
		PRIMARY KEY (dno)
	);


CREATE TABLE Project
	(
		pno char(40) NOT NULL, 
		sponsor char(40), 
		start_date DATE, 
		end_date DATE, 
		budget float, 
		PRIMARY KEY (pno)
	);

CREATE TABLE Graduates
	(
		ssn char(11) NOT NULL, 
		name char(40), 
		age integer, 
		deg_pg char(40),
		major char(40),  
		PRIMARY KEY (ssn),
		FOREIGN KEY (major) REFERENCES Department (dno)
	);

CREATE TABLE Work_Project
	(
		pno char(40), 
		ssn char(11),
		manage char(11),
		since DATE, 
		PRIMARY KEY (pno, ssn), 
		FOREIGN KEY (pno) REFERENCES Project (pno), 
		FOREIGN KEY (ssn) REFERENCES GRADUATEs (ssn),
		FOREIGN KEY (manage) REFERENCES Professor (ssn)
	);

CREATE TABLE Work_Dept
	(
		ssn char(11),
		dno char(40),
		time_pc integer,
		PRIMARY KEY (ssn, dno),
		FOREIGN KEY (ssn) REFERENCES Professor (ssn),
		FOREIGN KEY (dno) REFERENCES Department (dno)
	);

CREATE TABLE Runs
	(
		dno char(40),
		ssn char(11),
		Primary KEY (dno),
		FOREIGN KEY (dno) REFERENCES Department (dno),
		FOREIGN KEY (ssn) REFERENCES Professor (ssn)
	);

CREATE TABLE Advisor
	(
		advisor_ssn char(11),
		advisee_ssn char(11),
		PRIMARY KEY (advisor_ssn),
		FOREIGN KEY (advisor_ssn) REFERENCES Graduates (ssn),
		FOREIGN KEY (advisee_ssn) REFERENCES Graduates (ssn)
	);

CREATE TABLE Manages
	(
		pno char(40) NOT NULL,
		ssn char(11),
		PRIMARY KEY (pno),
		FOREIGN KEY (pno) REFERENCES Project (pno),
		FOREIGN KEY (ssn) REFERENCES Professor (ssn)
	);

CREATE TABLE Works_In
	(
		pno char(40),
		ssn char(11),
		PRIMARY KEY (pno, ssn),
		FOREIGN KEY (pno) REFERENCES Project (pno),
		FOREIGN KEY (ssn) REFERENCES Professor (ssn)
	);

CREATE TABLE Major
	(
		ssn char(11),
		dno char(40),
		PRIMARY KEY (ssn),
		FOREIGN KEY (ssn) REFERENCES Graduates (ssn),
		FOREIGN KEY (dno) REFERENCES Department (dno)
	);




CREATE TABLE Instrument
	(
		instrID char(20),
		dname char(40),
		key char(40),
		PRIMARY KEY (instrID)
	);

CREATE TABLE Musicians
	(
		ssn char(11),
		name char(40),
		PRIMARY KEY (ssn)
	);

CREATE TABLE Songs
	(
		songID char(20),
		title char(40),
		author char(40),
		PRIMARY KEY (songID)
	);

CREATE TABLE Album
	(
		albumID char(20),
		copyRightDate DATE,
		speed INTEGER,
		title char(40),
		PRIMARY KEY (albumID)
	);

CREATE TABLE Telephone
	(
		phone_no char(20),
		PRIMARY KEY (phone_no)
	);

CREATE TABLE Place
	(
		address char(80),
		PRIMARY KEY (address)
	);

CREATE TABLE Home
	(
		phone_no char(20) NOT NULL,
		address char(80),
		lives char (11),
		PRIMARY KEY (phone_no),
		FOREIGN KEY (phone_no) REFERENCES Telephone (phone_no),
		FOREIGN KEY (address) REFERENCES Place (address),
		FOREIGN KEY (lives) REFERENCES Musicians (ssn)
	);

CREATE TABLE Plays
	(
		ssn char(11),
		instrID char(20),
		PRIMARY KEY (ssn, instrID),
		FOREIGN KEY (ssn) REFERENCES Musicians (ssn),
		FOREIGN KEY (instrID) REFERENCES Instrument (instrID)
	);

CREATE TABLE Perform
	(
		ssn char(11),
		songID char(20),
		PRIMARY KEY (ssn, songID),
		FOREIGN KEY (ssn) REFERENCES Musicians (ssn),
		FOREIGN KEY (songID) REFERENCES Songs (songID)
	);

CREATE TABLE Appears
	(
		songID char(20),
		albumID char(20),
		PRIMARY KEY (songID),
		FOREIGN KEY (songID) REFERENCES Songs (songID),
		FOREIGN KEY (albumID) REFERENCES Album (albumID)
	);

CREATE TABLE Producer
	(
		albumID char(20),
		ssn char(11),
		PRIMARY KEY (albumID),
		FOREIGN KEY (albumID) REFERENCES Album (albumID),
		FOREIGN KEY (ssn) REFERENCES Musicians (ssn)
	);
