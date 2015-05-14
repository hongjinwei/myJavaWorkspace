package xmlConfigWebParser;

import xmlConfigWebParser.XmlOperator;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract public class ParserConfig{

	protected final static String CONFIG_DEFAULT_PATH = "C:\\Users\\BAO\\workspace\\xmlConfigWebParser\\src\\xmlConfigWebParser";
	protected final static String CONFIG_DEFAULT_FILE = "parserConfig.xml";
	
	protected static String CONFIG_PATH = CONFIG_DEFAULT_PATH;
	protected static String CONFIG_FILE = CONFIG_DEFAULT_FILE;
	
	private static XmlOperator operator = new XmlOperator(CONFIG_PATH);
	private static Document doc =  operator.getDoucumetFromXML(CONFIG_FILE);
	private static Element conf = doc.getDocumentElement();
	
	protected static XmlOperator getXmlOperator() {
		return operator;
	}
	
	synchronized protected void setXmlConfig(String path, String filename) {
		CONFIG_PATH = (path.equals("") || path == null)? CONFIG_PATH : path;
		CONFIG_FILE = (filename.equals("") || filename == null)? CONFIG_FILE : filename;
		operator = new XmlOperator(CONFIG_PATH);
		doc =  operator.getDoucumetFromXML(CONFIG_FILE);
		conf = doc.getDocumentElement();
	}
	
	/**
	 * 检查是否存在这个config
	 * @author BAO
	 */
	private static boolean isExist(List<String> l, String s){
		return (l.indexOf(s) == -1)?false:true; 
	}
	
	/**
	 * 将value值放入parseContent中
	 */
	protected static void updateParseContent(List<String> parseContent, Element parent, String tagName){
		
		Element[] e = operator.getElementByTagName(parent, tagName);
		for(int i=0; i<e.length; i++){
			NodeList lists = e[i].getChildNodes();
			for(int j = 0; j < lists.getLength(); j++){
				Node node = lists.item(j);
				if(node!=null && node.getNodeType() == Node.ELEMENT_NODE) {
					String value = node.getTextContent();
					if(value == null || value.equals("")){
						continue;
					}
					if(!isExist(parseContent,value)){
						parseContent.add(value);
					}
				}
			}
		}	
	}

	
	/**
	 * 将当前配置备份到backupParseContent
	 */
	private static void backup(List<String> parseContent, List<String> backup) {	
		
		backup.clear();
		for(int i=0;i<parseContent.size(); i++){
			backup.add(parseContent.get(i));
		}
	}
	
	/**
	 * 从backup中恢复到parserContent
	 */
	private static void recovery(List<String> parseContent, List<String> backup) {
		
		parseContent.clear();
		for(int i=0;i<backup.size(); i++){
			parseContent.add(backup.get(i));
		}
	}
	
	
	/**
	 * 解析xml
	 */
	protected static void parseXml(List<String> parseContent, List<String> backupParseContent, String tagName) {		
		try{			
			parseContent.clear();			
			updateParseContent(parseContent , conf, tagName);
			backup(parseContent, backupParseContent);
		}catch(Exception e){
			recovery(parseContent, backupParseContent);
			System.out.println("Error!");	
		}
	}
}
