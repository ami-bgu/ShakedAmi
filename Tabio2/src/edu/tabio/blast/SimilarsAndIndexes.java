package edu.tabio.blast;
import java.util.ArrayList;
import java.util.List;


public class SimilarsAndIndexes {
	

	private List<SimilarString> similarStrings = new ArrayList<>();
	private List<Integer> queryIndexes = new ArrayList<>();
	
	public void addSimilarString (SimilarString word){
		similarStrings.add(word);
	}
	
	public void addQueryIndexes (Integer i){
		queryIndexes.add(i);
	}
	
	
	public List<SimilarString> getSimilarStrings() {
		return similarStrings;
	}
	public List<Integer> getQueryIndexes() {
		return queryIndexes;
	}
	
	@Override
	public String toString() {
		return "Indexes in query: " + this.queryIndexes +  ", Similars in text:\n\t" + similarStrings + "\n";
		//return str+", ";
	}


	
}
