package com.my.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.my.serializable.Student;

public class DataBaseExecute {
	
	private DataBase myDB = new DataBase();
	public int insertStudent(Student stu){
		String sql = "insert into student values('" + stu.idNumber + "','" + stu.name + "','" + stu.studentClass
				+ "','" + stu.macAddr + "','" + stu.picName + "');";
		return myDB.executeUpdate(sql);
	}
	
	public boolean queryStudentExist(String number){
		String sql = "select * from student where number = '" + number + "';";
		ResultSet rs = myDB.executeRS(sql);
		boolean res = false;
		try {
			if(rs.next())
				res = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean queryStudentMac(String mac){
		String sql = "select * from student where macaddr = '" + mac + "';";
		ResultSet rs = myDB.executeRS(sql);
		boolean res = false;
		try {
			if(rs.next())
				res = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res; 
	}
	
	public List<Student> selectAll(){
		String sql = "select * from student";
		ResultSet rs = myDB.executeRS(sql);
		
		List<Student> students = new ArrayList<Student>();
		try {
			while(rs.next()){
				Student stu = new Student();
				stu.idNumber = rs.getString(1);
				stu.name = rs.getString(2);
				stu.studentClass = rs.getString(3);
				stu.macAddr = rs.getString(4);
				stu.picName = rs.getString(5);
				students.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			myDB.closeAll();
		}
		return students;
	}
	
	
}
