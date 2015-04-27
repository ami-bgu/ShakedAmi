package edu.tabio.blast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.tabio.Configuration.SubstitutionMatrix;

public class QueryPreprocess {
	


	private SubstitutionMatrix sbm;
	
	public QueryPreprocess(SubstitutionMatrix subsMat) {
		this.sbm = subsMat;
	}
	
	protected Map<String,List<SimilarString>> similarWordsMap = new HashMap<>();

	public void preprocess(String query, TextPreprocess textPreprocess)
	{
		Set<String> textKBlocks = textPreprocess.getKBlocksList();
		for (int i = 0; i < query.length()-BlastConstants.K + 1 ; i++) {
			String queryBlock = query.substring(i, i + BlastConstants.K);
			List<SimilarString> wordList = similarWordsMap.get(queryBlock);
			if(wordList == null)
			{
				wordList = new ArrayList<>(); //create a list of similar word if it doesnt exist
				for (String textBlock : textKBlocks) {
					Integer similarity =  sbm.similarity(queryBlock, textBlock, BlastConstants.T);
					if (similarity != null){
						wordList.add(new SimilarString(textBlock, similarity, textPreprocess.textMap.get(textBlock))); //add to the similar words list
					}			
				}
				if(!wordList.isEmpty()) similarWordsMap.put(queryBlock, wordList);
			}
		}
		printMap();
		System.exit(0);
	}
	
	
	private void printMap(){
		for (String word : similarWordsMap.keySet()) {
			List<SimilarString> similarWordsList = similarWordsMap.get(word);
			System.out.println(word + similarWordsList.toString());
			
			
		}
		
		
		
	}
	
	
	
	
}

	


