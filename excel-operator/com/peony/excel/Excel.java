package com.peony.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Excel {

	public Excel() {
		// TODO Auto-generated constructor stub
	}

	public static void createDataRow(HSSFSheet sheet, Picture pic, int index) {
		String id = pic.getId();
		String title = pic.getTitle();
		String url = pic.getUrl();
		String filename = pic.getFilename();
		String type = pic.getType();
		String website = pic.getWebsite();
		String keyword = pic.getKeyword();
		String bit = pic.getBit();
		String height = pic.getHeight();
		String width = pic.getWidth();
		String remark = "";
		String rank = "";

		HSSFRow row = sheet.createRow(index);

		int p = 0;
		HSSFCell cell = row.createCell(p++);
		cell.setCellValue(id);
		cell = row.createCell(p++);
		cell.setCellValue(title);
		cell = row.createCell(p++);
		cell.setCellValue(url);
		cell = row.createCell(p++);
		cell.setCellValue(filename);
		cell = row.createCell(p++);
		cell.setCellValue(type);
		cell = row.createCell(p++);
		cell.setCellValue(website);
		cell = row.createCell(p++);
		cell.setCellValue(keyword);
		cell = row.createCell(p++);
		cell.setCellValue(width);
		cell = row.createCell(p++);
		cell.setCellValue(height);
	}

	public static void initExcel(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		int p = 0;
		HSSFCell cell = row.createCell(p++);
		cell.setCellValue("id");
		cell = row.createCell(p++);
		cell.setCellValue("title");
		cell = row.createCell(p++);
		cell.setCellValue("url");
		cell = row.createCell(p++);
		cell.setCellValue("filename");
		cell = row.createCell(p++);
		cell.setCellValue("type");
		cell = row.createCell(p++);
		cell.setCellValue("website");
		cell = row.createCell(p++);
		cell.setCellValue("keyword");
		cell = row.createCell(p++);
		cell.setCellValue("width");
		cell = row.createCell(p++);
		cell.setCellValue("height");
		cell = row.createCell(p++);
		cell.setCellValue("rank");
		cell = row.createCell(p++);
		cell.setCellValue("remark");
		cell = row.createCell(p++);
		cell.setCellValue("bit");
	}

}
