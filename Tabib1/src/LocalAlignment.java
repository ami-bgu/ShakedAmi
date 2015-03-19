
public class LocalAlignment extends Alignment {

	public LocalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}

	private int maxOutOfThree(int a, int b, int c){
		if (a>=b && a>=c) return a;
		if (b>=c) return b;
		return c;
	}
	
	
	private int maxOutOfFour(int a, int b, int c ,int d){
		return Math.max(maxOutOfThree(a, b, c), d);
	}
	
	private int getMaxInMatrix(int[][] matrix){
		int max = 0;
		for (int i = 0; i < matrix.length; i++){ 
			for (int j = 0; j < matrix[0].length; j++) {
					max = Math.max(matrix[i][j], max);
			}		
		}
		return max;
	}
	
	@Override
	public int getAlignmentScore() {
		int a,b,c;
			
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
				a = mat[i-1][j-1] + sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
				b = mat[i-1][j] +  sbm.score(sequenceA.charAt(i), ' ');
				c = mat[i][j-1] +  sbm.score(' ', sequenceB.charAt(j));
				mat[i][j] = maxOutOfFour(a, b, c, 0);
			}
		}
		
		return getMaxInMatrix(mat);
	}

}
