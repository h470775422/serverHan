package com.my.serializable;

import java.io.Serializable;

public class Student implements Serializable{
	private static final long serialVersionUID = -1L;
	public int type;
	public String name;//����
	public String studentClass;//�༶
	public String idNumber;//ѧ��
	public String macAddr;//mac��ַ
	public String picName;
	public int picLength;
	public byte[] headPic;
}
