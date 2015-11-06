package com.gaorui.socket;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UDPService extends JFrame {
	JPanel jp, jp1;
	JButton jb1, jb2;
	Label l1;
	JLabel jb, jl1,jl2;
	JTextField jtfname;
	
	TextArea t1;
	private String e1;

	public static void main(String[] args) {
		ReadPropertiesTest r = new ReadPropertiesTest();
		r.getInterId();
		r.getIp();
		UDPService x = new UDPService(null);
	}

	public UDPService(String e1) {

		this.e1 = e1;
		jp = new JPanel();
		jp1 = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		this.setBounds(500, 350, 300, 150);
		GridLayout gl = new GridLayout(2, 1);
		gl.setVgap(5);// 设置垂直间距5个像素
		jp.setLayout(gl);
		if (e1 != null) {

			System.out.println(e1 + "grtest18:57");
			t1 = new TextArea(e1);

			jp.add(t1);
			this.add(jp);
			this.setTitle("error");
			this.setSize(200, 280);
			this.setLocation(550, 450);
			System.out.println(jp + "jp");

		} else {

			jb1 = new JButton("开启");
			jb2 = new JButton("关闭");
			jb1.setPreferredSize(new Dimension(100, 100));
			jb2.setPreferredSize(new Dimension(100, 100));
			jl1 = new JLabel("欢迎您使用打卡器,请输入本次打卡扣除学时数后开启");
		
			jtfname = new JTextField("0", 5);
			jb1.setActionCommand("jb1");
			jb1.addActionListener(new action1());
			jb2.setActionCommand("jb2");
			jb2.addActionListener(new action1());

			jp1.add(jl1);

			jp1.add(jtfname);
			jp.add(jb1);
		
			jp.add(jb2);
			this.add(jp1, "North");
			this.add(jp, "South");
			
			this.setTitle("启动读卡器服务端");
			this.setSize(400, 400);
			this.setLocation(400, 150);
		}

		this.setResizable(false);// 不能改变串口的大小
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	class action1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("jb1")) {
			String num =	jtfname.getText();
			int num1 = 0;
			if(num!=null&&!num.equals("")){
				 num1 = Integer.parseInt(num);
				
			}
			else{
				JOptionPane.showMessageDialog(jp1,"请输入本次打卡后想扣除的学时数");
				System.exit(0);
			}
				jl1.setText("服务器已经启动,请刷卡");
				jtfname.setVisible(false);
				jb1.setVisible(false);
				jb2.setVisible(true);
				UDPUtils t1 = new UDPUtils();
				try {
					t1.init();
					new Thread(new ServiceImpl(num1)).start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			else if (e.getActionCommand().equals("jb2")) {
				System.exit(0);
			}
		}
	}
}
