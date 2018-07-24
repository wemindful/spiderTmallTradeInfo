package com.dgw.bimgcollection.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.dgw.bimgcollection.utils.TesseractUtil;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

/**
 * @author DGW-PC
 * @data   2018年7月23日上午10:41:15
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 * 图片相关的处理办法
 */
public class TestImageProdure {

	@Test
	public void test() throws Exception, IOException {
		BufferedImage img=ImageIO.read(new FileInputStream(new File("Z:\\25.jpg")));
         //二值化
		BufferedImage binaryimage=ImageHelper.convertImageToBinary(img);
		//灰度化
        BufferedImage Grayimage = ImageHelper.convertImageToGrayscale(binaryimage);
		//纯色化
        //BufferedImage invertImageColor = ImageHelper.invertImageColor(img);
		//定比例缩放
        //BufferedImage sizeimg = ImageHelper.getScaledInstance(Grayimage, 600, 2000);
        ImageIO.write(Grayimage, "jpg", new FileOutputStream(new File("z://img2222.jpg")));
        Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
        String str=tesseract.doOCR(Grayimage);
        System.out.println(str);
	}
	
}
