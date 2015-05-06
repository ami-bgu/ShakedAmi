package edu.tabio.Model;

public class BlastResult {

	
	
	public BlastResult(Sequence aligned_text, Sequence aligned_query) {
		this.aligned_text = aligned_text;
		this.aligned_query = aligned_query;
	}
	
	public Sequence getAligned_text() {
		return aligned_text;
	}
	public Sequence getAligned_query() {
		return aligned_query;
	}
	public int getLength() {
		return aligned_query.getContent().length();
	}
	private Sequence aligned_text;
	private Sequence aligned_query;

	@Override
	public String toString() {
		return "TEXT:"+aligned_text + "\nQUERY:" + aligned_query + "\nLength: " + getLength();
	}
}
