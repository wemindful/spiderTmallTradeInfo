package com.dgw.bimgcollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dgw.bimgcollection.domain.ShopDTO;
import com.xuxueli.poi.excel.ExcelImportUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author DGW-PC
 * @data 2018年7月25日下午6:35:08 <a href="https://www.cnblogs.com/dgwblog/">我的博客</a>
 */
public class TestExcel {

	private File file = new File("z:/testread2.xls");

	@Before
	public void prepareData() {

	}

	@Test
	public void testCreateExcel() {
		List<Object> excel = ExcelImportUtil.importExcel(ShopDTO.class,file );
		for (Object object : excel) {
			System.out.println(object);
		}
		/*try {
			//1:创建workbook
			Workbook workbook = Workbook.getWorkbook(file);
			//2:获取第一个工作表sheet
			Sheet sheet = workbook.getSheet(0);
			 //3:读取数据
			System.out.println(sheet.getColumns());
			System.out.println(sheet.getRows());
			//4.自己注意行列关系
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					System.out.println(cell.getContents());
				}
			}
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}*/
	}

	private void test2() {
		try {
			// 创建xls文件
			file.createNewFile();
			// 2:创建工作簿
			WritableWorkbook workbook = Workbook.createWorkbook(file);

			// 3:创建sheet,设置第二三四..个sheet，依次类推即可
			WritableSheet sheet = workbook.createSheet("测试", 0);
			// 4：设置titles
			String[] titles = { "编号", "账号"};
			// 5:给第一行设置列名
			for (int i = 0; i < titles.length; i++) {
				sheet.addCell(new Label(i, 0, titles[i]));
			}
			sheet.setHeader("aa", "cc", "cc");
			// 6：模拟数据库导入数据 注意起始行为1
			for (int i = 1; i < 100; i++) {
				//添加编号
				sheet.addCell(new Label(0, i, new String("编号"+i)));
				//添加密码
				sheet.addCell(new Label(1, i, new String("编号"+i)));
			}
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	@After
	public void relareData() {

	}

}
