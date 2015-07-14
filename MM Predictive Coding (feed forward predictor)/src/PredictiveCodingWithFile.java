import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PredictiveCodingWithFile {
	PredictiveCodingAlgo predictiveCodingAlgo= new PredictiveCodingAlgo();
	void compress(String src,String des,int n){
		String out="";
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			predictiveCodingAlgo.inPut.clear();
			while (sc.hasNext()) {
	            int i = sc.nextInt();
	            predictiveCodingAlgo.inPut.add(i);
	        }
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
		out=predictiveCodingAlgo.compress(n);
		
		
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
		ArrayList<Integer> input =new ArrayList<Integer>() ;
		int n = 0,min = 0,step = 0;
		File file = new File(src);
		Scanner sc;
		try {
			sc = new Scanner(file);
			predictiveCodingAlgo.inPut.clear();
			min=sc.nextInt();
			step=sc.nextInt();
			n=sc.nextInt();
			while (sc.hasNext()) {
	            int i = sc.nextInt();
	            input.add(i);
	        }
			predictiveCodingAlgo.outPut=new int[input.size()];
			for(int i=0 ;i<predictiveCodingAlgo.outPut.length;i++)
				predictiveCodingAlgo.outPut[i]=input.get(i);
				
	        sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PredictiveCodingAlgo.Quantizer quantizer=predictiveCodingAlgo.new Quantizer();
		quantizer.min=min;
		quantizer.n=n;
		quantizer.step=step;
		int[]outPut1=predictiveCodingAlgo.deCompress(quantizer);// decompress
		
		try {
			FileWriter fileWrite=new FileWriter(des);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWrite);
			for(Integer i:outPut1)
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
