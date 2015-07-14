import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class LinearQuantizerWithFile {
	LinearQuantizerAlgo linearQuantizerAlgo= new LinearQuantizerAlgo();
	void compress(String src,String des,int n){
		String out="";
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			linearQuantizerAlgo.in_Out_Put.clear();
			while (sc.hasNext()) {
	            int i = sc.nextInt();
	            linearQuantizerAlgo.in_Out_Put.add(i);
	        }
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
		out=linearQuantizerAlgo.compress(n);
		
		
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
		String input = null;
		int n = 0,min = 0,step = 0;
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			linearQuantizerAlgo.in_Out_Put.clear();
			input=sc.next();
			n=sc.nextInt();
			min=sc.nextInt();
			step=sc.nextInt();
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		linearQuantizerAlgo.deCompress(input, n, min, step);// decompress
		
		try {
			FileWriter fileWrite=new FileWriter(des);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWrite);
			for(Integer i:linearQuantizerAlgo.in_Out_Put)
			   bufferedWriter.write(i+" ");			
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
