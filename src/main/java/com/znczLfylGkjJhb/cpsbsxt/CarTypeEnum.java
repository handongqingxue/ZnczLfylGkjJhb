package com.znczLfylGkjJhb.cpsbsxt;

/**
 * 车辆类型枚举
 * @author lhb
 *
 */
public class CarTypeEnum {
	
	/**
	 * 根据num 火球车辆名称
	 * @param num
	 * @return
	 */
	public static String getCarName(byte num) {
		
		switch (num) {
		case 0x0:
			return "未知";
		case 0x1:
			return "客车";
		case 0x2:
			return "货车";
		case 0x3:
			return "轿车";
		case 0x4:
			return "面包车";
		case 0x5:
			return "小货车";
		case 0x6:
			return "行人";
		case 0x7:
			return "二轮车";
		case 0x8:
			return "三轮车";
		case 0x9:
			return "SUV/MPV";
		case 10:
			return "中型客车";
		case 11:
			return "机动车";
		case 12:
			return "非机动车";
		case 13:
			return "小型轿车";
		case 14:
			return "微型轿车";
		case 15:
			return "皮卡车";
		case 16:
			return "集装箱卡车";
		case 17:
			return "微卡，栏板卡";
		case 18:
			return "微卡，栏板卡";
		case 19:
			return "吊车，工程车";
		case 20:
			return "油罐车";
		case 21:
			return "混凝土搅拌车";
		case 22:
			return "平板拖车";
		case 23:
			return "两厢轿车";
		case 24:
			return "三厢轿车";
		case 25:
			return "轿跑";
		case 26:
			return "小型客车";
			
		}
		return "未知";
	}


}