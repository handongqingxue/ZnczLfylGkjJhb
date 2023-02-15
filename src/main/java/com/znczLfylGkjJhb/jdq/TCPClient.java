package com.znczLfylGkjJhb.jdq;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.znczLfylGkjJhb.util.LoadProperties;
import com.znczLfylGkjJhb.util.StringUtil;

public class TCPClient {
	
    private Socket client;
    private Thread t_read; 

	public void open() {
		try {
			String jdqIp = LoadProperties.getJdqIp();
			int jdqPort = LoadProperties.getJdqPort();
			client=new Socket(jdqIp,jdqPort);
			t_read= new Thread(new ThreadReadSocket(client));
			t_read.start();
			System.out.println("连接");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			client.close();
			System.out.println("断开连接");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendData(String data) {
		try {
			if(client!=null) {
				if(!client.isClosed()) {
					/***************发送数据*****************/
					OutputStream out = client.getOutputStream();
					/***************将十六进制字符串转换为字节数组发送*****************/
					out.write (StringUtil.hexStringToByteArray(data));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//try {
			TCPClient tc=new TCPClient();
			tc.open();
			//tc.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			tc.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI2);
			//Thread.sleep(3000);
			//tc.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);
			
			//tc.close();
			/*
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
