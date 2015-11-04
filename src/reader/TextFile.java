package reader;

import java.util.ArrayList;
import java.util.HashMap;

import model.Word;

public class TextFile {
	private int fileNum;
	private ArrayList<String> lines;
	private String content;
	private String name;
	private ArrayList<Word> words;
	

	public TextFile() {
		lines = new ArrayList<String>();
		words = new ArrayList<Word>();
	}
	
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
		
		content = lines.get(0).trim();
		
		for (int i = 1; i < lines.size(); i++) {
			content = content + " " + lines.get(i).trim();
		}
		
		String[] words = content.split(" ");
		
		for (int i = 0; i < words.length; i++) {
			String w = words[i];
			int index = wordExists(w);
			if(index == -1) {
				Word word = new Word();
				word.setWord(w);
				this.words.add(word);
			} else {
				this.words.get(index).increment();
			}
		}
	}
	
	private int wordExists(String word) {
		for (int i = 0; i < words.size(); i++) {
			Word w = words.get(i);
			if(w.getWord().trim().equals(word.trim())) {
				return i;
			}
		}
		
		return -1;
	}
	
	public String getContent() {
		return content;
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

	public ArrayList<Word> getWords() {
		// TODO Auto-generated method stub
		return words;
	}
}
