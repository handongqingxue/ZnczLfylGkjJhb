package com.znczLfylGkjJhb.cpsbsxt;

import java.io.UnsupportedEncodingException;

public class HCNetSDKPath {
	public static String DLL_PATH;
	  static {
		  
		  String path2 = HCNetSDKPath.class.getResource("/").getPath();
		  String replaceAll = path2.replaceAll("%20", " ");
		  String substring = replaceAll.substring(1);
	    String path =substring.replace("/",
	        "\\");
	    try {
	      DLL_PATH = java.net.URLDecoder.decode(path, "utf-8");
	      System.out.println("DLL_PATH="+DLL_PATH);
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  
	  public static void main(String[] args) {
		
	}
}
