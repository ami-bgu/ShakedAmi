import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.SequenceAlignments.AffineGapGlobalAlignment;
import edu.tabio.SequenceAlignments.Alignment;
import edu.tabio.SequenceAlignments.GlobalAlignment;
import edu.tabio.SequenceAlignments.LocalAlignment;


public class Main {

	public static Map<String, String> sequencesMap = new HashMap<>();
	
	public static void readFasta(String filename)
	{
		String key = "";
		StringBuilder builder = new StringBuilder();
		//
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '>') {
					if (!key.equals(""))
						sequencesMap.put(key, builder.toString());
					builder = new StringBuilder();
					key = line.substring(1);
				} else {
					builder.append(line);
				}

			}
			sequencesMap.put(key, builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(sequencesMap);
	
	}
	
	public static void main(String[] args) {
		if (args.length==0)
		{
			System.err.println("please provide args!");
			return;
		}
		
		readFasta("sequence.fasta");
		SubstitutionMatrix subsMat = new SubstitutionMatrix("Score.matrix");
		Alignment alignment = null;
		
		if (args.length>1 && args[1].equals("-a"))	alignment = new AffineGapGlobalAlignment(subsMat);
		else if (args[0].equals("-g"))				alignment = new GlobalAlignment(subsMat);
		else if (args[0].equals("-l"))				alignment = new LocalAlignment(subsMat);

		alignment.SetSequences(sequencesMap.get("sample2"), sequencesMap.get("sample1"));
		//alignment.SetSequences("TTAATT", "CCAACC");
		System.out.println("Alignment score is: "+ alignment.getAlignmentScore());
		
	
	}
}
