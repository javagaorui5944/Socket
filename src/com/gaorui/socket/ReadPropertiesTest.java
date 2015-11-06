package com.gaorui.socket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.io.InputStream; 
import java.util.Properties; 
public class ReadPropertiesTest {
	 InputStream in ;
	 int interId1;
	 String ip;
	 
	 /**
	  * 从InterIdAndIp.properties配置文件中读取数据
	  * @return 网点号
	  */
	public int getInterId(){
		try {
			in = new FileInputStream("E://InterIdAndIp.properties");
		} catch (FileNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} 
		 Properties prop = new Properties(); 
		  try {
		   prop.load(in); 
		   String interId = prop.getProperty("interId");
		   interId1 = Integer.parseInt(interId);
		   System.out.println(interId);
		  } catch (IOException e) { 
		   e.printStackTrace(); 
		  } 
		  return interId1;
	}
	 /**
	  * 从InterIdAndIp.properties配置文件中读取数据
	  * @return 服务器ip号
	  */
	public String getIp(){
		try {
			in = new FileInputStream("E://InterIdAndIp.properties");
		} catch (FileNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} 
		 Properties prop = new Properties(); 
		  try {
		   prop.load(in); 
		   ip = prop.getProperty("ip");
		   System.out.println(ip);
		  } catch (IOException e) { 
		   e.printStackTrace(); 
		  } 
		  return ip;
	}
}