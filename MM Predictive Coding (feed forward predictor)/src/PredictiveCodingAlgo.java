import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;


public class PredictiveCodingAlgo {

	ArrayList<Integer>inPut=new ArrayList<Integer>();
	int[]outPut;
	Quantizer quantizer;
	
	String newline = System.getProperty("line.separator");
	
	class Quantizer{
		int min,step;
		int n;
	}
	public static void main(String[] args) {
		PredictiveCodingAlgo predictiveCodingAlgo=new PredictiveCodingAlgo();
		predictiveCodingAlgo.inPut.add(33);
		predictiveCodingAlgo.inPut.add(37);
		predictiveCodingAlgo.inPut.add(42);
		predictiveCodingAlgo.inPut.add(64);
		predictiveCodingAlgo.inPut.add(70);
		predictiveCodingAlgo.inPut.add(71);
		predictiveCodingAlgo.inPut.add(73);
		predictiveCodingAlgo.inPut.add(75);
		predictiveCodingAlgo.inPut.add(77);
		
		predictiveCodingAlgo.compress(2);
		int[] outPut1=predictiveCodingAlgo.deCompress(predictiveCodingAlgo.quantizer);
		System.out.println("------------------------------------");
		for(Integer i: predictiveCodingAlgo.outPut){
			System.out.println(i);
		}
		
		System.out.println("------------------------------------");
		int i=0;
		for(Integer ii: predictiveCodingAlgo.inPut){
			System.out.println(outPut1[i]+" "+ ii);
			i++;
		}

	}
	
	String compress(int n){
		createQuanizerForDifferentiation(n);
		outPut=new int[inPut.size()];
		outPut[0]=inPut.get(0);// send first number as is
		int previousSample=outPut[0];
		
		inPut.remove(0);//delete first number in input list
		int index=1;
		int differentiation;
		for(Integer i : inPut){
			differentiation= i- previousSample;
			outPut[index]=getCode(differentiation);
			index++;
			previousSample+=getCenterForDifferentiation(differentiation);			
		}
		
		String out="";
		out+=this.quantizer.min+" ";
		out+=this.quantizer.step+" ";
		out+=this.quantizer.n+newline;
		for (int i=0;i<outPut.length;i++){
			out+=outPut[i]+" ";
		}
		
		return out;
	}
	
	int[]deCompress(Quantizer quantizer){
		this.quantizer=quantizer;
		int[]outPut1=new int[this.outPut.length];
		outPut1[0]=this.outPut[0];
		int previousSample=outPut1[0];
		for(int i=1;i<outPut1.length;i++){
			outPut1[i]=previousSample+getCenter(this.outPut[i]);
			previousSample=outPut1[i];
		}
		return outPut1;
	}

	private int getCenter(int level) {
		int lower,upper,center;
		lower=level*this.quantizer.step+this.quantizer.min;
		upper=lower+this.quantizer.step-1;
		center=(int)Math.ceil((lower+upper)/(double)2);
		return center;
	}

	private void createQuanizerForDifferentiation(int n) {
		ArrayList<Integer>differentiation=new ArrayList<Integer>();
		//get differentiation
		int previous=inPut.get(0);
		int currentElement;
		ListIterator<Integer> iterator=inPut.listIterator(1);
		
		while(iterator.hasNext()){
			currentElement=iterator.next();
			differentiation.add(currentElement-previous);
			previous=currentElement;
		}
		for(Integer i:differentiation){
			System.out.println(i);
		}
		
		// end differentiation
		
		//create quantizer
		Quantizer quantizer=new Quantizer();
		int max=Collections.max(differentiation);
		quantizer.min=Collections.min(differentiation);
		quantizer.n=n;
		
		int levelNum=(int)Math.pow(2, quantizer.n);
		quantizer.step=(int) Math.ceil((double)(max-quantizer.min)/levelNum);
		this.quantizer=quantizer;
		//end create quantizer
	}

	private int getCenterForDifferentiation(int differentiation) {
		int level,lower,upper,center;
		level=(differentiation-this.quantizer.min)/this.quantizer.step;// find level for an integer
		level=((int)Math.pow(2, quantizer.n)==level? level-1:level);
		lower=level*this.quantizer.step+this.quantizer.min;
		upper=lower+this.quantizer.step-1;
		center=(int)Math.ceil((lower+upper)/(double)2);
		return center;
	}

	private int getCode(int differentiation) {
		int level;
		level=(differentiation-this.quantizer.min)/this.quantizer.step;// find level for an integer
		level=((int)Math.pow(2, quantizer.n)==level? level-1:level);
		return level;
	}
}
