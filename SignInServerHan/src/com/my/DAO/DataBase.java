package com.my.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.my.connection.SqlConnection;


public class DataBase {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	SqlConnection mysql = null;
	public DataBase()
	{
		mysql = new SqlConnection("jdbc:mysql://localhost:3306/ourproject?characterEncoding=utf-8", "root", "");
	}
	//查询操作
	public ResultSet executeRS(String sql){
		try {
			conn = mysql.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//插入 更新 删除操作
	public int executeUpdate(String sql){
		int affectLine = 0;
		try {
			conn = mysql.getConnection();
			stmt = conn.createStatement();
			affectLine = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return affectLine;
	}
	
	
	public void closeAll(){
		if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
