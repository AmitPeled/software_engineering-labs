package db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import com.mysql.cj.protocol.Resultset;

public class Main {
	static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	// update USER, PASS and DB URL according to credentials provided by the
	// website:
	// https://remotemysql.com/
	// in future get those hardcode string into separated config file.
	static private final String DB = "CXBhfBWaM9";
	static private final String DB_URL = "jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false";
	static private final String USER = "CXBhfBWaM9";
	static private final String PASS = "KotOXxFZry";

	public static void printTable(String tablename, Statement stmt) {
		System.out.println("\t============ printing flights table");
		try {
			String sql = "SELECT * FROM " + tablename;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int num = rs.getInt("num");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				int distance = rs.getInt("distance");
				int price = rs.getInt("price");

				System.out.format("Number %5s Origin %15s destinations %18s Distance %5d Price %5d\n", num, origin,
						destination, distance, price);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("cannot print table");
			System.err.println(e.getMessage());

		}
	}

	public static void main(String[] args) throws SSLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("starting the connection to DB");
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.err.println("cannot connect to DB");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println("cannot find driver class");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		try {
			try {
				/** exerecise 1+2 */
				System.out.println("\nTask2: ");
				rs = stmt.executeQuery("Select * FROM flights WHERE num = 387");
				rs.next();
				int priceBefore = rs.getInt("price");
				rs.updateInt("price", 1500);
				rs.updateRow();
				int priceAfter = rs.getInt("price");
				rs.close();
				System.out.println("price of flight 387 changed from " + priceBefore + " to " + priceAfter);
			} catch (SQLException se) {
				System.err.println("Cannot execute Task 1-2");
				System.out.println("SQLException: " + se.getMessage());
				System.exit(1);
			}
			try {
				/** exerecise 3 */
				System.out.println("\nTask3: ");
				rs = stmt.executeQuery("Select * FROM flights WHERE distance > 1000");
				ArrayList<Integer> pricesBefore = new ArrayList<Integer>();
				while (rs.next()) {
					pricesBefore.add(rs.getInt("price"));
				}
				
				rs.beforeFirst();
				while(rs.next()) {
					rs.updateInt("price",(int)(rs.getInt("price")+50));
					rs.updateRow();
				}
				rs.close();
				
				rs = stmt.executeQuery("Select * FROM flights WHERE distance > 1000");
				for (int pricebefore : pricesBefore) {
					rs.next();
					System.out.println("Price of flight number " + rs.getInt("num") + ", where distance = "
							+ rs.getInt("distance") + " changed from " + pricebefore + " to " + rs.getInt("price"));
				}
				rs.close();
			} catch (SQLException e) {
				System.err.println("Cannot execute Task 3");
				System.err.println(e.getMessage());
				System.exit(1);
			}
			try {
				/** exercise 4 */
				System.out.println("\nTask4: ");
				System.out.println("Working now with PreparedStatement");	
				PreparedStatement pstmt = conn.prepareStatement("Select * FROM flights WHERE distance > ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pstmt.setInt(1, 1000);
				ArrayList<Integer> pricesBefore = new ArrayList<Integer>();
				rs = pstmt.executeQuery();
				while (rs.next()) {
					pricesBefore.add(rs.getInt("price"));
				}
				
				rs.beforeFirst();
				while (rs.next()) {
					rs.updateInt("price",(int)(rs.getInt("price")+50));
					rs.updateRow();
				}
				rs.close();
				
				pstmt = conn.prepareStatement("Select * FROM flights WHERE distance > ?");
				pstmt.setInt(1, 1000);
				rs = pstmt.executeQuery();
				for (int pricebefore : pricesBefore) {
					rs.next();
					System.out.println("Price of flight number " + rs.getInt("num") + ", where distance = "
							+ rs.getInt("distance") + " changed from " + pricebefore + " to " + rs.getInt("price"));
				}
				rs.close();
				pstmt.close();

			} catch (SQLException e) {
				System.err.println("Cannot execute Task 4");
				System.err.println(e.getMessage());
				System.exit(1);
			}
			// printTable("flights", stmt);
			System.out.println("\nDone.");
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			se.printStackTrace();
			System.err.println("error occured");
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
