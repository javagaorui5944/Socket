package com.gaorui.socket;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServiceImpl implements Runnable {
	UDPUtils t1 = new UDPUtils();
	int num;
	// private DatagramPacket packet;
	public ServiceImpl(int num) {
		this.num = num;
	}

	public synchronized void run() {
		int serialnumber = -1;
		try {

			while (true) {
				System.out.println("num"+num);
				/*
				 * byte[] sendbuf111 = new byte[280]; sendbuf111 =
				 * RWCardEncode.updateReceiveIp(); DatagramPacket packet11 = new
				 * DatagramPacket(sendbuf111, sendbuf111.length);
				 * t1.response(packet11);
				 */
				ReadPropertiesTest r = new ReadPropertiesTest();// 创建读取配置文件数据的对象
				System.out.println(r.getInterId() + "run interId");

				byte[] buffer = new byte[1024 * 64]; // 缓冲区
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				t1.receive(packet);

				byte[] sendbuf = new byte[280];

				byte[] bt = new byte[packet.getLength()];
				bt = packet.getData();

				// 去除重复数据
				byte[] serialbytes = { bt[7], bt[8] };
				ByteBuffer serialbuffer = ByteBuffer.wrap(serialbytes);
				serialbuffer.order(ByteOrder.LITTLE_ENDIAN);
				int currentserialnumber = (bt[7] + bt[8] * 256);// serialbuffer.getInt();
				if (currentserialnumber == serialnumber) {
					continue;
				}
				serialnumber = currentserialnumber;
				System.out.println(t1.getCardId(bt) + "getCardId()");
				int[] a = t1.getInt(bt);
			
				for (int i = 0; i < a.length; i++) {
					System.out.println(a[i] + "a[i]");
				}

				// 第一次获取相对应byte类型数组返回给读卡器
				sendbuf = RWCardEncode.receiveConfirmationInfo(bt);

				DatagramPacket outputPacket = new DatagramPacket(sendbuf,
						sendbuf.length, packet.getAddress(), packet.getPort());

				// 发送9位字节给打卡器,让打卡器只发送一次数据报。
				// 同步发送数据回应读卡器,保证线程安全。

				t1.response(outputPacket);

				// Thread.sleep(300);
				/**
				 * 发送数据给服务器请求返回结果。
				 */
				String cardId = t1.getCardId(bt);
				// String CardId10 = null;
				for (int i = cardId.length(); i < 10; i++) {
					cardId = "0" + cardId;
				}
				System.out.println("CardId10" + cardId);
				String result = t1.sendPost(r.getIp(),
						"interId=" + r.getInterId() + "&cardId=" + cardId);

				System.out.println("result: " + result);
				if(result != null) {
					String[] res = result.split(";");
					
					if (res[0].equals("1")) {
						byte[] sendbuf1 = new byte[280];
						
						String userName = res[1];
						String userName1 = new
					 String(userName.getBytes("iso-8859-1"),"gb2312");
						System.out.println("userName1" + userName);
						for (int j = 0; j < res.length; j++) {
							System.out.println(res[j] + "res[j]");
						}
						
						
						// 第二次获取相对应byte类型数组返回给读卡器
						sendbuf1 = RWCardEncode.sendbird(bt, userName1);
						DatagramPacket outputPacket1 = new DatagramPacket(sendbuf1,
								sendbuf1.length, packet.getAddress(),
								packet.getPort());
	
						// 发给读卡器,命令行,机号,蜂鸣类型,文字显示时间,文字内容(编码为gb2312)
						t1.response(outputPacket1);
						Thread.sleep(300);
					}
				}
				System.out.println("resultxxxx: " + result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}