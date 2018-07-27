package com.dgw.bimgcollection;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.dgw.bimgcollection.config.Config;
import com.dgw.bimgcollection.config.UserConfig;
import com.dgw.bimgcollection.domain.ShopDTO;
import com.dgw.bimgcollection.services.GetBusinessImgService;
import com.xuxueli.poi.excel.ExcelImportUtil;

/**
 * @author DGW-PC
 * @data   2018年7月25日下午10:59:01
 * <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 *  excel数据读取类
 */
public class AppServices {
	
	private  List<String> urlList=new ArrayList<>();
	
	
	public void start() {
		System.out.println(Config.BASEPATH);
		fileReaderToUrl();
		// 设置手动自动验证开关
		GetBusinessImgService.switchOnVerCode = Boolean.valueOf(UserConfig.getVerOption());
		GetBusinessImgService imgService = new GetBusinessImgService();
		
		for (int i = 0; i < urlList.size(); i++) {
			String temppath=Config.BASEPATH+File.separator+"天猫工商数据"+File.separator+i+".png";
			while (true) {
				boolean b = imgService.GenerateBImage(temppath, urlList.get(i));
				if (b) {
					break;
				}
			}
		}
		System.out.println("处理完成！");
		try {
			Thread.sleep(3000);
			Desktop.getDesktop().open(new File(Config.BASEPATH));
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
		/*while (true) {
			boolean b = imgService.GenerateBImage("Z:\\公司.png", Config.SRCIMGURI);
			if (b) {
				System.exit(0);
			}
		}*/
		}
	
	/**
	 *  界面化读取excel数据
	 *  void
	 */
	private  void fileReaderToUrl() {
		JFileChooser fileChooser = new JFileChooser(Config.BASEPATH) {
			private static final long serialVersionUID = 1L;
			public boolean accept(File f) {
				return f.getName().endsWith(".xls") || f.isDirectory();
			}
			
		};
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("选择你需要采集excel文档");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			List<Object> list = ExcelImportUtil.importExcel(ShopDTO.class, path);
			if(list.isEmpty()) {
				try {
					throw new FileNotFoundException("excel 不能为空");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			for (Object object : list) {
				if(object instanceof ShopDTO) {
					ShopDTO sop=(ShopDTO) object;
					urlList.add(sop.getShopName());
				}
			}
		}
	}
}
