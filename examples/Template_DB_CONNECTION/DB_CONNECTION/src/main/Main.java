package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private final static String URL = "jdbc:mysql://localhost:3306/";
	private final static String DB_NAME = "vanzari";
	private final static String USER = "root";
	private final static String PASSWORD = "root1234";
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection c;
		
		try {
			c=DriverManager.getConnection(URL+DB_NAME, USER, PASSWORD);

			PreparedStatement s= c.prepareStatement("select * from produs");
			ResultSet rez= s.executeQuery();
			while (rez.next()) {
				
				System.out.println("Produs: " + rez.getString("nume"));
				}
			
			
			
		}
		
		catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + ((SQLException) e).getSQLState());
		    System.out.println("VendorError: " + ((SQLException) e).getErrorCode());
		}
		
		

	}

}
