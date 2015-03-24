package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public class GlobalAlignment extends Alignment{

	
	public GlobalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
		// TODO Auto-generated constructor stub
	}

	
	private Cell maxInRow(Cell[][] matrix, int i){
		int max = mat[i][0].getValue();
		Cell c= mat[i][0];
		for (int j = 1; j < matrix[0].length; j++) {
			if(mat[i][j].getValue() > max){
				max = mat[i][j].getValue();
				c = mat[i][j];
			}
		}
		return c;
	}
	
	private Cell maxInCol(Cell[][] matrix, int j){
		int max = mat[0][j].getValue();
		Cell c= mat[0][j];
		for (int i = 1; i < matrix.length; i++) {
			if (mat[i][j].getValue() > max){
				max = mat[i][j].getValue();
				c = mat[i][j];
			}
		}
		return c;
	}
	

	
	@Override
	public void printResult() {
		fillMatrices();
		String[] algn;
		Cell c;
		Cell colMax = maxInCol(mat, mat[0].length-1);
		Cell rowMax = maxInRow(mat, mat.length-1);
		if (colMax.getValue() > rowMax.getValue())		c = colMax;
		else 											c = rowMax;
		algn = c.alignment();
		System.out.println(algn[0]);
		System.out.println(algn[1]);
		System.out.println(c.getValue());
	}


	@Override
	protected void fillMatrices() {
		System.out.println("Filling mat using Global Alignment");
		int[] arr = new int[3];	//[0]=left [1]=up [2]=diagonal
		
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {

				arr[0] = mat[i][j-1].getValue() +  sbm.score(' ', sequenceB.charAt(j));
				arr[1] = mat[i-1][j].getValue() +  sbm.score(sequenceA.charAt(i), ' ');
				arr[2] = mat[i-1][j-1].getValue() + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
				int index = maxIndexInArray(arr);
				if		( index == 0) mat[i][j] = new Cell(arr[index], mat[i][j-1], "_" , sequenceB.charAt(j)+"");
				else if	( index == 1) mat[i][j] = new Cell(arr[index], mat[i-1][j], sequenceA.charAt(i)+"" , "_");
				else if	( index == 2) mat[i][j] = new Cell(arr[index], mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");

				//System.out.println("i:"+i+" j:"+j+";");
				//printMat();
			}
		}
	
	}


}
