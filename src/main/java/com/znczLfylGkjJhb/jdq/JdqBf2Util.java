package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

public class JdqBf2Util {
	
	/**
	 * 抬起一检上磅道闸
	 */
	public static void openYiJianShangBangDz() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			yjjdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int yiJianJdqMaiChong = LoadProperties.getYiJianJdqMaiChong();
			Thread.sleep(yiJianJdqMaiChong);
			yjjdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 抬起一检下磅道闸
	 */
	public static void openYiJianXiaBangDz() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			yjjdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int yiJianJdqMaiChong = LoadProperties.getYiJianJdqMaiChong();
			Thread.sleep(yiJianJdqMaiChong);
			yjjdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 抬起二检上磅道闸
	 */
	public static void openErJianShangBangDz() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			ejjdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int erJianJdqMaiChong = LoadProperties.getErJianJdqMaiChong();
			Thread.sleep(erJianJdqMaiChong);
			ejjdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 抬起二检下磅道闸
	 */
	public static void openErJianXiaBangDz() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			ejjdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int erJianJdqMaiChong = LoadProperties.getErJianJdqMaiChong();
			Thread.sleep(erJianJdqMaiChong);
			ejjdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//脉冲时间过后执行复位操作
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
