package com.znczLfylGkjJhb.task;

import com.znczLfylGkjJhb.util.HexadecimalUtil;
import com.znczLfylGkjJhb.util.LoadProperties;
import com.znczLfylGkjJhb.util.RXTXUtil;
import com.znczLfylGkjJhb.yz.YzZlUtil;

public class YinZhuTask {
	
	/**
	 * 发送指令给音柱
	 * @param zhiLing
	 * @param sleepTime
	 * @return
	 */
	public static String sendMsg(String zhiLing, long sleepTime) {
		try {
			String serialPortName = null;
			serialPortName=LoadProperties.getYinZhuCom();
			//String serialPortName = "COM6";
			// 开启串口
			//RXTXUtil.openSerialPort(LoadProperties.getSerialPortName(), 100);
			
			System.out.println("serialPortName=="+serialPortName);
			RXTXUtil.openSerialPort(serialPortName, 9600);

			String executeOrder = RXTXUtil.executeOrder(zhiLing, serialPortName, 9600);

			Thread.sleep(sleepTime);
			RXTXUtil.closeSerialPort();
			return executeOrder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zhiLing;
	}
	
	public static void main(String[] args) {
		YinZhuTask.sendMsg(YzZlUtil.get83().replaceAll(" ", ""), 1500);
		YinZhuTask.sendMsg(YzZlUtil.getByStr("鲁").replaceAll(" ", ""), 800);
		YinZhuTask.sendMsg(YzZlUtil.getByStr("9").replaceAll(" ", ""), 800);
		YinZhuTask.sendMsg(YzZlUtil.getByStr("8").replaceAll(" ", ""), 800);
		YinZhuTask.sendMsg(YzZlUtil.getByStr("8").replaceAll(" ", ""), 800);
		YinZhuTask.sendMsg(YzZlUtil.getByStr("8").replaceAll(" ", ""), 800);
		YinZhuTask.sendMsg(YzZlUtil.get81().replaceAll(" ", ""), 1500);
	}
}
