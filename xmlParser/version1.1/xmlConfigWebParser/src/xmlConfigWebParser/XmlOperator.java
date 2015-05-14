package xmlConfigWebParser;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Dom��дxml�ļ�����
 * @author BAO
 */

public class XmlOperator {

	
	public static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	public String path = "";
	
	/**
	 * ������,ָ����path
	 * @author BAO
	 */
	public XmlOperator(){}
	
	public XmlOperator(String path){
		if(path != null){
			this.path = path + (path.endsWith("\\")? "":"\\" );
		}
	}
			
	/**
	 * ��filename����ȡһ��document
	 * @author BAO
	 */
	public Document getDoucumetFromXML(String filename) {
		
		File file = new File(path + filename);
		
		try{
	
			InputStream in = new FileInputStream(file);
			try{
				DocumentBuilder builder = dbf.newDocumentBuilder();			
				Document doc = builder.parse(in);
			
				return doc;
			}catch(Exception e){
				throw e;
			}finally{
				in.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡ���ڵ�������ӽڵ�
	 * @author BAO
	 */
	public static NodeList getChildNodes(Element parent){
		return parent.getChildNodes();
	}
	
	/**
	 * ��ȡ���ڵ�����������Ϊname���ӽڵ㣬����һ��Element[] ����
	 * @author BAO
	 */
	public Element[] getElementByTagName(Element parent, String name) {
		
		ArrayList res = new ArrayList();
		NodeList childNodes = getChildNodes(parent);
		
		for(int i=0; i<childNodes.getLength(); i++){
			final Node tmp = childNodes.item(i);
			if(tmp.getNodeName().equals(name)){
				res.add(tmp);
			}
		}
		
		Element[] elements = new Element[res.size()];
		for(int i = 0; i < res.size(); i++){
			elements[i] = (Element)res.get(i);
		}
		return elements;
	}

	/**
	 * ��ȡԪ�ص�attr���Ե�ֵ
	 * @author BAO
	 */
	public static String getElementAttr(Element e, String attr) {
		return e.getAttribute(attr);
	}
	
	/**
	 * ��ȡNode������
	 */
	public static String getNodeName(Element e){
		return e.getNodeName();
	}
	
}
