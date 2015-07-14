import java.util.ArrayList;
import java.util.Collections;


public class LinearQuantizerAlgo {
	String newline = System.getProperty("line.separator");
	ArrayList<Integer>in_Out_Put=new ArrayList<Integer>(); 
	Integer min,max;

	public static void main(String[] args) {

		LinearQuantizerAlgo l=new LinearQuantizerAlgo();
		l.in_Out_Put.add(0);
		l.in_Out_Put.add(1);
		l.in_Out_Put.add(4);
		l.in_Out_Put.add(5);
		l.in_Out_Put.add(3);
		l.in_Out_Put.add(4);
		l.in_Out_Put.add(2);
		l.in_Out_Put.add(8);
//		l.in_Out_Put.add(6);
		//l.n=2;
		System.out.println(l.compress(2));
		//l.deCompress("00001010011001100", 2, 0, 2);
		for(Integer i:l.in_Out_Put){
			System.out.print(i+" ");
		}
	}
	String compress(int n){
		String out="";
		String code="";
		min_Max();
		int levelNum=(int)Math.pow(2, n);
		int step=(int) Math.ceil((double)(max-min)/levelNum);
		int level;
		for(Integer i:in_Out_Put){
			level=(i-min)/step;// find level for an integer
			code=Integer.toBinaryString(level);
			while(code.length()<n){
				code="0"+code;
			}
			if(code.length()>n)
				code=Integer.toBinaryString(level-1);
			out+=code;
			
		}
		out+=newline;
		out+=n+" "+" "+min+" "+step;
		return out;
	}
	void deCompress(String input,int n,int min,int step){
		in_Out_Put.clear();
		String code;
		int level,lower,upper,center;
		while(!input.isEmpty()){
			code=input.substring(0,n);
			input=input.substring(n);
			level =Integer.parseInt(code, 2);
			lower=level*step+min;
			upper=lower+step-1;
			center=(lower+upper)/2;
			in_Out_Put.add(center);
		}
	}
	void min_Max(){
		max=min=in_Out_Put.get(0);
		for(Integer i:in_Out_Put){
					if(i>max)
						max=i;
					else
						if(i<min)
							min=i;
				}
		//max = Collections.max(in_Out_Put)
	}
	

}
