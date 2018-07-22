package com.dgw.bimgcollection.utils;

import java.io.File;

import com.dgw.bimgcollection.config.Config;

import net.sourceforge.tess4j.Tesseract;
/**
 * @Description:  单例工具类
 * @Param:
 * @return:
 * @Author: Dai.GuoWei
 * @Date: 2018/6/5
 */
public class TesseractUtil {

   private volatile static Tesseract singletonTesseract;

   public final static String tessdata = System.getProperty("user.dir")+File.separator+"TESSDATA";//基础目录

   public static void main(String[] args) {
	System.out.println(Config.TESSDATA);
   }
   
   private TesseractUtil(){}

   /**
    * 拿到单例实例 
    * @return Tesseract
    */
    private static Tesseract getInstance() {
        if (singletonTesseract == null) {
            synchronized (TesseractUtil.class) {
                if (singletonTesseract == null) {
                    singletonTesseract = new Tesseract();
                }
            }
        }
        return singletonTesseract;
    }

    /**
     * 取得适合当前的tesseract
     * @param languageName 语言名字 eng
     * @return Tesseract 
     */
    public static Tesseract initCurrTesseract(String languageName){
        Tesseract instance = getInstance();
        instance.setLanguage(languageName);
        instance.setDatapath(Config.TESSDATA);
        return instance;
    }


}
