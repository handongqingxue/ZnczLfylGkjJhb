package com.znczLfylGkjJhb.jdq;

/**
 * 继电器指令工具类
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
	 * 开启继电器
	 */
	public static void openJdq() {
    	System.out.println("开启继电器");
		JdqZlUtil.jdq.open();
	}

	/**
	 * 关闭继电器
	 */
	public static void closeJdq() {
    	System.out.println("关闭继电器");
		JdqZlUtil.jdq.close();
	}

	public static void main(String[] args) {
		//JiDianQi jdq=new JiDianQi();
		//JdqZlUtil.setJdq(jdq);
		//JdqZlUtil.openJdq();
	}
}
