package com.my.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
	private final String driver = "com.mysql.jdbc.Driver";
	private String url,root,password;
	private Connection conn = null;
	public SqlConnection(String url,String root,String password)
	{
		this.url = url;
		this.root = root;
		this.password = password;
	}
	public Connection getConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,root,password);
			
		} catch (ClassNotFoundException e)
		{
			System.out.println("Sorry,can't find the Driver!");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("Sorry,SQL has exception!");
			e.printStackTrace();
		}
		return conn;
	}
}
