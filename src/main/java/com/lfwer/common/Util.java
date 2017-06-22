package com.lfwer.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Util {
 
	private static Map<Integer, String> carProvinceMap= null;
	static{
		carProvinceMap = new HashMap<Integer, String>();
		carProvinceMap.put(1, "京");
		carProvinceMap.put(2, "沪");
		carProvinceMap.put(3, "浙");
		carProvinceMap.put(4, "苏");
		carProvinceMap.put(5, "粤");
		carProvinceMap.put(6, "鲁");
		carProvinceMap.put(7, "晋");
		carProvinceMap.put(8, "冀");
		carProvinceMap.put(9, "豫");
		carProvinceMap.put(10, "川");
		carProvinceMap.put(11, "渝");
		carProvinceMap.put(12, "辽");
		carProvinceMap.put(13, "吉");
		carProvinceMap.put(14, "黑");
		carProvinceMap.put(15, "皖");
		carProvinceMap.put(16, "鄂");
		carProvinceMap.put(17, "湘");
		carProvinceMap.put(18, "赣");
		carProvinceMap.put(19, "闽");
		carProvinceMap.put(20, "陕");
		carProvinceMap.put(21, "甘");
		carProvinceMap.put(22, "宁");
		carProvinceMap.put(23, "蒙");
		carProvinceMap.put(24, "津");
		carProvinceMap.put(25, "贵");
		carProvinceMap.put(26, "云");
		carProvinceMap.put(27, "桂");
		carProvinceMap.put(28, "琼");
		carProvinceMap.put(29, "青");
		carProvinceMap.put(30, "新");
		carProvinceMap.put(31, "藏");
	}
	
	public static Map<Integer, String>  getCarProvinceMap(){
		return carProvinceMap;
	}
		
	
	public static void main(String[] args) throws Exception {
//		  BufferedReader read = new BufferedReader(new FileReader("D:\\Workspaces\\eclipse\\pinche\\src\\main\\webapp\\pages\\register.jsp"));
//		  String line = "";
//	        while((line=read.readLine())!=null){
//	            if(!line.equals("")){
//	                System.out.println(line);
//	            }
//	        }
//	        read.close();
		Map<Integer, String> carProvinceMap = Util.getCarProvinceMap();
		for (Integer i : carProvinceMap.keySet()) {
			System.out.println("<button class=\"btn btn-default chooseCarProvince\" value=\""+i+"\">"+carProvinceMap.get(i)+"</button>");
		}
	}
	 
}
