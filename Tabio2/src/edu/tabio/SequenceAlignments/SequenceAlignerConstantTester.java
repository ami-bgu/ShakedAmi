package edu.tabio.SequenceAlignments;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tabio.Model.BlastResult;
import edu.tabio.Model.Sequence;
import edu.tabio.blast.BlastAlignment;
import edu.tabio.blast.BlastConstants;


public class SequenceAlignerConstantTester extends SequenceAligner{
	
	public SequenceAlignerConstantTester(BlastAlignment alignment) {
		super(alignment);
	}
	
	private int k_range[] = 	{3,6};
	private int t_range[] = 	{BlastConstants.K+1, BlastConstants.K + 5};
	private int hsp_t_range[] = {-5, 2};


	private int localResults[][] = {
										{5,2},
										{4,2},
										{5,6},
										{4,5},
										{2,5},
										{5,6},
										{4,5},
										{4,3},
										{4,2},
										{4,5}		
												};
	
	//recieves filenames
	public void allAgainstAll(String textsFilename, String queryFilename) {
		textSequences = readFasta(textsFilename);
		querySequences = readFasta(queryFilename);
			
		long startTime = System.currentTimeMillis();
		
		int successes = 0;

		for (BlastConstants.K = k_range[0]; BlastConstants.K <= k_range[1]; BlastConstants.K++) {
			for (BlastConstants.T = BlastConstants.K+1; BlastConstants.T <= BlastConstants.K*2; BlastConstants.T++) {
				for (BlastConstants.HSP_T = -2; BlastConstants.HSP_T <= 7; BlastConstants.HSP_T++) {
					
					long textStartTime = System.currentTimeMillis();
					int textIndex = 0;
					int tmp = 0;
					//real code
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
						//System.out.println("===================================================================================");
						//System.out.println("1st best Result is:\n" + firstBestResult + "\n***\n2nd best result is:\n" + secondBestResult);
				
						int a = firstBestResult.getAligned_query().getName().charAt(firstBestResult.getAligned_query().getName().length()-1) - 48;
						int b = secondBestResult.getAligned_query().getName().charAt(secondBestResult.getAligned_query().getName().length()-1) - 48;

						if (localResults[textIndex][0]==a || localResults[textIndex][0]==b)
						{
							tmp++;
						}
						if (localResults[textIndex][1]==a || localResults[textIndex][1]==b)
						{
							tmp++;
						}
						
						textIndex++;
					}
					
					long textEndTime = System.currentTimeMillis() - textStartTime;
					//if (textEndTime < 2316 && tmp >= 8)
					//{
						successes = tmp;
						System.out.println("K: " + BlastConstants.K + "\tT: "+ BlastConstants.T + "\tHSP_T: "+ BlastConstants.HSP_T+
								"\tAccuracy: " +tmp+"/20\tTime: "+ textEndTime);
					//}
					
				}
			}	
		}
		

		System.out.println("===================================================================================");
		System.out.println("All Against All Took: "+ (System.currentTimeMillis() - startTime) + " ms");

	}
	
	
}
