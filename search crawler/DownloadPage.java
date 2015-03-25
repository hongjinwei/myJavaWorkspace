import java.util.*;
import java.net.*;
import java.io.*;

public class DownloadPage{
	
	public String getHtmlCode(String httpUrl) throws IOException{
		String content = "";
		URL uu = new URL(httpUrl);
		BufferedReader ii = new BufferedReader(new InputStreamReader(uu.openStream()));
		String input;
		while((input = ii.readLine()) != null){
			content += input;
		}
		ii.close();
		return content;
	}
	
	public static void main(String[] args){
		DownloadPage a = new DownloadPage();
		String url = "http://www.baidu.com";
		String result=" ";
		try{
			result = a.getHtmlCode(url);
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println(result);
	}
}