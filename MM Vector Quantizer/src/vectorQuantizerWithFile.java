import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;


public class vectorQuantizerWithFile {
	 VectorQuantizerAlgo vectorQuantizerAlgo=new VectorQuantizerAlgo();
	public static void main(String[] args) {
		vectorQuantizerWithFile v=new vectorQuantizerWithFile();
		v.compress("C:/Users/ADNAN/Desktop/cercle.jpg", "C:/Users/ADNAN/Desktop/out.com", 5);
		v.deCompress("C:/Users/ADNAN/Desktop/out.com", "C:/Users/ADNAN/Desktop/out.jpg");
	}
	public void compress(String src ,String dis,int n){
		int[][] input= readImage(src);
		CompressedImage compressedImage =vectorQuantizerAlgo.compress(input, n);
		
		try {
			OutputStream file = new FileOutputStream(dis);
			OutputStream buffer = new BufferedOutputStream(file);
		    ObjectOutput output = new ObjectOutputStream(buffer);
		    output.writeObject(compressedImage);
		    output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public void deCompress(String src ,String dis){
		int[][] output = null;
		try {
			InputStream file = new FileInputStream(src);
			InputStream buffer = new BufferedInputStream(file);
		    ObjectInput input = new ObjectInputStream (buffer);
		    
		    CompressedImage compressedImage=(CompressedImage) input.readObject();
		    output=vectorQuantizerAlgo.deCompress(compressedImage);
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
		
		writeImage(output,output.length,output[0].length, dis);
		
	}
	public  int[][] readImage(String filePath) {
        File file=new File(filePath);
        
        BufferedImage image=null;
        
        try {
            image=ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width=image.getWidth();
        int height=image.getHeight();
        int[][] pixels=new int[height][width];
        
        
        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
                int rgb=image.getRGB(x, y);
                int alpha=(rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;
                
                pixels[y][x]=r;
            }
        }
        
        
        
        return pixels;
    }
    
    public  void writeImage(int[][] pixels,int height,int width,String outputFilePath) {
        
        File fileout=new File(outputFilePath);
        
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        
        
        for(int x=0;x<width ;x++)
            for(int y=0;y<height;y++) {
            image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        
        try {
            ImageIO.write(image2, "jpg", fileout);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
    
    
}
