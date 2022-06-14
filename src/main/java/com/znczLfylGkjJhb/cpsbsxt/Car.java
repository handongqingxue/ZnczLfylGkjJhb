package com.znczLfylGkjJhb.cpsbsxt;

public class Car {

	// 摄像头ip地址
	private String ip;
	
	// 颜色+ 车牌号
	private String sLicense;
	// 车辆类型
	private byte type;
	
	private String typeStr;
	// 车牌颜色编号
	private byte sLicenseColor;
	// 车牌颜色
	private String sLicenseColorStr;
	
	// 图片二进制
	 byte [] bytes=null;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTypeStr() {
		return CarTypeEnum.getCarName(this.type);
	}
	
	public String getsLicense() {
		return sLicense;
	}
	public void setsLicense(String sLicense) {
		this.sLicense = sLicense.substring(1, sLicense.length()).trim();
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.setTypeStr(CarTypeEnum.getCarName(type));
	}
	
	public byte getsLicenseColor() {
		return sLicenseColor;
	}
	public void setsLicenseColor(byte sLicenseColor) {
		this.sLicenseColor = sLicenseColor;
		this.setsLicenseColorStr(SLicenseColor.getSLicenseColorName((int)sLicenseColor));
	}
	public String getsLicenseColorStr() {
		int num = sLicenseColor;
		return SLicenseColor.getSLicenseColorName(num);
	}
	public void setsLicenseColorStr(String sLicenseColorStr) {
		this.sLicenseColorStr = sLicenseColorStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
//		 FileOutputStream fout = null;
//             try {
//				fout = new FileOutputStream("d://" + this.sLicense +"_plateResult.jpg");
//				fout.write(bytes);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					fout.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		
	}
	@Override
	public String toString() {
		return "Car [ip=" + ip + ", sLicense=" + sLicense + ", type=" + type + ", typeStr=" + typeStr
				+ ", sLicenseColor=" + sLicenseColor + ", sLicenseColorStr=" + sLicenseColorStr + "]";
	}
	
	
	public static void main(String[] args) {
		
		String sLicense= "黄鲁N3849";
		
		System.out.println(sLicense.substring(1, sLicense.length()));
	}
}
