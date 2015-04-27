package edu.tabio.blast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextPreprocess {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	protected Map<String,List<Integer>> textMap = new HashMap<>();

	public void preprocess(String text)
	{
		for (int i = 0; i < text.length()-BlastConstants.K + 1 ; i++) {
			String word = text.substring(i, i + BlastConstants.K);
			List<Integer> wordList = textMap.get(word);
			if(wordList == null){
				wordList = new ArrayList<>();
				textMap.put(word, wordList);
			}
			wordList.add(i);
		}
	}
	
	public Set<String> getKBlocksList()
	{
		return textMap.keySet();
	}
}


