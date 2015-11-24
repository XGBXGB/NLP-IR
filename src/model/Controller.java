package model;

import java.util.ArrayList;

import reader.DBModel;
import reader.TextFile;
import reader.TextFileReader;

public class Controller {
	public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u'};
        final int NUMBER_OF_DOCUMENTS = 1074;//CHANGE TO NUMBER OF TAGALOG NEWS ONLY
	private ArrayList<TextFile> files;
	private DBModel dbModel;
	private TextFileReader tfr;
	private ArrayList<String> fWords;

	public Controller() {
		files = new ArrayList<TextFile>();
		dbModel = new DBModel();
		tfr = new TextFileReader();
		fWords = new ArrayList<String>();
		retrieveFWords();
	}

	public ArrayList<TextFile> getFiles() {
		return files;
	}

	public ArrayList<String> getFWords() {
		return fWords;
	}

	public void retrieveFWords() {
		fWords = tfr.readFunctionWords();
	}

	public void retrieveFiles() {
		files = tfr.readFolder("..\\NLP-IRPT2-2\\IR");
	}

	private boolean isFunctionWord(String word) {
		for (String fWord : fWords) {
			if (fWord.trim().equalsIgnoreCase(word.trim())) {
				return true;
			}
		}
		return false;
	}

	private String removePunc(String word) {
		word.replaceAll("\\(", "");
		word.replaceAll("\\)", "");
		return word.replaceAll("[?!.,;:-`~!@#$%^&*_=+\"']", "");
	}
	
	private boolean isVowel(char c) {
        for (char vowel : VOWELS) {
            if (c == vowel) {
                return true;
            }
        }
        return false;
    }

	private boolean isStemmable(String word) {
		if (word.length() >= 3 && isVowel(word.charAt(0))) {
			return true;
		}
		if (word.length() >= 4 && !isVowel(word.charAt(0))) {
			return true;
		}
		return false;
	}

	public void insertEverything() {
		// gawa kayo ng folder sa desktop maybe and put filipino texts and
		// function words
		boolean canBeProper = false;
		boolean isProper = false;

		/*for (int i = 0; i < files.size(); i++) {
			System.out.println((i+1)/303.0000 * 100 + "%");
			TextFile file = files.get(i);
			dbModel.insertFile(file.getName());
			ArrayList<Word> words = file.getWords();
			// output = "";
			canBeProper = false;
			isProper = false;

			for (Word w : words) {
				String word = w.getWord();
				if (isStemmable(removePunc(word))) {
					word = removePunc(word);
					try {
						// current word is a proper noun if the previous word
						// ended with a punctuation mark,
						// and the first character is in uppercase
						isProper = Character.isUpperCase(word.charAt(0)) && canBeProper;

						if (!isProper) {
							word = Stemmer.removeInfix(word);
							// System.out.println("After infix: " + word);
							word = Stemmer.removePartialReduplicates12(word);
							// System.out.println("After red12: " + word);
							word = Stemmer.removePartialReduplicates3(word);
							// System.out.println("After red3: " + word);
							word = Stemmer.removePartialReduplicates4(word);
							// System.out.println("After red4: " + word);
							word = Stemmer.removeFullReduplicates(word);
							// System.out.println("After full: " + word);

							word = Stemmer.removePrefixes(word);
							// System.out.println("After prefix: " + word);

							word = Stemmer.removePartialReduplicates12(word);
							// System.out.println("After red12: " + word);
							word = Stemmer.removePartialReduplicates3(word);
							// System.out.println("After red3: " + word);
							word = Stemmer.removePartialReduplicates4(word);
							// System.out.println("After red4: " + word);
							word = Stemmer.removeFullReduplicates(word);
							// System.out.println("After full: " + word);

							// System.out.println("before suffix: "+word);
							word = Stemmer.removeSuffix(word);
							// System.out.println("after suffix: "+word);
							// System.out.println("After suffix: " + word);

							word = Stemmer.removePartialReduplicates12(word);
							// System.out.println("After red12: " + word);
							word = Stemmer.removePartialReduplicates3(word);
							// System.out.println("After red3: " + word);
							word = Stemmer.removePartialReduplicates4(word);
							// System.out.println("After red4: " + word);
							word = Stemmer.removeFullReduplicates(word);
							// System.out.println("After full: " + word);

							word = Stemmer.removePrefixes(word);
							// System.out.println("After prefixes: " + word);

							word = Stemmer.removePartialReduplicates12(word);
							// System.out.println("After red12: " + word);
							word = Stemmer.removePartialReduplicates3(word);
							// System.out.println("After red3: " + word);
							word = Stemmer.removePartialReduplicates4(word);
							// System.out.println("After red4: " + word);
							word = Stemmer.removeFullReduplicates(word);
							// System.out.println("After full: " + word);
							// if word didn't end with punctuation then true;
						}
						canBeProper = !Stemmer.endsWithPunctuation(word);
					} catch (Exception e) {
					}

					// System.out.println(word);
					// output += word + " ";
					w.setWord(word);
					if(!isFunctionWord(word)) {
						dbModel.insertWord(word);
						if (!dbModel.checkWordFiles(word, file.getName())) {
							double tf = 1 + Math.log((double)w.getCount());
							dbModel.insertWordFile(word, tf, file.getName());
                                                        //int idf = (int) (Math.log(NUMBER_OF_DOCUMENTS/dbModel.getNumOfDocs(w.getWord())));
						}
					}
					// int wordId = db.getLastInsertedId();
					
					// System.out.print(word + " ");
				}
			}
		}
        dbModel.updateIDF();*/
		dbModel.updateScore();
	}
}
