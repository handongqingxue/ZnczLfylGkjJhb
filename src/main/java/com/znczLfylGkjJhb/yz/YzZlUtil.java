package com.znczLfylGkjJhb.yz;

public class YzZlUtil {
	
	/**
	 * 
	 * @return  的车辆入厂
	 */
	public static String get81() {
		return getByDuanHao(81);
	}
	
	/**
	 * 
	 * @return  读取成功请
	 */
	public static String get82() {
		return getByDuanHao(82);
	}
	
	/**
	 * 
	 * @return  请车牌号为
	 */
	public static String get83() {
		return getByDuanHao(83);
	}
	
	/**
	 * 
	 * @return  身份识别已成功请拿走身份证
	 */
	public static String get84() {
		return getByDuanHao(84);
	}
	
	/**
	 * @return  请等待
	 */
	public static String get85() {
		return getByDuanHao(85);
	}
	
	/**
	 * @return  没有找到匹配订单
	 */
	public static String get86() {
		return getByDuanHao(86);
	}
	
	/**
	 * 
	 * @return  请上磅把车辆停放在地磅中间
	 */
	public static String get87() {
		return getByDuanHao(87);
	}
	
	/**
	 * 
	 * @return  停车熄火等待称重，请相关人员及时离开车辆和地磅
	 */
	public static String get88() {
		return getByDuanHao(88);
	}
	
	/**
	 * 
	 * @return  称重完成请下磅
	 */
	public static String get89() {
		return getByDuanHao(89);
	}
	
	/**
	 * 
	 * @return  请确保车辆停在磅秤中间
	 */
	public static String get90() {
		return getByDuanHao(90);
	}
	
	/**
	 * 
	 * @return  车牌识别失败，请联系相关人员
	 */
	public static String get91() {
		return getByDuanHao(91);
	}
	
	/**
	 * 
	 * @return  车牌未识别，请移动车辆位置
	 */
	public static String get92() {
		return getByDuanHao(92);
	}
	
	/**
	 * 
	 * @return  红外设备故障，请联系相关人员
	 */
	public static String get93() {
		return getByDuanHao(93);
	}
	
	/**
	 * 
	 * @return  红外识别有误， 请联系管理人员
	 */
	public static String get94() {
		return getByDuanHao(94);
	}
	
	/**
	 * 
	 * @return  称重失败， 请联系相关人员
	 */
	public static String get95() {
		return getByDuanHao(95);
	}
	
	/**
	 * 
	 * @return  车牌号和订单不一致， 请联系相关人员
	 */
	public static String get96() {
		return getByDuanHao(96);
	}
	
	/**
	 * 
	 * @return  正在称重
	 */
	public static String get97() {
		return getByDuanHao(97);
	}
	
	/**
	 * 
	 * @return  当前重量
	 */
	public static String get98() {
		return getByDuanHao(98);
	}
	
	/**
	 * 
	 * @return  请取称重打印票据
	 */
	public static String get99() {
		return getByDuanHao(99);
	}
	
