package xmlConfigWebParser;

import java.io.*;
import java.util.*;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import xmlConfigWebParser.XmlOperator;


public class ParserConfig {

	private final static List<String> parseContent = new LinkedList<String>();
	private final static List<String> backupParseContent = new LinkedList<String>();
		
	private final static String CONFIG_DEFAULT_PATH = "C:\\Users\\BAO\\workspace\\xmlConfigWebParser\\src\\xmlConfigWebParser";
	private final static String CONFIG_DEFAULT_FILE = "parserConfig.xml";
	
	private static String CONFIG_PATH = CONFIG_DEFAULT_PATH;
	private static String CONFIG_FILE = CONFIG_DEFAULT_FILE;
	private static boolean fetchable = false;
	
	private static XmlOperator operator = new XmlOperator(CONFIG_PATH);
	private static ParserConfig config = new ParserConfig();
	
	//Singleton
	private ParserConfig() {};

	static{
		try{
			config.parseXml();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("xml解析启动失败");
		}	
	}
	
	public static ParserConfig access(){
		return config;
	}
	
	synchronized public static List<String> fetchConfig() {
		if(config.fetchable){
			return parseContent;
		}else{
			return backupParseContent;
		}
	}
	
	synchronized public static void setXmlConfig() {
		config.parseXml();
	}
	
	synchronized public static void setXmlConfig(String path, String filename) {
		config.fetchable = false;
		config.CONFIG_PATH = (path.equals("") || path == null)? CONFIG_DEFAULT_PATH : path;
		config.CONFIG_FILE = (filename.equals("") || filename == null)? CONFIG_DEFAULT_FILE : filename;
		config.operator = new XmlOperator(CONFIG_PATH);
		config.parseXml();
		config.fetchable = true;
	}
	/*
	public static void main(String[] args) {
		config.parseXml();
		System.out.println(parseContent);
	}
	*/
	
	/**
	 * 检查是否存在这个config
	 * @author BAO
	 */
	private boolean isExist(String s){
		return (parseContent.indexOf(s) == -1)?false:true; 
	}
	
	/**
	 * 将value值放入parseContent中
	 * @author BAO
	 * @param value
	 */
	private void updateParseContent(String value){
		if(value == null || value.equals("")) return ;
		
		if(!isExist(value)){
			parseContent.add(value);
		}
	}
	
	/**
	 * 将当前配置备份到backupParseContent
	 */
	private static void backup() {	
		
		backupParseContent.clear();
		for(int i=0;i<parseContent.size(); i++){
			backupParseContent.add(parseContent.get(i));
		}
	}
	
	/**
	 * 从backup中恢复到parserContent
	 */
	private static void recovery() {
		
		parseContent.clear();
		for(int i=0;i<backupParseContent.size(); i++){
			parseContent.add(backupParseContent.get(i));
		}
	}
	
	/**
	 * 解析xml
	 */
	private void parseXml() {		
		try{
			config.fetchable = false;
			parseContent.clear();
			Document doc = operator.getDoucumetFromXML(CONFIG_FILE);
			Element conf = doc.getDocumentElement();
			Element[] sinaList = operator.getElementByTagName(conf, "sina");
			for(int i=0; i<sinaList.length; i++){
				NodeList lists = sinaList[i].getChildNodes();
				for(int j = 0; j < lists.getLength(); j++){
					Node node = lists.item(j);
					if(node!=null && node.getNodeType() == Node.ELEMENT_NODE) {
						String value = node.getTextContent();
						updateParseContent(value);
					}
				}
			}
			config.backup();
			config.fetchable = true;
		}catch(Exception e){
			config.recovery();
			System.out.println("Error!");	
		}finally{
			config.fetchable = true;
		}
	}
}
