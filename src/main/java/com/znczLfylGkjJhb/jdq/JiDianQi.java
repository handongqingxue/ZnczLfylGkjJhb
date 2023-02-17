package com.znczLfylGkjJhb.jdq;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.znczLfylGkjJhb.util.LoadProperties;
import com.znczLfylGkjJhb.util.StringUtil;

public class JiDianQi {

    private Socket client;
    private Thread t_read; 
    private boolean kgl1Open;
    private boolean kgl2Open;
    
	public boolean isKgl2Open() {
		return kgl2Open;
	}

	public void setKgl2Open(boolean kgl2Open) {
		this.kgl2Open = kgl2Open;
	}

	public boolean isKgl1Open() {
		return kgl1Open;
	}

	public void setKgl1Open(boolean kgl1Open) {
		this.kgl1Open = kgl1Open;
	}

	public void open() {
		try {
			System.out.println("client==="+client);
			if(client!=null) {
				System.out.println("client.isClosed==="+client.isClosed());
				if(!client.isClosed())//在继电器未关闭情况下需要先关闭再连接
					client.close();
			}
			String jdqIp = LoadProperties.getJdqIp();
			int jdqPort = LoadProperties.getJdqPort();
			client=new Socket(jdqIp,jdqPort);
			t_read= new Thread(new ReadJdqSocket(client,JiDianQi.this));
			t_read.start();
			System.out.println("连接继电器");
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
			System.out.println("断开继电器连接");
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
}
