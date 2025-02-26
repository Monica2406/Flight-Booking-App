package com.air_app.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConfiguration {
		private static final String url = "jdbc:mysql://localhost:3306/air_reservation";
		private static final String username = "root";
		private static final String password = "root";
		private static Connection con=null;

		public static Connection getConnection()  {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				if(con==null){
				con = DriverManager.getConnection(url, username, password);
				
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return con;
			

		}

	}
