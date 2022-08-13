package com.znczLfylGkjJhb.cpsbsxt;

import java.io.File;

public class Test {
	
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

	public static void main(String[] args) {
		 boolean initSuc = hCNetSDK.NET_DVR_Init();
         hCNetSDK.NET_DVR_SetLogToFile(3,"d:SdkLog", true);
		 System.out.println("initSuc: " + initSuc);
	}
}
