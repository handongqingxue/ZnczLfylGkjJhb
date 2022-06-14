package com.znczLfylGkjJhb.jdq;

import com.znczLfylGkjJhb.util.LoadProperties;

/**
 * 继电器指令工具类
 * */
public class JdqZlUtil {
	
	private static YiJianJdq yjjdq;
	private static ErJianJdq ejjdq;
	
	public static ErJianJdq getEjjdq() {
		return ejjdq;
	}

	public static void setEjjdq(ErJianJdq ejjdq) {
		JdqZlUtil.ejjdq = ejjdq;
	}

	public static YiJianJdq getYjjdq() {
		return yjjdq;
	}

	public static void setYjjdq(YiJianJdq yjjdq) {
		JdqZlUtil.yjjdq = yjjdq;
	}

	/**
	 * 开启一检继电器
	 */
	public static void openYiJianJdq() {
		JdqZlUtil.yjjdq.open();
	}

	/**
	 * 关闭一检继电器
	 */
	public static void closeYiJianJdq() {
		JdqZlUtil.yjjdq.close();
	}

	/**
	 * 开启二检继电器
	 */
	public static void openErJianJdq() {
    	System.out.println("开启二检继电器");
		JdqZlUtil.ejjdq.open();
	}

	/**
	 * 关闭二检继电器
	 */
	public static void closeErJianJdq() {
		JdqZlUtil.ejjdq.close();
	}


	
	public static void main(String[] args) {
		//YiJianJdq yjjdq=new YiJianJdq();
		//JdqZlUtil.setYjjdq(yjjdq);
		//JdqZlUtil.openYiJianJdq();
    	//JdqZlUtil.openYiJianShangBangDz();
		
		ErJianJdq ejjdq=new ErJianJdq();
		JdqZlUtil.setEjjdq(ejjdq);
		JdqZlUtil.openErJianJdq();
		JdqBf2Util.openErJianXiaBangDz();
		JdqZlUtil.closeErJianJdq();
	}
}
