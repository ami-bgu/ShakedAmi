import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.SequenceAlignments.AffineGapGlobalAlignment;
import edu.tabio.SequenceAlignments.AffineGapLocalAlignment;
import edu.tabio.SequenceAlignments.Alignment;
import edu.tabio.SequenceAlignments.GlobalAlignment;
import edu.tabio.SequenceAlignments.LocalAlignment;
import edu.tabio.SequenceAlignments.SequenceAligner;


public class Main {

	public static void main(String[] args) {
		
		if (args.length==0)	{	System.err.println("please provide args!");		return;		}
		
		boolean isLocal = false;
		boolean isAffine = false;
		String[] fastas = new String[2];
		String scoreFile = null;
		int i = 0;
		for (String string : args) {
			if 		(string.equals("-l"))	isLocal  = true;
			else if	(string.equals("-a"))	isAffine = true;
			else if	(string.endsWith(".matrix"))	scoreFile = string;
			else if	(string.endsWith(".fasta"))	{
				fastas[i] = string;
				i++;
			}
		}
		
		SubstitutionMatrix subsMat = new SubstitutionMatrix(scoreFile);

		Alignment alignment = null;
		if (isAffine) {
			if (isLocal)	alignment = new AffineGapLocalAlignment(subsMat);
			else			alignment = new AffineGapGlobalAlignment(subsMat);	//global
		}
		else {
			if (isLocal)	alignment = new LocalAlignment(subsMat);
			else			alignment = new GlobalAlignment(subsMat);	//global			
		}
		
		SequenceAligner aligner = new SequenceAligner(alignment);
		//aligner.allAgainstAll(fastas[0], fastas[1]);
		
		aligner.oneAgainstOne(	"GCTTATACCA",
								"TCCCGTTAGAACAA");
		 
	}
}
