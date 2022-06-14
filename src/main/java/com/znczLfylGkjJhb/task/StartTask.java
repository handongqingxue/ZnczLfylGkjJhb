package com.znczLfylGkjJhb.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.znczLfylGkjJhb.jdq.*;
import com.znczLfylGkjJhb.socket.*;

public class StartTask {
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		// 车牌识别的线程任务
		CpsbsxtTask cpsbsxtTask = new CpsbsxtTask();
		cpsbsxtTask.start();
		
		YiJianJdq yjjdq=new YiJianJdq();
		JdqZlUtil.setYjjdq(yjjdq);
		
		ErJianJdq ejjdq=new ErJianJdq();
		JdqZlUtil.setEjjdq(ejjdq);

		//testLiuCheng();
		
		ClientSocket cs = new ClientSocket();
		cs.connectServer();
		
		// 其他线程启动
		while (true) {
			// main程序一直运行
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!cs.isServerOpend()) {
				System.out.println("与服务器端通讯断开了，正在重新建立连接,时间:"+sdf.format(new Date()));
				cs.connectServer();
			}
		}
	}
	
	public static void testLiuCheng() {
		try {
			///////////一检上磅开始
			//Car car1=new Car();
			//car1.setsLicense(" 鲁A9031");
			//APIUtil.updateYJCPSBDDXX(car1);
			
			//APIUtil.checkYJSBHWGSState();

			//Thread.sleep(10000);
			//APIUtil.checkYJSBHXBHWGSState();
			
			//APIUtil.yiJianChengZhongZhong();
			
			//APIUtil.checkYJXBHWGSState();
			
			//Thread.sleep(10000);
			//APIUtil.checkIfYJXBYwc();
			///////////一检上磅结束

			///////////二检上磅开始
			//Car car2=new Car();
			//car2.setsLicense(" 鲁A9031");
			//APIUtil.updateEJCPSBDDXX(car2);
			
			//APIUtil.checkEJSBHWGSState();
			
			//Thread.sleep(10000);
			//APIUtil.checkEJSBHXBHWGSState();
			
			//APIUtil.erJianChengZhongZhong();
			
			//APIUtil.checkEJXBHWGSState();

			//Thread.sleep(10000);
			//APIUtil.checkIfEJXBYwc();
			///////////二检上磅结束
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
