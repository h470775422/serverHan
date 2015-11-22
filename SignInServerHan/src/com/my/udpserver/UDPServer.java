package com.my.udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPServer {
	private byte[] bytes;
	private DatagramSocket dsServer = null;
	private DatagramPacket dsPacket = null;
	
	public void start(int port)
	{
		try {
			bytes = new byte[128];
			dsServer = new DatagramSocket(port);
			dsPacket = new DatagramPacket(bytes,128);
			//System.out.println("udp������������...");
			while(true)
			{
				//System.out.println("udp�ȴ���Ϣ");
				dsServer.receive(dsPacket);
				//System.out.println("udp�а�����");
				String macAddr = new String(dsPacket.getData(),0,dsPacket.getLength());
				InetAddress backInetAddr = dsPacket.getAddress();
				int backPort = dsPacket.getPort();
				new Thread(new UDPClient(macAddr,backInetAddr,backPort,dsServer)).start();
				bytes = new byte[128];
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
