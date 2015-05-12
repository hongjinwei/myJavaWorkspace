package xmlConfigWebParser;

import xmlConfigWebParser.ParserConfig;

public class WebParser {

	private static ParserConfig conf = ParserConfig.access();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(conf.fetchConfig());
	}

}
