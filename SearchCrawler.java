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

}