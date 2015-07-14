import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class LoyedMaxQuantizerWithFiles {
	LoyedMaxQuantizerAlgo loyedMaxQuantizerAlgo=new LoyedMaxQuantizerAlgo();
	String newline = System.getProperty("line.separator");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoyedMaxQuantizerWithFiles lo=new LoyedMaxQuantizerWithFiles();
		lo.deCompress("com.txt", "out.txt");

	}
	
	void compress(String src,String des,int n){
		String out="";
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNext()) {
	            float f = sc.nextFloat();
	            loyedMaxQuantizerAlgo.in_Out_Put.add(f);
	        }
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
		out=loyedMaxQuantizerAlgo.compress(n);
		
		
		try {
			FileWriter fileWrite=new FileWriter(des);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWrite);
			bufferedWriter.write(out);			
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void deCompress(String src,String des){
		String out="";
		String input ="";
		int n = 0,min = 0,step = 0;
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNext()) {
				input+=sc.nextLine()+newline;
			}
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		out=loyedMaxQuantizerAlgo.deCompress(input);// decompress
		
		try {
			FileWriter fileWrite=new FileWriter(des);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWrite);
			bufferedWriter.write(out);			
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	String showFile(String src){
		String text="";
		try {
			FileReader fileReader=new FileReader(src);
			BufferedReader bufferReader=new BufferedReader(fileReader);
			String Line="";
			try {
				while((Line=bufferReader.readLine())!=null){
					text+=Line+'\n';
					//in+=Line+newline;
				}
				
				bufferReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return text;
		
	}



}
