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
	}
	

	private TextPreprocess textPreprocess;
	private QueryPreprocess queryPreprocess;
	
	
	private void extendHSPs(){
		for (String queryKey : queryPreprocess.similarWordsMap.keySet()) {
			
		}
		
	}
	
	//private void extendWord
	
	@Override
	public void printResult()
	{
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
