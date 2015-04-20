import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;


public class SearchCrawler implements Runnable{
	/* disallowListCache缓存robot不允许搜索的URL。 Robot协议在Web站点的根目录下设置一个robots.txt文件,
  	*规定站点上的哪些页面是限制搜索的。 搜索程序应该在搜索过程中跳过这些区域,下面是robots.txt的一个例子:
 	# robots.txt for http://somehost.com/
   	User-agent: *
   	Disallow: /cgi-bin/
   	Disallow: /registration # /Disallow robots on registration page
   	Disallow: /login
  	*/

   	private HashMap< String,ArrayList<String> > disallowListCache = new HashMap< String,ArrayList<String> >();
   	String startUrl;//起始的url
   	int maxUrl;//最大处理的url数目
   	String searchString;//搜索关键字
   	boolean caseSensitive = false;//是否区分大小写
   	boolean limitHost = false;

   	public SearchCrawler(String startUrl,int maxUrl,String searchString){
   		this.startUrl = startUrl;
   		this.maxUrl = maxUrl;
   		this.searchString = searchString;
   	}

   	public ArrayList<String> getResult(){
   		return result;
   	}

   	public void run(){//启动线程
   		crawl(startUrl,maxUrl,searchString,limitHost,caseSensitive);
   	}

   	private URL verifyURL(String url){
   		if(!url.toLowerCase().startWith("http://")){
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

   	private boolean isRobotAllowed(URL urlToCheck){
   		String host = urlToCheck.getHost().toLowerCase();//get the host of the url
   		System.out.println("host = " + host );

   		ArrayList<String> disallowList = disallowListCache.get(host);
   		//如果没有缓存，下载缓存
   		if(disallowList == null)
   	}
}