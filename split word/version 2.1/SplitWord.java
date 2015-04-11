import java.io.*;
import java.util.*;

class Dictionary{

	HashMap dict = new HashMap();
	
	public Dictionary()
	{
		dict.put("a", 1);
		dict.put("apple", 1);
		dict.put("pear", 1);
		dict.put("is", 1);
		dict.put("are", 1);
		dict.put("good", 1);
		dict.put("delicious", 1);
		dict.put("ad", 1);
		dict.put("admin", 1);
		dict.put("min", 1);
		dict.put("aaaaaaa",1);
	}

	public boolean get(String str)
	{
		return dict.containsKey(str);
	}
}


public class SplitWord{

	static int MAX = 50;
	static int MAX_LEN = 10;
	//static String raw_str = "appleisdelicasddarisgoodpaaaa";
	//static String raw_str = "adminappleisdelicious";
	//static String raw_str = "admin";
	static String raw_str = "aaaaaaaaa";

	static Dictionary dict = new Dictionary();
	static String[] guessStr = new String[MAX_LEN]; 
	static LinkedList<String> q = new LinkedList<String>();
	static String[] result = new String[MAX];	

	static int resNum = 0;

	public static boolean guess(String str)
	{
		int start;
		try{
			start = str.lastIndexOf(' ') + 1;
		}catch(Exception e){
			start = 0;
		}

		String guessWord;
		String prefix,suffix;
		String tmp;
		try{
			for(int i = start; (i-start) <= MAX_LEN && i <= str.length(); i++){
				guessWord = str.substring(start,i);
				if( dict.get(guessWord) ){
					if( start == 0){
						prefix = "";
					}else{
						prefix = str.substring(0,start-1) + " ";
					}
					
					if(i < str.length()){
						suffix = " " + str.substring(i,str.length());
					}else{
						suffix = "";
					}
				
					tmp = prefix + guessWord + suffix;
					q.addFirst(tmp);//put the possessed result into the queueList

					if(i >= str.length()){
						result[resNum++] = tmp;//if get one reusult, return right now
						return true;
					}
				}
			}

		}catch(Exception e){}
		
		return false;
	}


	public static void main(String[] args)
	{
		q.add(raw_str);

		try{
			while(q.size() != 0){
				if(guess(q.pollFirst().toString())){
					break;
				}
			}
	
			if(resNum == 0){
				System.out.println("can't split into a meaningful sentence");
			}else{
				System.out.println( resNum + " split result:");
				for(int i=0;i<resNum;i++){
					System.out.println(result[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}