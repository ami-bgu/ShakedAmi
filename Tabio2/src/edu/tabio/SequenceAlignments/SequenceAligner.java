package edu.tabio.SequenceAlignments;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tabio.Model.Sequence;


public class SequenceAligner {

	public SequenceAligner(Alignment alignment) {
		this.alignment = alignment;
	}
		
	private List<Sequence> fastaList1;
	private List<Sequence> fastaList2;
	private Alignment alignment;
	
	private List<Sequence> readFasta(String filename)
	{
		//System.out.println(filename);
		List<Sequence> sequencesList = new ArrayList<>();
		String name = "";
		StringBuilder builder = new StringBuilder();
		//
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '>') {
					if (!name.equals(""))
						sequencesList.add(new Sequence(name, builder.toString()));
					builder = new StringBuilder();
					name = line.substring(1);
				} else {
					builder.append(line);
				}

			}
			sequencesList.add(new Sequence(name, builder.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println(sequencesList);
		return sequencesList;
	
	}
	
	
	//recieves filenames
	public void allAgainstAll(String fasta1, String fasta2) {
		fastaList1 = readFasta(fasta1);
		fastaList2 = readFasta(fasta2);
		
		long startTime = System.currentTimeMillis();
		for (Sequence seq1 : fastaList1) {
			for (Sequence seq2 : fastaList2) {
				alignment.SetSequences(seq1, seq2);
				alignment.printResult();
			}
		}
		System.out.println("All Against All Took: "+ (System.currentTimeMillis() - startTime) + " ms");
	}
	
	//recieves sequences
	public void oneAgainstOne(String seq1, String seq2) {
		alignment.SetSequences(new Sequence("", seq1), new Sequence("", seq2));
		alignment.printResult();
	}
	
}
