package edu.tabio.SequenceAlignments;

public class Cell {

	public Cell(int value, Cell prevCell, String s1, String s2) {
		this.value = value;
		this.prevCell = prevCell;
		this.s1 = s1;
		this.s2 = s2;
	}
	
	public Cell() {
		this.value = 0;
		this.prevCell = null;
		this.s1 = "";
		this.s2 = "";
	}
	
	private int value = 0;
	private Cell prevCell;
	private String s1;
	private String s2;  
		
	
	
	
	public int getValue() {
		return value;
	}
	
	public String[] alignment(){
		StringBuilder b1 = new StringBuilder();
		StringBuilder b2 = new StringBuilder();
		alignment(b1, b2);
		return new String[]{b1.toString(),b2.toString()};
		
	}
	
	private void alignment(StringBuilder a, StringBuilder b){
		if (prevCell != null) prevCell.alignment(a, b);
		a.append(s1);
		b.append(s2);
	}

	@Override
	public String toString() {
		return value+"";
	}
}
