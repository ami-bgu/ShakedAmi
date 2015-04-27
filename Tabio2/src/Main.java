import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.SequenceAlignments.AffineGapGlobalAlignment;
import edu.tabio.SequenceAlignments.AffineGapLocalAlignment;
import edu.tabio.SequenceAlignments.Alignment;
import edu.tabio.SequenceAlignments.GlobalAlignment;
import edu.tabio.SequenceAlignments.LocalAlignment;
import edu.tabio.SequenceAlignments.SequenceAligner;
import edu.tabio.blast.BlastAlignment;


public class Main {

	public static void main(String[] args) {
		
		if (args.length==0)	{	System.err.println("please provide args!");		return;		}
		

		String[] fastas = new String[2];
		String scoreFile = null;
		int i = 0;
		for (String string : args) {
			if	(string.endsWith(".matrix"))	scoreFile = string;
			else if	(string.endsWith(".fasta"))	{
				fastas[i] = string;
				i++;
			}
		}
		
		SubstitutionMatrix subsMat = new SubstitutionMatrix(scoreFile);

		Alignment alignment = new BlastAlignment(subsMat);
		SequenceAligner aligner = new SequenceAligner(alignment);
		aligner.allAgainstAll(fastas[0], fastas[1]);
		/*
		aligner.oneAgainstOne(	"GCTTATACCA",
								"TCCCGTTAGAACAA");
		 */
	}
}
