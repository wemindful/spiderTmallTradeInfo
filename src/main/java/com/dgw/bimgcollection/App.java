package com.dgw.bimgcollection;

import com.dgw.bimgcollection.config.Config;
import com.dgw.bimgcollection.services.GetBusinessImgService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       GetBusinessImgService imgService = new GetBusinessImgService();
       
       	while(true) {
       		boolean b = imgService.GenerateBImage("Z:\\222.png", Config.SRCIMGURI);
       		if(b) {
       			break;
       		}
       	}
       
    }
}
