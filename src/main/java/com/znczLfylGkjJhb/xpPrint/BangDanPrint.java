package com.znczLfylGkjJhb.xpPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.json.JSONObject;

import com.znczLfylGkjJhb.entity.*;

/**
 *小票打印机类
 */
public class BangDanPrint implements Printable {
	
	private GuoBangJiLu gbjl;
	private BangDanJiLu bdjl;
	private int dyFlag;//打印标志 1.过磅记录 2.磅单记录
	public static final int GUO_BANG_JI_LU=1;
	public static final int BANG_DAN_JI_LU=2;
	
	public BangDanPrint(GuoBangJiLu gbjl,int dyFlag) {
		this.gbjl=gbjl;
		this.dyFlag=dyFlag;
	}
	
	public BangDanPrint(BangDanJiLu bdjl,int dyFlag) {
		this.bdjl=bdjl;
		this.dyFlag=dyFlag;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//此 Graphics2D 类扩展 Graphics 类，以提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制。
		//它是用于在 Java(tm) 平台上呈现二维形状、文本和图像的基础类。 
		Graphics2D g2 = (Graphics2D) graphics;  
		g2.setColor(Color.black);//设置打印颜色为黑色

		//打印起点坐标  
		double x= pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。  
		double y= pageFormat.getImageableY();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 y坐标。

		//Font.PLAIN： 普通样式常量  	Font.ITALIC 斜体样式常量	Font.BOLD 粗体样式常量。
		Font font = new Font("宋体",Font.BOLD,10); //根据指定名称、样式和磅值大小，创建一个新 Font。
		
		g2.setFont(font);//设置标题打印字体     
		
		float heigth = font.getSize2D();//获取字体的高度  
		float line = 0;
		
		switch (dyFlag) {
		case GUO_BANG_JI_LU:
			//设置小票的标题标题  
			g2.drawString("山东蓝帆健康有限公司",(float)x+25,(float)y+heigth);

			line = 2*heigth; //下一行开始打印的高度
			g2.setFont(new Font("宋体", Font.PLAIN,8));//设置正文字体  
			heigth = font.getSize2D();// 字体高度  

			//设置订单号  
			g2.drawString("订单号:"+gbjl.getDdh(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置车牌号  
			g2.drawString("车牌号:"+gbjl.getCph(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置司机姓名  
			g2.drawString("司机姓名:"+gbjl.getSjxm(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置司机身份证号  
			g2.drawString("司机身份证号:"+gbjl.getSjsfzh(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置过磅重量  
			g2.drawString("过磅重量:"+gbjl.getGbzl() , (float)x+20,(float)y+line);
			line+=heigth+2;
			
			line+=2;
			//设置过磅类型 
			g2.drawString("过磅类型:"+gbjl.getGblxName(),(float)x+20,(float)y+line);
			line+=heigth;

			//设置过磅时间  
			g2.drawString("过磅时间:"+gbjl.getGbsj(), (float)x+20,(float)y+line);
			line+=heigth+100;
			g2.drawString(".", (float)x+20,(float)y+line);
			break;
		case BANG_DAN_JI_LU:
			//设置小票的标题标题  
			g2.drawString("山东蓝帆健康科技过磅单",(float)x+25,(float)y+heigth);

			line = 2*heigth; //下一行开始打印的高度
			g2.setFont(new Font("宋体", Font.PLAIN,8));//设置正文字体  
			heigth = font.getSize2D();// 字体高度  

			//设置订单号  
			g2.drawString("订单号:"+bdjl.getDdh(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置车牌号  
			g2.drawString("车牌号:"+bdjl.getCph(), (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置毛重  
			g2.drawString("毛重:"+bdjl.getMz() , (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置皮重  
			g2.drawString("皮重:"+bdjl.getPz() , (float)x+20,(float)y+line);
			line+=heigth+2;

			//设置净重  
			g2.drawString("净重:"+bdjl.getJz() , (float)x+20,(float)y+line);
			line+=heigth+2;
			
			line+=2;
			//设置时间(第一次过磅时间)
			g2.drawString("时间:"+bdjl.getRq(), (float)x+20,(float)y+line);
			line+=heigth+100;
			g2.drawString(".", (float)x+20,(float)y+line);
			break;
		}
		
		switch (pageIndex) {  
			case 0:  
				return PAGE_EXISTS;//0 
			default:  
				return NO_SUCH_PAGE;//1
		}  
	}

}
