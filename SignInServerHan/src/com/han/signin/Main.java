package com.han.signin;

import com.my.studentmanager.StudentManager;
import com.my.tcpserver.TCPServer;
import com.my.udpserver.UDPServer;

public class Main 
{
	public static int count = 0;
	private final StudentManager stuMan = new StudentManager();
	public StudentManager getStuMan() {
		return stuMan;
	}
	public void main()
	{
		stuMan.LoadStudentInfo();
//		new Thread(){
//			public void run(){
//				while(true){
//					if(StudentManager.getUpdate()){
//						stuMan.ShowStudentInfo();
//					}
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}.start();
		new Thread(){
			@Override
			public void run()
			{
				TCPServer server = new TCPServer();
				server.start(8889);	
			}
		}.start();
		new Thread(){
			@Override
			public void run()
			{
				UDPServer server = new UDPServer();
				server.start(8890);			
			}
		}.start();
		
	}

}
