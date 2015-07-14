import java.io.*;


public class NormalArithmeticWithFile {
	NormalArithmeticAlgo AA=new NormalArithmeticAlgo();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NormalArithmeticWithFile g=new NormalArithmeticWithFile();
		g.compress("t.txt", "tt.txt");
		g.deCompress("tt.txt", "ttt.txt");

	}
	void compress(String src,String des){
		String input="";
		try {
			FileReader fileReader=new FileReader(src);
			BufferedReader bufferReader=new BufferedReader(fileReader);
			String Line="";
			try {
				while((Line=bufferReader.readLine())!=null){
					input+=Line+'\n';
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
		
		compressedObject CO=AA.compress(input);
		
		try {
			OutputStream file = new FileOutputStream(des);
			OutputStream buffer = new BufferedOutputStream(file);
		    ObjectOutput output = new ObjectOutputStream(buffer);
		    output.writeObject(CO);
		    output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	void deCompress(String src,String des){
		String output = "";
		try {
			InputStream file = new FileInputStream(src);
			InputStream buffer = new BufferedInputStream(file);
		    ObjectInput input = new ObjectInputStream (buffer);
		    
		    compressedObject CO=(compressedObject) input.readObject();
		    output=AA.deCompress(CO);
		    input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileWriter fileWrite=new FileWriter(des);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWrite);
			bufferedWriter.write(output);
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
