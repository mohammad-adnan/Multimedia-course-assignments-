
public class try1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] center={5,4,3,2};
		int[] data={10,13,8,7,6,7,2};
		for(int k=0;k<center.length;k++)
			System.out.print(center[k]+"\t");
		System.out.println();
		for(int i=0;i<data.length;i++){
			System.out.print(data[i]+"\t");
			for(int j=0;j<center.length;j++){
				System.out.print((int)Math.pow(data[i]-center[j],2)+"\t");
			}
			System.out.println();
		}

	}

}
