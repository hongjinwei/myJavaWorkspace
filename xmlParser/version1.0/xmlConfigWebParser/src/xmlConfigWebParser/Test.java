package xmlConfigWebParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlConfigWebParser.XmlOperator;

import xmlConfigWebParser.ParserConfig;

public class Test {
	
	XmlOperator s = new XmlOperator("C:\\Users\\BAO\\workspace\\xmlConfigWebParser\\src\\xmlConfigWebParser");
	
	/**
	 * read xml config
	 * @author BAO
	 */
	public void testRead() {
		Document doc = s.getDoucumetFromXML("text.xml");
		Element root = doc.getDocumentElement();
		if(root == null){
			System.out.println("¿ÕxmlÅäÖÃ£¡");
			return ;
		}
		System.out.println(root.getNodeName());
		System.out.println(root.getAttribute("name"));
	}	
	
	/**
	 * used for format String
	 * @author BAO
	 */
	private String repeat(String r, int times) {
		String res = "";	
		if(r == null) return res;
		
		while(times > 0){
			res += r;
		}
		return res;
	}
	
	/*
	public static void main(String[] args) {
		//Test t = new Test();
		//t.testRead();
		System.out.println(ParserConfig.fetchConfig());
	}
	*/
}
