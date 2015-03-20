package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;


public abstract class Alignment {

	public Alignment(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
	}
	
	protected SubstitutionMatrix sbm;
	protected String sequenceA;
	protected String sequenceB;
	
	protected int[][] mat;
	
	public void SetSequences(String a, String b)
	{
		this.sequenceA = "#"+a;
		this.sequenceB = "#"+b;
		mat = new int[a.length()+1][b.length()+1];
	}
	
	protected abstract void fillMatrices();
	public abstract int getAlignmentScore();
	
	
	
}
