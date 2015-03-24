package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public class AffineGapLocalAlignment extends GlobalAlignment {

	public AffineGapLocalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}
	
	@Override
	public void SetSequences(String a, String b) {
		super.SetSequences(a, b);
		I_s_mat = new Cell[a.length()+1][b.length()+1];
		I_r_mat = new Cell[a.length()+1][b.length()+1];
		
		//init 1st row  & 1st col to empty cells
		for (int j = 0; j < b.length()+1; j++) {
			I_s_mat[0][j] = new Cell();
			I_r_mat[0][j] = new Cell();

		}
		for (int i = 0; i < a.length()+1; i++) {
			I_s_mat[i][0] = new Cell();
			I_r_mat[i][0] = new Cell();

		}
	}


	protected Cell[][] I_s_mat;
	protected Cell[][] I_r_mat;
	

	@Override
	protected void fillMatrices() {

		System.out.println("Filling mat using Local Alignment + Affine Gap");
		int[] arr = new int[4];
		arr[3]=0;

		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
				int score = sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
				arr[0] = mat[i-1][j-1].getValue();
				arr[1] = I_s_mat[i-1][j-1].getValue();
				arr[2] = I_r_mat[i-1][j-1].getValue();			
				int index = maxIndexInArray(arr);
				score+=arr[index];
				if		( index == 0) mat[i][j] = new Cell(score, mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
				else if	( index == 1) mat[i][j] = new Cell(score, I_s_mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
				else if	( index == 2) mat[i][j] = new Cell(score, I_r_mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
				else if	( index == 3) mat[i][j] = new Cell();
				
				
				arr[0] = mat[i-1][j].getValue() - sbm.getAffineGap_A();
				arr[1] = I_s_mat[i-1][j].getValue() - sbm.getAffineGap_B();
				arr[2] = I_r_mat[i-1][j].getValue() - sbm.getAffineGap_A();			
				index = maxIndexInArray(arr);
				score = arr[index];
				if		( index == 0) I_s_mat[i][j] = new Cell(score, mat[i-1][j], sequenceA.charAt(i)+"" , "_");
				else if	( index == 1) I_s_mat[i][j] = new Cell(score, I_s_mat[i-1][j], sequenceA.charAt(i)+"" , "_");
				else if	( index == 2) I_s_mat[i][j] = new Cell(score, I_r_mat[i-1][j], sequenceA.charAt(i)+"" , "_");
				else if	( index == 3) I_s_mat[i][j] = new Cell();


				arr[0] = mat[i][j-1].getValue() - sbm.getAffineGap_A();
				arr[1] = I_s_mat[i][j-1].getValue() - sbm.getAffineGap_A();
				arr[2] = I_r_mat[i][j-1].getValue() - sbm.getAffineGap_B();			
				index = maxIndexInArray(arr);
				score = arr[index];
				if		( index == 0) I_r_mat[i][j] = new Cell(score, mat[i][j-1], "_" , sequenceB.charAt(j)+"");
				else if	( index == 1) I_r_mat[i][j] = new Cell(score, I_s_mat[i][j-1], "_" , sequenceB.charAt(j)+"");
				else if	( index == 2) I_r_mat[i][j] = new Cell(score, I_r_mat[i][j-1], "_" , sequenceB.charAt(j)+"");
				else if	( index == 3) I_r_mat[i][j] = new Cell();


			}
		}
		
	}

}
