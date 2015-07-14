import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class LoyedMaxQuantizerAlgo {
	String newline = System.getProperty("line.separator");
	ArrayList<Float>in_Out_Put=new ArrayList<Float>();
	Group[] groups;
	Range[] table;
	public static void main(String[] args) {
		LoyedMaxQuantizerAlgo l=new  LoyedMaxQuantizerAlgo();
		l.in_Out_Put.add(2f);
		l.in_Out_Put.add(20f);
		l.in_Out_Put.add(7f);
		l.in_Out_Put.add(6f);
		l.in_Out_Put.add(12f);
		l.in_Out_Put.add(8f);
		l.in_Out_Put.add(3f);
		l.in_Out_Put.add(4f);
		l.in_Out_Put.add(7f);
		l.in_Out_Put.add(14f);
		l.in_Out_Put.add(3f);

		//System.out.println(l.compress(2));
		System.out.println(l.deCompress(l.compress(2)));
	}
	String deCompress(String in){
		Scanner s=new Scanner(in);
		
		String input=s.next();
		int n=s.nextInt();
		Range[] table = new Range[(int) Math.pow(2,n)];
		
		for(int i=0;i<table.length;i++){
			table[i]=new Range();
			table[i].code=s.next();
			table[i].center=s.nextFloat();
			table[i].lower=s.nextFloat();
			table[i].upper=s.nextFloat();
		}
		String code="",out="";
		
		while(!input.isEmpty()){
			code=input.substring(0,n);
			input=input.substring(n);
			int i;
			for(i=0;i<table.length;i++)
				if(code.equals(table[i].code))
						break;
			out+=table[i].center+" ";
		}
		return out;
	}
	String compress(int n){
		String out="";
		initializeGroups(n);
		while(destrebuteNumbers()){
			for(int i=0;i<groups.length;i++)
				groups[i].numbers.clear();
		}
		
		//create table
		for(int i=0;i<groups.length;i++)
			table[i].center=groups[i].center;
		Arrays.sort(table);
		for(int i=0;i<table.length;i++){			
			table[i].code=Integer.toBinaryString(i);
			while(table[i].code.length()<n)
				table[i].code='0'+table[i].code;
			
			if(i==0)
				table[i].lower=Float.NEGATIVE_INFINITY;
			else
				table[i].lower=table[i-1].upper;
			
			if(i==table.length-1)
				table[i].upper=Float.POSITIVE_INFINITY;
			else
				table[i].upper=(table[i].center+table[i+1].center)/2;
		}
		//end create table
		for(Float f:in_Out_Put){
			int i;
			for(i=0;i<table.length;i++)//find range
				if(f>=table[i].lower && f<table[i].upper)
					break;
			out+=table[i].code;
		}
		out+=newline;
		out+=n+newline;
		for(int i=0;i<table.length;i++)
			out+=table[i].code+'\t'+
					table[i].center+'\t'+
					table[i].lower+'\t'+
					table[i].upper+newline;
		in_Out_Put.clear();
		return out;
	}
    boolean destrebuteNumbers() {
    	for(Float i : in_Out_Put){
    		int index=findGroup(i);
    		groups[index].numbers.add(i);
    	}
    	float newAVG;
    	boolean groupsChange = false;
    	for(int i=0;i<groups.length;i++){
    		newAVG=avarage(groups[i].numbers);
			if(newAVG!=groups[i].center)
    			groupsChange=true;
    		groups[i].center=avarage(groups[i].numbers);
    	}
		return groupsChange;
	}
    float avarage(ArrayList<Float> numbers) {
    	float sum=0;
    	for(Float i:numbers )
    		sum+=i;
		return sum/numbers.size();
	}
	int findGroup(Float input) {
    	float squareError,smallestSquareError=(float) Math.pow(input-groups[0].center, 2);
    	int index=0;
    	
    	for (int i=1;i<groups.length;i++){
    		squareError=(float) Math.pow(input-groups[i].center, 2);
    		if(squareError<smallestSquareError){
    			smallestSquareError=squareError;
    			index=i;
    		}
    			}
		return index;
	}
//	boolean groupsChange() {
//    	for(int i=0;i<groups.length;i++)
//    		if(!groups[i].numbers.equals(previousGroups[i].numbers))
//    			return true;
//		return false;
//	}
	void initializeGroups(int n) {
		groups=new Group[(int)Math.pow(2, n)];
		table=new Range[(int)Math.pow(2, n)];
		for(int i=0;i<groups.length;i++){
			groups[i]=new Group();
			groups[i].center=in_Out_Put.get(i);
			
			table[i]=new Range();
		}
		
	}

}

class Group{
	float center;
	ArrayList<Float>numbers=new ArrayList<Float>();
}

class Range implements Comparable<Range>{
	String code;
	float center;
	float lower,upper;
	@Override
	public int compareTo(Range o) {
		return Float.valueOf(center).compareTo(o.center);
	}
}
