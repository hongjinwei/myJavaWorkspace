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
	
	//��ֹ��parse�����з��ʳ��ִ�����parse��ʱ��fetchableΪfalse����ʱ����backupParseContent
	private static boolean fetchable = false;	

	//����
	private static SinaParserConfig config = new SinaParserConfig();
	
	private SinaParserConfig() {};
	
	/**
	 * ��ʼ��
	 */
	static{
		try{
			config.parseXml();
			config.fetchable = true;
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("xml��������ʧ��");
		}	
	}
	
	public static  SinaParserConfig access(){
		return config;
	}
	
	/**
	 * ��������List
	 */
	synchronized public List<String> fetchConfig() {
		if(config.fetchable){
			return sinaParseContent;
		}else{
			return sinaBackupParseContent;
		}
	}
	
	/**
	 * �ı�Xml���õ��ļ�����·�����䣩
	 */
	synchronized public void setXmlConfig(String filename) {
		config.fetchable = false;
		super.setXmlConfig("", filename);
		config.fetchable = true;
	}
	/**
	 * ��������Xml���õ�·����xml�ļ���
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
