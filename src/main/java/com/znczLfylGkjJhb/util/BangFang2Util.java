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
	        	JSONObject ddExistResult = APIUtil.checkDingDanIfExistByZt(bfh,DingDanZhuangTai.CHECK_SHANG_BANG_TEXT);
	        	if("ok".equals(ddExistResult.getString("status"))) {
	            	System.out.println("音柱播报：其他订单状态存在其他订单");
	        	}
	        	else {
		    		JdqZlUtil.openJdq();
		        	JdqBf2Util.openShangBangDz();
		        	
		        	System.out.println("改变订单状态为一检上磅");
					JSONObject ddJO=resultJO.getJSONObject("dingDan");
		        	DingDan dd=new DingDan();
		        	dd.setId(ddJO.getInt("id"));
		        	dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
		        	dd.setYjzt(DingDan.DAI_SHANG_BANG);
		        	dd.setYjbfh(bfh);
		        	APIUtil.editDingDan(dd);

		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500);
		        	
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
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;

				System.out.println("后open1==="+jdq.isKgl1Open());
				if(waitTime>30*1000) {
					System.out.println("上磅失败，请重新车牌识别");
					System.out.println("查找订单状态为一检上磅的订单，将一检上磅状态从待上磅更改为一检排队中");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
		    		JdqBf2Util.openXiaBangDz();
		        	JdqZlUtil.closeJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000);
				}
				
				
				if(jdq.isKgl1Open()) {
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
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("磅和下磅红外光栅状态前open1==="+jdq.isKgl1Open());
			System.out.println("磅和下磅红外光栅状态前open2==="+jdq.isKgl2Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				System.out.println("后open1==="+jdq.isKgl1Open());
				System.out.println("后open2==="+jdq.isKgl2Open());
				System.out.println("waitTime==="+waitTime);
				if(waitTime>30*1000) {
					//
					//若光栅被遮挡超过30s，可能车辆已经上磅，但车头或车尾依然未离开光栅区域。这种情况需先获取下地磅上的车辆重量，判断有无车辆，有的话进入称重环节，没的话则还原到上一步
					int weight = DiBangTask3190.getTestWeight();
					if(weight>0) {
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
					else {
					//
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
			    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
			    		JdqBf2Util.openXiaBangDz();
			        	JdqZlUtil.closeJdq();
						Thread.sleep(2000);
			    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
						break;
					//
					}
					//
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000);
				}
				
				if(!jdq.isKgl1Open()&&!jdq.isKgl2Open()) {
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
	    		YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500);
				Thread.sleep(6000);
	    		YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500);
				Thread.sleep(15000);
	    		YinZhuTask.sendMsg(YzZlUtil.get97().replaceAll(" ", ""), 1500);

	    		//float yjzl=(float)500;
				float yjzl=(float)DiBangTask3190.getWeight();
				
				if(yjzl>0) {
					APIUtil.playWeight(yjzl);
					Thread.sleep(2000);
					APIUtil.playWeight(yjzl);
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

		    		YinZhuTask.sendMsg(YzZlUtil.get89().replaceAll(" ", ""), 1500);
					
		        	JdqBf2Util.openXiaBangDz();
					
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
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500);
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
					
					JdqBf2Util.openXiaBangDz();
		        	JdqZlUtil.closeJdq();
					
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1500);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
				}
			}
			else {
				String message = resultJO.getString("message");
				System.out.println("message==="+message+",语音播报");
	        	//这段代码到现场有了音柱后再打开
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
				Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测一检下磅红外光栅状态
	 */
	public static void checkYJXBHWGSState() {
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+jdq.isKgl1Open());
				if(jdq.isKgl1Open()) {
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
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+jdq.isKgl1Open());
				if(!jdq.isKgl1Open()) {
					System.out.println("查找订单状态为一检上磅的订单，更改订单状态为二检排队中、一检状态从下磅中更改为已完成");
					DingDan dd=new DingDan();
					dd.setYjbfh(bfh);
			    	dd.setDdztMc(DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT);
			    	dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
			    	dd.setYjzt(DingDan.XIA_BANG_ZHONG);
			    	dd.setXyjzt(DingDan.YI_WAN_CHENG);
			    	APIUtil.editDingDanByZt(dd);
			    	
		    		JdqZlUtil.closeJdq();
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
	        	JSONObject ddExistResult = APIUtil.checkDingDanIfExistByZt(bfh,DingDanZhuangTai.CHECK_SHANG_BANG_TEXT);
	        	if("ok".equals(ddExistResult.getString("status"))) {
	            	System.out.println("音柱播报：其他订单状态存在其他订单");
	        	}
	        	else {
		    		JdqZlUtil.openJdq();
		        	JdqBf2Util.openShangBangDz();
		        	
		        	System.out.println("改变订单状态为二检上磅");
					JSONObject ddJO=resultJO.getJSONObject("dingDan");
		        	DingDan dd=new DingDan();
		        	dd.setId(ddJO.getInt("id"));
		        	dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
		        	dd.setEjzt(DingDan.DAI_SHANG_BANG);
		        	dd.setEjbfh(bfh);
		        	APIUtil.editDingDan(dd);

		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500);
		        	
		    		checkEJSBHWGSState();
	        	}
	        }
	        else {
	        	System.out.println("message==="+resultJO.getString("message"));
	        	System.out.println("音柱播报：没有找到匹配订单");
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
	    		Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
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
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				
				System.out.println("后open1==="+jdq.isKgl1Open());
				if(waitTime>30*1000) {
					System.out.println("上磅失败，请重新车牌识别");
					System.out.println("查找订单状态为二检上磅的订单，将二检上磅状态从待上磅更改为二检排队中");
					DingDan dd=new DingDan();
					dd.setEjbfh(bfh);
					dd.setDdztMc(DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT);
					dd.setXddztMc(DingDanZhuangTai.ER_JIAN_PAI_DUI_ZHONG_TEXT);
					APIUtil.editDingDanByZt(dd);
					
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
		    		JdqBf2Util.openXiaBangDz();
		        	JdqZlUtil.closeJdq();
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
					break;
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000);
				}
				
				if(jdq.isKgl1Open()) {
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
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			System.out.println("前open2==="+jdq.isKgl2Open());
			Integer bfh = LoadProperties.getBangFangHao();
			int waitTime=0;
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				waitTime+=1000;
				System.out.println("后open1==="+jdq.isKgl1Open());
				System.out.println("后open2==="+jdq.isKgl2Open());
				System.out.println("waitTime==="+waitTime);
				if(waitTime>30*1000) {
					//
					//若光栅被遮挡超过30s，可能车辆已经上磅，但车头或车尾依然未离开光栅区域。这种情况需先获取下地磅上的车辆重量，判断有无车辆，有的话进入称重环节，没的话则还原到上一步
					int weight = DiBangTask3190.getTestWeight();
					if(weight>0) {
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
					else {
					//
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
			    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
			    		JdqBf2Util.openXiaBangDz();
			        	JdqZlUtil.closeJdq();
						Thread.sleep(2000);
			    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
						break;
					//
					}
					//
				}
				else if(waitTime%(5*1000)==0) {
					waitTime+=1000;
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1000);
				}
				
				if(!jdq.isKgl1Open()&&!jdq.isKgl2Open()) {
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
				YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500);
				Thread.sleep(6000);
				YinZhuTask.sendMsg(YzZlUtil.get88().replaceAll(" ", ""), 1500);
				Thread.sleep(15000);
	    		YinZhuTask.sendMsg(YzZlUtil.get97().replaceAll(" ", ""), 1500);

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
				//ejzl=300;
				ejzl=(float)DiBangTask3190.getWeight();

				if(yjzl>ejzl) {
					lxlx=DingDan.SONG_YUN;
					mz=yjzl;
					pz=ejzl;
				}
				else {
					lxlx=DingDan.QU_YUN;
					pz=yjzl;
					mz=ejzl;
				}
				/*
				else if(yjzl<ejzl) {
					lxlx=DingDan.QU_YUN;
					pz=yjzl;
					mz=ejzl;
				}
				*/
				jz=mz-pz;
				
				if(ejzl>0) {
					APIUtil.playWeight(ejzl);
					Thread.sleep(2000);
					APIUtil.playWeight(ejzl);
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
					
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get99().replaceAll(" ", ""), 1500);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get89().replaceAll(" ", ""), 1500);
					
		        	JdqBf2Util.openXiaBangDz();
					
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
		    		YinZhuTask.sendMsg(YzZlUtil.get87().replaceAll(" ", ""), 1500);
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
					
					JdqBf2Util.openXiaBangDz();
		        	JdqZlUtil.closeJdq();
					
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1500);
					Thread.sleep(2000);
		    		YinZhuTask.sendMsg(YzZlUtil.get95().replaceAll(" ", ""), 1000);
				}
			}
			else {
				String message = resultJO.getString("message");
				System.out.println("message==="+message+",语音播报");
	        	//这段代码到现场有了音柱后再打开
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
				Thread.sleep(3000);
	    		YinZhuTask.sendMsg(YzZlUtil.get86().replaceAll(" ", ""), 1500);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检测二检下磅红外光栅状态
	 */
	public static void checkEJXBHWGSState() {
		try {
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1==="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+jdq.isKgl1Open());
				if(jdq.isKgl1Open()) {
		        	
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
			////
	        int waitTime=0;
	        ////
			JiDianQi jdq = JdqZlUtil.getJdq();
			System.out.println("前open1=="+jdq.isKgl1Open());
			Integer bfh = LoadProperties.getBangFangHao();
			while (true) {
				jdq.sendData(WriteZhiLingConst.DU_QU_KAI_GUAN_LIANG_ZHUANG_TAI);
				Thread.sleep(1000);
				System.out.println("后open1==="+jdq.isKgl1Open());
				
				////
				if(jdq.isKgl1Open()) {//要是车辆称重完成一直未下磅，则计算滞留时间
					waitTime++;
					System.out.println("waitTime==="+waitTime);
				}
				////
				
				//if(!jdq.isKgl1Open()) {
				if(!jdq.isKgl1Open()||waitTime>10) {//滞留时间超过10s，订单状态自动变为已完成
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
			    	
		    		JdqZlUtil.closeJdq();
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
