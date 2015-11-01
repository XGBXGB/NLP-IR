package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileReader {
	
	public ArrayList<String> readFunctionWords() {
		ArrayList<String> fWords = new ArrayList<String>();
		
		try {
			FileReader fr = new FileReader("..\\NLP-IR\\IR\\fil-function-words.txt");
			BufferedReader br = new BufferedReader(fr);
			String word = null;
			while((word = br.readLine()) != null) {
				fWords.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fWords;
	}
	
	public ArrayList<TextFile> readFolder(String path) {
		ArrayList<TextFile> textFiles = new ArrayList<TextFile>();
		
		File folder = new File(path);
		File files[] = folder.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if(file.getName().endsWith(".txt")) {
				TextFile textFile = new TextFile();
				textFile.setFileNum(i + 1);
				textFile.setName(file.getName());
				textFile.setLines(readFile(file.getPath()));
				textFiles.add(textFile);
			}
		}
		
		return textFiles;
	}
	
	public ArrayList<String> readFile(String name) {
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			FileReader fr = new FileReader(name);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String word = null;
			while((word = br.readLine()) != null) {
				lines.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
