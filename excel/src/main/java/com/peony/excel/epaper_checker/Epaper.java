package com.peony.excel.epaper_checker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.peony.excel.ConnectionManager;
import com.peony.util.StringUtils;
import com.peony.util.http.HttpQuery;

public class Epaper {

	public Epaper() {
		// TODO Auto-generated constructor stub
	}

	public static void find() {
		List<String[]> list = new ArrayList<String[]>();
		try {
			InputStream in = Epaper.class.getClass().getResourceAsStream("/epaper.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				String[] res = line.split("\\s+");
				list.add(res);
			}
			br.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ConnectionManager.getInstance().init();
		Set<String> urls = new HashSet<String>();
		String sql = "SELECT * from wdyq_infosource a , wdyq_papersource b where a.type=5 and a.papersource=b.id";
		String sql2 = "SELECT * from wdyq_infosource  where type=5";
		try {
			Connection conn = ConnectionManager.getInstance().getDBConnection();
			try {
				PreparedStatement ps = conn.prepareStatement(sql2);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String url = rs.getString("url");
					urls.add(url);
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (String[] str : list) {
			String url = str[str.length - 1];
			if (!urls.contains(url)) {
				for (int i = 0; i < str.length; i++) {
					System.out.print(str[i] + " ");

				}
				System.out.println();
			}
		}
	}

	public static String absUrl(String baseUri, String relUrl) {
		URL base;
		try {
			try {
				base = new URL(baseUri);
			} catch (MalformedURLException e) {
				// the base is unsuitable, but the attribute may be abs on its
				// own, so try that
				URL abs = new URL(relUrl);
				return abs.toExternalForm();
			}
			// workaround: java resolves '//path/file + ?foo' to '//path/?foo',
			// not '//path/file?foo' as desired
			if (relUrl.startsWith("?"))
				relUrl = base.getPath() + relUrl;
			URL abs = new URL(base, relUrl);
			return abs.toExternalForm();
		} catch (MalformedURLException e) {
			return "";
		}
	}

	public static List<String> getMatchUrl(String homeUrl, String regex) {
		List<String> ans = new ArrayList<String>();
		try {
			String html = HttpQuery.getInstance().get(homeUrl).asString();
			List<String> m = StringUtils.match(html, regex);
			for (String url : m) {
				ans.add(absUrl(homeUrl, url));
			}
			return ans;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * 清除字符串中的标签，主要是
	 * <p>
	 * 
	 * @param str
	 *            待处理字符串
	 * @return 返回处理之后的字符串
	 */
	public static String cleanHtml(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("&nbsp;", "");
		str = str.replaceAll("<p>", "");
		str = str.replaceAll("<P>", "");
		str = str.replaceAll("</p>", "");
		str = str.replaceAll("</P>", "");
		str = str.replaceAll("<(.[^>]*)>", "");
		str = str.replaceAll("<!--", "");
		str = str.replaceAll("--!>", "");
		str = str.replaceAll("<--", "");
		str = str.replaceAll("-->", "");
		return str.trim();
	}

	public static void checkContent(String contentUrl, String titleRegex, String timeRegex, String contentRegex) {
		try {
			String html = HttpQuery.getInstance().get(contentUrl).asString();
			System.out.print("			title:");
			try {
				if (!StringUtils.isEmpty(titleRegex)) {
					String title = dotAllMatch(html, titleRegex);

					System.out.println(title);
				}
			} catch (Exception e) {
				System.out.println();
			}
			System.out.print("			content:");
			try {
				if (!StringUtils.isEmpty(contentRegex)) {
					String content = dotAllMatch(html, contentRegex);

					System.out.println(cleanHtml(content));
				}
			} catch (Exception e) {
				System.out.println();
			}
			System.out.print("			time:");
			try {
				if (!StringUtils.isEmpty(timeRegex)) {
					String time = StringUtils.match(html, timeRegex).get(0);

					System.out.println(time);
				}
			} catch (Exception e) {
				System.out.println();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 单行模式匹配
	 */
	public static String dotAllMatch(String content, String regex) {
		String res = "";
		try {
			Pattern p = Pattern.compile(regex, Pattern.DOTALL);
			Matcher m = p.matcher(content);
			if (m.find()) {
				res = m.group(1);
			}
		} catch (Exception e) {

		}
		return res;
	}

	public static void checkRegex(String homeUrl) {

		System.out.println("homeUrl : " + homeUrl);
		
		String homeRegex = "URL=\"{0,1}(.*?.htm)\"";

		 String pageRegex = "(node_.*?htm)";
//		String pageRegex = "(nbs\\..*?htm)";

		 String contentUrlRegex = "(content_.*?htm)";
//		String contentUrlRegex = "(nw\\..*?htm)";

		String titleRegex = "<founder-title>\\s*(.*?)\\s*</founder-title>";

//		 String contentRegex = "<!--enpcontent-->(.*?)<!--/enpcontent-->";
		String contentRegex = "<founder-content>(.*?)</founder-content>";

		String timeRegex = "<founder-date>\\s*(.*?)\\s*</founder-date>";
		// String timeRegex = "(\\d{4}年\\d+月\\d+日)";

		System.out.println("homeRegex : " + homeRegex);
		System.out.println("pageRegex : " + pageRegex);
		System.out.println("contentUrlRegex : " + contentUrlRegex);
		System.out.println("titleRegex : " + titleRegex);
		System.out.println("contentRegex : " + contentRegex);
		System.out.println("timeRegex : " + timeRegex);

		try {
			String html = HttpQuery.getInstance().get(homeUrl).asString();
			try {
				String url = StringUtils.match(html, homeRegex).get(0);
				String home = absUrl(homeUrl, url);
				System.out.println("homeUrl:" + home);
				List<String> pageUrls = getMatchUrl(home, pageRegex);
				for (String pageUrl : pageUrls) {
					System.out.println("	pageUrl : " + pageUrl);
					List<String> contentUrls = getMatchUrl(pageUrl, contentUrlRegex);
					for (String contentUrl : contentUrls) {
						System.out.println("		contentUrl:" + contentUrl);
						checkContent(contentUrl, titleRegex, timeRegex, contentRegex);

					}
					System.out.println("----------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showHtml(String url) {
		try {
			System.out.println(HttpQuery.getInstance().get(url).asString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String homeUrl = "http://bjcb.morningpost.com.cn/";
//		showHtml(homeUrl);
//		 checkRegex(homeUrl);
		 find();
	}
}
