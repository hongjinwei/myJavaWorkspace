package com.peony.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Hello world!
 *
 */
public class App {

	private static final String WORKDIR = "C:\\Users\\BAO\\Desktop\\excel\\";

	public static List<Picture> query(String websitename) {
		List<Picture> ans = new ArrayList<Picture>();
		try {
			Connection conn = ConnectionManager.getInstance().getDBConnection();
			try {
				PreparedStatement ps = conn.prepareStatement("select * from wdyq_picture where website=?");
				ps.setString(1, websitename);

				// PreparedStatement ps =
				// conn.prepareStatement("select * from wdyq_picture where id in (?,?)");
				// ps.setString(1, "0000190F295363073FDD59B282878A83");
				// ps.setString(2, "0000338C6ADCE46AFD35B744C0C2B5E5");
				ResultSet rs = ps.executeQuery();
				System.out.println(websitename + "查询完成！");
				while (rs.next()) {
					String id = rs.getString("id");
					String title = rs.getString("title");
					String url = rs.getString("url");
					String filename = rs.getString("filename");
					String type = rs.getString("type");
					String website = rs.getString("website");
					String keyword = rs.getString("keyword");
					String width = rs.getString("width");
					String height = rs.getString("height");
					String bit = "0";
					Picture pic = new Picture();
					pic.setId(id);
					pic.setTitle(title);
					pic.setUrl(url);
					pic.setFilename(filename);
					pic.setType(type);
					pic.setWebsite(website);
					pic.setKeyword(keyword);
					pic.setBit(bit);
					pic.setHeight(height);
					pic.setWidth(width);
					ans.add(pic);
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	public static void write(String websitename) {
		try {
			File file = new File(WORKDIR + websitename + ".xlsx");
			if (!file.exists()) {
				System.out.println("创建文件" + websitename + ".xlsx");
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("data");
			Excel.initExcel(sheet);
			List<Picture> list = query(websitename);
			int p = 1;
			for (Picture pic : list) {
				Excel.createDataRow(sheet, pic, p);
				p++;
			}
			FileOutputStream os = new FileOutputStream(WORKDIR + websitename + ".xlsx");
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConnectionManager.getInstance().init();
		// write("橡树摄影");
		String websitename = "蜂鸟网";
		String siteKeyword = "人像";
		String fileSrc = WORKDIR + websitename  + "-" + siteKeyword + ".xlsx";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("data");
		int index = 1;
		Excel.initExcel(sheet);
		try {
			Connection conn = ConnectionManager.getInstance().getDBConnection();
			try {
				PreparedStatement ps = conn.prepareStatement("select * from wdyq_picture where website=? and keyword = ?");
				ps.setString(1, websitename);
				ps.setString(2, siteKeyword);
				ResultSet rs = ps.executeQuery();
				System.out.println(websitename + "查询完成！");
				Picture pic = new Picture();
				while (rs.next()) {
					String id = rs.getString("id");
					String title = rs.getString("title");
					String url = rs.getString("url");
					String filename = rs.getString("filename");
					String type = rs.getString("type");
					String website = rs.getString("website");
					String keyword = rs.getString("keyword");
					String width = rs.getString("width");
					String height = rs.getString("height");
					String bit = "0";

					pic.setId(id);
					pic.setTitle(title);
					pic.setUrl(url);
					pic.setFilename(filename);
					pic.setType(type);
					pic.setWebsite(website);
					pic.setKeyword(keyword);
					pic.setBit(bit);
					pic.setHeight(height);
					pic.setWidth(width);
					Excel.createDataRow(sheet, pic, index);
					
					System.out.println(index);
					index++;
					
					if(index%100 == 0) {
						FileOutputStream os = new FileOutputStream(fileSrc);
						workbook.write(os);
						os.flush();
						os.close();
						InputStream in = new FileInputStream(fileSrc);
						workbook = new HSSFWorkbook(in);
						sheet = workbook.getSheet("data");
					}
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
			FileOutputStream os = new FileOutputStream(fileSrc);
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done!");

	}
}
