package com.dgw.bimgcollection.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;


/**
 * @author DGW-PC
 * @data   2018年7月21日下午3:24:14
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class UserConfig {
	
	/*
	 * 用户名
	 */
	private static String usernmae;
	/**
	 * 密码
	 */
	private static String passwd;
	
	/*
	 * 文件路径
	 */
	private static String path=System.getProperty("user.dir");
			
	/**
	 * 读取配置文件数据
	 */
	private static void Builder() {
		Properties pro = new Properties();
		try {
			FileInputStream fis = new FileInputStream(path+File.separator+"user.ini");
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			pro.load(isr);
			usernmae= pro.getProperty("用户名");
			passwd = pro.getProperty("密码");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造配置文件数据
	 */
	private static void init() {
		Properties pro = new Properties();
		pro.setProperty("用户名", "sss");
		pro.setProperty("密码", "kkkk");
		try {
		FileOutputStream os = new FileOutputStream(path+File.separator+"user.ini");
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			pro.store(osw, "必须写登录淘宝的用户名密码");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getUsernmae() {
		Builder();
		return usernmae;
	}

	public static String getPasswd() {
		Builder();
		return passwd;
	}

	public static void main(String[] args) {
		init();
	}

}
