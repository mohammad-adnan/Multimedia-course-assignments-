/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LZ78;
import java.util.ArrayList;

/**
 *
 * @author FCI User
 */
class Tag{
    public int Index;
    public char NextChar;
}
class Comm{
    String Input="abaababaababbbbbbbbbbb";
    ArrayList Dectionary;
    ArrayList Tags;
    Comm(){
        Dectionary= new ArrayList<String>();
        Dectionary.add(0, "");
        Tags=new ArrayList<Tag>();
    }
    public int SearchInDec(String Target){
        int index=-1;
        //System.out.println(Target);
        for(int i=0;i<Dectionary.size();i++ )
        {
        	//System.out.println(((String)Dectionary.get(i)));
            if (Target.equals(Dectionary.get(i)))
            return i;
        }
        return index;
    }
    
    public void Compress(){
        
        int Start=0,End=1;
        while(End<=Input.length()){
            int Index=SearchInDec(Input.substring(Start, End));
            //System.out.println(Input.substring(Start, End)+Index);
            if(Index!=-1)// found
            {//System.out.println("Here"+End+"   "+Input.length());
                End++;
                if(End>Input.length())
                {
                	//System.out.println("Here"+End+"   "+Input.length());
                    Tag temp=new Tag();
                        temp.Index=Index;
                        temp.NextChar=0;
                        Tags.add(temp);
                        Dectionary.add(Input.substring(Start, End-1));
                }
            }
            else
            {
                if(Start!=1+End)//not one char
                {
                     Index=SearchInDec(Input.substring(Start, End-1));
                    if(Index!=-1)
                    {
                        Tag temp=new Tag();
                        temp.Index=Index;
                        temp.NextChar=Input.charAt(End-1);
                        Tags.add(temp);
                        Dectionary.add(Input.substring(Start, End));
                        
                        Start=End;
                        End++;
                    }
                    
                }
                else
                {
                    Tag temp=new Tag();
                        temp.Index=0;
                        temp.NextChar=Input.charAt(End-1);
                        Tags.add(temp);
                        Dectionary.add(Input.substring(Start, End));
                         Start=End;
                        End++;
                }
            }
        }
    }
    public void Display(){
        for(int i=0;i<Tags.size();i++)
        {
            Tag T=(Tag)Tags.get(i);
            System.out.print("<");
            System.out.print(T.Index);
            System.out.print(",");
            System.out.print(T.NextChar);
            System.out.print(">");
            System.out.print(",");
        }
    }
    
}
public class LZ78 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        Comm c=new Comm();
        
//        for(int i=0;i<c.Dectionary.size();i++)
//        	System.out.println((String)c.Dectionary.get(i));
//        String s="tytyt";
//        if(s.equals((String)c.Dectionary.get(0)))
        System.out.println(c.Input);
        c.Compress();
        c.Display();
    }
}
