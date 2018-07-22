package com.dgw.bimgcollection.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dgw.bimgcollection.config.Config;

public class GetBusinessImgService  {
	
	
	  static void getMessage() throws IOException {
		  Document doc = Jsoup.connect(Config.SRCIMGURI)
				  .userAgent("Mozilla")
				  .timeout(3000)
				  .get();
		  System.out.println(doc);
		  Element body = doc.body();
		  Elements codeimg = body.select("#J_CheckCode");
		 // String attr = codeimg.attr("src");
	  }
	
	  public static void main(String[] args) throws IOException {
		  getMessage();
	  }

	@SuppressWarnings("unused")
	private static void m2() throws IOException {
		File input = new File(Config.HTMLSRC);
		  Document doc = Jsoup.parse(input, "UTF-8", "");
		  Element body = doc.body();
		  Elements codeimg = body.select("#J_CheckCode");
		  String attr = codeimg.attr("src");
		  System.out.println(attr);
	}
	 
}

