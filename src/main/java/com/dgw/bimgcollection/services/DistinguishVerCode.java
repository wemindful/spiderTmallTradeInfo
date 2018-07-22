package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;

import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;

import com.dgw.bimgcollection.utils.TesseractUtil;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @author DGW-PC
 * @data 2018年7月22日下午12:43:51
 *       <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class DistinguishVerCode {

   private static final Logger logger = LoggerFactory.getLogger(DistinguishVerCode.class);

	public String getProbableCode(BufferedImage buffimg) {
		Tesseract tesseract = TesseractUtil.initCurrTesseract("eng");
		String ocr = "";
		try {
			ocr = tesseract.doOCR(buffimg);
		} catch (TesseractException e) {
			e.printStackTrace();
			logger.info("识别失败");
		}
		return ocr;
	}

}
