import java.io.*;
import java.util.*;

public class Solution {
    
    Set<String> err = new TreeSet<String>();
    List<String> res = new LinkedList<String>();
    private static LinkedList<Integer> tmp = new LinkedList<Integer>();
    
    private boolean guess(String s,Set<String> dict,int start)
    {
    	boolean flag = false;
    	for(int i=start+1; i<=s.length(); i++){
    		if(dict.contains(s.substring(start,i)) && !err.contains(s.substring(i))){
    			tmp.add(i);
    			flag = true;
    			//System.out.println(tmp);
    			if(i == s.length()){
   					String t = "";
   					int k = 0;
   					for(int x:tmp){
   						if(k==0){
   							t += s.substring(k,x);
   						}else{
   							t = t + " " + s.substring(k,x);
   						}
   						k = x;
   					}
   					System.out.println(t);
   					res.add(t);
    				tmp.pollLast();
    				return true;
    			}else{
    				if(!guess(s,dict,i)){
    					//System.out.println("err: " + s.substring(i));
    					err.add(s.substring(i));
    				}
    				tmp.pollLast();	
    			}		
    		}
    	}
    	//System.out.println("err String: " + s);
    	return flag;
    }
	

    public List<String> wordBreak(String s, Set<String> wordDict) {
        guess(s,wordDict,0);
        return res;
    }

    public static void main(String[] args)
    {
    	Set<String> dict = new TreeSet<String>();
    	Collection c = new LinkedList<String>();
    	//String[] words = {"cat","cats","and","sand","dog"};
    	String[] words = {"aaaa","aa","a"};
    	dict.addAll(Arrays.asList(words));
    	//System.out.println(dict.contains("cat"));
   		//System.out.println("abc".substring(1));
   		//String s = "catsanddog";
   		String s = "aaaaaaa";
   		Solution sol = new Solution();
   		System.out.println(sol.wordBreak(s,dict) + "\n " + sol.res.size());
    }

}