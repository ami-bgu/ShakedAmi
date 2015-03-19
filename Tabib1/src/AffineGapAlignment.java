
public class AffineGapAlignment extends Alignment {

	public AffineGapAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}

	private int maxOutOfThree(int a, int b, int c){
		if (a>=b && a>=c) return a;
		if (b>=c) return b;
		return c;
	}
	
	@Override
	public void SetSequences(String a, String b) {
		super.SetSequences(a, b);
		I_s_mat = new int[a.length()+1][b.length()+1];
		I_r_mat = new int[a.length()+1][b.length()+1];
	}


	protected int[][] I_s_mat;
	protected int[][] I_r_mat;
	
	@Override
	public int getAlignmentScore() {
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
				mat[i][j] = 	sbm.score(sequenceA.charAt(i), sequenceB.charAt(j)) + 
									maxOutOfThree(	mat[i-1][j-1],
													I_s_mat[i-1][j-1],
													I_r_mat[i-1][j-1]);
				I_s_mat[i][j] = maxOutOfThree(	mat[i-1][j] - sbm.getAffineGap_A(),
												I_s_mat[i-1][j]- sbm.getAffineGap_B(),
												I_r_mat[i-1][j]- sbm.getAffineGap_A());
				I_r_mat[i][j] = maxOutOfThree(	mat[i][j-1] - sbm.getAffineGap_A(),
												I_s_mat[i][j-1]- sbm.getAffineGap_A(),
												I_r_mat[i][j-1]- sbm.getAffineGap_B());
			}
		}
		
		//TODO: Connect this method!!!!!!!!!!!
		return 0;
	}

}
