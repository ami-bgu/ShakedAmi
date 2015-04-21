package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public class LocalAlignment extends Alignment {

	public LocalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}

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
	protected Cell calculateEndCell() {
		return getMaxInMatrix(mat);
	}

	private	int[] arr = new int[4];	//[0]=left [1]=up [2]=diagonal [3]=zero

	@Override
	protected void ij_operation(int i, int j) {
		arr[0] = mat[i][j-1].getValue() +  sbm.score(' ', sequenceB.charAt(j));
		arr[1] = mat[i-1][j].getValue() +  sbm.score(sequenceA.charAt(i), ' ');
		arr[2] = mat[i-1][j-1].getValue() + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
		arr[3] = 0;
		int index = maxIndexInArray(arr);
		if		( index == 0) 	mat[i][j] = new Cell(arr[index], mat[i][j-1], "_" , sequenceB.charAt(j)+"");
		else if	( index == 1) 	mat[i][j] = new Cell(arr[index], mat[i-1][j], sequenceA.charAt(i)+"" , "_");
		else if	( index == 2) 	mat[i][j] = new Cell(arr[index], mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
		else					mat[i][j] = new Cell();	//empty cell with no parent
	}

}
