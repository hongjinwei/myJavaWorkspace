import java.util.*;
import java.net.*;
import java.io.*;
import java.text.*;

interface Config{

	public final static String FILEPATH = "C:\\Users\\bao\\Documents\\GitHub\\myJavaWorkspace\\search crawler\\file\\";
	public final static String PICPATH = FILEPATH + "pic\\";
}


class Contenthandler{

	public static boolean urlVerify(String url){
		//TODO verify if the url is legal
		if(!url.toLowerCase().startsWith("http://")){
			return false;
		}
	}

	private void getLinkUrl(){
		//TODO find link url
	}

	private void getPicUrl(){
		//TODO find pictuire url
	}

	public static void processContent(String content){
		//TODO get pic url and link url from the content and add them into download queues
	}
}


class Downloader{

	public static String getHtmlCode(String httpUrl) throws IOException{
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

	public static void output(String content)throws Exception{
		if(content == null){
			//TODO throw an exception
			throw new IOException("content is null");
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String filename = formatter.format(date) + ".html";
		//System.out.println(Config.FILEPATH);
		//System.out.println(filename);

		try{
			File file = new File(Config.FILEPATH+filename);
			if(!file.exists()){
				System.out.println("create a new file!"); 
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("Done!");
	}	
}


public class DownloadPage{
	public static void main(String[] args){
		String url = "http://www.baidu.com";
		String result=" ";
		try{
			result = Downloader.getHtmlCode(url);
			Downloader.output(result);
		}catch(IOException e){
			e.printStackTrace();
		}
	//System.out.println(result);
	}
}
