package edu.tabio.SequenceAlignments;
import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.Model.Sequence;


public abstract class Alignment {

	public Alignment(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
		System.out.println(this.getClass().getSimpleName()+" initiated!");
	}
	
	protected SubstitutionMatrix sbm;
	protected String sequenceA;	//vertical sequence
	protected String sequenceB;	//horizontal sequence
	
	private String seqA_name;
	private String seqB_name;
		
	protected Cell[][] mat;
	
	//set sequences to be aligned
	public void SetSequences(Sequence seq1, Sequence seq2)
	{
		//on purpose 1=b 2=a
		seqA_name = seq2.getName();
		seqB_name = seq1.getName();
		String b = seq1.getContent();
		String a = seq2.getContent();
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
	
	//returns the index of the maximum cell in the array
	protected int maxIndexInArray(int[] arr){
		int max = arr[0];
		int index = 0;
		for (int i = 1; i < arr.length; i++) {
			if(arr[i]>max){
				index = i;
				max = arr[i];
			}
		}
		return index;
	}

	
	//fills the main matrix according to the alignment algorithm
	protected void fillMatrices()
	{
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[0].length; j++) {
				ij_operation(i, j);
			}
		}
	}
	
	protected abstract Cell calculateEndCell();		//returns the last cell of the alignment's path 
	protected abstract void ij_operation(int i, int j);	//returns the maximum value for the cell [i,j]

	
	//prints the sequences names, alignment & score
	public void printResult()
	{
		fillMatrices();
		String[] algn;
		Cell c = calculateEndCell();
		algn = c.alignment();
		StringBuilder builder = new StringBuilder();
		builder.append(">"+seqB_name+"\n");
		builder.append(algn[1]+"\n");
		builder.append(">"+seqA_name+"\n");
		builder.append(algn[0]+"\n");
		builder.append("Score: "+c.getValue()+"\n");
		System.out.println(builder.toString());
	}
	
	//prints the alignment matrix (for debugging purposes)
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
