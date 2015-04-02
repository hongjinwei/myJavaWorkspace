import java.io.*;
import java.util.*;

public class Test{

	public static void main(String[] args)
	{
		String str = "abc defhasda";
		//System.out.println(str.length());
		System.out.println(str.substring(str.lastIndexOf(' '),str.length()));
		

		//for(int i=0;i<=9&&i<=str.length();i++){
		//	System.out.println(str.substring(0,i));
		//}
	}
}