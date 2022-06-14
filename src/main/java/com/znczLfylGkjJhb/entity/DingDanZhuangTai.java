package com.znczLfylGkjJhb.entity;

public class DingDanZhuangTai {

	public static final String YI_JIAN_PAI_DUI_ZHONG_TEXT="一检排队中";//1
	public static final String YI_JIAN_SHANG_BANG_TEXT="一检上磅";//2
	public static final String ER_JIAN_PAI_DUI_ZHONG_TEXT="二检排队中";//3
	public static final String ER_JIAN_SHANG_BANG_TEXT="二检上磅";//4
	public static final String YI_WAN_CHENG_TEXT="已完成";//5

	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public Integer getPx() {
		return px;
	}
	public void setPx(Integer px) {
		this.px = px;
	}
	private String mc;
	private Integer px;
}