	public static String getByDuanHao(int num) {
		String zlStr=null;
		switch (num) {
		case 1:
			zlStr="F0 01 06 04 01 FC";
			break;
		case 2:
			zlStr="F0 01 06 04 02 FD";
			break;
		case 3:
			zlStr="F0 01 06 04 03 FE";
			break;
		case 4:
			zlStr="F0 01 06 04 04 FF";
			break;
		case 5:
			zlStr="F0 01 06 04 05 00";
			break;
		case 6:
			zlStr="F0 01 06 04 06 01";
			break;
		case 7:
			zlStr="F0 01 06 04 07 02";
			break;
		case 8:
			zlStr="F0 01 06 04 08 03";
			break;
		case 9:
			zlStr="F0 01 06 04 09 04";
			break;
		case 10:
			zlStr="F0 01 06 04 0A 05";
			break;
		case 11:
			zlStr="F0 01 06 04 0B 06";
			break;
		case 12:
			zlStr="F0 01 06 04 0C 07";
			break;
		case 13:
			zlStr="F0 01 06 04 0D 08";
			break;
		case 14:
			zlStr="F0 01 06 04 0E 09";
			break;
		case 15:
			zlStr="F0 01 06 04 0F 0A";
			break;
		case 16:
			zlStr="F0 01 06 04 10 0B";
			break;
		case 17:
			zlStr="F0 01 06 04 11 0C";
			break;
		case 18:
			zlStr="F0 01 06 04 12 0D";
			break;
		case 19:
			zlStr="F0 01 06 04 13 0E";
			break;
		case 20:
			zlStr="F0 01 06 04 14 0F";
			break;
		case 21:
			zlStr="F0 01 06 04 15 10";
			break;
		case 22:
			zlStr="F0 01 06 04 16 11";
			break;
		case 23:
			zlStr="F0 01 06 04 17 12";
			break;
		case 24:
			zlStr="F0 01 06 04 18 13";
			break;
		case 25:
			zlStr="F0 01 06 04 19 14";
			break;
		case 26:
			zlStr="F0 01 06 04 1A 15";
			break;
		case 27:
			zlStr="F0 01 06 04 1B 16";
			break;
		case 28:
			zlStr="F0 01 06 04 1C 17";
			break;
		case 29:
			zlStr="F0 01 06 04 1D 18";
			break;
		case 30:
			zlStr="F0 01 06 04 1E 19";
			break;
		case 31:
			zlStr="F0 01 06 04 1F 1A";
			break;
		case 32:
			zlStr="F0 01 06 04 20 1B";
			break;
		case 33:
			zlStr="F0 01 06 04 21 1C";
			break;
		case 34:
			zlStr="F0 01 06 04 22 1D";
			break;
		case 35:
			zlStr="F0 01 06 04 23 1E";
			break;
		case 36:
			zlStr="F0 01 06 04 24 1F";
			break;
		case 37:
			zlStr="F0 01 06 04 25 20";
			break;
		case 38:
			zlStr="F0 01 06 04 26 21";
			break;
		case 39:
			zlStr="F0 01 06 04 27 22";
			break;
		case 40:
			zlStr="F0 01 06 04 28 23";
			break;
		case 41:
			zlStr="F0 01 06 04 29 24";
			break;
		case 42:
			zlStr="F0 01 06 04 2A 25";
			break;
		case 43:
			zlStr="F0 01 06 04 2B 26";
			break;
		case 44:
			zlStr="F0 01 06 04 2C 27";
			break;
		case 45:
			zlStr="F0 01 06 04 2D 28";
			break;
		case 46:
			zlStr="F0 01 06 04 2E 29";
			break;
		case 47:
			zlStr="F0 01 06 04 2F 2A";
			break;
		case 48:
			zlStr="F0 01 06 04 30 2B";
			break;
		case 49:
			zlStr="F0 01 06 04 31 2C";
			break;
		case 50:
			zlStr="F0 01 06 04 32 2D";
			break;
		case 51:
			zlStr="F0 01 06 04 33 2E";
			break;
		case 52:
			zlStr="F0 01 06 04 34 2F";
			break;
		case 53:
			zlStr="F0 01 06 04 35 30";
			break;
		case 54:
			zlStr="F0 01 06 04 36 31";
			break;
		case 55:
			zlStr="F0 01 06 04 37 32";
			break;
		case 56:
			zlStr="F0 01 06 04 38 33";
			break;
		case 57:
			zlStr="F0 01 06 04 39 34";
			break;
		case 58:
			zlStr="F0 01 06 04 3A 35";
			break;
		case 59:
			zlStr="F0 01 06 04 3B 36";
			break;
		case 60:
			zlStr="F0 01 06 04 3C 37";
			break;
		case 61:
			zlStr="F0 01 06 04 3D 38";
			break;
		case 62:
			zlStr="F0 01 06 04 3E 39";
			break;
		case 63:
			zlStr="F0 01 06 04 3F 3A";
			break;
		case 64:
			zlStr="F0 01 06 04 40 3B";
			break;
		case 65:
			zlStr="F0 01 06 04 41 3C";
			break;
		case 66:
			zlStr="F0 01 06 04 42 3D";
			break;
		case 67:
			zlStr="F0 01 06 04 43 3E";
			break;
		case 68:
			zlStr="F0 01 06 04 44 3F";
			break;
		case 69:
			zlStr="F0 01 06 04 45 40";
			break;
		case 70:
			zlStr="F0 01 06 04 46 41";
			break;
		case 71:
			zlStr="F0 01 06 04 47 42";
			break;
		case 72:
			zlStr="F0 01 06 04 48 43";
			break;
		case 73:
			zlStr="F0 01 06 04 49 44";
			break;
		case 74:
			zlStr="F0 01 06 04 4A 45";
			break;
		case 75:
			zlStr="F0 01 06 04 4B 46";
			break;
		case 76:
			zlStr="F0 01 06 04 4C 47";
			break;
		case 77:
			zlStr="F0 01 06 04 4D 48";
			break;
		case 78:
			zlStr="F0 01 06 04 4E 49";
			break;
		case 79:
			zlStr="F0 01 06 04 4F 4A";
			break;
		case 80:
			zlStr="F0 01 06 04 50 4B";
			break;
		case 81:
			zlStr="F0 01 06 04 51 4C";
			break;
		case 82:
			zlStr="F0 01 06 04 52 4D";
			break;
		case 83:
			zlStr="F0 01 06 04 53 4E";
			break;
		case 84:
			zlStr="F0 01 06 04 54 4F";
			break;
		case 85:
			zlStr="F0 01 06 04 55 50";
			break;
		case 86:
			zlStr="F0 01 06 04 56 51";
			break;
		case 87:
			zlStr="F0 01 06 04 57 52";
			break;
		case 88:
			zlStr="F0 01 06 04 58 53";
			break;
		case 89:
			zlStr="F0 01 06 04 59 54";
			break;
		case 90:
			zlStr="F0 01 06 04 5A 55";
			break;
		case 91:
			zlStr="F0 01 06 04 5B 56";
			break;
		case 92:
			zlStr="F0 01 06 04 5C 57";
			break;
		case 93:
			zlStr="F0 01 06 04 5D 58";
			break;
		case 94:
			zlStr="F0 01 06 04 5E 59";
			break;
		case 95:
			zlStr="F0 01 06 04 5F 5A";
			break;
		case 96:
			zlStr="F0 01 06 04 60 5B";
			break;
		case 97:
			zlStr="F0 01 06 04 61 5C";
			break;
		case 98:
			zlStr="F0 01 06 04 62 5D";
			break;
		case 99:
			zlStr="F0 01 06 04 63 5E";
			break;
		}
		return zlStr;
	}
	
