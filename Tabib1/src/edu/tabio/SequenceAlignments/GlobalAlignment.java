package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public class GlobalAlignment extends Alignment{

	
	public GlobalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
		// TODO Auto-generated constructor stub
	}


	private int maxOutOfThree(int a, int b, int c){
		if (a>=b && a>=c) return a;
		if (b>=c) return b;
		return c;
	}
	
	private int maxInRow(int[][] matrix, int i){
		int max = mat[i][0];
		for (int j = 1; j < matrix[0].length; j++) {
			max = Math.max(mat[i][j], max);
		}
		return max;
	}
	
	private int maxInCol(int[][] matrix, int j){
		int max = mat[0][j];
		for (int i = 1; i < matrix.length; i++) {
			max = Math.max(mat[i][j], max);
		}
		return max;
	}
	
	@Override
	public int getAlignmentScore() {
		fillMatrices();
		return Math.max(maxInCol(mat, mat[0].length-1), maxInRow(mat, mat.length-1));
	}


	@Override
	protected void fillMatrices() {
		System.out.println("Filling mat using Global Alignment");
		int a;
		int b;
		int c;
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
				a = mat[i-1][j-1] + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
				b = mat[i-1][j] +  sbm.score(sequenceA.charAt(i), ' ');
				c = mat[i][j-1] +  sbm.score(' ', sequenceB.charAt(j));
				mat[i][j] = maxOutOfThree(a, b, c);
			}
		}
	
	}


}
