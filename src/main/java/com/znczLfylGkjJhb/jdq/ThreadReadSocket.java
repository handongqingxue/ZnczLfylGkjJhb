package com.znczLfylGkjJhb.jdq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import com.znczLfylGkjJhb.util.StringUtil;

public class ThreadReadSocket implements Runnable {
	
	private Socket client;
	private int BUFFER_SIZE = 50;
	
	public ThreadReadSocket(Socket i) {
		// TODO Auto-generated constructor stub
		client = i;
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
