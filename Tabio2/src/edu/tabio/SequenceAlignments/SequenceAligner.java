package edu.tabio.SequenceAlignments;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tabio.Model.BlastResult;
import edu.tabio.Model.Sequence;
import edu.tabio.blast.BlastAlignment;


public class SequenceAligner {

	public SequenceAligner(BlastAlignment alignment) {
		this.alignment = alignment;
	}
		
	private List<Sequence> textSequences;
	private List<Sequence> querySequences;
	private BlastAlignment alignment;
	
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
	public void allAgainstAll(String textsFilename, String queryFilename) {
		textSequences = readFasta(textsFilename);
		querySequences = readFasta(queryFilename);
		
		
		long startTime = System.currentTimeMillis();
		

		for (Sequence text : textSequences) {
			alignment.setText(text);
			BlastResult firstBestResult = null;
			BlastResult secondBestResult = null;
			for (Sequence query : querySequences) {
				BlastResult latestResult = alignment.runQuery(query);
				if (firstBestResult == null || latestResult.getLength() > firstBestResult.getLength())
				{
					secondBestResult = firstBestResult;
					firstBestResult = latestResult;
				}
				else if (secondBestResult == null || latestResult.getLength() > secondBestResult.getLength())
				{
					secondBestResult = latestResult;
				}
			}
			System.out.println("===================================================================================");
			System.out.println("1st best Result is:\n" + firstBestResult + "\n***\n2nd best result is:\n" + secondBestResult);
		}
		
		System.out.println("===================================================================================");
		System.out.println("All Against All Took: "+ (System.currentTimeMillis() - startTime) + " ms");

	}
	
	//recieves sequences
	public void oneAgainstOne(String text, String query) {
		alignment.setText(new Sequence("", text));
		alignment.runQuery(new Sequence("", query));
	}
	
}
