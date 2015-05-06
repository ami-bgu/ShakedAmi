package edu.tabio.blast;

import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.Model.Sequence;
import edu.tabio.SequenceAlignments.Alignment;
import edu.tabio.SequenceAlignments.Cell;

public class BlastAlignment extends Alignment{

	public BlastAlignment(SubstitutionMatrix subsMat) {
		super(subsMat);
	}
	
	@Override
	public void SetSequences(Sequence text, Sequence query)
	{
		super.SetSequences(text, query);
		this.textPreprocess = new TextPreprocess();
		this.queryPreprocess = new QueryPreprocess(this.sbm);
		textPreprocess.preprocess(text.getContent());
		queryPreprocess.preprocess(query.getContent(), textPreprocess);
		this.sequenceA = text.getContent();
		this.sequenceB = query.getContent();
	}
	
	

	private TextPreprocess textPreprocess;
	private QueryPreprocess queryPreprocess;
	
	
	private void extendHSPs(){
		String bestMatchQuery = "";
		String bestMatchText = "";
		String secondBestMatchQuery = "";
		String secondBestMatchText = "";
		for (String queryKey : queryPreprocess.similarWordsMap.keySet()) {
			for ( Integer i :  queryPreprocess.similarWordsMap.get(queryKey).getQueryIndexes()) {
				for (SimilarString similar :  queryPreprocess.similarWordsMap.get(queryKey).getSimilarStrings()) {
					int score = similar.getPunishment();
					for (Integer j  : similar.getIndexesInText()) {
						int r = BlastConstants.K ;
						while (r < sequenceB.length()-i && r < sequenceA.length() - j){
							int newScore = sbm.score(sequenceB.charAt(i + r), sequenceA.charAt(j + r)) + score;
							if ( newScore >= BlastConstants.HSP_T){
								score = newScore;
								r++;
							}
							else break;
						}
						String queryCurrent = sequenceB.substring(i, i + r);
						String textCurrent = sequenceA.substring(j, j + r);
						
						if (queryCurrent.length() > bestMatchQuery.length())
						{
							secondBestMatchQuery = bestMatchQuery;
							bestMatchQuery = queryCurrent;
							
							secondBestMatchText = bestMatchText;
							bestMatchText = textCurrent;
						}
						else if (queryCurrent.length() > secondBestMatchQuery.length())
						{
							secondBestMatchQuery = queryCurrent;
							secondBestMatchText = textCurrent;
						}
						
					}
				}
				
			}
		}
		
		System.out.println("Best:\n" + bestMatchQuery +"\n"+ bestMatchText);
		System.exit(0);
		
	}
	
	//private void extendWord
	
	@Override
	public void printResult()
	{
		extendHSPs();
	}
	
	
	@Override
	protected Cell calculateEndCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void ij_operation(int i, int j) {
		// TODO Auto-generated method stub
		
	}
}
