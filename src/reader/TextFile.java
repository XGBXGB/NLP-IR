package reader;

import java.util.ArrayList;
import java.util.HashMap;

public class TextFile {
	private int fileNum;
	private ArrayList<String> lines;
	private String content;
	private String name;
	private String[] words;
	

	public TextFile() {
		lines = new ArrayList<String>();
	}
	
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
		
		content = lines.get(0).trim();
		
		for (int i = 1; i < lines.size(); i++) {
			content = content + " " + lines.get(i).trim();
		}

		content.replace(",", "");
		content.replace(".", "");
		content.replace("!", "");
		content.replace("?", "");
		content.replace("(", "");
		content.replace(")", "");
		
		words = content.split(" ");
	}
	public String getContent() {
		return content;
	}
	
	public String[] getWords() {
		return words;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getLines() {
		return lines;
	}
	
	public String getName() {
		return name;
	}
	
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	
	public void print() {
		System.out.println(name);
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}
	}
}
