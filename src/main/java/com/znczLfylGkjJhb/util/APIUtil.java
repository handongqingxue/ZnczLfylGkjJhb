package com.znczLfylGkjJhb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.*;

import org.json.JSONObject;

import com.znczLfylGkjJhb.cpsbsxt.Car;
import com.znczLfylGkjJhb.entity.*;
import com.znczLfylGkjJhb.task.*;
import com.znczLfylGkjJhb.xpPrint.*;
import com.znczLfylGkjJhb.yz.*;

public class APIUtil {
	
	public static final String SERVICE_URL="http://10.10.99.20:8080/ZnczLfylJhb/gkj/";
	//public static final String SERVICE_URL="http://localhost:8080/ZnczLfylJhb/gkj/";
	public static final int YI_HAO_BANG_FANG=1;
	public static final int ER_HAO_BANG_FANG=2;

	//https://www.cnblogs.com/aeolian/p/7746158.html
	//https://www.cnblogs.com/bobc/p/8809761.html
	public static JSONObject doHttp(String method, Map<String, Object> params) throws IOException {
		// 构建请求参数  
        StringBuffer paramsSB = new StringBuffer();
		if (params != null) {  
            for (Entry<String, Object> e : params.entrySet()) {
            	paramsSB.append(e.getKey());  
            	paramsSB.append("=");  
            	paramsSB.append(e.getValue());  
            	paramsSB.append("&");  
            }  
            paramsSB.substring(0, paramsSB.length() - 1);  
        }  
		
		StringBuffer sbf = new StringBuffer(); 
		String strRead = null; 
		URL url = new URL(SERVICE_URL+method);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");//请求post方式
		connection.setDoInput(true); 
		connection.setDoOutput(true); 
		//header内的的参数在这里set    
		//connection.setRequestProperty("key", "value");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect(); 
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
		//OutputStream writer = connection.getOutputStream(); 
		writer.write(paramsSB.toString());
		writer.flush();
		InputStream is = connection.getInputStream(); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((strRead = reader.readLine()) != null) {
			sbf.append(strRead); 
			sbf.append("\r\n"); 
		}
		reader.close();
		
		connection.disconnect();
		String result = sbf.toString();
		System.out.println("result==="+result);
		JSONObject resultJO = new JSONObject(result);
		return resultJO;
	}
	
	public static void updateCPSBDDXX(Car car, int bfh) {
		JSONObject resultJO=getPDZDingDanByCph(car.getsLicense());
		if(resultJO!=null) {
			if("ok".equals(resultJO.getString("status"))) {//根据车牌号查询排队中的订单，若存在执行下面方法
				JSONObject dingDanJO = resultJO.getJSONObject("dingDan");
				String ddztMc = dingDanJO.getString("ddztMc");
				
            	switch (bfh) {
				case APIUtil.YI_HAO_BANG_FANG:
					if(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT.equals(ddztMc)) {//判断是否是一检状态（可能之前一检过磅失败了，订单复位回去需要重新一检）
						BangFang1Util.updateYJCPSBDDXX(car);
					}
					else {
                    	BangFang1Util.updateEJCPSBDDXX(car);
					}
					break;
				case APIUtil.ER_HAO_BANG_FANG:
					if(DingDanZhuangTai.YI_JIAN_PAI_DUI_ZHONG_TEXT.equals(ddztMc)) {
						System.out.println("重复识别了111。。。。。。。。。。");
						JSONObject cpsbJO = APIUtil.checkIfCPSB(car.getsLicense());//若在旧磅房过磅，避免出现下磅后重复识别现象，得验证下是否刚下磅
						System.out.println("cpsbJO:status识别了==="+cpsbJO.get("status"));
						if("ok".equals(cpsbJO.get("status")))
							BangFang2Util.updateYJCPSBDDXX(car);
					}
					else {
						System.out.println("重复识别了222。。。。。。。。。。");
						JSONObject cpsbJO = APIUtil.checkIfCPSB(car.getsLicense());
						System.out.println("cpsbJO:status识别了==="+cpsbJO.get("status"));
						if("ok".equals(cpsbJO.get("status")))
							BangFang2Util.updateEJCPSBDDXX(car);
					}
					break;
				}
            }
			else {//根据车牌号查询排队中的订单，若不存在执行下面方法
            	switch (bfh) {
				case APIUtil.YI_HAO_BANG_FANG:
					BangFang1Util.updateYJCPSBDDXX(car);//若订单不存在，肯定需要先一检
					break;
				case APIUtil.ER_HAO_BANG_FANG:
					System.out.println("重复识别了333。。。。。。。。。。");
					JSONObject cpsbJO = APIUtil.checkIfCPSB(car.getsLicense());
					System.out.println("cpsbJO:status识别了==="+cpsbJO.get("status"));
					if("ok".equals(cpsbJO.get("status")))
						BangFang2Util.updateYJCPSBDDXX(car);
					break;
				}
			}
		}
	}

