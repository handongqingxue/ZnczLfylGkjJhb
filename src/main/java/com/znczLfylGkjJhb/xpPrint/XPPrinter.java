package com.znczLfylGkjJhb.xpPrint;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.json.JSONObject;

import com.znczLfylGkjJhb.entity.DingDan;
import com.znczLfylGkjJhb.entity.DingDanZhuangTai;
import com.znczLfylGkjJhb.entity.GuoBangJiLu;
import com.znczLfylGkjJhb.util.APIUtil;

public class XPPrinter {

	private BangDanPrint bangDanPrint;

	public XPPrinter() {
		
	}
	
	public XPPrinter(BangDanPrint bangDanPrint){
		this.bangDanPrint=bangDanPrint;
	}

	public void printer() {  
        try {  
            //Book 类提供文档的表示形式，该文档的页面可以使用不同的页面格式和页面 painter
            Book book = new Book(); //要打印的文档
            
            //PageFormat类描述要打印的页面大小和方向  
            PageFormat pf = new PageFormat();  //初始化一个页面打印对象
            pf.setOrientation(PageFormat.PORTRAIT); //设置页面打印方向，从上往下，从左往右
          
            //设置打印纸页面信息。通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
            Paper paper = new Paper(); 
            paper.setSize(158,30000);// 纸张大小
            paper.setImageableArea(0,0,158,30000);// A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72  
            pf.setPaper(paper);  
  
            book.append(bangDanPrint,pf);
           
            PrinterJob job = PrinterJob.getPrinterJob();   //获取打印服务对象  
            
            job.setPageable(book);  //设置打印类  
  
            job.print(); //开始打印 
        } catch (PrinterException e) {  
            e.printStackTrace();  
        }  
    }  
	
	public void findPrinter(String printerName) {
	    HashAttributeSet hash = new HashAttributeSet();    // 存储打印机属性的集合
	    hash.add(new PrinterName(printerName, null));    // 添加打印机名称属性，这个名称是你在配置打印机硬件时指定的名称
	    PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hash);    // 查找可用的打印机服务并不指定自定的打印格式
	    if (pss.length == 0) {
	     System.out.println("没有这个打印机");
	    } else {
	     System.out.println("找到打印机");
	    }
	}
    
    public static void main(String[] args) {
    	APIUtil.printGbjl(GuoBangJiLu.RU_CHANG_GUO_BANG);
    	/*
    	XPPrinter xpp=new XPPrinter();
    	xpp.findPrinter("DL-801PN");
    	xpp.findPrinter("DL-801P(NEW)");
        */
	}
}
