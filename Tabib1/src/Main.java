import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
		
		readFasta("sequence.fasta");
		SubstitutionMatrix subsMat = new SubstitutionMatrix("Score.matrix");
		Alignment alignment = new GlobalAlignment(subsMat);
		//Alignment alignment = new LocalAlignment(subsMat);
		alignment.SetSequences(sequencesMap.get("sample2"), sequencesMap.get("sample1"));
		//alignment.SetSequences("TTAATT", "CCAACC");
		System.out.println("Alignment score is: "+ alignment.getAlignmentScore());
	
	}
}
