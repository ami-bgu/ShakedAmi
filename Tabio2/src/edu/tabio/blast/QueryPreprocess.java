package edu.tabio.blast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.tabio.Configuration.SubstitutionMatrix;

public class QueryPreprocess {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private SubstitutionMatrix sbm;
	
	public QueryPreprocess(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
	}
	
	private Map<String,List<SimilarString>> similarWordsMap = new HashMap<>();

	public void preprocess(String query, TextPreprocess textPreprocess)
	{
		Set<String> textKBlocks = textPreprocess.getKBlocksList();
		for (int i = 0; i < query.length()-BlastConstants.K + 1 ; i++) {
			String queryBlock = query.substring(i, i + BlastConstants.K);
			for (String textBlock : textKBlocks) {
				Integer similarity =  sbm.similarity(queryBlock, textBlock, BlastConstants.T);
				if (similarity != null){
					List<SimilarString> wordList = similarWordsMap.get(queryBlock);
					if(wordList == null){
						wordList = new ArrayList<>();
						similarWordsMap.put(queryBlock, wordList);
					}
					wordList.add(new SimilarString(textBlock, similarity));
				}			
			}
		}

	}
	
	
	
	
	
}

	


