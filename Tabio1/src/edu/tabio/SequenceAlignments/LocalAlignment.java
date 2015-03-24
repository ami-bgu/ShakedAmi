package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public class LocalAlignment extends Alignment {

	public LocalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}
/*
	private int maxOutOfThree(int a, int b, int c){
		if (a>=b && a>=c) return a;
		if (b>=c) return b;
		return c;
	}
	
	
	private int maxOutOfFour(int a, int b, int c ,int d){
		return Math.max(maxOutOfThree(a, b, c), d);
	}
	*/
	private Cell getMaxInMatrix(Cell[][] matrix){	
		Cell c = null;
		int max = 0;
		for (int i = 0; i < matrix.length; i++){ 
			for (int j = 0; j < matrix[0].length; j++) {
				if (mat[i][j].getValue() > max){
					max = mat[i][j].getValue();
					c = mat[i][j];
				}
			}
		}
		return c;
	}
	
	@Override
	public void printResult() {
		fillMatrices();
		
		String[] algn;
		Cell c = getMaxInMatrix(mat);
		algn = c.alignment();
		System.out.println(algn[0]);
		System.out.println(algn[1]);
		System.out.println(c.getValue());
		
	}

	@Override
	protected void fillMatrices() {
		System.out.println("Filling mat using Local Alignment");
		int[] arr = new int[4];	//[0]=left [1]=up [2]=diagonal [3]=zero

		int a,b,c;
		
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
//				a = mat[i-1][j-1] + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
//				b = mat[i-1][j] +  sbm.score(sequenceA.charAt(i), ' ');
//				c = mat[i][j-1] +  sbm.score(' ', sequenceB.charAt(j));
//				mat[i][j] = maxOutOfFour(a, b, c, 0);
				arr[0] = mat[i][j-1].getValue() +  sbm.score(' ', sequenceB.charAt(j));
				arr[1] = mat[i-1][j].getValue() +  sbm.score(sequenceA.charAt(i), ' ');
				arr[2] = mat[i-1][j-1].getValue() + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
				arr[3] = 0;
				int index = maxIndexInArray(arr);
				if		( index == 0) 	mat[i][j] = new Cell(arr[index], mat[i][j-1], "_" , sequenceB.charAt(j)+"");
				else if	( index == 1) 	mat[i][j] = new Cell(arr[index], mat[i-1][j], sequenceA.charAt(i)+"" , "_");
				else if	( index == 2) 	mat[i][j] = new Cell(arr[index], mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
				else					mat[i][j] = new Cell();
			}
		}
				
	}

}