	public static JSONObject addDingDan(String cph) {
		// TODO Auto-generated method stub
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("cph", cph);  
	        resultJO = doHttp("addDingDan",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}

	public static JSONObject getDingDan(String cph, String ddztMc) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("cph", cph);  
	        parames.put("ddztMc", ddztMc);
	        resultJO = doHttp("getDingDan",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}

	public static JSONObject getDingDanByZt(Integer yjbfh,Integer ejbfh,String ddztMc, Integer yjzt, Integer ejzt) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("yjbfh", yjbfh);
	        parames.put("ejbfh", ejbfh);
	        parames.put("ddztMc", ddztMc);
	        parames.put("yjzt", yjzt);
	        parames.put("ejzt", ejzt);
	        resultJO = doHttp("getDingDanByZt",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject getPDZDingDanByCph(String cph) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("cph", cph);  
	        resultJO = doHttp("getPDZDingDanByCph",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject editDingDan(DingDan dd) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("id", dd.getId());
	        parames.put("ddztMc", dd.getDdztMc());
	        parames.put("yjbfh", dd.getYjbfh());
	        parames.put("ejbfh", dd.getEjbfh());
	        resultJO = doHttp("editDingDan",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject editDingDanByZt(DingDan dd) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("yjbfh", dd.getYjbfh());
	        parames.put("ejbfh", dd.getEjbfh());
	        parames.put("ddztMc", dd.getDdztMc());
	        parames.put("yjzt", dd.getYjzt());
	        parames.put("ejzt", dd.getEjzt());
	        parames.put("xddztMc", dd.getXddztMc());
	        parames.put("xyjzt", dd.getXyjzt());
	        parames.put("xejzt", dd.getXejzt());
	        if(dd.getLxlx()>0)
	        	parames.put("lxlx", dd.getLxlx());
	        resultJO = doHttp("editDingDanByZt",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject newBangDanJiLu(Float yjzl, Integer ddId) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        if(yjzl!=null)
	        	parames.put("yjzl", yjzl);
	        if(ddId!=null)
	        	parames.put("ddId", ddId);  
	        resultJO = doHttp("newBangDanJiLu",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject editBangDanJiLu(Integer id, Float ejzl, Float mz, Float pz, Float jz) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("id", id);
	        if(ejzl!=null)
	        	parames.put("ejzl", ejzl);
	        if(mz!=null)
	        	parames.put("mz", mz);
	        if(pz!=null)
	        	parames.put("pz", pz);
	        if(jz!=null)
	        	parames.put("jz", jz);
	        resultJO = doHttp("editBangDanJiLu",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject selectBangDanJiLuByDdId(Integer ddId) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("ddId", ddId);
	        resultJO = doHttp("selectBangDanJiLuByDdId",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject newGuoBangJiLu(GuoBangJiLu gbjl) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("gbzl", gbjl.getGbzl());
	        parames.put("gblx", gbjl.getGblx());
	        parames.put("ddId", gbjl.getDdId());
	        resultJO = doHttp("newGuoBangJiLu",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject checkDingDanIfExistByZt(Integer checkBfh,String checkDdztMc) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
			parames.put("checkBfh", checkBfh);
			parames.put("checkDdztMc", checkDdztMc);
			resultJO = doHttp("checkDingDanIfExistByZt",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	/**
	 * 根据车牌号验证车辆是否可以上磅
	 * @param cph
	 * @return
	 */
	public static JSONObject checkIfCPSB(String cph) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
			parames.put("cph", cph);
			resultJO = doHttp("checkIfCPSB",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	/**
	 * 更新一检称重订单信息
	 */
	public static void updateYJCZDDXX() {
		/*
		checkYJSBHWGSState();
		checkYJSBHXBHWGSState();
		yiJianChengZhongZhong();
		checkYJXBHWGSState();
		checkIfYJXBYwc();
		*/
	}
	
	/**
	 * 打印过磅记录小票(现在改成订单所有过磅都完成后一起打印，这个方法暂时不用了)
	 * @param gblx
	 */
	public static void printGbjl(int gblx) {
		System.out.println("打印过磅记录小票");
		JSONObject resultJO = null;
		Integer bfh = LoadProperties.getBangFangHao();
		switch (gblx) {
		case GuoBangJiLu.RU_CHANG_GUO_BANG:
			System.out.println("查询订单状态为一检上磅，一检状态为待下磅的订单");
			resultJO=getDingDanByZt(bfh,0,DingDanZhuangTai.YI_JIAN_SHANG_BANG_TEXT,DingDan.DAI_XIA_BANG,DingDan.DAI_SHANG_BANG);
			break;
		case GuoBangJiLu.CHU_CHANG_GUO_BANG:
			System.out.println("查询订单状态为二检上磅，二检状态为待下磅的订单");
			resultJO=getDingDanByZt(0,bfh,DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT,DingDan.YI_WAN_CHENG,DingDan.DAI_XIA_BANG);
			break;
		}
		
		String status = resultJO.getString("status");
		if("ok".equals(status)) {
			JSONObject dingDanJO=resultJO.getJSONObject("dingDan");
			int ddId = dingDanJO.getInt("id");
			JSONObject gbjlResultJO=selectGuoBangJiLuByDdId(ddId,gblx);
			
			GuoBangJiLu gbjl=(GuoBangJiLu)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(gbjlResultJO.get("gbjl").toString()), GuoBangJiLu.class);
			
	        BangDanPrint bdp=new BangDanPrint(gbjl,BangDanPrint.GUO_BANG_JI_LU);
	        XPPrinter xpp=new XPPrinter(bdp);
	        xpp.printer();
		}
		else {
        	System.out.println("message==="+resultJO.getString("message"));
        	System.out.println("音柱播报：找不到相关过磅记录");
		}
	}
	
	/**
	 * 打印磅单记录小票
	 */
	public static void printBdjl() {
		System.out.println("打印磅单记录小票");
		Integer bfh = LoadProperties.getBangFangHao();
		System.out.println("查询订单状态为二检上磅，二检状态为待下磅的订单");
		JSONObject resultJO=getDingDanByZt(0,bfh,DingDanZhuangTai.ER_JIAN_SHANG_BANG_TEXT,DingDan.YI_WAN_CHENG,DingDan.DAI_XIA_BANG);
		String status = resultJO.getString("status");
		if("ok".equals(status)) {
			System.out.println("status===ok");
			JSONObject dingDanJO=resultJO.getJSONObject("dingDan");
			int ddId = dingDanJO.getInt("id");
			JSONObject bdjlResultJO=selectBangDanJiLuByDdId(ddId);

			BangDanJiLu bdjl=(BangDanJiLu)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(bdjlResultJO.get("bdjl").toString()), BangDanJiLu.class);
			
	        BangDanPrint bdp=new BangDanPrint(bdjl,BangDanPrint.BANG_DAN_JI_LU);
	        XPPrinter xpp=new XPPrinter(bdp);
	        xpp.printer();
		}
		else {
        	System.out.println("message==="+resultJO.getString("message"));
        	System.out.println("音柱播报：找不到相关磅单记录");
		}
	}
	
	public static JSONObject selectGuoBangJiLuByDdId(Integer ddId, Integer gblx) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("ddId", ddId);
	        parames.put("gblx", gblx);
	        resultJO = doHttp("selectGuoBangJiLuByDdId",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}

	public static JSONObject getCphByBfhDdzt(Integer bfh, String ddztMc) {
		// TODO Auto-generated method stub
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("bfh", bfh);  
	        parames.put("ddztMc", ddztMc);
	        resultJO = doHttp("getCphByBfhDdzt",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject updateCheLiangWcddcsByCph(String cph) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("cph", cph);
	        resultJO = doHttp("updateCheLiangWcddcsByCph",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}

	public static JSONObject addRglrCphJiLu(String cph, Integer ddId) {
		JSONObject resultJO = null;
		try {
			Map parames = new HashMap<String, String>();
	        parames.put("cph", cph);
	        parames.put("ddId", ddId);
	        resultJO = doHttp("addRglrCphJiLu",parames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static void playWeight(float djczl) {
		YinZhuTask.sendMsg(YzZlUtil.get98().replaceAll(" ", ""), 1500);
		String djczlStr = String.valueOf((int)djczl);
		System.out.println("djczlStr==="+djczlStr);
		for (int i = 0; i < djczlStr.length(); i++) {
			char ch = djczlStr.charAt(i);
			
			String chStr = String.valueOf(ch);
			System.out.println("chStr==="+chStr);
			int chi = Integer.parseInt(chStr);
			
			if(chi==0)
				chi=36;
			System.out.println("chi==="+chi);
    		YinZhuTask.sendMsg(YzZlUtil.getByDuanHao(chi).replaceAll(" ", ""), 800);
		}
	}
	
	public static void main(String[] args) {
		Car car = new Car();
    	car.setsLicense(" 鲁B9008");
		APIUtil.updateCPSBDDXX(car,1);
	}
}
