package com.znczLfylGkjJhb.jdq;

/**
 * �̵���ָ�����
 * */
public class JdqZlUtil {
	
	private static JiDianQi jdq;
	

	public static JiDianQi getJdq() {
		return jdq;
	}

	public static void setJdq(JiDianQi jdq) {
		JdqZlUtil.jdq = jdq;
	}

	/**
	 * �����̵���
	 */
	public static void openJdq() {
    	System.out.println("�����̵���");
		JdqZlUtil.jdq.open();
	}

	/**
	 * �رռ̵���
	 */
	public static void closeJdq() {
    	System.out.println("�رռ̵���");
		JdqZlUtil.jdq.close();
	}

	public static void main(String[] args) {
		//JiDianQi jdq=new JiDianQi();
		//JdqZlUtil.setJdq(jdq);
		//JdqZlUtil.openJdq();
	}
}
