package com.znczLfylGkjJhb.cpsbsxt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.znczLfylGkjJhb.cpsbsxt.HCNetSDK.NET_DVR_ALARMER;
import com.znczLfylGkjJhb.cpsbsxt.HCNetSDK.NET_DVR_PLATE_INFO;
import com.znczLfylGkjJhb.cpsbsxt.HCNetSDK.RECV_ALARM;
import com.znczLfylGkjJhb.entity.*;
import com.znczLfylGkjJhb.task.*;
import com.znczLfylGkjJhb.util.*;
import com.znczLfylGkjJhb.yz.*;

/**
 * 	车辆识别回调函数
 * @author lhb
 *
 */
public class FMSGCallBack implements HCNetSDK.FMSGCallBack
{
	javax.swing.JTable jTableAlarm = new javax.swing.JTable();
	
    //报警信息回调函数
    public void invoke(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, HCNetSDK.RECV_ALARM pAlarmInfo, int dwBufLen, Pointer pUser)
    {
    	
    	System.out.println("车辆识别报警回调函数触发");
    	
    	 String sAlarmType = new String();
         DefaultTableModel alarmTableModel = ((DefaultTableModel) jTableAlarm.getModel());//获取表格模型
         String[] newRow = new String[3];
         //报警时间
         Date today = new Date();
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         String[] sIP = new String[2];

         sAlarmType = new String("lCommand=0x") +  Integer.toHexString(lCommand.intValue());

         System.out.println("lCommand.intValue(): " + lCommand.intValue());
//        COMM_ITS_PLATE_RESULT	0x3050	 12368 (十进制) 交通抓拍结果(新报警信息)	
//        COMM_UPLOAD_PLATE_RESULT	0x2800   10240 (十进制)	交通抓拍结果
        //lCommand是传的报警类型
        switch (lCommand.intValue())
        {
        	
        case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:
        	// 创建车辆对象
        	Car car = new Car();
        	
            HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
            
            //
            System.out.println("结构体大小 : " + strPlateResult.dwSize);
            System.out.println("图片长度（近景图） : " + strPlateResult.dwPicLen);
            
            byte byVehicleType = strPlateResult.byVehicleType;
            
            car.setType(byVehicleType);
            
            System.out.println("车辆类型: " + byVehicleType);
            strPlateResult.write();
            Pointer pPlateInfo = strPlateResult.getPointer();
			byte[] buf = new byte[1024];
			
            pPlateInfo.write(0, pAlarmInfo.RecvBuffer, 0, strPlateResult.size());
            strPlateResult.read();
            String srt3 = "";
            try {
            	
            	NET_DVR_PLATE_INFO struPlateInfo = strPlateResult.struPlateInfo;
            	byte[] sLicenseByte = struPlateInfo.sLicense;
            	
            	String sLicense = new String(sLicenseByte, "GBK");
            	
                srt3=new String(strPlateResult.struPlateInfo.sLicense,"GBK");
                System.out.println("车牌号: " + sLicense);
                car.setsLicense(srt3);
                sAlarmType = sAlarmType + "：交通抓拍上传，车牌："+ srt3.toString();
                
                // 车牌颜色
                byte byColor = struPlateInfo.byColor;
                System.out.println("车牌颜色： " + byColor);
                car.setsLicenseColor(byColor);
            }
            catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            newRow[0] = dateFormat.format(today);
            //报警类型
            newRow[1] = sAlarmType;
            //报警设备IP地址
            sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
            System.out.println("报警设备ip地址sIP ： " + new String(pAlarmer.sDeviceIP).trim());
            // 设置ip地址
            car.setIp(new String(pAlarmer.sDeviceIP).trim());
            newRow[2] = sIP[0];
            alarmTableModel.insertRow(0, newRow);

            System.out.println("图片长度： strPlateResult.dwPicLen: " + strPlateResult.dwPicLen);
            
            // 车牌的对象
            System.out.println(car.toString());
            
            try {
            	int bfh = LoadProperties.getBangFangHao();
            	String ip = car.getIp().trim();
            	if(LoadProperties.getHikvisionYiJianIP().equals(ip)) {
            		APIUtil.updateCPSBDDXX(car,bfh);
            	} else if (LoadProperties.getHikvisionErJianIP().equals(ip)) {
            		//二检车辆识别摄像头
            		JSONObject resultJO=null;
            		resultJO=APIUtil.getDingDan(car.getsLicense(),DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
            		if(resultJO!=null) {
	                    if("ok".equals(resultJO.getString("status"))) {
	                		//二检车辆识别摄像头
	                    	switch (bfh) {
							case APIUtil.YI_HAO_BANG_FANG:
		                    	BangFang1Util.updateEJCPSBDDXX(car);
								break;
							}
	                    }
            		}
            	} else {
            		System.out.println("车辆识别摄像头ip地址配置错误");
            	}
			} catch (Exception e) {
				System.out.println(e + "");
				e.printStackTrace();
			}
            break;
        case HCNetSDK.COMM_ITS_PLATE_RESULT:
            HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
            strItsPlateResult.write();
            Pointer pItsPlateInfo = strItsPlateResult.getPointer();
//            pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
//            pPlateInfo.write(0, buf, 0, strPlateResult.size());
            strItsPlateResult.read();
            try {
                 srt3=new String(strItsPlateResult.struPlateInfo.sLicense,"GBK");
                sAlarmType = sAlarmType + ",车辆类型："+strItsPlateResult.byVehicleType + ",交通抓拍上传，车牌："+ srt3;
            }
            catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            newRow[0] = dateFormat.format(today);
            //报警类型
            newRow[1] = sAlarmType;
            //报警设备IP地址
            sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
            newRow[2] = sIP[0];
            alarmTableModel.insertRow(0, newRow);

            for(int i=0;i<strItsPlateResult.dwPicNum;i++)
            {
                if(strItsPlateResult.struPicInfo[i].dwDataLen>0)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = ".\\pic\\"+ new String(pAlarmer.sDeviceIP).trim() + "_"
                                + newName+"_type["+strItsPlateResult.struPicInfo[i].byType+"]_ItsPlate.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                        byte [] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            break;
        	
            //9000报警
            case HCNetSDK.COMM_ALARM_V30:
               HCNetSDK.NET_DVR_ALARMINFO_V30 strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
               strAlarmInfoV30.write();
               Pointer pInfoV30 = strAlarmInfoV30.getPointer();
               pInfoV30.write(0, pAlarmInfo.RecvBuffer, 0, strAlarmInfoV30.size());
               strAlarmInfoV30.read();

                switch (strAlarmInfoV30.dwAlarmType)
                {
                    case 0:
                        sAlarmType = new String("信号量报警");
                        break;
                    case 1:
                        sAlarmType = new String("硬盘满");
                        break;
                    case 2:
                        sAlarmType = new String("信号丢失");
                        break;
                    case 3:
                        sAlarmType = new String("移动侦测");
                        break;
                    case 4:
                        sAlarmType = new String("硬盘未格式化");
                        break;
                    case 5:
                        sAlarmType = new String("读写硬盘出错");
                        break;
                    case 6:
                        sAlarmType = new String("遮挡报警");
                        break;
                    case 7:
                        sAlarmType = new String("制式不匹配");
                        break;
                    case 8:
                        sAlarmType = new String("非法访问");
                        break;
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                alarmTableModel.insertRow(0, newRow);

                break;

            //8000报警
            case HCNetSDK.COMM_ALARM:
                HCNetSDK.NET_DVR_ALARMINFO strAlarmInfo = new HCNetSDK.NET_DVR_ALARMINFO();
                strAlarmInfo.write();
                Pointer pInfo = strAlarmInfo.getPointer();
                pInfo.write(0, pAlarmInfo.RecvBuffer, 0, strAlarmInfo.size());
                strAlarmInfo.read();


                switch (strAlarmInfo.dwAlarmType)
                {
                    case 0:
                        sAlarmType = new String("信号量报警");
                        break;
                    case 1:
                        sAlarmType = new String("硬盘满");
                        break;
                    case 2:
                        sAlarmType = new String("信号丢失");
                        break;
                    case 3:
                        sAlarmType = new String("移动侦测");
                        break;
                    case 4:
                        sAlarmType = new String("硬盘未格式化");
                        break;
                    case 5:
                        sAlarmType = new String("读写硬盘出错");
                        break;
                    case 6:
                        sAlarmType = new String("遮挡报警");
                        break;
                    case 7:
                        sAlarmType = new String("制式不匹配");
                        break;
                    case 8:
                        sAlarmType = new String("非法访问");
                        break;
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
                alarmTableModel.insertRow(0, newRow);

                break;

            //ATM DVR transaction information
            case HCNetSDK.COMM_TRADEINFO:
                //处理交易信息报警
                break;

            //IPC接入配置改变报警
            case HCNetSDK.COMM_IPCCFG:
                // 处理IPC报警
                break;

            default:
                System.out.println("未知报警类型");
                break;
        }
    }

	
	
	
	
	/**
	 * 
	 * @param tradeFile
	 * @return
	 */
	public static byte[] FileToByte(File tradeFile){
	    byte[] buffer = null;
	    try
	    {
	        FileInputStream fis = new FileInputStream(tradeFile);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] b = new byte[1024];
	        int n;
	        while ((n = fis.read(b)) != -1)
	        {
	            bos.write(b, 0, n);
	        }
	        fis.close();
	        bos.close();
	        buffer = bos.toByteArray();
	    }catch (FileNotFoundException e){
	        e.printStackTrace();
	    }catch (IOException e){
	        e.printStackTrace();
	    }
	    return buffer;
	}

	
	/** 
     * 2进制转16进制字符串 
     * @param bytes 
     * @return 
     */  
    public static String byteToHexString(byte[] bytes){  
        if(bytes==null){  
            return null;  
        }  
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < bytes.length; i++) {     
             String strHex=Integer.toHexString(bytes[i]);     
             if(strHex.length() > 3){     
                    sb.append(strHex.substring(6));     
             } else {     
                  if(strHex.length() < 2){     
                     sb.append("0" + strHex);     
                  } else {     
                     sb.append(strHex);     
                  }     
             }     
        }     
       return  sb.toString();     
   }  

//	public void invoke(NativeLong lCommand, NET_DVR_ALARMER pAlarmer, RECV_ALARM pAlarmInfo, int dwBufLen,
//			Pointer pUser) {
//		// TODO Auto-generated method stub
//		
//	}
    
    
    
}