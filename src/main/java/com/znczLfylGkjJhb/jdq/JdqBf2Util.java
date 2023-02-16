package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

public class JdqBf2Util {
	
	/**
	 * 抬起上磅道闸
	 */
	public static void openShangBangDz() {
		try {
        	System.out.println("抬起上磅道闸");
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//jdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 抬起下磅道闸
	 */
	public static void openXiaBangDz() {
		try {
        	System.out.println("抬起下磅道闸");
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
