package xmlConfigWebParser;

import java.io.*;
import java.util.*;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class SinaParserConfig extends ParserConfig {

	private final static List<String> sinaParseContent = new LinkedList<String>();
	private final static List<String> sinaBackupParseContent = new LinkedList<String>();
	private final static XmlOperator operator = getXmlOperator();
	
	//防止在parse过程中访问出现错误，在parse的时候，fetchable为false，此时返回backupParseContent
	private static boolean fetchable = false;	

	//单例
	private static SinaParserConfig config = new SinaParserConfig();
	
	private SinaParserConfig() {};
	
	/**
	 * 初始化
	 */
	static{
		try{
			config.parseXml();
			config.fetchable = true;
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("xml解析启动失败");
		}	
	}
	
	public static  SinaParserConfig access(){
		return config;
	}
	
	/**
	 * 返回配置List
	 */
	synchronized public List<String> fetchConfig() {
		if(config.fetchable){
			return sinaParseContent;
		}else{
			return sinaBackupParseContent;
		}
	}
	
	/**
	 * 改变Xml配置的文件名（路径不变）
	 */
	synchronized public void setXmlConfig(String filename) {
		config.fetchable = false;
		super.setXmlConfig("", filename);
		config.fetchable = true;
	}
	/**
	 * 重新设置Xml配置的路径和xml文件名
	 */
	@Override
	synchronized public void setXmlConfig(String path, String filename) {
		config.fetchable = false;
		super.setXmlConfig(path, filename);
		config.fetchable = true;
	}
	
	private void parseXml() {
		config.fetchable = false;
		parseXml(sinaParseContent,sinaBackupParseContent, "sina");
		config.fetchable = true;
	}
}
