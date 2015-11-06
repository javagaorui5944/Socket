package com.gaorui.socket;



public class RWCardEncode {

	/**
	 * 接收成功要发送确认信息
	 * @param MyUdpData
	 * @return
	 */
	public static byte[] receiveConfirmationInfo(byte[] MyUdpData){
		// 接收成功要发送确认信息
		// 发送确认信息
		byte[] sendbuf = new byte[280];
		sendbuf[0] = 0x69;// 表示修改读卡器参数/$69

		// IP地址
		sendbuf[1] = MyUdpData[1];
		sendbuf[2] = MyUdpData[2];
		sendbuf[3] = MyUdpData[3];
		sendbuf[4] = MyUdpData[4];

		// 机号
		sendbuf[5] = MyUdpData[5];
		sendbuf[6] = MyUdpData[6];

		// 数据包序号
		sendbuf[7] = MyUdpData[7];
		sendbuf[8] = MyUdpData[8];
		
		return sendbuf;
	}
	/**
	 * 返回数据让读卡器蜂鸣
	 * @param MyUdpsendData
	 * @return
	 */
	public static byte[] sendbird(byte[] MyUdpsendData,String strls1){
		// 接收成功要发送确认信息
		// 发送确认信息
		byte[] sendbuf = new byte[280];
		//sendbuf[0] = (byte) 0x96;  // 表示修改读卡器参数/$69
		sendbuf[0]= 0x5a;
		

		// 机号
		sendbuf[1] = MyUdpsendData[5];
		sendbuf[2] = MyUdpsendData[6];
		sendbuf[3] = (byte)0;
		sendbuf[4] = 3;//显示保留时间，单位为秒，为255时表示永久显示

          //显示文字的ASCII码
          //Edit8
          String strls = strls1;
          byte[] strlsansi = null ;
          
        	  try {
				strlsansi =  strls.getBytes("gb2312");
				for(int i = 0;i <strlsansi.length;i++)
	              {
	                  sendbuf[5 + i] =(byte)strlsansi[i];
	               //   System.out.println(i+"xxxx"+sendbuf[5+i]);
	              }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  
		 //转换为Ansi
          
		// 数据包序号
		/*sendbuf[7] = MyUdpData[7];
		sendbuf[8] = MyUdpData[8];*/
		
		return sendbuf;
	}
	
	/**
	 * 修改读卡器
	 * @param ipAttr
	 * @return
	 */
	public static byte[] updateReceiveIp(){ 
		byte[] sendbuf = new byte[280];
		sendbuf[0] = (byte) 0xf0;// 表示修改读卡器参数
		String strls = "";
		// IP地址
		
		sendbuf[1] = (byte)192;
		sendbuf[2] = (byte)168;
		sendbuf[3] = 2;
		sendbuf[4] = (byte)217;

		// 子网掩码
		sendbuf[5] = (byte)255;
		sendbuf[6] = (byte)255;
		sendbuf[7] = (byte)255;
		sendbuf[8] = 0;
		
		// 对方地址，接收方IP地址
		sendbuf[9] = (byte)192;
		sendbuf[10] = (byte)168;
		sendbuf[11] = 2;
		sendbuf[12] = 1;

		// 机号
		strls = "0-0-237-0-193-118";
		
		sendbuf[13] = (byte) (Integer.valueOf(strls) % 256);
		sendbuf[14] = (byte) ((Integer.valueOf(strls) / 256) % 256);
		

		// 机器序列号
	
		String[] serialArray = strls.split("-");
		sendbuf[15] = Integer.valueOf(serialArray[0]).byteValue();
		sendbuf[16] = Integer.valueOf(serialArray[1]).byteValue();
		sendbuf[17] = Integer.valueOf(serialArray[2]).byteValue();
		sendbuf[18] = Integer.valueOf(serialArray[3]).byteValue();
		
		
	
		sendbuf[19] = 0;
		

		return sendbuf;
	}

}
