
//import static Config.*;

import java.util.concurrent.LinkedBlockingQueue;
public class CrawlerQueue {
	private  static LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
	private static int maxSize;
	
	public CrawlerQueue() {
		maxSize = Config.MAX_QUEUE_SIZE;
	}
	
	public CrawlerQueue(int size){
		if(size <= Config.MAX_QUEUE_SIZE){
			maxSize = size;
		}else{
			maxSize = Config.MAX_QUEUE_SIZE;
		}
	}
	
	public static boolean isFull()
	{
		return (q.size() >= maxSize);
	}
	
	public static boolean isEmpty()
	{
		return (q.size() == 0);
	}
	
	public static int size()
	{
		try{
			return q.size();
		}catch(Exception e){
			return 0;
		}
	}
	
	public static boolean push(String url)
	{
		try{
			if(!isFull()){
				q.add(url);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	public static String pop()
	{
		try{	
			if(isEmpty()){
				return null;
			}else{
				return q.poll();
			}
		}catch(Exception e){
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("Hello!");
	}
}
