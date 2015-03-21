package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public abstract class Alignment {

	public Alignment(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
	}
	
	protected SubstitutionMatrix sbm;
	protected String sequenceA;
	protected String sequenceB;
	
	protected Cell[][] mat;
	
	public void SetSequences(String a, String b)
	{
		this.sequenceA = "#"+a;
		this.sequenceB = "#"+b;
		mat = new Cell[a.length()+1][b.length()+1];
		
		//init 1st row  & 1st col to empty cells
		for (int j = 0; j < b.length()+1; j++) {
			mat[0][j] = new Cell();
		}
		for (int i = 0; i < a.length()+1; i++) {
			mat[i][0] = new Cell();
		}
		
		
	}
	
	
	protected abstract void fillMatrices();
	public abstract void printResult();
	
	protected void printMat()
	{
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("-----------------------------------------");
		
	}
	
}
