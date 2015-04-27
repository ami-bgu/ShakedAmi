package edu.tabio.Model;

public class Sequence {
		
	public Sequence(String name, String content) {
		this.name = name;
		this.content = content.toUpperCase();
	}
	
	private String name;
	private String content;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
