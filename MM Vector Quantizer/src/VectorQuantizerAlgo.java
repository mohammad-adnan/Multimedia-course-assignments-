import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

class CompressedImage implements Serializable{
	Vector[] codeBook;
	String[][] compressedPixels;
	int n;
}
class Vector implements Serializable{
	int[][] center=new int[VectorQuantizerAlgo.vectorSize][VectorQuantizerAlgo.vectorSize];
	String code;
}
public class VectorQuantizerAlgo{
	static final int vectorSize=8;
	int[][] pixels;
	Vector[] codeBook;
	String[][] compressedPixels;
	Group groups[];
	ArrayList<int[][]> vectorInImage=new ArrayList<int[][]>();
	class Group{
		int[][] center=new int[vectorSize][vectorSize];
		ArrayList<int[][]> vectors=new ArrayList<int[][]>();
	}
	
	
	

	public static void main(String[] args) {

		int[][] m1={{0,2},{2,3}};
		int[][] m2={{1,2},{2,5}};

		VectorQuantizerAlgo val=new VectorQuantizerAlgo();
		vectorQuantizerWithFile v=new vectorQuantizerWithFile();
		int[][] pixels=v.readImage("C:/Users/ADNAN/Desktop/cercle.jpg");
		pixels=val.deCompress(val.compress(pixels,4));
		v.writeImage(pixels,pixels.length,pixels[0].length,"C:/Users/ADNAN/Desktop/xx.jpg");
		
	}
	
