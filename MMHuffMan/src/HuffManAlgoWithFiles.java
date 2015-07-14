import java.io.*;
public class HuffManAlgoWithFiles {
	String newline = System.getProperty("line.separator");
	HuffManAlgo huffManAlgo=new HuffManAlgo();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HuffManAlgoWithFiles g=new HuffManAlgoWithFiles();
		g.compress("t.txt", "tt.txt");
		g.decompress("tt.txt", "ttt.txt");
		
	}

	void compress(String src,String des){
		String in="";
		String out="";
		try {
			FileReader fileReader=new FileReader(src);
			BufferedReader bufferReader=new BufferedReader(fileReader);
			String Line="";
			try {
				while((Line=bufferReader.readLine())!=null){
					in+=Line+'\n';
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
//		System.out.println(in);
//		if(in.contains(String.valueOf(huffManAlgo.delemater)))
//				System.out.println("yesyesyesyesyesyesyesyes");
		out=huffManAlgo.compress(in);
		
//		try {
//			PrintWriter printWriter=new PrintWriter(des);
//			printWriter.print(out);
//			printWriter.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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
    void decompress(String src,String des){
    	String in="";
    	String HMC="";
		String out="";
		try {
			FileReader fileReader=new FileReader(src);
			BufferedReader bufferReader=new BufferedReader(fileReader);
			String Line="";
			try {
				Line=bufferReader.readLine();
				HMC=Line;
				while((Line=bufferReader.readLine())!=null){
					if(Line.isEmpty()){
						in+="\n";
						//in+=newline;
						Line=bufferReader.readLine();
						in+=Line+huffManAlgo.delemater;
						}
					else
					    in+=Line+huffManAlgo.delemater;
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
//		System.out.println(HMC);
//		System.out.println(in);
		out=huffManAlgo.deCompress(HMC,in);
//		System.out.println(out);
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
