package com.my.studentmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

import com.my.serializable.Student;
import com.my.swing.ImagePanel;

public class StudentState {
	public final static int offline = 0;
	public final static int SecondWarn = 1;
	public final static int firstWarn = 2;
	public final static int online = 3;
	
	
	JToolTip jtt;
	private Student student = null;
	private int onlineState = 0;
	private Date lastSignTime = null;
	private boolean isSign = false;
	
	//控件
	private ImagePanel headPanel = null; 
	private JLabel nameLabel = null;
	private JLabel stateLabel = null;
	//控件属性
	private Color color = null;
	private String stateStr = null;
	
	private JFrame frame = null;
	
	public boolean isSign() {
		return isSign;
	}
	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}
	public Date getLastSignTime() {
		return lastSignTime;
	}
	public void setLastSignTime(Date lastSignTime) {
		this.lastSignTime = lastSignTime;
	}

	
	
	public ImagePanel getHeadPanel() {
		return headPanel;
	}
	public void setHeadPanel(ImagePanel headPanel) {
		this.headPanel = headPanel;
	}
	public JLabel getNameLabel() {
		return nameLabel;
	}
	public void setNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}
	public JLabel getStateLabel() {
		return stateLabel;
	}
	public void setStateLabel(JLabel stateLabel) {
		this.stateLabel = stateLabel;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getOnlineState() {
		return onlineState;
	}
	//设置学生在线状态，同时设置每个学生对应的头像的颜色和状态文字
	public void setOnlineState(int onlineState) {
		this.onlineState = onlineState;
		if(onlineState == this.offline){
			isSign = false;
			System.out.println("离线 sign设为false");
		}
		color = getStateColor(onlineState);
		stateStr = getStateStr(onlineState);
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	
	
	public static String getStateStr(int state){
		String stateStr;
		if(state == StudentState.online){
			stateStr = "在线";
		}else if(state == StudentState.firstWarn){
			stateStr = "离开";
		}else if(state == StudentState.SecondWarn){
			stateStr = "隐身";
		}else{
			stateStr = "离线";
		}
		return stateStr;
	}
	public static Color getStateColor(int state){
		Color color;
		if(state == StudentState.online){
			color = Color.green;
		}else if(state == StudentState.firstWarn){
			color = Color.orange;
		}else if(state == StudentState.SecondWarn){
			color = Color.red;
		}else{
			color = Color.gray;
		}
		return color;
	}
	
	public void initialPanel(){
		JPanel panel = new ImagePanel(student.picName);
		headPanel = (ImagePanel)panel;
		
		
		JLabel lblNewLabel = new JLabel(student.name);
		nameLabel = lblNewLabel;
		
		JLabel lblNewLabel2 = new JLabel("");
		stateLabel = lblNewLabel2;
	}
	
	public void reduceOnlineLevel(){
		onlineState--;
		if(onlineState < 0)
			onlineState = 0;
		setOnlineState(onlineState);
	}
//	@Override
//	public void mouseDragged(MouseEvent e) {
//	}
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		StudentManager.selectStudent = this;
//		headPanel.setToolTipText("dddd");
//	}


}
