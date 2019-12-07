/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public ResultSet executeQuery (String query, Boolean verbos) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
     if (verbos) { 
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
     while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
	 }
      //stmt.close ();
      return rs;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB
	try {
		ResultSet c = esql.executeQuery("SELECT MAX(customerID) AS max_id FROM customer", false);
		int cID = -1;
		if (c.next()) {
                	cID = c.getInt("max_id") + 1;
		}
		System.out.print("\tEnter your First Name: ");
		String fname = in.readLine();
		System.out.print("\tEnter your Last Name: ");
		String lname = in.readLine();
		System.out.print("\tEnter your Address: ");
		String Address = in.readLine();
		System.out.print("\tEnter your Phone Number with no symbols or spaces: ");
		String phNo = in.readLine();
		System.out.print("\tEnter your Date of Birth using YYYY-MM-DD format: ");
		String DOB = in.readLine();
		System.out.print("\tEnter your Gender: ");
		String gender = in.readLine();
		try{
			String query = "INSERT INTO customer (customerID, fname, lname, address, phno, dob, gender) VALUES (" + cID + ", \'" + fname + "\', \'" + lname + "\', \'" + Address + "\', " + phNo + ", DATE \'" + DOB + "\', \'" + gender +"\')";
			esql.executeQuery(query, false);
			System.out.print("Successfully Added Customer!");
		}catch(Exception e) {
			System.err.println (e.getMessage());
		}
	} catch (Exception e){
		System.err.println (e.getMessage());
	}
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
	try{
		System.out.print("\tEnter the Hotel ID: ");
		String hID = in.readLine();
		String getHID = "SELECT * FROM Hotel WHERE hotelID = " + hID;
		ResultSet res = esql.executeQuery(getHID, false);
		if (!res.isBeforeFirst()) {
			System.out.print("\tHotel ID Not Found\n");
		} else {
			System.out.print("\tEnter the Room Number: ");
			String roomNum = in.readLine();
			ResultSet res1 = esql.executeQuery("Select * FROM Room WHERE hotelID = " + hID + " AND roomNo = " + roomNum, false);
			if (res1.next()) {
				System.out.print("\tRoom Already Exists\n");
			} else{
				System.out.print("\tEnter the Room Type: ");
				String rType = in.readLine();
				String query = "INSERT INTO Room (hotelID, roomNo, roomType) VALUES (" + hID + ", " + roomNum + ", \'" + rType + "\')";
				esql.executeQuery(query, false);
				System.out.print("\tSuccessfully Added Room!\n");
			}
		}
	} catch (Exception e) {
		System.err.println (e.getMessage());
	}
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
	try {
		System.out.print("\tEnter the Company Name: ");
		String CName = in.readLine();
		System.out.print("\tEnter the Company Address: ");
		String cAddress = in.readLine();
		String check = "SELECT * FROM MaintenanceCompany WHERE name = \'" + CName + "\'  AND address = \'" + cAddress + "\'";
		ResultSet res = esql.executeQuery(check, false);
		if (res.next()) {
			System.out.print("\tCompany Already Exists in Database\n");
		} else {
			System.out.print("\tIs the Company Certified (Y/N): ");
			String cert = in.readLine();
			String cmp = new String("Y");
			boolean certBool = false;
			if (cert.equals(cmp)) {
				certBool = true;
			}
			ResultSet c = esql.executeQuery("SELECT MAX(cmpID) AS max_id FROM MaintenanceCompany", false);
                	int cID = -1;
			if (c.next()) {
				cID = c.getInt("max_id") + 1;
			}
			String query = "INSERT INTO MaintenanceCompany (cmpID, name, address, isCertified) VALUES (" + cID + ", \'" + CName + "\', \'" + cAddress + "\', " + certBool +")";
			esql.executeQuery(query, false);
			System.out.print("\tSuccessfully Added a Maintenance Company!\n");
		}
	} catch (Exception e) {
		System.err.println (e.getMessage());
	}
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  // Given repair details add repair in the DB
	try {
		System.out.print("\tEnter the Hotel ID: ");
		String hID = in.readLine();
		System.out.print("\tEnter the Room Number: ");
		String roomNum = in.readLine();
		ResultSet res = esql.executeQuery("Select * FROM Room WHERE hotelID = " + hID + " AND roomNo = " + roomNum, false);
		if (res.next()) {
			System.out.print("\tEnter the Maintenace Company ID: ");
			String maintID = in.readLine();
			ResultSet res1 = esql.executeQuery("SELECT * FROM MaintenanceCompany WHERE cmpID = " + maintID + " AND isCertified", false);
			if (res1.next()) {
				ResultSet c = esql.executeQuery("SELECT MAX(rID) AS max_id FROM Repair", false);
                        	int rID = -1;
                        	if (c.next()) {
                                	rID = c.getInt("max_id") + 1;
                        	}
				System.out.print("\tEnter the Repair Date in YYYY-MM-DD Format: ");
				String date = in.readLine();
				System.out.print("\tEnter a Description of the maintenance required: ");
				String desc = in.readLine();
				System.out.print("\tEnter the Repair Type: ");
				String rType = in.readLine();
				String query = "INSERT INTO Repair (rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (" + rID + ", " + hID + ", " + roomNum + ", " + maintID + ", DATE \'" + date + "\', \'" + desc + "\', \'" + rType + "\')";
				esql.executeQuery(query, false);
				System.out.print("Successfully Added a Maintenance Request");
			} else {
				System.out.print("\tMaintenance Company Does Not Exist!\n");
			}
		} else {
			System.out.print("\tNo Such Room Exists!\n");
		}
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
	try {
		System.out.print("\tEnter Hotel ID: ");
		String hID = in.readLine();
		System.out.print("\tEnter the Room Number: ");
		String roomNum = in.readLine();
		ResultSet res = esql.executeQuery("Select * FROM Room WHERE hotelID = " + hID + " AND roomNo = " + roomNum, false);
		if (res.next()) {
			System.out.print("\tEnter the Customer's First Name: ");
			String Fname = in.readLine();
			System.out.print("\tEnter the Customer's Last Name: ");
			String Lname = in.readLine();
			ResultSet getCust = esql.executeQuery("SELECT COUNT(*) AS rowcount FROM customer WHERE fname = \'" + Fname + "\' AND lname = \'" + Lname + "\'", false);
			if (getCust.next()) {
				int count = getCust.getInt("rowcount");
				String cID = "";
				ResultSet getCID = esql.executeQuery("SELECT * FROM customer WHERE fname = \'" + Fname + "\' AND lname = \'" + Lname + "\'", false);
				if (count == 1) {
					getCID.next();
					cID = getCID.getString("customerID");
				} else {
					System.out.print("\tThere are more than 1 customer with this name. Please specify a Customer ID: ");
					cID = in.readLine();
				}
				ResultSet verify = esql.executeQuery("SELECT * FROM customer WHERE customerID = " + cID, false);
				if (verify.next()) {
					System.out.print("\tEnter the Date you with to book the room for in YYYY-MM-DD Format: ");
					String date = in.readLine();
					ResultSet checkDate = esql.executeQuery("SELECT * FROM Booking WHERE hotelID = " + hID + " AND roomNo = " + roomNum + " AND bookingDate = DATE \'" + date + "\'", false);
					if (!checkDate.next()) {
						ResultSet c = esql.executeQuery("SELECT MAX(bID) AS max_id FROM Booking", false);
                                		int bID = -1;
                                		if (c.next()) {
                                        		bID = c.getInt("max_id") + 1;
                                		}
						System.out.print("Enter the Price of the room: ");
						String price = in.readLine();
						System.out.print("Enter the number of Occupants: ");
						String numPeople = in.readLine();
						String query = "INSERT INTO Booking (bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price) VALUES (" + bID + ", " + cID + ", " + hID + ", " + roomNum + ", DATE \'" + date + "\', " + numPeople + ", "+ price + ")";
						esql.executeQuery(query, false);
						System.out.print("Successfully Booked Room!");
					} else {
						System.out.print("\tRoom is already Booked\n");
					}
				} else {
					System.out.print("\tInvalid Customer ID!\n");
				}
			} else {
				System.out.print("\tNo Customer with that name exists!\n");
			}
		} else {
			System.out.print("\tNo Such Room Exists!\n");
		}
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
	try {
		System.out.print("\tEnter the Staff's Social Security Number: ");
		String ssn = in.readLine();
		System.out.print("\tEnter the Hotel ID: ");
		String hID = in.readLine();
		ResultSet staff = esql.executeQuery("SELECT * FROM Staff WHERE SSN = " + ssn + " AND employerID = " + hID + " AND role = \'HouseCleaning\'", false);
		String fname = "";
		if (staff.next()) {
			fname = staff.getString("fname");
			System.out.print("\tEnter the Room Number you wish to asign to " + fname + ": ");
			String roomNum = in.readLine();
			ResultSet res = esql.executeQuery("Select * FROM Room WHERE hotelID = " + hID + " AND roomNo = " + roomNum, false);
                	if (res.next()) {
				 ResultSet c = esql.executeQuery("SELECT MAX(asgID) AS max_id FROM Assigned", false);
                                 int asgID = -1;
                                 if (c.next()) {
                                 	asgID = c.getInt("max_id") + 1;
                                 }
				String query = "INSERT INTO Assigned (asgID, staffID, hotelID, roomNo) VALUES (" + asgID + ", " + ssn + ", " + hID + ", " + roomNum + ")";
				System.out.print("\tSuccessfully Assigned " + fname + " to Room Number " + roomNum + "!\n");
			} else {
				System.out.print("\tRoom does not exist in the Hotel!\n");
			}
		} else {
			System.out.print("\tStaff Member Does not exist!\n");
		}
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
	try {
		System.out.print("\tEnter the Hotel ID: ");
		String hID = in.readLine();
		System.out.print("\tEnter your Social Security Number: ");
		String ssn = in.readLine();
		ResultSet checkHotelManager = esql.executeQuery("SELECT * FROM Hotel WHERE hotelID = " + hID + " AND manager = " + ssn, false);
		if (checkHotelManager.next()) {
			System.out.print("\tEnter the Room Number: ");
			String roomNum = in.readLine();
			System.out.print("\tEnter the Repair ID: ");
			String repID = in.readLine();
			ResultSet checkRepair = esql.executeQuery("SELECT * FROM Repair WHERE rID =" + repID + " AND hotelID = " + hID + " AND roomNo = " + roomNum, false);
			if (checkRepair.next()) {
				ResultSet c = esql.executeQuery("SELECT MAX(reqID) AS max_id FROM Request", false);
                		int rID = -1;
                		if (c.next()) {
                        		rID = c.getInt("max_id") + 1;
                		}
				System.out.print("\tEnter Today's Date in YYYY-MM-DD Format: ");
				String date = in.readLine();
				System.out.print("\tEnter a Description of the Repair Request: ");
				String desc = in.readLine();
				String query = "INSERT INTO Request (reqID, managerID, repairID, requestDate, description) VALUES (" + rID + ", " + ssn + ", " + repID + ", DATE \'" + date + "\', \'" + desc +"\')";
				esql.executeQuery(query, false);
				System.out.print("\tSuccessfully Made a Repair Request!\n");
			} else {
				System.out.print("\tNot a Valid Repair, Hotel, Room Number Combination!\n");
			}
		} else {
			System.out.print("\tNot a valid user!\n");
		}
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
	int hotelID;
	try {
	System.out.print("Enter hotelID: ");
	hotelID = Integer.parseInt(in.readLine());
 	System.out.println("Checking today's available rooms.");
	esql.executeQuery("SELECT COUNT(*) FROM room WHERE hotelid = " + hotelID 
                           + " AND (hotelid, roomno) NOT IN "
                           + "(SELECT hotelid, roomno FROM booking " 
                           + "WHERE bookingdate = '" + LocalDate.now().toString() + "');", true);  
	}catch (Exception e) {
		System.err.println (e.getMessage());
    }//end try
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
int hotelID;
	try {
		System.out.print("Enter hotelID: ");
		hotelID = Integer.parseInt(in.readLine());
		System.out.println("Checking today's booked rooms.");
		esql.executeQuery("SELECT COUNT(*) FROM booking " 
				+ "WHERE hotelid = " + hotelID 
				+ " AND bookingdate = '" + LocalDate.now().toString() + "';", true);  
	}catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
	int hotelID;
	String date;
	int day, month, year;
	LocalDate startDate, endDate; 
	try {
		System.out.print("Enter hotelID: ");
		hotelID = Integer.parseInt(in.readLine());
		System.out.print("Enter intial date (mm-dd-yyyy): ");
		date = in.readLine();
      
		String[] tokens = date.split("-");
		month = Integer.parseInt(tokens[0]);
		day = Integer.parseInt(tokens[1]);
		year = Integer.parseInt(tokens[2]);
		startDate = LocalDate.of(year,month,day);
		endDate = startDate.plusDays(7);

		esql.executeQuery("SELECT r.* FROM room r WHERE r.hotelid = " + hotelID 
                        	+ " AND (r.hotelid, r.roomno) NOT IN "
                        	+ "(SELECT b.hotelid, b.roomno FROM booking b " 
                        	+ "WHERE b.bookingdate BETWEEN '" + startDate.toString() 
                        	+ "' AND '" + endDate.toString() + "');", true);  

	}catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
	int k;
    String date1, date2;
    LocalDate startDate, endDate; 

    try {
      System.out.print("Enter k: ");
      k = Integer.parseInt(in.readLine());
      System.out.print("Enter start date (mm-dd-yyyy): ");
      date1 = in.readLine();
      System.out.print("Enter end date (mm-dd-yyyy): ");
      date2 = in.readLine();

      String[] tokens1 = date1.split("-");
      String[] tokens2 = date2.split("-");
      
      startDate = LocalDate.of(Integer.parseInt(tokens1[2]), Integer.parseInt(tokens1[0]) , Integer.parseInt(tokens1[1]));
      endDate = LocalDate.of(Integer.parseInt(tokens2[2]), Integer.parseInt(tokens2[0]) , Integer.parseInt(tokens2[1]));

		esql.executeQuery("SELECT * FROM booking WHERE bookingdate BETWEEN '" + startDate.toString() 
                        	  + "' AND '" + endDate.toString() + "' ORDER BY price DESC LIMIT " + k + ";", true);  

 	} catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
	int k;
	String customerName;

	try {
		System.out.print("Enter k: ");
		k = Integer.parseInt(in.readLine());
		System.out.print("Enter customer's full name: ");
		String fullName = in.readLine();
		String[] names = fullName.split(" ");

		esql.executeQuery("SELECT b.price FROM booking b, customer c WHERE c.fName = '" + names[0] 
                        	+ "' AND c.lName = '" + names[1] 
                        	+ "' AND b.customer = c.customerID ORDER BY b.price DESC LIMIT " + k + ";", true);  

	} catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
	try {
 		System.out.print("Enter hotelID: ");
 		int hotelID = Integer.parseInt(in.readLine());
 		System.out.print("Enter customer's full name: ");
 		String fullName = in.readLine();
 		String[] names = fullName.split(" ");
		System.out.print("Enter start date (mm-dd-yyyy): ");
		String date1 = in.readLine();
		System.out.print("Enter end date (mm-dd-yyyy): ");
		String date2 = in.readLine();
		String[] tokens1 = date1.split("-");
		String[] tokens2 = date2.split("-");
		LocalDate startDate = LocalDate.of(Integer.parseInt(tokens1[2]), Integer.parseInt(tokens1[0]) , Integer.parseInt(tokens1[1]));
		LocalDate endDate = LocalDate.of(Integer.parseInt(tokens2[2]), Integer.parseInt(tokens2[0]) , Integer.parseInt(tokens2[1]));

		esql.executeQuery("SELECT b.price FROM booking b, customer c WHERE b.hotelid = " + hotelID 
                        	+ " AND c.fName = '" + names[0] + "' AND c.lName = '" + names[1] 
                        	+ "' AND b.customer = c.customerID "
                        	+ "AND b.bookingdate BETWEEN DATE '" + startDate.toString() 
                        	+ "' AND DATE '" + endDate.toString() + "';", true);  

	}catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
	try {
	System.out.print("Enter maintenance company name: ");
	String companyName = in.readLine();
	esql.executeQuery("SELECT rep.repairType, rep.hotelID, rep.roomNo FROM repair rep, MaintenanceCompany mc" 
                        	+ " WHERE mc.name = '" + companyName 
                        	+ "' AND mc.cmpID = rep.mCompany;", true);  
	}catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
	try {
		System.out.print("Enter k: ");
		int k = Integer.parseInt(in.readLine());

		esql.executeQuery("SELECT mc.name, COUNT(rep.rid) as num_of_repairs FROM repair rep, MaintenanceCompany mc"
                        	+ " WHERE mc.cmpID = rep.mCompany"
                        	+ " GROUP BY mc.name"
                        	+ " ORDER BY num_of_repairs DESC LIMIT " + k + ";", true); 
	} catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
	try {
	System.out.print("Enter hotelID: ");
	int hotelID = Integer.parseInt(in.readLine());
	System.out.print("Enter roomNo: ");
	int roomNo = Integer.parseInt(in.readLine());

	esql.executeQuery("SELECT hotelID, roomNo, EXTRACT(YEAR FROM repairDate) AS year, COUNT(rID) as num_of_repairs"
                        	+ " FROM repair"
                        	+ " WHERE hotelID = " + hotelID + " AND roomNo = " + roomNo
                        	+ " GROUP BY hotelID, roomNo, year;", true);  
	}catch (Exception e) {
		System.err.println (e.getMessage());
	}//end try
   }//end listRepairsMade

}//end DBProject
