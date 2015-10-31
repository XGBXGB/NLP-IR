package reader;

import java.util.ArrayList;

public class Driver {
	public static void main(String[] args) {
		TextFileReader r = new TextFileReader();
		//gawa kayo ng folder sa desktop maybe and put filipino texts and function words
		ArrayList<TextFile> t = r.readFolder("C:\\Users\\Jake\\Desktop\\NLP\\filipino texts\\");

		DBModel db = new DBModel();
		
		for (int i = 0; i < t.size(); i++) {
			TextFile file = t.get(i);
			//db.inserFile(file.getName());
			//int fileId = db.getLastInsertedId();
			String[] words = file.getWords();
			for(int j = 0; j < words.length; j++) {
				String word = words[j]; 
				//normalize here
				
				if(!isFunctionWord(word)) {
					//db.insertWord(word);
					//int wordId = db.getLastInsertedId();
					//db.insertWordFile(fileId, wordId);
					System.out.print(word + " ");
				}
			}
			System.out.println();
		}
	}

	private static boolean isFunctionWord(String word) {
		TextFileReader r = new TextFileReader();
		ArrayList<String> fWords = r.readFunctionWords();
		for (String fWord : fWords) {
			if(fWord.trim().equalsIgnoreCase(word.trim())) {
				return true;
			}
		}
		return false;
	}
}
