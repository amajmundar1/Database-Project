DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Maint_Comp CASCADE;
DROP TABLE IF EXISTS Hotel CASCADE;
DROP TABLE IF EXISTS Staff CASCADE;
DROP TABLE IF EXISTS Room CASCADE;
DROP TABLE IF EXISTS Assigned CASCADE;
DROP TABLE IF EXISTS House_Keeping CASCADE;
DROP TABLE IF EXISTS Receptionist CASCADE;
DROP TABLE IF EXISTS Manager CASCADE;
DROP TABLE IF EXISTS Booking CASCADE;
DROP TABLE IF EXISTS Repair CASCADE;

CREATE TABLE Customer
	(
		CustomerID integer NOT NULL,
		fName char(40) NOT NULL,
		lName char(40) NOT NULL,
		Address char(80),
		phNo char(15) NOT NULL,
		DOB char(11) NOT NULL,
		Gender integer,
		PRIMARY KEY (CustomerID)
	);

CREATE TABLE Maint_Comp
	(
		cmpID integer NOT NULL,
		Name char(40) NOT NULL,
		Address char(80) NOT NULL,
		certification char(20) NOT NULL,
		active_cert boolean NOT NULL,
		PRIMARY KEY (cmpID)
	);

CREATE TABLE Hotel
	(
		HotelID integer NOT NULL,
		Address char(80) NOT NULL,
		managed_by char(11) NOT NULL,
		PRIMARY KEY (HotelID)
	);

CREATE TABLE Staff
        (
                ssn char(11) NOT NULL,
                HotelID integer NOT NULL,
                fname char(40) NOT NULL,
                lName char(40) NOT NULL,
                Address char(80),
                PRIMARY KEY (ssn),
                FOREIGN KEY (HotelID) REFERENCES Hotel(HotelID)
        );

CREATE TABLE Room
	(
		RoomNo integer NOT NULL,
		HotelID integer NOT NULL,
		room_type char(50),
		PRIMARY KEY (HotelID, RoomNo),
		FOREIGN KEY (HotelID) REFERENCES Hotel(HotelID)
		ON DELETE CASCADE
	);

CREATE TABLE Receptionist
	(
		emp_ssn char(11) NOT NULL REFERENCES Staff(ssn),
		PRIMARY KEY (emp_ssn)
	);

CREATE TABLE House_Keeping
	(
		emp_ssn char(11) NOT NULL REFERENCES Staff(ssn),
		PRIMARY KEY (emp_ssn)
	);

CREATE TABLE Manager
	(
		emp_ssn char(11) NOT NULL REFERENCES Staff(ssn),
		PRIMARY KEY (emp_ssn)
	);

ALTER TABLE Hotel
ADD FOREIGN KEY (managed_by)
REFERENCES Manager(emp_ssn);

CREATE TABLE Booking 
	(
		RoomNo integer NOT NULL,
		HotelID integer NOT NULL,
		CustomerID integer NOT NULL,
		booking_date date NOT NULL,
		num_people integer,
		price float NOT NULL,
		PRIMARY KEY (RoomNo, HotelID, CustomerID),
		FOREIGN KEY (RoomNo, HotelID) REFERENCES Room(RoomNo, HotelID),
		FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
	);

CREATE TABLE Repair
	(
		RoomNo integer NOT NULL,
                HotelID integer NOT NULL,
		cmpID integer NOT NULL,
		repair_date date NOT NULL,
		requested_by char(11) NOT NULL,
		date_requested date NOT NULL,
		request_description char(100) NOT NULL,
		repair_description char(100),
		repair_type char(30),
		PRIMARY KEY (RoomNo, HotelID, cmpID),
                FOREIGN KEY (RoomNo, HotelID) REFERENCES Room(RoomNo, HotelID),
		FOREIGN KEY (cmpID) REFERENCES Maint_Comp(cmpID),
		FOREIGN KEY (requested_by) REFERENCES Manager(emp_ssn)
	);

CREATE TABLE Assigned
	(
		RoomNo integer NOT NULL,
                HotelID integer NOT NULL,
		emp_ssn char(11) NOT NULL,
		PRIMARY KEY (RoomNo, HotelID, emp_ssn),
                FOREIGN KEY (RoomNo, HotelID) REFERENCES Room(RoomNo, HotelID),
		FOREIGN KEY (emp_ssn) REFERENCES House_Keeping(emp_ssn)
	);

