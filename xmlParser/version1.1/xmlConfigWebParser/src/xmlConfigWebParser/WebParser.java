package xmlConfigWebParser;

import xmlConfigWebParser.SinaParserConfig;

import java.io.*;
import java.util.*;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebParser {

	private  static SinaParserConfig config = SinaParserConfig.access();
	List<String> pids = config.fetchConfig();
	
	/**
	 * 将一个文件内容转化成string
	 * @param filename
	 * @return
	 */
	private String fileToString(String filename) {	
		File file = new File(filename);
		String res;
	
		StringBuilder builder = new StringBuilder();
		BufferedReader r = null;
		try{
			
			r = new BufferedReader(new FileReader(file));
			String str;
			while( (str=r.readLine()) != null ){
				builder.append(str);
			}
			return builder.toString();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(r!=null){
					r.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获得html的string数据
	 * @return
	 */
	private String getHtmlString() {
		String path = "C:\\Users\\BAO\\workspace\\xmlConfigWebParser\\src\\xmlConfigWebParser\\";
		return fileToString(path + "a.htm");
	}
	
	/**
	 * 解析html的string，返回其中包含的所有jason数据。
	 */
	private Map<String, JSONObject> resolve(String html) {
		Map<String, JSONObject> jsonMap = new HashMap<String, JSONObject>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("script");
		
		for(Element element : elements) {
			String text = element.data();
			if(text.contains("STK.pageletM.view")){
				text = text.substring(text.indexOf('(') + 1, text.lastIndexOf(')'));
				JSONObject o = new JSONObject(text);
				String pid = o.getString("pid");
				jsonMap.put(pid, o);
			}
		}
		return jsonMap;
	}
	
	public String parseAll(String html) {
		Map<String, JSONObject> map = resolve(html);
		//获取页面中 pid为pl_weibo_direct的JSONObject
		//tag = pids.get(0); //从pids中取用合适的pid
		String tag = "pl_weibo_direct";
		JSONObject data = map.get(tag);
		if(data == null) {
			return null;
		}
		
		//获取这个Json数据中的html的数据
		String htm = data.getString("html");

		Document doc = Jsoup.parse(htm);
		
		Elements ele = doc.getElementsByAttributeValue("action-type", "feed_list_item");
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < ele.size(); i++){
			Element e = ele.get(i);
			String content = parse(e);
			builder.append(content);
			builder.append("\n===========\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * 获取微博内容
	 */
	private String parse(Element e) {		
		Elements childs = e.getElementsByAttributeValue("node-type", "like");
		Element Allcontent = childs.get(0);
		Elements contents = Allcontent.getElementsByAttributeValue("node-type", "feed_list_content");
		Element content = contents.get(0);
		String res = content.text();
		//System.out.println(res);
		return res;
	}
	
	
	/**
	 * 显示map的内容
	 * @param map
	 */
	private void showMap(Map<String, JSONObject> map) {
        for (Map.Entry<String, JSONObject> entry : map.entrySet()) {  
            System.out.println(" \"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"");  
        }  
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebParser parser = new WebParser();
		//System.out.println(parser.pids);
		String html = parser.getHtmlString();
		System.out.println( parser.parseAll(html) );
	}

}
