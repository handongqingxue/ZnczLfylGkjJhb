package com.znczLfylGkjJhb.task;

import java.util.ArrayList;
import java.util.List;

import com.znczLfylGkjJhb.entity.*;
import com.znczLfylGkjJhb.jdq.*;
import com.znczLfylGkjJhb.util.ByteUtil;
import com.znczLfylGkjJhb.yz.MachineTool;

import gnu.io.SerialPort;

public class DiBangTask3190 {

    public static int getWeight() {
		SerialPort serialPortTest = null;
		int weight=0;
        try {
			byte[] bytes = null;
			int preWeight=0;
			int steadyCount=0;//稳定次数
			int waitTime=0;
			int i = 1;
			//循环遍历串口
			List<String> serialPorts = MachineTool.uartPortUseAblefind();
			System.out.println("size==="+serialPorts.size());
			for (String name : serialPorts) {
				System.out.println("name==="+name);
				serialPortTest = MachineTool.portParameterOpen(name, 9600);
				
				while (true) {
					Thread.sleep(600);
			        //接收数据
			        bytes = MachineTool.uartReceiveDatafromSingleChipMachine(serialPortTest);
			        System.out.println("b==="+bytes);
			        if (bytes != null && bytes.length > 0) {
			            String str=ByteUtil.byte2hex(bytes);
			            //在此处可以对数据进行判断处理，识别操作
						//String str="022B30303030303030314403";
						//String str="022B30303030313030314403";
						//str=str.substring(str.indexOf("022b"), 32);
			            System.out.println((i++) + ". 从串口" + name + "接收的数据：" + str);
			            System.out.println("str==="+str);
			            if(!str.startsWith("022b"))
			            	continue;
						
						String str5 = str.substring(4, 6);
						String str6 = str.substring(6, 8);
						String str7 = str.substring(8, 10);
						String str8 = str.substring(10, 12);
						String str9 = str.substring(12, 14);
						String str10 = str.substring(14, 16);
						
						System.out.println("str5==="+str5);
						System.out.println("str6==="+str6);
						System.out.println("str7==="+str7);
						System.out.println("str8==="+str8);
						System.out.println("str9==="+str9);
						System.out.println("str10==="+str10);
						
						List<String> list=new ArrayList<String>();
						list.add(ByteUtil.hex2Zf(str5));
						list.add(ByteUtil.hex2Zf(str6));
						list.add(ByteUtil.hex2Zf(str7));
						list.add(ByteUtil.hex2Zf(str8));
						list.add(ByteUtil.hex2Zf(str9));
						list.add(ByteUtil.hex2Zf(str10));
						weight=ByteUtil.connectZf2Weight(list);
						System.out.println("重量==="+weight);
						//if(weight>0) {
							if(steadyCount>5) {
								break;
							}
							if(waitTime>10) {//光栅被遮挡
								//weight=-1;
								weight=2;
								break;
							}
							
							boolean kgl1Open=false;
							boolean kgl2Open=false;
							
							JiDianQi jdq = JdqZlUtil.getJdq();
							jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
							kgl1Open=jdq.isKgl1Open();
							kgl2Open=jdq.isKgl2Open();
							System.out.println("称重中open1==="+kgl1Open);
							System.out.println("称重中open2==="+kgl2Open);
							
							/*
							 * 光栅被遮挡情况先忽略掉
							if(kgl1Open||kgl2Open) {
								System.out.println("光栅被遮挡");
								steadyCount=0;
								waitTime++;
							}
							else {
							*/
								waitTime=0;
								if(weight<=preWeight+preWeight*0.3||weight>=preWeight-preWeight*0.3)
									steadyCount++;
								else
									steadyCount=0;
								preWeight=weight;
							//}
						//}
					} 
			        else {
			        	System.out.println("串口号：" + name + "接收到的数据为空！");
						if(waitTime>10) {
							//weight=-1;
							weight=1;
							break;
						}
						steadyCount=0;
						waitTime++;
			        }
				}
			    
			    //serialPortTest.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			weight=1;//称重过程中出现异常就默认重量为1
		}
        finally {
        	System.out.println("serialPortTest.close()");
        	if(serialPortTest!=null)
        		serialPortTest.close();
			return weight;
		}
    }

    /**
     * 这个方法在车辆上磅称重时，若光栅前方被挡时调用，判断地磅上有无车辆。若不判断，就算称重失败还原到上磅状态，也会形成死循环
     * @return
     * @throws InterruptedException
     */
    public static int getTestWeight() {
		SerialPort serialPortTest = null;
		int weight=0;
        try {
			byte[] bytes = null;
			int i = 1;
			//循环遍历串口
			List<String> serialPorts = MachineTool.uartPortUseAblefind();
			System.out.println("size==="+serialPorts.size());
			for (String name : serialPorts) {
				System.out.println("name==="+name);
				serialPortTest = MachineTool.portParameterOpen(name, 9600);
				
			    //接收数据
			    bytes = MachineTool.uartReceiveDatafromSingleChipMachine(serialPortTest);
			    System.out.println("b==="+bytes);
			    if (bytes != null && bytes.length > 0) {
			        String str=ByteUtil.byte2hex(bytes);
			        //在此处可以对数据进行判断处理，识别操作
					//String str="022B30303030303030314403";
					//String str="022B30303030313030314403";
					//str=str.substring(str.indexOf("022b"), 32);
			        System.out.println((i++) + ". 从串口" + name + "接收的数据：" + str);
			        System.out.println("str==="+str);
			        if(!str.startsWith("022b"))
			        	continue;
					
					String str5 = str.substring(4, 6);
					String str6 = str.substring(6, 8);
					String str7 = str.substring(8, 10);
					String str8 = str.substring(10, 12);
					String str9 = str.substring(12, 14);
					String str10 = str.substring(14, 16);
					
					System.out.println("str5==="+str5);
					System.out.println("str6==="+str6);
					System.out.println("str7==="+str7);
					System.out.println("str8==="+str8);
					System.out.println("str9==="+str9);
					System.out.println("str10==="+str10);
					
					List<String> list=new ArrayList<String>();
					list.add(ByteUtil.hex2Zf(str5));
					list.add(ByteUtil.hex2Zf(str6));
					list.add(ByteUtil.hex2Zf(str7));
					list.add(ByteUtil.hex2Zf(str8));
					list.add(ByteUtil.hex2Zf(str9));
					list.add(ByteUtil.hex2Zf(str10));
					weight=ByteUtil.connectZf2Weight(list);
					System.out.println("重量==="+weight);
						
					
					JiDianQi jdq = JdqZlUtil.getJdq();
					jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				} 
			    else {
			    	System.out.println("串口号：" + name + "接收到的数据为空！");
					weight=1;
			    }
			    
			    //serialPortTest.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			weight=1;
		}
        finally {
        	System.out.println("serialPortTest.close()");
        	if(serialPortTest!=null)
        		serialPortTest.close();
			return weight;
		}
    }
    
    public static void main(String[] args) {
		try {
			getWeight();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
