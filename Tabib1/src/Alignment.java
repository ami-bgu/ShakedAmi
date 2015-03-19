
public abstract class Alignment {

	public Alignment(SubstitutionMatrix subsMat) {
		// TODO Auto-generated constructor stub
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
	
	public abstract int getAlignmentScore();
	
	
	
}
