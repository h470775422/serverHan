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
			//System.out.println("tcp������������...");
			while(true)
			{
				//System.out.println("tcp�ȴ�������...");
				socket = server.accept();
				//System.out.println("tcp�пͻ�������");
				new Thread(new TCPClient(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
