package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

public class JdqBf2Util {
	
	/**
	 * ̧���ϰ���բ
	 */
	public static void openShangBangDz() {
		try {
        	System.out.println("̧���ϰ���բ");
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//����ʱ�����ִ�и�λ����
			//Thread.sleep(1000);
			//jdq.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ̧���°���բ
	 */
	public static void openXiaBangDz() {
		try {
        	System.out.println("̧���°���բ");
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
}
