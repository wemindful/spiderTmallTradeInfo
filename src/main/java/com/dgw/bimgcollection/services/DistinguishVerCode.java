package com.dgw.bimgcollection.services;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.dgw.bimgcollection.utils.TesseractUtil;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

/**
 * @author DGW-PC
 * @data 2018年7月22日下午12:43:51
 *       <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class DistinguishVerCode {

   private static final Logger logger = LoggerFactory.getLogger(DistinguishVerCode.class);

	public String getProbableCode(BufferedImage buffimg) {
		Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
		String ocr = "";
		try {
			 logger.info("======>>写出获取到验证码");
			ImageIO.write(buffimg, "jpg", new FileOutputStream("z://test.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 //BufferedImage binaryimage=ImageHelper.convertImageToBinary(buffimg);
		 //ImageIO.read(new FileInputStream("z://test.jpg")
		// ocr = tesseract.doOCR(binaryimage);
		logger.info("当前识别验证码为"+ocr);
		return ocr;
	}

}
