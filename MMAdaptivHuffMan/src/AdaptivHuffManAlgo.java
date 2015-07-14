
public class AdaptivHuffManAlgo {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AdaptivHuffManAlgo a=new AdaptivHuffManAlgo();
//		String temp="01100001001100100000110111001011000010000010000110110010000001100010010001110101110000110101110101110001101001";
//		System.out.println(temp=a.compress("adnan labukaai"));
//		System.out.println(a.deCompress(temp));
//		//a.prt(a.tree.node);
//
//	}
	Tree tree;
	String out="";
	static int UnicodeORASCIIcode=8;
	
	AdaptivHuffManAlgo(){
		initialize();
		
	}
	void initialize(){
		tree=new Tree();
		tree.NYT=new Node();
		tree.NYT.code="";
		tree.NYT.counter=0;
		tree.node=tree.NYT;
		tree.node.parent=null;
		out="";
	}
	void prt(Node n){
		if(n!=null){
			System.out.println(n.symbol +n.code+"::"+n.counter+" "+n.number);
			prt(n.right);
			prt(n.left);
		}
	}
	class Node{
		String code;
		int number;
		int counter;
		char symbol;
		Node left,right;
		Node parent;
	}
	class Tree{
		Node node;
		Node NYT;
		Node nodeWithMaxNum=null;//node that i want to swap with it
		Node getSymbolNode(Node n,char c) {
			if(n==null)
				return null;
			else
				if(n.symbol==c&& n.left==null)//node leaf
					return n;
				else{
					Node temp=getSymbolNode(n.right,c);
					if(temp==null)
						temp=getSymbolNode(n.left,c);
					return temp;
				}
		}
		void splitNYTNode(){
			NYT.right=new Node();
			NYT.right.parent=NYT;
			NYT.right.counter=0;
			NYT.right.left=null;
			NYT.right.right=null;
			
			NYT.left=new Node();
			NYT.left.parent=NYT;
			NYT.left.counter=0;
			NYT.left.left=null;
			NYT.left.right=null;
		}
		public void putHuffManCode(Node currentNode) {
			if(currentNode!=null){
				if(currentNode.parent==null){
					currentNode.code="";
					putHuffManCode(currentNode.right);
					putHuffManCode(currentNode.left);
				}
				else{
					if(currentNode.parent.right==currentNode)
						currentNode.code=currentNode.parent.code+"1";
					else
						currentNode.code=currentNode.parent.code+"0";
					putHuffManCode(currentNode.right);
					putHuffManCode(currentNode.left);
				}
				
			}
		}
		public void putNumber(Node currentNode) {
			if(currentNode!=null){
				if(currentNode.parent!=null){				
					if(currentNode.parent.parent==null){//current node is root child
						if(currentNode.parent.right==currentNode)
							currentNode.number=currentNode.parent.number-1;
						else
							currentNode.number=currentNode.parent.number-2;
						}
					else{
						if(currentNode.parent.left==currentNode)// left child
							currentNode.number=currentNode.parent.number-2;
						else// right child
							if(currentNode.parent.parent.right==currentNode.parent)// current node father is right child
								currentNode.number=currentNode.parent.number-2;
							else// current node father is left child
								currentNode.number=currentNode.parent.number-3;
					}
				}
				putNumber(currentNode.right);
				putNumber(currentNode.left);
			}
		}
		Node needSwap(Node currentNode) {
			setSwappedNode(tree.node,currentNode);
			return nodeWithMaxNum;
		}
		void setSwappedNode(Node node, Node currentNode) {
			if(node!=null){
				if(currentNode.parent!=null/*not root*/){
					if(currentNode.number<node.number && currentNode.counter>=node.counter && !currentNode.code.startsWith(node.code)/*node not parent tocurrentNode*/){
						if(nodeWithMaxNum==null)
							nodeWithMaxNum=node;
						else
							if(nodeWithMaxNum.number<node.number)
								nodeWithMaxNum=node;
					}
					
					
					setSwappedNode(node.right,currentNode);
					setSwappedNode(node.left,currentNode);
					
					
				}
				else
					nodeWithMaxNum=null;
			}
			
		}
		public void swap(Node currentNode, Node y) {
			Node currentNodeParent=currentNode.parent;
			Node yParent=y.parent;
			
			currentNode.parent=yParent;
			y.parent=currentNodeParent;
			
			if(currentNodeParent.right==currentNode)// is currentNode right child
				currentNodeParent.right=y;
			else //currentNode left child
				currentNodeParent.left=y;
			
			if(yParent.right==y)// is y right child
				yParent.right=currentNode;
			else //y left child
				yParent.left=currentNode;
			
			tree.putHuffManCode(currentNode);
			tree.putNumber(currentNode);
			
			tree.putHuffManCode(y);
			tree.putNumber(y);
		}
	}
	
