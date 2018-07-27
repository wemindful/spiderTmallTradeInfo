package com.dgw.bimgcollection.config;

import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;

/**
 * @author DGW-PC
 * @data   2018年7月22日下午4:36:31
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 * @description 
 */
public class LogConfig {
	void test() {
		 LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
		 java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
	}
}
