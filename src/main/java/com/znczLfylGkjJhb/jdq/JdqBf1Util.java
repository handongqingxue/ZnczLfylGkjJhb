package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

public class JdqBf1Util {

	/**
	 * ̧��һ���ϰ���բ
	 */
	public static void openYiJianShangBangDz() {
    	System.out.println("̧��һ���ϰ���բ");
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//����ʱ�����ִ�и�λ����
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ̧��һ���°���բ
	 */
	public static void openYiJianXiaBangDz() {
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI2);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI2);//����ʱ�����ִ�и�λ����
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ̧������ϰ���բ
	 */
	public static void openErJianShangBangDz() {
    	System.out.println("̧������ϰ���բ");
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//����ʱ�����ִ�и�λ����
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ̧������°���բ
	 */
	public static void openErJianXiaBangDz() {
    	System.out.println("̧������°���բ");
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI2);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI2);//����ʱ�����ִ�и�λ����
			//Thread.sleep(1000);
			//yjjdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