	void printMatrix(int[][] m1){
		if(m1==null)
			System.out.println("matrix==null in printMatrix method");
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1[0].length;j++)
				System.out.print(m1[i][j]+"  ");
			System.out.println();
			}
		System.out.println();
	}
	static int[][] mClone(int[][] m){
		if(m==null)
			return null;
		int[][] mm=new int[m.length][m[0].length];
		for(int i=0;i<m.length;i++)
			for(int j=0;j<m[0].length;j++)
				mm[i][j]=m[i][j];
		return mm;
	}
	
	static boolean mEqsual(int[][] m1,int[][] m2){
		if(m1.length!=m2.length || m1[0].length!=m2[0].length )
			return false;
		for(int i=0;i<m1.length;i++)
			if(!Arrays.equals(m1[i], m2[i]))
				return false;
		return true;
	}
	
	int[][] deCompress(CompressedImage com){
		codeBook=com.codeBook;
		compressedPixels=com.compressedPixels;
		
		pixels=new int[compressedPixels.length*vectorSize][compressedPixels[0].length*vectorSize];
		vectorInImage=new ArrayList<int[][]>();
		

		int index=0;
		for(int i=0;i<compressedPixels.length;i++)
			for(int j=0;j<compressedPixels[i].length;j++){
				String code=compressedPixels[i][j];
				for(index=0;index<codeBook.length;index++)
					if(codeBook[index].code.equals(code))
						break;

				
				vectorInImage.add(codeBook[index].center);
			}
		addVectorToImage();
		
		return pixels;
	}
	
	CompressedImage compress(int[][] pixels,int n){
		this.pixels=pixels;
		initializeCodeBook(n);
		while(destrebuteVector()){
			for(int i=0;i<groups.length;i++)
				groups[i].vectors.clear();
		}
		
		//create codeBook
		codeBook= new Vector[groups.length];
		
		for(int i=0;i<groups.length;i++){
			codeBook[i]=new Vector();
			codeBook[i].center=groups[i].center;}
		
		for(int i=0;i<codeBook.length;i++){			
			codeBook[i].code=Integer.toBinaryString(i);
			while(codeBook[i].code.length()<n)
				codeBook[i].code='0'+codeBook[i].code;
		}
		
		
		//end create codeBook
		
		//create compressedPixels
		compressedPixels=new String[(int) Math.ceil((double)pixels.length/(double)vectorSize)][(int) Math.ceil((double)pixels[0].length/(double)vectorSize)];
		
		System.out.println("vectorInImage.size() = "+vectorInImage.size());
		int i=0,j=0;
		for(int[][] vector : vectorInImage){
			int index=findGroup(vector);
			
			if(j==compressedPixels[0].length){
				j=0;
				i++;
			}

			compressedPixels[i][j]=codeBook[index].code;
			j++;
			
		}
		
		//end create compressedPixels
		
		CompressedImage com=new CompressedImage();
		com.codeBook=codeBook;
		com.compressedPixels=compressedPixels;
		return com;
	}
	
	private boolean destrebuteVector() {
		for(int[][] vector : vectorInImage){
    		int index=findGroup(vector);
    		groups[index].vectors.add(mClone(vector));
    	}
		
		int[][] newAVG = null;
    	boolean groupsChange = false;
    	for(int i=0;i<groups.length;i++){
    		newAVG=avarage(groups[i].vectors);
			
			if(!mEqsual(newAVG,groups[i].center))
    			groupsChange=true;
    		groups[i].center=newAVG;
    	}
		return groupsChange;
	}

	private int findGroup(int[][] vector) {
		Double squareError,smallestSquareError=Double.POSITIVE_INFINITY;
    	int index=1;
    	
    	for (int i=0;i<groups.length;i++){
    		squareError=getSquareError(vector,groups[i].center);
    		if(squareError<smallestSquareError){
    			smallestSquareError=squareError;
    			index=i;
    		}
    	}
    	
		return index;
	}
	
	private Double getSquareError(int[][] vector1,int[][] center){
		Double squareError=0d;
		if(center==null)
			System.out.println("centxxer==null");
		if(vector1==null)
			System.out.println("vector1==null");
		for(int i=0;i<vector1.length;i++)
			for(int j=0;j<vector1[i].length;j++)
				squareError+=(Double) Math.pow(vector1[i][j]-center[i][j], 2);
		return squareError;
	}

	private int[][] avarage(ArrayList<int[][]> vectors) {
		if(vectors.size()==0)
			return null;
		long[][] sum=new long[vectorSize][vectorSize];
		for(int[][] vector : vectors)
			sum=add(sum,vector);
		
		int[][] sum1=new int[vectorSize][vectorSize];
		for(int i=0;i<sum.length;i++)
			for(int j=0;j<sum[i].length;j++)
				sum1[i][j]=(int)sum[i][j]/vectors.size();
		
		return sum1;
	}

	private long[][] add(long[][] m1, int[][] m2) {
		long[][] sum=new long[m1.length][m1.length];
	
		for(int i=0;i<m1.length;i++)
			for(int j=0;j<m1[i].length;j++)
				sum[i][j]=m1[i][j]+m2[i][j];
		
		return sum;
	}

	private void initializeCodeBook(int n) {
		groups=new Group[(int)Math.pow(2, n)];
		getVectorInImage();
		for(int i=0;i<groups.length;i++){
			groups[i]=new Group();
			groups[i].center=getRandomMatrix(i);
		}

	}

	private int[][] getRandomMatrix(int i) {
		int[][] m = null;
		for(int[][] vector :vectorInImage){
			boolean found=false;
			for(int j=0;j<i;j++)
				if(mEqsual(vector, groups[j].center)){
					found=true;
					break;
					}
			if(!found){
				m=mClone(vector);
				break;}
		}
		if(m==null){
			System.out.println("n very big\nSystem.exit(0); in getRandomMatrix method");
			System.exit(0);
			}
		return m;
	}

	void getVectorInImage(){
		vectorInImage=new ArrayList<int[][]>();
		for(int i=0;i<pixels.length;i+=vectorSize)
			for(int j=0;j<pixels[i].length;j+=vectorSize)
			{
				int[][] vector=getVector(i, j);
				vectorInImage.add(mClone(vector));
			}
	}
	
	int[][] getVector(int i,int j){
		int[][] vector=new int[vectorSize][vectorSize];
		
		int ii=0,jj=0;
		for( ii=0;ii<vectorSize && i<pixels.length ;ii++,i++)
			for(jj=0;jj<vectorSize && j<pixels[i].length ;jj++,j++)
				vector[ii][jj]=pixels[i][j];
		
		if(ii<vectorSize || jj<vectorSize){
			long sum=0;
			
			for(i=0;i<ii;i++)
				for(j=0;j<jj;j++)
					sum+=vector[i][j];
			
			int avarage=(int)sum/ii*jj;
			
			for(int k=ii;k<vectorSize;k++)
				for(int h=jj;h<vectorSize;h++)
					vector[k][h]=avarage;
		}
		return vector;
	}
	
	void addVectorToImage(){
		int index=0;
		for(int i=0;i<pixels.length;i+=vectorSize)
			for(int j=0;j<pixels[i].length;j+=vectorSize)
			{
				int[][] vector=vectorInImage.get(index);
				addVector(i, j,vector);
				index++;
			}
	}
	
	void addVector(int i,int j,int[][] vector){
		int ii=0,jj=0;
		for( ii=0;ii<vectorSize && i<pixels.length ;ii++,i++)
			for(jj=0;jj<vectorSize && j<pixels[i].length ;jj++,j++){
				pixels[i][j]=vector[ii][jj];}
		
		if(ii<vectorSize || jj<vectorSize){
			
			for(int iii=i, k=ii;k<vectorSize;k++,iii++)
				for(int jjj=j, h=jj;h<vectorSize;h++,jjj++)
					pixels[iii][jjj]=vector[k][h];
		}
	}
	
	
	}
