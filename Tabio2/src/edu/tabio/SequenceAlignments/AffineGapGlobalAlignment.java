package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.Model.Sequence;


public class AffineGapGlobalAlignment extends GlobalAlignment {

	public AffineGapGlobalAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}
	
	@Override
	public void SetSequences(Sequence seq1, Sequence seq2){
		super.SetSequences(seq1, seq2);
		String b = seq1.getContent();
		String a = seq2.getContent();
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
	
	private int[] arr = new int[3];
	
	@Override
	protected void ij_operation(int i, int j) {
		int score = sbm.score(sequenceA.charAt(i), sequenceB.charAt(j));
		arr[0] = mat[i-1][j-1].getValue();
		arr[1] = I_s_mat[i-1][j-1].getValue();
		arr[2] = I_r_mat[i-1][j-1].getValue();			
		int index = maxIndexInArray(arr);
		score += arr[index];
		if		( index == 0) mat[i][j] = new Cell(score, mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
		else if	( index == 1) mat[i][j] = new Cell(score, I_s_mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");
		else if	( index == 2) mat[i][j] = new Cell(score, I_r_mat[i-1][j-1], sequenceA.charAt(i)+"" , sequenceB.charAt(j)+"");

		arr[0] = mat[i-1][j].getValue() - sbm.getAffineGap_A();
		if (i==1)	arr[1] = I_s_mat[i-1][j].getValue() - sbm.getAffineGap_A();
		else		arr[1] = I_s_mat[i-1][j].getValue() - sbm.getAffineGap_B();
		arr[2] = I_r_mat[i-1][j].getValue() - sbm.getAffineGap_A();			
		index = maxIndexInArray(arr);
		score = arr[index];
		if		( index == 0) I_s_mat[i][j] = new Cell(score, mat[i-1][j], sequenceA.charAt(i)+"" , "_");
		else if	( index == 1) I_s_mat[i][j] = new Cell(score, I_s_mat[i-1][j], sequenceA.charAt(i)+"" , "_");
		else if	( index == 2) I_s_mat[i][j] = new Cell(score, I_r_mat[i-1][j], sequenceA.charAt(i)+"" , "_");

		arr[0] = mat[i][j-1].getValue() - sbm.getAffineGap_A();
		arr[1] = I_s_mat[i][j-1].getValue() - sbm.getAffineGap_A();
		if (j==1)	arr[2] = I_r_mat[i][j-1].getValue() - sbm.getAffineGap_A();	
		else		arr[2] = I_r_mat[i][j-1].getValue() - sbm.getAffineGap_B();
		index = maxIndexInArray(arr);
		score = arr[index];
		if		( index == 0) I_r_mat[i][j] = new Cell(score, mat[i][j-1], "_" , sequenceB.charAt(j)+"");
		else if	( index == 1) I_r_mat[i][j] = new Cell(score, I_s_mat[i][j-1], "_" , sequenceB.charAt(j)+"");
		else if	( index == 2) I_r_mat[i][j] = new Cell(score, I_r_mat[i][j-1], "_" , sequenceB.charAt(j)+"");

	}

}
