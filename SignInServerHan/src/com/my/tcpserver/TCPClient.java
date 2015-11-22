package com.my.tcpserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;






import com.my.DAO.DataBaseExecute;
import com.my.key.ResultFlag;
import com.my.key.Type;
import com.my.serializable.Student;
import com.my.studentmanager.OnlineVerify;
import com.my.studentmanager.StudentManager;

public class TCPClient implements Runnable{

	private Student student = null;
	
	private Socket socket = null;
    ObjectOutputStream objectOS = null;
    ObjectInputStream objectIS = null;
    
    
	TCPClient(Socket socket)
	{
		System.out.println("tcp进入线程");
		this.socket = socket;
	}
	
	@Override
	public void run() {
		student = (Student)recvData();
		switchType();
		disConnect();
	}
	
	public void switchType()
	{
		switch(student.type)
		{
		case Type.register:
			registerProcess();
			break;
		case Type.signIn:
			signinProcess();
			break;
		}
	}
	
	public void signinProcess()
	{
		OnlineVerify ov = new OnlineVerify();
		String str;
		System.out.println(student.macAddr);
		if(ov.verifyMacAddr(student.macAddr)){
			str = "successed";
		}else{
			str = "none";
			System.out.println(str);
		}
		sendData(str);
		disConnect();
	}
	public void registerProcess()
	{
		String str;
		int i = registerStudent();
		if(i == ResultFlag.successed){
			str = "successed";
		}
		else if(i == ResultFlag.existStudent){
			str = "existedStudent";
			System.out.println("学生已存在");
		}else if(i == ResultFlag.existMac){
			str = "existedMac";
			System.out.println("mac地址已存在");
		}
		else{
			str = "error";
			System.out.println("发生错误");
		}
		sendData(str);
		disConnect();
	}
	
	public int registerStudent()
	{
		System.out.println("tcp" + student.name + "," + student.idNumber + "," + student.studentClass);
		DataBaseExecute dbe = new DataBaseExecute();
		if(dbe.queryStudentExist(student.idNumber))
			return ResultFlag.existStudent;
		if(dbe.queryStudentMac(student.macAddr))
			return ResultFlag.existMac;
		if(dbe.insertStudent(student) <= 0)
			return ResultFlag.error;
		StudentManager.insertStudent(student);
		return ResultFlag.successed;
		
	}

    //发送object
    public void sendData(Object object)
    {
        if(object == null)
            return;
        try {
            objectOS = new ObjectOutputStream(socket.getOutputStream());
            objectOS.writeObject(object);
            objectOS.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //接收object
    public Object recvData()
    {
        Object object = null;
        try {
            objectIS = new ObjectInputStream(socket.getInputStream());
            object = objectIS.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
    //断开连接
    public void disConnect()
    {
        try {
            if(objectIS != null)objectIS.close();
            if(objectOS != null)objectOS.close();
            if(socket != null)socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
