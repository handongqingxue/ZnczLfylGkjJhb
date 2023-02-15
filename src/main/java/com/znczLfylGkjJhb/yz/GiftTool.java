package com.znczLfylGkjJhb.yz;

import gnu.io.SerialPort;

import java.util.List;
import java.util.logging.Logger;

import com.znczLfylGkjJhb.task.YinZhuTask;

public class GiftTool {

	private final static Logger LOGGER = Logger.getLogger("GiftTool");
	

    public static String run(byte[] msg) throws InterruptedException {
        SerialPort serialPortTest = null;
        byte[] bytes = null;
        String dataReceive = null;
        int i = 1;
        //循环遍历串口
        List<String> serialPorts = MachineTool.uartPortUseAblefind();
        System.out.println("size==="+serialPorts.size());
        for (String name : serialPorts) {
        	System.out.println("name==="+name);
			serialPortTest = MachineTool.portParameterOpen(name, 9600);
            //发送数据
            //System.out.println("msg==="+(msg==null));
            MachineTool.uartSendDatatoSerialPort(serialPortTest, msg);
            // 休眠300ms，等待单片机反应
            Thread.sleep(500);
            //接收数据
            bytes = MachineTool.uartReceiveDatafromSingleChipMachine(serialPortTest);
            if (bytes != null && bytes.length > 0) {
                dataReceive = ByteUtil.byte2hex(bytes);
                //在此处可以对数据进行判断处理，识别操作
                LOGGER.info((i++) + ". 从串口" + name + "接收的数据：" + dataReceive);
            } else {
                LOGGER.warning("串口号：" + name + "接收到的数据为空！");
            }
            serialPortTest.close();
        }
        return ByteUtil.byte2hex(bytes);
    }

	public static void main(String[] args) {
		try {
			//GiftTool.run(ByteUtil.hex2byte(YzZlUtil.getByDuanHao(86).replaceAll(" ", "")));
			//GiftTool.run(ByteUtil.hex2byte(ZhiLingUtil.getByDuanHao(81).replaceAll(" ", "")));

    		YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500);
    		//YinZhuTask.sendMsg(YzZlUtil.getByDuanHao(1).replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
			/*
			while (true) {
				GiftTool.run(ByteUtil.hex2byte(WriteZhiLingConst.XIA_YI_QU));
				Thread.sleep(2000);
			}
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
