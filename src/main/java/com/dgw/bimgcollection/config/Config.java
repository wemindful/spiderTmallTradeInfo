package com.dgw.bimgcollection.config;

import java.io.File;

/**
 * @author DGW-PC
 * @data 2018年7月20日 配置信息
 */
public interface Config {
	
	/**
	 * 天猫验证码获取图片 每一个商品的地址是固定的 网址
	 */
	String SRCIMGURI = "https://zhaoshang.tmall.com/maintaininfo/liangzhao.htm?spm=a220o.1000855.1997427721.5.6174cbddUnBdbY&xid=1183a425054ac0d2943d9c02db4668db";
	
	/**
	 * 天猫验证码/index.html
	 */
	String HTMLSRC="C:/Users/DGW-PC/Desktop/天猫验证码/index.html";
	
	/**
	 * 淘宝天猫统一登录办法 https://login.taobao.com/
	 */
	String TAOBAOURL="https://login.taobao.com/";
	
	
	/**
	 * JLInF 引擎执行Js的延迟时间 15000
	 */
	Integer POSTPONE=15000;
	
	/**
	 * 工商图片测试地址 
	 */
	String gongshang="https://zhaoshang.tmall.com/maintaininfo/liangzhao.htm?spm=a1z10.3741-b-s.1997427721.5.4aaa2d8LsuX7E&xid=92e1adf6003bc453eae5091e911951ad";
	
	/**
	 * 
	 */
	String FILEPATH="C:\\Users\\DGW-PC\\Desktop\\天猫验证码\\验证码3";
	
	/**
	 * 验证码样本地址下载路径
	 */
	String IMGVERSIONURL = "http://pin.aliyun.com/get_img?sessionid=ALIcc57f102942c952f08fcba941daadbef&amp;identity=zhaoshang_sellermanager%22%20id=%22J_CheckCode%22%20src=%22//pin.aliyun.com/get_img?sessionid=ALIcc57f102942c952f08fcba941daadbef&amp;identity=zhaoshang_sellermanager";
	
	  /**
     * 启动基础目录
     */
    public final static String BASEPATH=System.getProperty("user.dir");


    /**
     * Sets path to <code>tessdata</code>
     */
    public final static String TESSDATA = System.getProperty("user.dir")+File.separator +"TESSDATA";//基础目录

}
