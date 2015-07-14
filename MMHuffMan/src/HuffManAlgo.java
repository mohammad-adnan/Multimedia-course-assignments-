import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;

public class HuffManAlgo {
	char delemater=(char)27;//ESC (: escape ASCII code :)
	String newline = System.getProperty("line.separator");
	ArrayList<Result> result = new ArrayList();
	Frequenc[] frecArray;
	ArrayList<Tree> T = new ArrayList();//ArrayList of Tree
	Tree HMTree;// final Tree

	class Result {
		char c;
		int count;
		String code;
	}

	class Tree {
		String key;
		String code;
		Tree left, right;
	}

	class Frequenc implements Comparable<Frequenc> {
		String s;
		int count;

		@Override
		public int compareTo(Frequenc o) {
			// TODO Auto-generated method stub
			if (count > o.count)
				return -1;
			else if (count < o.count)
				return 1;
			else
				return 0;
		}
	}

	public static void main(String[] args) {
		HuffManAlgo hm = new HuffManAlgo();
		hm.v();

	}

	void v() {
//		String in = "123 adnan albukaai";
//		String out="010101000010010110010101100011001100101110001001111000001111";
//		String HMC="1 0101 2 01000 3 01001 * 011 a 00 d 1010 n 110 l 1011 b 1000 u 1001 k 1110 i 1111";
		String in = "pppppmmmc\r\nali\r\nabd\r\n";
		String out="00000000001111110101011100010000100101110010101011";
		String HMC="p@00@m@11@c@0101@\n@011@a@100@l@01000@i@01001@b@1010@d@1011";
	
//111111111111111111000100111100010010101011000\np 11\nm 11\nc 11\n\n 000\na 100\nl 11\ni 11\nb 1010\nd 1011
		
		System.out.println(compress(in)+"\n___________________-");
		System.out.println();
		//System.out.println(deCompress(out,HMC));
		//pt(HMTree);
		//getCode(HMTree,'c');
		//System.out.println("plic\n".matches("pl.*"));
		//System.out.println("plic\n".contains("\n"));
//		for(Result r:result){
//			System.out.println(r.code+"   "+r.c);
	//	}
//		String o="";
//		String[] e =HMC.split("@");
//		for(String s:e)
//			System.out.println(s);
	}

	void pt(Tree t) {
		if (t != null) {
			//if(t.left==null)
			out.println(t.code + "  " + t.key + "  ");
			pt(t.left);
			pt(t.right);
		}
	}

	String deCompress(String out,String HMC/*table code*/) {
		result.clear();
		fillCodeVsymbol(HMC);//find symbol and its code then put them in result
		String in = "";
		while (out.length() != 0) {
			for (Result r : result) {
				if (out.startsWith(r.code)) {
					if(r.c=='\n')
						in += newline;
					else
					    in += r.c;
					if (r.code.length() == out.length())
						out = "";
					else
						out = out.substring(r.code.length());
					break;
				}
			}
		}
		return in;
	}

	String compress(String in) {
		result.clear();
		T.clear();
		
		fillresultWithSymbol(in);
		fillFrequenc();
		buildTree();
		HMTree = T.get(0);
		putHaffManCode(HMTree, "");
		fillresultWithHMcode();
		
		String out = "";
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			out += getCode(HMTree, c);
		}
		
		for(Result r:result){
			out+=newline+r.c+delemater+r.code;
		}
		return out;
	}
    void fillCodeVsymbol(String codes){
//    	while (codes.length() != 0) {
//    		Result r=new Result();
//    		r.c=codes.charAt(0);
//    		codes=codes.substring(2);
//    		r.code=codes.substring(0, codes.indexOf(delemater));
//    		result.add(r);
//					if (r.code.length() == codes.length()-1)
//						codes = "";
//					else
//						codes = codes.substring(r.code.length()+1);
//					
//			
//		}
    	String[] s=codes.split(String.valueOf(delemater));
    	for(int i=0;i<s.length;i++){
    		Result r=new Result();
    		r.c=s[i].charAt(0);
    		r.code=s[++i];
    		result.add(r);
    	}
    	
    }
	void fillresultWithSymbol(String input) {
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			boolean found = false;
			for (Result r : result) {
				if (r.c == c) {
					found = true;
					r.count++;
					break;
				}
			}

			if (!found) {
				Result r = new Result();
				r.c = c;
				r.count = 1;
				result.add(r);
			}
		}
	}

	void fillresultWithHMcode() {
		for (Result r : result) {
			r.code = getCode(HMTree, r.c);
		}
	}

	String getCode(Tree HMTree, char c) {
		if (HMTree.left == null)// is leaf
			return HMTree.code;
		else {
			if (HMTree.left.key.contains(String.valueOf(c)))
				return getCode(HMTree.left, c);
			else
				return getCode(HMTree.right, c);
		}
	}

	void putHaffManCode(Tree HMTree, String startcode) {
		if (HMTree.left != null) {//not leaf
			HMTree.left.code = startcode + '0';
			HMTree.right.code = startcode + '1';
			putHaffManCode(HMTree.left, startcode + '0');
			putHaffManCode(HMTree.right, startcode + '1');
		}
	}

	void buildTree() {
		if (frecArray.length == 1) {
			Tree leaf;
			leaf = new Tree();
			leaf.left = null;
			leaf.right = null;
			leaf.key = frecArray[0].s;
			T.add(leaf);

			return;
		}
		for (int i = frecArray.length - 1; i != 0; i--) {
			int lindex = inTree(frecArray[i - 1].s);
			int rindex = inTree(frecArray[i].s);
			Tree left;
			Tree right;
			Tree parent;
			// create left node
			if (lindex != -1) {// in tree
				left = T.get(lindex);
			} else {
				left = new Tree();
				left.left = null;
				left.right = null;
				left.key = frecArray[i - 1].s;
			}
			// create right node
			if (rindex != -1) {// in tree
				right = T.get(rindex);
			} else {
				right = new Tree();
				right.left = null;
				right.right = null;
				right.key = frecArray[i].s;
			}

			// create parent
			parent = new Tree();
			parent.left = left;
			parent.right = right;
			parent.key = left.key + right.key;

			// add to tree (T)

			if (lindex != -1) {
				T.remove(lindex);// remove left if found
				if (lindex < rindex)
					rindex--;
			}
			if (rindex != -1) {
				T.remove(rindex);// remove right if found
			}
			T.add(parent);// add parent

			// sort array
			frecArray[i - 1].count += frecArray[i].count;
			frecArray[i - 1].s += frecArray[i].s;
			Arrays.sort(frecArray, 0, i);
//			for(int y=0;y<i;y++)
//				System.out.println(frecArray[y].s+"  "+frecArray[y].count);
		}
	}

	int inTree(String key) {
		if (key.length() == 1)
			return -1;
		else {
			int i = 0;
			for (Tree t : T) {
				if (key.equals(t.key))
					return i;
				i++;
			}
			return -1;
		}
	}

	void fillFrequenc() {
		frecArray = new Frequenc[result.size()];
		for (int i = 0; i < result.size(); i++) {
			frecArray[i] = new Frequenc();
			frecArray[i].count = result.get(i).count;
			frecArray[i].s = "" + result.get(i).c;
		}
		Arrays.sort(frecArray, 0, frecArray.length);
//		for(Frequenc s:frecArray)
//			System.out.println(s.s+"  "+s.count);
	}

}
