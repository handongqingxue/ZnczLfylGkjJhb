package com.znczLfylGkjJhb.task;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.znczLfylGkjJhb.cpsbsxt.*;
import com.znczLfylGkjJhb.cpsbsxt.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.znczLfylGkjJhb.util.LoadProperties;

public class CpsbsxtTask extends Thread {
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static FMSGCallBack fMSFCallBack;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		
		System.out.println("initSuc: " + initSuc);
		//  注册回调
	 	//FMSGCallBack fMSFCallBack = new FMSGCallBack();  //报警回调函数实现
	 	fMSFCallBack = new FMSGCallBack();  //报警回调函数实现
		Pointer pUser = null;
		hCNetSDK.NET_DVR_SetDVRMessageCallBack_V30(fMSFCallBack, pUser );
		
		//注册1
        String  yiJianDeviceIP = LoadProperties.getHikvisionYiJianIP();//设备ip地址
        System.out.println("yiJianDeviceIP==="+yiJianDeviceIP);
        //String  yiJianDeviceIP = "192.168.1.11";
        int yiJianPort = Integer.parseInt(LoadProperties.getHikvisionYiJianPort());
        //int yiJianPort = 8000;
        String yiJianUserName =LoadProperties.getHikvisionYiJianUserName();
        //String yiJianUserName ="admin";
        String yiJianPassword = LoadProperties.getHikvisionYiJianPassword();
        //String yiJianPassword = "lanfan2022";
        NET_DVR_DEVICEINFO_V30 yiJianDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        NativeLong lUserID = hCNetSDK.NET_DVR_Login_V30(yiJianDeviceIP, (short) yiJianPort, yiJianUserName, yiJianPassword, yiJianDeviceInfo);

        long userID = lUserID.longValue();
        if (userID == -1){
           System.out.println("注册失败，ip为： " + yiJianDeviceIP);
           return;
        }
        System.out.println(yiJianDeviceIP + "  userID: " + userID);
        
        NativeLong yiJianAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V30(lUserID);
        if (yiJianAlarmHandle.intValue() == -1){
        	System.out.println(yiJianDeviceInfo + "布防失败！");
            return;
        }
        
        /*
        //注册2
	    String  erJianDeviceIP = LoadProperties.getHikvisionErJianIP();//设备ip地址
	    int erJianPort = Integer.parseInt(LoadProperties.getHikvisionErJianPort());
	    String erJianUserName =LoadProperties.getHikvisionErJianUserName();
	    String erJianPassword = LoadProperties.getHikvisionErJianPassword();
	    NET_DVR_DEVICEINFO_V30 erJianDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
	    NativeLong lUserID2 = hCNetSDK.NET_DVR_Login_V30(erJianDeviceIP, (short) erJianPort, erJianUserName, erJianPassword, erJianDeviceInfo);

        long userID2 = lUserID.longValue();
        if (userID2 == -1){
        	System.out.println("注册失败，ip为： " + erJianDeviceIP);
            return;
        }
        System.out.println(erJianDeviceIP + "  userID2: " + userID2);

        NativeLong erJianAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V30(lUserID2);
        if (erJianAlarmHandle.intValue() == -1){
        	System.out.println(erJianDeviceIP + "布防失败！");
            return;
        }
        */
	}
}
