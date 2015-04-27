package edu.tabio.blast;

import java.util.List;

public class SimilarString {

	
	public SimilarString(String str, int punishment, List<Integer> indexesInText) {
		super();
		this.str = str;
		this.punishment = punishment;
		this.indexesInText = indexesInText;
		
	}
	private String str;
	private int punishment;
	private List<Integer> indexesInText;
	
	@Override
	public String toString() {
		return "\t"+str+ ", punishment: " + punishment +  ", List: " + indexesInText+"\n";
		//return str+", ";
	}
}
