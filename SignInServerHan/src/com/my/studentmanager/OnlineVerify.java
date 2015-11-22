package com.my.studentmanager;


public class OnlineVerify {

	public boolean verifyMacAddr(String macAddr)
	{
		boolean res = StudentManager.updateState(macAddr);
		StudentManager.setUpdate(true);
		return res;
	}

}
