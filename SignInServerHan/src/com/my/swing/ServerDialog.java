package com.my.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager;

import com.han.signin.Main;
import com.my.studentmanager.OnlineManager;
import com.my.studentmanager.StudentManager;
import com.my.studentmanager.StudentState;

public class ServerDialog {

	private JFrame frame;
	private Main main = new Main();
	private int width = 800,height = 600;
	private int pWidth = 100,pHeight = 105;
	private int lWidth = 50,lHeight = 15;
	private int space = 20;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String windows="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(windows);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerDialog window = new ServerDialog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void mainThread(){
		new Thread(new OnlineManager()).start();//学生在线验证线程
		new Thread(){//界面更新线程
			public void run(){
				while(true){
					if(StudentManager.getUpdate()){
						UpdateStudentPanel();
						repaintHead();
						StudentManager.setUpdate(false);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}		
			}
		}.start();
	}
	/**
	 * Create the application.
	 */
	public ServerDialog() {
		main.main();
		initialize();
		mainThread();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StudentManager.setFrame(frame);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u83DC\u5355");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u767B\u9646");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u4FDD\u5B58");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u9000\u51FA");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("\u5173\u4E8E");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		frame.getContentPane().setLayout(null);
		
	}
	
	public void UpdateStudentPanel(){
		List<StudentState> students = StudentManager.getStuStates();
		int x = 10,y = 10;
		int xOffset = pWidth + space;
		int col = width / xOffset - 1;
		int i = 0;
		for(StudentState ss:students){
			updateHead(ss,x,y);
			x += xOffset;
			i++;
			if(i > col){
				y += pHeight + lHeight*2+10;
				x = 10;
				i = 0;
			}
		}
	}
	
	private void updateHead(StudentState ss,int x,int y){
		ss.getHeadPanel().setBounds(x, y, pWidth, pHeight);	
		ss.getHeadPanel().setBackground(ss.getColor());
		
		ss.getNameLabel().setBounds(x, y+pHeight, lWidth, lHeight);
		
		ss.getStateLabel().setText(ss.getStateStr());
		ss.getStateLabel().setBounds(x, y+pHeight+lHeight, lWidth, lHeight);
	}
	
	private void repaintHead(){
		List<StudentState> students = StudentManager.getStuStates();
		for(StudentState ss:students){
			frame.getContentPane().add(ss.getHeadPanel());
			ss.getHeadPanel().repaint();
			frame.getContentPane().add(ss.getNameLabel());
			ss.getNameLabel().repaint();
			frame.getContentPane().add(ss.getStateLabel());
			ss.getStateLabel().repaint();
		}
		
	}
	
	
}
