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

class ErrorSubstring{
	
	HashMap dict = new HashMap();
	public void put(String s)
	{
		dict.put(s,1);
	}

	public boolean get(String s)
	{
		return dict.containsKey(s);
	}
}

public class SplitWord{

	static int MAX_LEN = 10;
	//static String raw_str = "appleisdelicasddarisgoodaaaa";
	static String raw_str = "adminappleisdelicious";
	//static String raw_str = "admin";
	//static String raw_str = "admin";
	
	static int MAX = raw_str.length();
	static int[][] array = new int[MAX][MAX];
	static LinkedList<Integer> blank = new LinkedList<Integer>();

	static{
		init();
	}

	public static void init()
	{
		for(int i=0;i<MAX;i++){
			for(int j=0;j<MAX;j++){
				array[i][j] = 0;
			}
		}
	}

	static Dictionary dict = new Dictionary();
	static ErrorSubstring err = new ErrorSubstring();

	public static void printArray()
	{
		for(int[] a:array){
			for(int b:a){
				System.out.print(b + " ");
			}
			System.out.print("\n");
		}
	}
	
	public static void print(Object obj)
	{
		System.out.println(obj);
	}
	
	public static boolean guess(String str,int start)
	{
		String guessWord,subWord;
		int tmp,k=0;
		try{
			//for(int i = start+1; (i-start) <= MAX_LEN && i <= str.length(); i++){
			for(int i = ((MAX-start) <= MAX_LEN)?MAX:start+MAX_LEN;i>start;i--){
				guessWord = str.substring(start,i);
				subWord = str.substring(i);

				if( dict.get(guessWord) && !err.get(subWord) ){	
					blank.push(i);
					if(i == str.length()){
						while(blank.size() > 0){
							tmp = k;
							k = blank.pollLast(); 
							array[tmp][k-1] = 1;
						}
						return true;
					}else{
						if(guess(str,i)){
							return true;
						}else{
							//sub string is not cuttable,store it
							err.put(str.substring(i));
						}
					}
					if(blank.size() > 0){
						blank.pop();
					}
				}
			}
		}catch(Exception e){			
		}
		return false;
	}

	public static void showResult()
	{
		int i=0,j=0;
		while(j<MAX&&i<MAX){
			if(array[i][j] != 1){
				j++;
			}else{
				System.out.print(raw_str.substring(i,j+1) + " ");
				i=j+1;
				j++;
			}
		}
	}	
	public static void main(String[] args){
		if(guess(raw_str,0)){
			showResult();
		}else{
			print("No Result");
		}
	}
}