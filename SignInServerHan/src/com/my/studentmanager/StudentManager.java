package com.my.studentmanager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import com.my.DAO.DataBaseExecute;
import com.my.serializable.Student;


public class StudentManager {

	public static final String serverPicPath = "D:\\SignInManager\\SignInManager\\SignInServerHan\\image\\";
	
	
	private static JFrame frame = null;
	private static List<StudentState> stuStates = new ArrayList<StudentState>();
	private static boolean update = true;
	private DataBaseExecute dm = new DataBaseExecute();
	public static StudentState selectStudent = null;
	//private Image picture = new BufferedImage();
	
	
	public static synchronized List<StudentState> getStuStates() {
		return stuStates;
	}

	public static synchronized boolean getUpdate(){
		return update;
	}
	
	public static synchronized void setUpdate(boolean update){
		StudentManager.update = update;
	}
	public static JFrame getFrame() {
		return frame;
	}
	public static void setFrame(JFrame frame) {
		StudentManager.frame = frame;
	}
	//显示所有信息
	public void ShowStudentInfo(){
		System.out.println("当前在线学生：");
		for(StudentState ss:stuStates){
			Student s = ss.getStudent();
			int o = ss.getOnlineState();
			String str = s.name + "," + s.idNumber + "," + s.studentClass + ", 状态：" + o;
			System.out.println(str);
			update = false;
		}
	}
	//学生状态的计算
	public void calculateState(){
		
	}
	//加载学生信息
	public void LoadStudentInfo(){
		List<Student> students = dm.selectAll();
		for(Student s:students){
			StudentState ss = new StudentState();
			ss.setStudent(s);
			ss.setOnlineState(0);
			ss.initialPanel();
			stuStates.add(ss);
		}
	}
	//异步更新状态
	public static synchronized boolean updateState(String macAddr){
		for(StudentState ss:stuStates){
			if(ss.getStudent().macAddr.equals(macAddr))
			{
				signIn(ss);
				return true;
			}
		}
		return false;
	}
	
	public static synchronized void signIn(StudentState ss){
		ss.setOnlineState(StudentState.online);
		ss.setLastSignTime(new Date(System.currentTimeMillis()));
		ss.setSign(true);
	}
	
	public static synchronized void insertStudent(Student stu)
	{
		StudentState ss = new StudentState();
		ss.setStudent(stu);
		ss.setOnlineState(0);
		ss.initialPanel();
		try {
			ss.getStudent().picName = savePicToFile(stu.picName,stu.picLength,stu.headPic);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		stuStates.add(ss);
		update = true;
	}

	public static String savePicToFile(String name,int length,byte[] image) throws IOException{
		
		String path = serverPicPath;
		path += File.separator;
		path += name;
		File file = new File(path);
		if(!file.exists())
			file.createNewFile();
		OutputStream os = new FileOutputStream(file);
		os.write(image);
		os.close();
		System.out.println(path);
		return path;
	}
	
	public static synchronized void checkOnlineState(){
		for(StudentState ss:stuStates){
			if(ss.isSign()){
				Date currentTime = new Date(System.currentTimeMillis());
				//long millis5 = 300000;//5分钟
				long millis5 = 20000;
				long cm = currentTime.getTime();
				long lm = ss.getLastSignTime().getTime();
				if((cm - lm) > millis5){
					ss.reduceOnlineLevel();
					ss.setLastSignTime(currentTime);
					update = true;
					System.out.println("somebody is going away");
				}
			}
		}
	}

}
