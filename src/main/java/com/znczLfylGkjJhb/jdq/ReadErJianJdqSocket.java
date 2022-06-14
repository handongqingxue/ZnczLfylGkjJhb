package com.znczLfylGkjJhb.jdq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import com.znczLfylGkjJhb.util.StringUtil;

public class ReadErJianJdqSocket implements Runnable {

	private Socket client;
	ErJianJdq erJianJdq;
	private int BUFFER_SIZE = 50;
	
	public ReadErJianJdqSocket(Socket socket, ErJianJdq erJianJdq) {
		// TODO Auto-generated constructor stub
		client = socket;
		this.erJianJdq=erJianJdq;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//声明一个输入流监听客户端数据
		try {
			InputStream in = client.getInputStream();
			OutputStream out =client.getOutputStream();
			byte[] recData = null;
			int munber =0;
			while(true) {
				if(client.isClosed()) {
					break;
				}
				else {
					try {
						recData = new byte[1024];
						//从输入流中读取客户端数据
						int size = in.read(recData);
						System.out.println("size="+size);
						if(size>-1) {
							byte[] new_data=new byte[size]	;
							//截取有用数据放到新数组
							new_data=Arrays.copyOf(recData,size);					
							String data = StringUtil.bytesToHex(new_data);	
							System.out.println("data==="+data);
							if(data.substring(0,4).equals("EEFF")) {
								/***************截取状态十六进制字符串*****************/
						    	String state=data.substring(8,12);
						    	/***************将状态十六进制字符串转换为十六进制*****************/
						    	BigInteger h = new BigInteger(state, 16);// 16进制转10进制
						    	/***************将状态十进制数转换为二进制字符串*****************/
						    	String tb = h.toString(2);    // 10进制转2进制
						    	int length=16-tb.length();
						    	/***************将二进制状态高位补0*****************/
						    	for(int i=0;i<length;i++){
						    		tb="0"+tb;
						    	}
						    	tb=new StringBuffer(tb).reverse().toString();
						    	
						    	int kglNum1=1;
						    	char state1 = tb.charAt(kglNum1-1);
					            if(state1=='1'){
					                System.out.println("开关量1:已导通"); 
					                erJianJdq.setKgl1Open(true);
					            }
					            else { 
					            	System.out.println("开关量1:已断开"); 
					            	erJianJdq.setKgl1Open(false);
					            }
					            
						    	int kglNum2=2;
						    	char state2 = tb.charAt(kglNum2-1);
					            if(state2=='1'){
					                System.out.println("开关量2:已导通");
					                erJianJdq.setKgl2Open(true);
					            }
					            else { 
					                System.out.println("开关量2:已断开");
					                erJianJdq.setKgl2Open(false);
					            }
							}
						}
						else {
							System.out.println("数据读取完毕！");
							client.close();
							break;
						}
					} catch (SocketException err) {
						// TODO Auto-generated catch block
						//err.printStackTrace();
						System.out.println("已断开连接");
						client.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