	String compress(String in){
		initialize();
		tree.NYT.number=2*in.length();
		char c;
		for(int i=0;i<in.length();i++){
			c=in.charAt(i); //read symbol
			if(firstOccurance(c)){
				if(i!=0){//except first character in string 
					sendNYTcode();
				}
				sendSymbolCode(c);
			}
			else{
				sendSymbolCode(c);
			}
			updateTree(c);
			
		}
		return out;
	}
	String deCompress(String in ){
		initialize();
		String code=in.substring(0,UnicodeORASCIIcode);
		char c=(char)Integer.parseInt(code, 2);
		updateTree(c);
		out+=c;
		in=in.substring(UnicodeORASCIIcode);
		while(!in.isEmpty()){
			if(in.startsWith(tree.NYT.code))
			{
				in=in.substring(tree.NYT.code.length());
				code=in.substring(0,UnicodeORASCIIcode);
				c=(char)Integer.parseInt(code, 2);
				in=in.substring(UnicodeORASCIIcode);
			}
			else
			{
				//go to symbol node
				Node n=tree.node;
				code="";
				boolean found=false;
				int i=0;
				while(!found&& i<in.length()){
					c=in.charAt(i);
					if(n.left==null)//leaf
						found=true;
					else{
						if(c=='1')
							n=n.right;
						else
							n=n.left;
					}
					i++;
				}
				c=n.symbol;
				in=in.substring(n.code.length());
			}
			out+=c;
			updateTree(c);
		}
	return out;		
	}
    void updateTree(char c) {
		Node currentNode=tree.getSymbolNode(tree.node, c);
		if(currentNode==null){//first occurance
			tree.splitNYTNode();
			currentNode=tree.NYT.right;
			tree.NYT=tree.NYT.left;
			currentNode.symbol=c;
			currentNode.counter++;//increment counter of symbol node
			currentNode.parent.counter++;//increment counter in parent node
			currentNode=currentNode.parent;// goto parent
			tree.putHuffManCode(currentNode);
			tree.putNumber(currentNode);
		}
		else{
			//"go to symbol node" but node already in symbol node
			Node Y=tree.needSwap(currentNode);
			if(Y!=null)//need swap
				tree.swap(currentNode,Y);//swap
			currentNode.counter++;//increment counter of symbol node
		}

		
		while(currentNode.parent!=null){ // while not root
			currentNode=currentNode.parent;// goto parent
			tree.nodeWithMaxNum=null;
			Node Y=tree.needSwap(currentNode);
			if(Y!=null)//need swap
				tree.swap(currentNode,Y);//swap
			currentNode.counter++;//increment counter of symbol node
			
		}
	}
	void sendSymbolCode(Character c) {
		Node n=tree.getSymbolNode(tree.node, c);
		if(n!=null)
			out+=n.code;
		else{
			String code=Integer.toBinaryString(c.hashCode());
			while(code.length()<UnicodeORASCIIcode)
				code="0"+code;
			out+=code;
			}
	}
	void sendNYTcode() {
		out+=tree.NYT.code;
	}
	boolean firstOccurance(char c){
		if(tree.getSymbolNode(tree.node, c)==null)
			return true;
		else
			return false;
		
	}

}
