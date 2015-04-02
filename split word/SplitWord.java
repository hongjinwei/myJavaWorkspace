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
	static String raw_str = "adminappleisdelicious";

	static Dictionary dict = new Dictionary();
	static String[] guessStr = new String[MAX]; 
	static String[] result = new String[MAX];	

	static int start = 0;
	static int end = 0;
	static int resNum = 0;

	public static void guess(String str)
	{
		int start;
		try{
			start = str.lastIndexOf(' ') + 1;
		}catch(Exception e){
			start = 0;
		}

		String guessWord;
		String prefix,suffix;
		try{
			for(int i = start; (i-start) <= MAX_LEN && i <= str.length(); i++){
				guessWord = str.substring(start,i);
				if( dict.get(guessWord) ){
					//System.out.println("guessWord: " + guessWord);
					if( start == 0){
						prefix = "";
					}else{
						prefix = str.substring(0,start-1) + " ";
					}

					//System.out.println("prefix: " + prefix);
					
					if(i < str.length()){
						suffix = " " + str.substring(i,str.length());
					}else{
						suffix = "";
					}
				
					//System.out.println("suffix: " + suffix + " now i: " + i + " length: "+ str.length());
					guessStr[end] = prefix + guessWord + suffix;
					//System.out.println(guessStr[end] + "\n" + "======" );
				
					if(i >= str.length()){
						result[resNum] = guessStr[end];
						resNum++;
						end--;
					}
				
					end++;
				}
			}
		}catch(Exception e){}
	}

	public static void checkResultRepeated()
	{
		int len = resNum;
		int k=0;
		String[] res = new String[MAX];
		if(resNum == 0){
			return ;
		}else{
			for(int i=0;i<resNum;i++){
				if(result[i].equals(" ")){
					continue;
				}

				res[k++] = result[i];
				
				for(int j=i+1;j<resNum;j++){
					if(result[i].equals(result[j])){
						result[j] = " ";
					}
				}
			}
		}
		resNum = k;
		for(int i=0;i<k;i++){
			result[i] = res[i];
		}
	}

	public static void main(String[] args)
	{
		guessStr[0] = raw_str;
		end++;
		try{
			while(start <= end){
				guess(guessStr[start]);			
				start++;
			}
	
			if(resNum == 0){
				System.out.println("can't split into a meaningful sentence");
			}else{
				checkResultRepeated();
				System.out.println( resNum + " split result:");
				for(int i=0;i<resNum;i++){
					System.out.println(result[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		for(int i=0;i<=end;i++){
			System.out.println(guessStr[i]);
		}
		*/
	}
}