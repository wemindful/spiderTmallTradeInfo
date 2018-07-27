package com.dgw.bimgcollection.services;

import java.awt.image.BufferedImage;

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

	public String getProbableCode(BufferedImage buffimg){
		Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
		String ocr = "";
		BufferedImage image;
		image=ImageHelper.convertImageToGrayscale(buffimg);
		image= ImageHelper.convertImageToBinary(image);
		try {
			ocr= tesseract.doOCR(image);
		} catch (TesseractException e) {
			logger.info("========>>识别引擎出现异常！");
			e.printStackTrace();
		}
		return ocr;
	}

}
