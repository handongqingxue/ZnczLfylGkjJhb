package com.znczLfylGkjJhb.util;

import org.json.JSONObject;

import com.znczLfylGkjJhb.cpsbsxt.Car;
import com.znczLfylGkjJhb.entity.*;
import com.znczLfylGkjJhb.jdq.*;
import com.znczLfylGkjJhb.task.*;
import com.znczLfylGkjJhb.yz.YzZlUtil;

/**
 * 南磅房（2号磅房）工具类
 * */
public class BangFang2Util {

	/**
	 * 更新一检车牌识别订单信息
	 * @param car
	 */
	public static void updateYJCPSBDDXX(Car car) {
		try {
			System.out.println("查询订单状态为一检排队中的订单");
			String cph = car.getsLicense();
			JSONObject resultJO=APIUtil.getDingDan(cph,DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT);
	        if("ok".equals(resultJO.getString("status"))) {
	        	System.out.println("存在该订单");
	        	System.out.println("根据其他订单状态验证是否存在其他订单");
	        	Integer bfh = LoadProperties.getBangFangHao();
	        	JSONObject ddExistResult = APIUtil.checkDingDanIfExistByZt(bfh,0,DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT,DingDan.DAI_SHANG_BANG,DingDan.DAI_SHANG_BANG);
	        	if("ok".equals(ddExistResult.getString("status"))) {
	            	System.out.println("音柱播报：其他订单状态存在其他订单");
	        	}
	        	else {
	            	System.out.println("开启一检继电器");
		    		JdqZlUtil.openYiJianJdq();
		        	System.out.println("抬起一检上磅道闸");
		        	JdqBf2Util.openYiJianShangBangDz();
		        	
		        	System.out.println("改变订单状态为一检上磅");
					JSONObject ddJO=resultJO.getJSONObject("dingDan");
		        	DingDan dd=new DingDan();
		        	dd.setId(ddJO.getInt("id"));
		        	dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
		        	dd.setYjzt(DingDan.DAI_SHANG_BANG);
		        	dd.setYjbfh(bfh);
		        	APIUtil.editDingDan(dd);

		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
		        	
		    		checkYJSBHWGSState();
	        	}
	        }
	        else {
	        	System.out.println("message==="+resultJO.getString("message"));
	        	System.out.println("没有找到匹配订单，创建订单");
				APIUtil.addDingDan(cph);
				updateYJCPSBDDXX(car);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测一检上磅红外光栅状态
	 */
	public static void checkYJSBHWGSState() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			System.out.println("前open1==="+yjjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				yjjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;

				System.out.println("后open1==="+yjjdq.isKgl1Open());
				if(waitTime>30*1000) {
					System.out.println("上磅失败，请重新车牌识别");
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待上磅更改为一检排队中");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
		    		JdqBf2Util.openYiJianXiaBangDz();
		        	JdqZlUtil.closeYiJianJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
				}
				
				
				if(yjjdq.isKgl1Open()) {
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待上磅更改为上磅中");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.DAI_SHANG_BANG);
					dd.setXyjzt(DingDan.SHANG_BANG_ZHONG);
					APIUtil.editDingDanByZt(dd);
					
					checkYJSBHXBHWGSState();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检测一检上磅和下磅红外光栅状态
	 */
	public static void checkYJSBHXBHWGSState() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			System.out.println("磅和下磅红外光栅状态前open1==="+yjjdq.isKgl1Open());
			System.out.println("磅和下磅红外光栅状态前open2==="+yjjdq.isKgl2Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				yjjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				System.out.println("后open1==="+yjjdq.isKgl1Open());
				System.out.println("后open2==="+yjjdq.isKgl2Open());
				System.out.println("waitTime==="+waitTime);
				if(waitTime>30*1000) {
					System.out.println("称重失败，请重新车牌识别");
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待上磅更改为一检排队中");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT);
					dd.setYjzt(DingDan.SHANG_BANG_ZHONG);
					dd.setXyjzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
		    		JdqBf2Util.openYiJianXiaBangDz();
		        	JdqZlUtil.closeYiJianJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
				}
				
				if(!yjjdq.isKgl1Open()&&!yjjdq.isKgl2Open()) {
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从上磅中更改为待称重");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.SHANG_BANG_ZHONG);
					dd.setXyjzt(DingDan.DAI_CHENG_ZHONG);
					APIUtil.editDingDanByZt(dd);
					
					yiJianChengZhongZhong();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 一检称重中
	 */
	public static void yiJianChengZhongZhong() {
		try {
			System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待称重更改为称重中");
			Integer bfh = LoadProperties.getBangFangHao();
			DingDan dd=new DingDan();
			dd.setYjbfh(bfh);
			dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
			dd.setYjzt(DingDan.DAI_CHENG_ZHONG);
			dd.setXyjzt(DingDan.CHENG_ZHONG_ZHONG);
			APIUtil.editDingDanByZt(dd);
			
			System.out.println("查询订单状态为一检上磅，一检状态为称重中的订单");
			JSONObject resultJO=APIUtil.getDingDanByZt(bfh,0,DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT,DingDan.CHENG_ZHONG_ZHONG,DingDan.DAI_SHANG_BANG);
			String status = resultJO.getString("status");
			if("ok".equals(status)) {
				DingDan dd1=(DingDan)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(resultJO.get("dingDan").toString()), DingDan.class);
				
				System.out.println("请确保车辆稳定，15秒后开始一检称重");
	    		YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
				Thread.sleep(6000);
	    		YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
				Thread.sleep(15000);
	    		YinZhuTask.sendMsg(YzZlUtil.get97().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);

	    		//float yjzl=(float)500;
				float yjzl=(float)DiBangTask3190.getWeight(GuoBangJiLu.RU_CHANG_GUO_BANG);
				
				if(yjzl>0) {
					APIUtil.playWeight(yjzl,YinZhuTask.YI_JIAN);
					Thread.sleep(2000);
					APIUtil.playWeight(yjzl,YinZhuTask.YI_JIAN);
					Thread.sleep(2000);
					
					JSONObject ddJO=resultJO.getJSONObject("dingDan");
			    	int ddId = ddJO.getInt("id");
			    	System.out.println("ddId==="+ddId);
			    	System.out.println("生成磅单记录");
					System.out.println("根据称重出来的重量，添加订单对应的磅单记录");
					APIUtil.newBangDanJiLu(yjzl, ddId);

					System.out.println("生成一检过磅记录");
					GuoBangJiLu gbjl=new GuoBangJiLu();
					gbjl.setGbzl(yjzl);
					gbjl.setGblx(GuoBangJiLu.RU_CHANG_GUO_BANG);
					gbjl.setDdId(dd1.getId());
					APIUtil.newGuoBangJiLu(gbjl);
				
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从称重中更改为待下磅");
					dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXyjzt(DingDan.DAI_XIA_BANG);
					APIUtil.editDingDanByZt(dd);
					
					/*
		    		//打印一检过磅记录(现在改为完成一次订单一起打印模式，这里暂时不需要打印了)
					APIUtil.printGbjl(GuoBangJiLu.RU_CHANG_GUO_BANG);
					
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
					Thread.sleep(2000);
					*/

		    		YinZhuTask.sendMsg(YzZlUtil.get89().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
					
		        	System.out.println("抬起一检下磅道闸");
		        	JdqBf2Util.openYiJianXiaBangDz();
					
					checkYJXBHWGSState();
				}
				else if(yjzl==0) {//地磅上没有车辆
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从称重中更改为待上磅");
					dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXyjzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);
					
		        	//这段代码到现场有了音柱后再打开
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
					checkYJSBHWGSState();
				}
				else if(yjzl==-1) {//称重失败
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从称重中更改为待上磅");
					dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT);
					dd.setYjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXyjzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);
					
					JdqBf2Util.openYiJianXiaBangDz();
		        	JdqZlUtil.closeYiJianJdq();
					
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.YI_JIAN);
				}
			}
			else {
				String message = resultJO.getString("message");
				System.out.println("message==="+message+",语音播报");
	        	//这段代码到现场有了音柱后再打开
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
				Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.YI_JIAN);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测一检下磅红外光栅状态
	 */
	public static void checkYJXBHWGSState() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			System.out.println("前open1==="+yjjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				yjjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+yjjdq.isKgl1Open());
				if(yjjdq.isKgl1Open()) {
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待下磅更改为下磅中");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.DAI_XIA_BANG);
					dd.setXyjzt(DingDan.XIA_BANG_ZHONG);
					APIUtil.editDingDanByZt(dd);
					
					checkIfYJXBYwc();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测一检下磅是否完成
	 */
	public static void checkIfYJXBYwc() {
		try {
			YiJianJdq yjjdq = JdqZlUtil.getYjjdq();
			System.out.println("前open1==="+yjjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				yjjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+yjjdq.isKgl1Open());
				if(!yjjdq.isKgl1Open()) {
					System.out.println("查找订单状态为一检上磅的订单，更改订单状态为二检排队中、一检状态从下磅中更改为已完成");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
			    	dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
			    	dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
			    	dd.setYjzt(DingDan.XIA_BANG_ZHONG);
			    	dd.setXyjzt(DingDan.YI_WAN_CHENG);
			    	APIUtil.editDingDanByZt(dd);
			    	
	            	System.out.println("关闭一检继电器");
		    		JdqZlUtil.closeYiJianJdq();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新二检车牌识别订单信息
	 * @param car
	 */
	public static void updateEJCPSBDDXX(Car car) {
		try {
			System.out.println("查询订单状态为二检排队中的订单");
        	Integer bfh = LoadProperties.getBangFangHao();
			JSONObject resultJO=APIUtil.getDingDan(car.getsLicense(),DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
	        if("ok".equals(resultJO.getString("status"))) {
	        	System.out.println("存在该订单");
	        	System.out.println("根据其他订单状态验证是否存在其他订单");
	        	JSONObject ddExistResult = APIUtil.checkDingDanIfExistByZt(0,bfh,DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT,DingDan.YI_WAN_CHENG,DingDan.DAI_SHANG_BANG);
	        	if("ok".equals(ddExistResult.getString("status"))) {
	            	System.out.println("音柱播报：其他订单状态存在其他订单");
	        	}
	        	else {
	            	System.out.println("开启二检继电器");
		    		JdqZlUtil.openErJianJdq();
		        	System.out.println("抬起二检上磅道闸");
		        	JdqBf2Util.openErJianShangBangDz();
		        	
		        	System.out.println("改变订单状态为二检上磅");
					JSONObject ddJO=resultJO.getJSONObject("dingDan");
		        	DingDan dd=new DingDan();
		        	dd.setId(ddJO.getInt("id"));
		        	dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
		        	dd.setEjzt(DingDan.DAI_SHANG_BANG);
		        	dd.setEjbfh(bfh);
		        	APIUtil.editDingDan(dd);

		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
		        	
		    		checkEJSBHWGSState();
	        	}
	        }
	        else {
	        	System.out.println("message==="+resultJO.getString("message"));
	        	System.out.println("音柱播报：没有找到匹配订单");
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
	    		Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
	        }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检测二检上磅红外光栅状态
	 */
	public static void checkEJSBHWGSState() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			System.out.println("前open1==="+ejjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				ejjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				
				System.out.println("后open1==="+ejjdq.isKgl1Open());
				if(waitTime>30*1000) {
					System.out.println("上磅失败，请重新车牌识别");
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待上磅更改为二检排队中");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
		    		JdqBf2Util.openErJianXiaBangDz();
		        	JdqZlUtil.closeErJianJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
				}
				
				if(ejjdq.isKgl1Open()) {
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待上磅更改为上磅中");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setYjzt(DingDan.YI_WAN_CHENG);
					dd.setEjzt(DingDan.DAI_SHANG_BANG);
					dd.setXejzt(DingDan.SHANG_BANG_ZHONG);
					APIUtil.editDingDanByZt(dd);

					checkEJSBHXBHWGSState();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测二检上磅和下磅红外光栅状态
	 */
	public static void checkEJSBHXBHWGSState() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			System.out.println("前open1==="+ejjdq.isKgl1Open());
			System.out.println("前open2==="+ejjdq.isKgl2Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				ejjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				System.out.println("后open1==="+ejjdq.isKgl1Open());
				System.out.println("后open2==="+ejjdq.isKgl2Open());
				System.out.println("waitTime==="+waitTime);
				if(waitTime>30*1000) {
					System.out.println("称重失败，请重新车牌识别");
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待上磅更改为二检排队中");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
					dd.setEjzt(DingDan.SHANG_BANG_ZHONG);
					dd.setXejzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
		    		JdqBf2Util.openYiJianXiaBangDz();
		        	JdqZlUtil.closeYiJianJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
				}
				
				if(!ejjdq.isKgl1Open()&&!ejjdq.isKgl2Open()) {
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从上磅中更改为待称重");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setEjzt(DingDan.SHANG_BANG_ZHONG);
					dd.setXejzt(DingDan.DAI_CHENG_ZHONG);
					APIUtil.editDingDanByZt(dd);
					
					erJianChengZhongZhong();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 二检称重中
	 */
	public static void erJianChengZhongZhong() {
		try {
			System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待称重更改为称重中");
			Integer bfh = LoadProperties.getBangFangHao();
			DingDan dd=new DingDan();
			dd.setEjbfh(bfh);
			dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
			dd.setEjzt(DingDan.DAI_CHENG_ZHONG);
			dd.setXejzt(DingDan.CHENG_ZHONG_ZHONG);
			APIUtil.editDingDanByZt(dd);
			
			System.out.println("查询订单状态为二检上磅，二检状态为称重中的订单");
			JSONObject resultJO=APIUtil.getDingDanByZt(0,bfh,DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT,DingDan.YI_WAN_CHENG,DingDan.CHENG_ZHONG_ZHONG);
			String status = resultJO.getString("status");
			if("ok".equals(status)) {
				DingDan dd1=(DingDan)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(resultJO.get("dingDan").toString()), DingDan.class);
	
				System.out.println("请确保车辆稳定，15秒后开始二检称重");
				YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
				Thread.sleep(6000);
				YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
				Thread.sleep(15000);
	    		YinZhuTask.sendMsg(YzZlUtil.get97().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);

				Float yjzl=null;
				float ejzl=0;
				Float mz=null;
				Float pz=null;
				Float jz=null;
				Integer lxlx=null;
				JSONObject gbjlJO=APIUtil.selectBangDanJiLuByDdId(dd1.getId());
				JSONObject bdJO=gbjlJO.getJSONObject("bdjl");
				int bdId = bdJO.getInt("id");
				yjzl=(float)bdJO.getDouble("yjzl");
				//ejzl=1300;
				ejzl=(float)DiBangTask3190.getWeight(GuoBangJiLu.CHU_CHANG_GUO_BANG);

				if(yjzl>ejzl) {
					lxlx=DingDan.SONG_YUN;
					mz=yjzl;
					pz=ejzl;
				}
				else if(yjzl<ejzl) {
					lxlx=DingDan.QU_YUN;
					pz=yjzl;
					mz=ejzl;
				}
				jz=mz-pz;
				
				if(ejzl>0) {
					APIUtil.playWeight(ejzl,YinZhuTask.ER_JIAN);
					Thread.sleep(2000);
					APIUtil.playWeight(ejzl,YinZhuTask.ER_JIAN);
					Thread.sleep(2000);
					
					System.out.println("根据称重出来的重量，修改订单对应的磅单记录");
					APIUtil.editBangDanJiLu(bdId,ejzl,mz,pz,jz);

					System.out.println("生成二检过磅记录");
					GuoBangJiLu gbjl=new GuoBangJiLu();
					gbjl.setGbzl(ejzl);
					gbjl.setGblx(GuoBangJiLu.CHU_CHANG_GUO_BANG);
					gbjl.setDdId(dd1.getId());
					APIUtil.newGuoBangJiLu(gbjl);
				
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从称重中更改为待下磅");
					dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setEjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXejzt(DingDan.DAI_XIA_BANG);
					dd.setLxlx(lxlx);
					APIUtil.editDingDanByZt(dd);

					/*
		    		//打印二检过磅记录(现在改为完成一次订单一起打印模式，这里暂时不需要打印了)
					APIUtil.printGbjl(GuoBangJiLu.CHU_CHANG_GUO_BANG);
					*/
					
					//打印磅单记录
					APIUtil.printBdjl();
					
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get89().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
					
		        	System.out.println("抬起二检下磅道闸");
		        	JdqBf2Util.openErJianXiaBangDz();
					
					checkEJXBHWGSState();
				}
				else if(ejzl==0) {//地磅上没有车辆
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从称重中更改为待上磅");
					dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setEjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXejzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);

		        	//这段代码到现场有了音柱后再打开
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
					checkEJSBHWGSState();
				}
				else if(ejzl==-1) {//称重失败
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从称重中更改为待上磅");
					dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
					dd.setEjzt(DingDan.CHENG_ZHONG_ZHONG);
					dd.setXejzt(DingDan.DAI_SHANG_BANG);
					APIUtil.editDingDanByZt(dd);
					
					JdqBf2Util.openErJianXiaBangDz();
		        	JdqZlUtil.closeErJianJdq();
					
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000,YinZhuTask.ER_JIAN);
				}
			}
			else {
				String message = resultJO.getString("message");
				System.out.println("message==="+message+",语音播报");
	        	//这段代码到现场有了音柱后再打开
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
				Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500,YinZhuTask.ER_JIAN);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检测二检下磅红外光栅状态
	 */
	public static void checkEJXBHWGSState() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			System.out.println("前open1==="+ejjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				ejjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+ejjdq.isKgl1Open());
				if(ejjdq.isKgl1Open()) {
		        	
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待下磅更改为下磅中");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setEjzt(DingDan.DAI_XIA_BANG);
					dd.setXejzt(DingDan.XIA_BANG_ZHONG);
					APIUtil.editDingDanByZt(dd);
					
					checkIfEJXBYwc();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测二检下磅是否完成
	 */
	public static void checkIfEJXBYwc() {
		try {
			ErJianJdq ejjdq = JdqZlUtil.getEjjdq();
			System.out.println("前open1=="+ejjdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				ejjdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+ejjdq.isKgl1Open());
				if(!ejjdq.isKgl1Open()) {
					String cph=null;
					JSONObject cphResultJO=APIUtil.getCphByBfhDdzt(bfh,DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					String cphStatus = cphResultJO.getString("status");
					if("ok".equals(cphStatus))
						cph=cphResultJO.getString("cph");
				
			    	System.out.println("查找订单状态为二检上磅的订单，更改订单状态为已完成、二检状态从下磅中更改为已完成");
			    	DingDan dd=new DingDan();
			    	dd.setEjbfh(bfh);
			    	dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
			    	dd.setXddztMc(DingDanZhuangTai.YI_WAN_CHENG_TEXT);
			    	dd.setEjzt(DingDan.XIA_BANG_ZHONG);
			    	dd.setXejzt(DingDan.YI_WAN_CHENG);
			    	JSONObject eddResultJO=APIUtil.editDingDanByZt(dd);
					String message = eddResultJO.getString("message");
					if("ok".equals(message)) {
						APIUtil.updateCheLiangWcddcsByCph(cph);
					}
			    	
	            	System.out.println("关闭二检继电器");
		    		JdqZlUtil.closeErJianJdq();
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Car car = new Car();
    	car.setsLicense(" 鲁B9001");
    	//updateYJCPSBDDXX(car);
    	updateEJCPSBDDXX(car);
	}
}
