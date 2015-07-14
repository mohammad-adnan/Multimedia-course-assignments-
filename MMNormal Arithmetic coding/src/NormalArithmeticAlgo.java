import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class compressedObject implements Serializable{
	
	ArrayList<Frequency> frequency;
	int characterNum;
	double magicNum;
}

class Frequency  implements Comparable<Frequency>, Serializable{
	
	char c;
	double F;
	@Override
	public int compareTo(Frequency o) {
		return Character.valueOf(c).compareTo(o.c);
	}
}

public class NormalArithmeticAlgo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="";
		for(int i=1;i<=80;i++)
			s+="a";
		for(int i=1;i<=2;i++)
			s+="b";
		for(int i=1;i<=18;i++)
			s+="c";
		NormalArithmeticAlgo a=new NormalArithmeticAlgo();
		System.out.println(a.deCompress(a.compress("123456789")));
		//a.calculateFrequency(s);
		//System.out.println(a.F('3', -1));
//		a.input="acba";
//		System.out.println(a.magicNum(0,1,0));
		
//		a.prl();

	}
	
	ArrayList<Frequency> frequency = new ArrayList();
	String input="";
	String output="";	
	void prl(){
		for (Frequency f : frequency) {
			System.out.println(f.c+"  "+f.F);
		}
	}
	compressedObject compress(String input){
		this.input=input;
		frequency = new ArrayList();
		calculateFrequency(input);//calculate frequency and F(char)
		double magicNum=magicNum(0,1,0);
		compressedObject co=new compressedObject();
		co.characterNum=this.input.length();
		co.frequency=this.frequency;
		co.magicNum=magicNum;
		this.frequency=null;
		return co;
	}
	String deCompress(compressedObject CO){
		this.frequency=CO.frequency;
		return getOrigin(CO.magicNum,CO.characterNum,0,1,0);
	}
	String getOrigin(double magicNum, int characterNum,double lower,double upper,int i) {
		if(i==characterNum)
			return output;
		else{
			boolean firstChar=true;
			double l,u;
			Frequency previous=null;
			for(Frequency f:frequency){
				if(firstChar){
					l=lower+(upper-lower)*0;
					u=lower+(upper-lower)*f.F;
				}
				else
				{
					l=lower+(upper-lower)*previous.F;
					u=lower+(upper-lower)*f.F;
				}
				
				if(magicNum>l&&magicNum<u){
					output+=f.c;
					return getOrigin(magicNum,characterNum,l,u,++i);
				}
				previous=f;
				firstChar=false;
			}
			return output;
		}
		
	}
	void calculateFrequency(String input) {
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			boolean found = false;
			for (Frequency f : frequency) {
				if (f.c == c) {
					found = true;
					f.F++;
					break;
				}
			}

			if (!found) {
				Frequency f = new Frequency();
				f.c = c;
				f.F = 1;
				frequency.add(f);
			}
		}
		
		Collections.sort(frequency);
		// calculate F(char)
		double F_1=0;
		for (Frequency f : frequency) {
			f.F=f.F/input.length() +F_1;
			F_1=f.F;
		}
	}
	double F(char c,int position){//find F(char)
		boolean firstchar=true;
		Frequency previous=null;
		for (Frequency f : frequency) {
			if (f.c == c) {
				if(position==-1){//want previous Symbol
					if(firstchar)
						return 0;
					else
						return previous.F;
						
			    }
				else
					return f.F;
			}
			previous=f;
			firstchar=false;
		}
		return -1;
	}
	double magicNum(double lower,double upper,int i){
		if(i==input.length())
			return (lower+upper)/2;
		else
		{
			double l=lower+(upper-lower)*F(input.charAt(i),-1);
			double u=lower+(upper-lower)*F(input.charAt(i),1);
			//System.out.println(l+"  "+u);
			return magicNum(l,u,++i);
		}
	}

}

