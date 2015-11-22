package com.my.tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer 
{
	private ServerSocket server;
	private Socket socket;
	
	public void start(int port)
	{
		try {
			server = new ServerSocket(port);
			//System.out.println("tcp服务器已启动...");
			while(true)
			{
				//System.out.println("tcp等待连接中...");
				socket = server.accept();
				//System.out.println("tcp有客户机接入");
				new Thread(new TCPClient(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
