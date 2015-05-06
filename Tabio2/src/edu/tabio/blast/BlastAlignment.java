package edu.tabio.blast;

import edu.tabio.Configuration.SubstitutionMatrix;
import edu.tabio.Model.BlastResult;
import edu.tabio.Model.Sequence;


public class BlastAlignment{

	public BlastAlignment(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
	}
	
	protected SubstitutionMatrix sbm;

	protected Sequence m_text;
	protected Sequence m_query;
	
	
	public void setText(Sequence text)
	{
		this.m_text = text;
		this.textPreprocess = new TextPreprocess();
		textPreprocess.preprocess(text.getContent());
	}
	
	public BlastResult runQuery(Sequence query)
	{
		this.m_query = query;
		this.queryPreprocess = new QueryPreprocess(this.sbm);
		queryPreprocess.preprocess(query.getContent(), this.textPreprocess);
		return extendHSPs(m_text.getContent(), m_query.getContent());
	}
	

	private TextPreprocess textPreprocess;
	private QueryPreprocess queryPreprocess;
	
	
	private BlastResult extendHSPs(String text, String query){
		String bestMatchQuery = "";
		String bestMatchText = "";

		for (String queryKey : queryPreprocess.similarWordsMap.keySet()) {
			for ( Integer i :  queryPreprocess.similarWordsMap.get(queryKey).getQueryIndexes()) {
				for (SimilarString similar :  queryPreprocess.similarWordsMap.get(queryKey).getSimilarStrings()) {
					int score = similar.getPunishment();
					for (Integer j  : similar.getIndexesInText()) {
						int r = BlastConstants.K ;
						while (r < query.length()-i && r < text.length() - j){
							int newScore = sbm.score(query.charAt(i + r), text.charAt(j + r)) + score;
							if ( newScore >= BlastConstants.HSP_T){
								score = newScore;
								r++;
							}
							else break;
						}
						String queryCurrent = query.substring(i, i + r);
						String textCurrent = text.substring(j, j + r);
						
						if (queryCurrent.length() > bestMatchQuery.length())
						{
							bestMatchQuery = queryCurrent;
							bestMatchText = textCurrent;
						}
						
					}
				}
				
			}
		}
		
		return new BlastResult(	new Sequence(m_text.getName(), bestMatchText),
								new Sequence(m_query.getName(), bestMatchQuery));
		//System.out.println("Best:\n" + bestMatchQuery +"\n"+ bestMatchText);
		
		
	}
	

	
	

}
