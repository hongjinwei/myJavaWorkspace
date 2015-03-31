import java.util.*;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.text.*;

interface Config{

	public final static String FILEPATH = "C:\\Users\\bao\\Documents\\GitHub\\myJavaWorkspace\\search crawler\\file\\";
	public final static String PICPATH = FILEPATH + "pic\\";
	public final static int MAXSIZE = 20;
	public final static int ROUND = 100;
}

class NewQueue<AnyType> extends LinkedList<AnyType> 
{
	static int size;

	public NewQueue(int initsize)
	{
		try{
			if(initsize > Config.MAXSIZE){
				System.out.println("too large queue size!!");
				this.size = Config.MAXSIZE;
			}
			this.size = initsize;
		}catch(Exception e){
			System.out.println("too large queue size");
			this.size = Config.MAXSIZE;
		}
	}

	public void qpush(AnyType s) throws Exception
	{
		try{
			if(this.size() > this.size){
				throw new Exception();
			}
			this.addLast(s);
		}catch(Exception e){
			System.out.println("队列已满");
		}
	}

	public AnyType qpop()
	{
		try{
			return this.pollFirst();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public int getSize()
	{
		try{
			return this.size();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public boolean isEmpty()
	{
		return (this.size()<=0);
	}
}


class Contenthandler{

	public static URL urlVerify(String url)
	{
		//TODO verify if the url is legal
		if(!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")){
			return null;
		}

		URL verifiedUrl = null;
		try{
			verifiedUrl = new URL(url);
		}catch(Exception e){
			return null;
		}
		return verifiedUrl;
	}

	public static void processContent(String content,NewQueue<String> q)
	{
		//TODO get pic url and link url from the content and add them into download queues
		//String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")"; 
		//String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")"; 
		String searchImgReg = "<img.*?src=\"(.*?)\"";
		Pattern p = Pattern.compile(searchImgReg);
		Matcher m = p.matcher(content);
		while(m.find()){
			//System.out.println(m.group(1));
			String picSrc = m.group(1);
			Downloader.getPicture(picSrc);
		}
		
		String searchLinkReg = "<a.*?href=\"(.*?)\"";
		Pattern p2 = Pattern.compile(searchLinkReg);
		Matcher m2 = p2.matcher(content);
		while(m2.find()){
			//System.out.println(m2.group(1));
			String pageSrc = m2.group(1);
			try{
				q.qpush(pageSrc);
				//String new_content = Downloader.getHtmlCode(pageSrc);
				//Downloader.output(new_content);
			}catch(Exception e){
				System.out.println("页面url加入队列失败!");
			}
		}		

	}
}


class Downloader{

	public static void getPicture(String httpUrl)
	{
		BufferedInputStream in;
		FileOutputStream file;
		try{
			String fileName = httpUrl.substring(httpUrl.lastIndexOf("/"));
			String filePath = Config.PICPATH;
			URL url = Contenthandler.urlVerify(httpUrl);
			if(url != null){
				in = new BufferedInputStream(url.openStream());
				file = new FileOutputStream(new File(filePath+fileName));
				int t;
				while((t = in.read()) != -1){
					file.write(t);
				} 
				file.close();
				in.close();
				System.out.println("图片下载完成！");
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
	}

	public static String getHtmlCode(String httpUrl) throws IOException
	{
		String content = "";
		URL url = Contenthandler.urlVerify(httpUrl);
		BufferedReader response = new BufferedReader(new InputStreamReader(url.openStream()));
		String input;
		while((input = response.readLine()) != null){
			content += input;
		}
		response.close();
		return content;
	}

	public static void output(String content)throws Exception
	{
		if(content == null){
			//TODO throw an exception
			throw new IOException("content is null");
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
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

		//System.out.println("Done!");
	}	
}


public class DownloadPage{

	static NewQueue<String> queue = new NewQueue<String>(20);
	static String startUrl = "http://blog.chinaunix.net/uid-26284395-id-3135495.html";

	public static void main(String[] args)
	{
		String url,result;
		try{
			queue.qpush(startUrl);
		}catch(Exception e){
			System.out.println("error to init startUrl!");
			e.printStackTrace();
			return ;
		}
		//String picUrl = "http://passport.ixpub.net/data/avatar/026/73/28/49_avatar_small.jpg";
		for(int round = 0;round < Config.ROUND; round++){
				
			try{
				url = queue.qpop();
				result = Downloader.getHtmlCode(url);
				Downloader.output(result);
				Contenthandler.processContent(result,queue);
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		
		System.out.println("Done!");
	}
}
