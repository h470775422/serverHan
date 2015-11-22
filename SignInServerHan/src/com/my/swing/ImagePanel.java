package com.my.swing;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.my.studentmanager.StudentManager;

public class ImagePanel extends JPanel{

	private String name;
	public ImagePanel(String path){
		super();
		this.name = path;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon img = new ImageIcon(StudentManager.serverPicPath + name);
		g.drawImage(img.getImage(), 0, 5,this.getWidth(),this.getHeight()-5, null);
		}
}
