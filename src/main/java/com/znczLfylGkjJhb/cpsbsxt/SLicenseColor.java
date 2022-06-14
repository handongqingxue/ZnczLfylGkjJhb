package com.znczLfylGkjJhb.cpsbsxt;

/**
 * 	车牌颜色枚举
 * @param num
 */
public enum SLicenseColor {
	
	VCA_BLUE_PLATE("蓝色车牌", 0x0),
	VCA_YELLOW_PLATE("黄色车牌", 0x1),
	VCA_WHITE_PLATE("白色车牌", 0x2),
	VCA_BLACK_PLATE("黑色车牌", 0x3),
	VCA_GREEN_PLATE("绿色车牌", 0x4),
	VCA_BKAIR_PLATE("民航黑色车牌",  0x5);
	
	private String name;
	private int num;
	
	private SLicenseColor(String name, int num) {
		this.name = name;
		this.num = num;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	

	/**
	 * 根据编号， 返回车牌颜色
	 * @param num
	 * @return
	 */
	public static String getSLicenseColorName(int num) {
		switch (num) {
		case 0x0:
			return VCA_BLUE_PLATE.getName();
		case 0x1:
			return VCA_YELLOW_PLATE.getName();
		case 0x2:
			return VCA_WHITE_PLATE.getName();
		case 0x3:
			return VCA_BLACK_PLATE.getName();
		case 0x4:
			return VCA_GREEN_PLATE.getName();
		case 0x5:
			return VCA_BKAIR_PLATE .getName();
		}
		
		return "其他";
	}
	
}