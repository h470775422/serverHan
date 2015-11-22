package com.my.udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.han.signin.Main;
import com.my.studentmanager.OnlineVerify;

public class UDPClient implements Runnable{

	private DatagramSocket dsServer;
	private DatagramPacket dsPacket;
	private String macAddr;
	private InetAddress backInetAddr;
	private int backPort;
	UDPClient(String macAddr,InetAddress iAddr,int port,DatagramSocket dsServer)
	{
		this.macAddr = macAddr;
		this.backInetAddr = iAddr;
		this.backPort = port;
		this.dsServer = dsServer;
	}
	@Override
	public void run() {
		updateState();
		sendBack();
	}
	
	public void updateState()
	{
		System.out.println("upd MACµØÖ·£º"+macAddr+"µÚ"+Main.count++ +"´Î");
		OnlineVerify ov = new OnlineVerify();
		ov.verifyMacAddr(macAddr);
	}
	
	public void sendBack()
	{
		String str = "successed";
		dsPacket = new DatagramPacket(str.getBytes(),0,str.length(),backInetAddr,backPort);
		try {
			dsServer.send(dsPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