	public static String getByStr(String str) {
		String zlStr=null;
		switch (str) {
		case "0": 
			zlStr=getByDuanHao(36);
			break;
		case "1": 
			zlStr=getByDuanHao(1);
			break;
		case "2": 
			zlStr=getByDuanHao(2);
			break;
		case "3":
			zlStr=getByDuanHao(3);
			break;
		case "4":
			zlStr=getByDuanHao(4);
			break;
		case "5":
			zlStr=getByDuanHao(5);
			break;
		case "6":
			zlStr=getByDuanHao(6);
			break;
		case "7":
			zlStr=getByDuanHao(7);
			break;
		case "8":
			zlStr=getByDuanHao(8);
			break;
		case "9":
			zlStr=getByDuanHao(9);
			break;
		case "A":
			zlStr=getByDuanHao(10);
			break;
		case "B":
			zlStr=getByDuanHao(11);
			break;
		case "C":
			zlStr=getByDuanHao(12);
			break;
		case "D":
			zlStr=getByDuanHao(13);
			break;
		case "E":
			zlStr=getByDuanHao(14);
			break;
		case "F":
			zlStr=getByDuanHao(15);
			break;
		case "G":
			zlStr=getByDuanHao(16);
			break;
		case "H":
			zlStr=getByDuanHao(17);
			break;
		case "I":
			zlStr=getByDuanHao(18);
			break;
		case "J":
			zlStr=getByDuanHao(19);
			break;
		case "K":
			zlStr=getByDuanHao(20);
			break;
		case "L":
			zlStr=getByDuanHao(21);
			break;
		case "M":
			zlStr=getByDuanHao(22);
			break;
		case "N":
			zlStr=getByDuanHao(23);
			break;
		case "O":
			zlStr=getByDuanHao(24);
			break;
		case "P":
			zlStr=getByDuanHao(25);
			break;
		case "Q":
			zlStr=getByDuanHao(26);
			break;
		case "R":
			zlStr=getByDuanHao(27);
			break;
		case "S":
			zlStr=getByDuanHao(28);
			break;
		case "T":
			zlStr=getByDuanHao(29);
			break;
		case "U":
			zlStr=getByDuanHao(30);
			break;
		case "V":
			zlStr=getByDuanHao(31);
			break;
		case "W":
			zlStr=getByDuanHao(32);
			break;
		case "X":
			zlStr=getByDuanHao(33);
			break;
		case "Y":
			zlStr=getByDuanHao(34);
			break;
		case "Z":
			zlStr=getByDuanHao(35);
			break;
		case "藏":	
			zlStr=getByDuanHao(41); 
			break;
		case "甘":	
			zlStr=getByDuanHao(42); 
			break;
		case "赣":	
			zlStr=getByDuanHao(43); 
			break;
		case "贵":	
			zlStr=getByDuanHao(44); 
			break;
		case "桂":	
			zlStr=getByDuanHao(45); 
			break;
		case "黑":	
			zlStr=getByDuanHao(46); 
			break;
		case "沪":	
			zlStr=getByDuanHao(47); 
			break;
		case "吉":	
			zlStr=getByDuanHao(48); 
			break;
		case "冀":	
			zlStr=getByDuanHao(49); 
			break;
		case "津":	
			zlStr=getByDuanHao(50); 
			break;
		case "晋":	
			zlStr=getByDuanHao(51); 
			break;
		case "京":	
			zlStr=getByDuanHao(52); 
			break;
		case "辽":	
			zlStr=getByDuanHao(53); 
			break;
		case "鲁":	
			zlStr=getByDuanHao(54); 
			break;
		case "蒙":	
			zlStr=getByDuanHao(55); 
			break;
		case "闽":	
			zlStr=getByDuanHao(56); 
			break;
		case "青":	
			zlStr=getByDuanHao(57); 
			break;
		case "琼":	
			zlStr=getByDuanHao(58); 
			break;
		case "陕":	
			zlStr=getByDuanHao(59); 
			break;
		case "苏":	
			zlStr=getByDuanHao(60); 
			break;
		case "皖":	
			zlStr=getByDuanHao(61); 
			break;
		case "湘":	
			zlStr=getByDuanHao(62); 
			break;
		case "新":	
			zlStr=getByDuanHao(63); 
			break;
		case "渝":	
			zlStr=getByDuanHao(64); 
			break;
		case "豫":	
			zlStr=getByDuanHao(65); 
			break;
		case "粤":	
			zlStr=getByDuanHao(66); 
			break;
		case "云":	
			zlStr=getByDuanHao(67); 
			break;
		case "浙":	
			zlStr=getByDuanHao(68);
			break;
		}
		return zlStr;
	}
}
