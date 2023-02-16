package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

public class JdqBf1Util {

	/**
	 * ̧���ϰ���բ
	 */
	public static void openShangBangDz() {
    	System.out.println("̧���ϰ���բ");
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI1);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI1);//����ʱ�����ִ�и�λ����
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
			jdq.sendData(WriteZhiLingConst.KAI_JI_DIAN_QI2);
			int jdqMaiChong = LoadProperties.getJdqMaiChong();
			Thread.sleep(jdqMaiChong);
			jdq.sendData(WriteZhiLingConst.GUAN_JI_DIAN_QI2);//����ʱ�����ִ�и�λ����
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
