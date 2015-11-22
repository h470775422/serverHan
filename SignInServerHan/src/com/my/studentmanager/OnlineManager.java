package com.my.studentmanager;

import java.util.List;

public class OnlineManager implements Runnable{
	@Override
	public void run() {
		while(true){
			StudentManager.checkOnlineState();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
