import java.io.*;
import java.util.*;
import java.io.FilenameFilter;
import java.text.*;

public class P2t{

	static String PATH = ".\\";
	static String RAW_PIC_PATH = PATH + "raw\\pic\\";
	static String RAW_TORRENT_PATH = PATH + "raw\\torrent\\";
	static String PROCESSED_PIC_PATH = PATH + "processed\\pic\\";
	static String PROCESSED_TORRENT_PATH = PATH + "processed\\torrent\\";

	public static String getpath()
	{
		String path = System.getProperty("user.dir");
		return path;
	}

	public static String timeStamp()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		String timeStamp = formatter.format(date);
		return timeStamp;
	}

	public static void appendFile(String fileName,String contentFileName)
	{
		try{
			String content = "";
			FileInputStream in = new FileInputStream(RAW_PIC_PATH + fileName);
			FileInputStream in2 = new FileInputStream(RAW_TORRENT_PATH + contentFileName);
			FileOutputStream out = new FileOutputStream(PROCESSED_PIC_PATH + fileName);
			int tmpByte;
			while((tmpByte = in.read()) != -1){
				out.write(tmpByte);
			}

			while((tmpByte = in2.read()) != -1){
				out.write(tmpByte);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void torrent2pic()
	{
		File path = new File(RAW_PIC_PATH); 
		String[] files = path.list();
		String[] legalFile = new String[30];
		int size = 0;
		//check for legal selected pic
		for(String filename : files ){
			if(filename.endsWith(".bmp")){
				legalFile[size] = filename;
				size++;
				//System.out.println(filename);
			}
		}

		File torrent_path = new File(RAW_TORRENT_PATH);
		String[] torrents = torrent_path.list();
		
		for(String torrent : torrents){
			if(torrent.endsWith(".torrent")){
				int x = (int)(Math.random() * size);
				//System.out.println(torrent + " " + x );
				appendFile(legalFile[x],torrent);
			}
		}

	}


	public static void pic2torrent()
	{
		File path = new File(PROCESSED_PIC_PATH); 
		String[] files = path.list();
		for(String filename : files){
			if(filename.endsWith(".bmp")){
				
			}
		}
	}
	public static void main(String[] args)
	{
		//System.out.println(getpath());
		try{
			torrent2pic();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}