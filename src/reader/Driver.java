package reader;

import java.util.ArrayList;
import java.util.Scanner;

import model.Stemmer;

public class Driver {
	
	
	public static final char[] VOWELS = { 'a', 'e', 'i', 'o', 'u' };

	private static String removePunc(String word)
	{
		return word.replaceAll("[?!.,;]","");
	}
	
	private static boolean isVowel(char c) {
		for (char vowel : VOWELS) {
			if (c == vowel) {
				return true;
			}
		}
		return false;
	}

	private static boolean isStemmable(String word) {
		if (word.length() >= 3 && isVowel(word.charAt(0))) {
			return true;
		}
		if (word.length() >= 4 && !isVowel(word.charAt(0))) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		TextFileReader r = new TextFileReader();
		//gawa kayo ng folder sa desktop maybe and put filipino texts and function words
		ArrayList<TextFile> t = r.readFolder("..\\NLP-IR2\\IR");
                boolean canBeProper = false;
                boolean isProper = false;
		DBModel db = new DBModel();
		
            
                /** DB POPULATE MODULE **/
		for (int i = 0; i < t.size(); i++) {
			TextFile file = t.get(i);
			db.inserFile(file.getName());
			int fileId = db.getLastInsertedId();
			String[] words = file.getWords();
			//output = "";
	        canBeProper = false;
	        isProper = false;
	        
                    for (String word : words) {
                        if(!isFunctionWord(word)) {
                        	 if (isStemmable(removePunc(word))) {
                                 try {
                                     //current word is a proper noun if the previous word ended with a punctuation mark,
                                     //and the first character is in uppercase
                                     isProper = Character.isUpperCase(word.charAt(0)) && canBeProper;
                                     
                                     if(!isProper){
                                     	word = Stemmer.removeInfix(word);
                                         //System.out.println("After infix: " + word);
                                         word = Stemmer.removePartialReduplicates12(word);
                                         //System.out.println("After red12: " + word);
                                         word = Stemmer.removePartialReduplicates3(word);
                                         //System.out.println("After red3: " + word);
                                         word = Stemmer.removePartialReduplicates4(word);
                                         //System.out.println("After red4: " + word);
                                         word = Stemmer.removeFullReduplicates(word);
                                         //System.out.println("After full: " + word);
                                         
                                     	word = Stemmer.removePrefixes(word);
                                         //System.out.println("After prefix: " + word);
                                         
                                         word = Stemmer.removePartialReduplicates12(word);
                                         //System.out.println("After red12: " + word);
                                         word = Stemmer.removePartialReduplicates3(word);
                                         //System.out.println("After red3: " + word);
                                         word = Stemmer.removePartialReduplicates4(word);
                                         //System.out.println("After red4: " + word);
                                         word = Stemmer.removeFullReduplicates(word);
                                         //System.out.println("After full: " + word);
                                         
                                         //System.out.println("before suffix: "+word);
                                         word = Stemmer.removeSuffix(word);
                                         //System.out.println("after suffix: "+word);
                                         //System.out.println("After suffix: " + word);
                                         
                                         word = Stemmer.removePartialReduplicates12(word);
                                         //System.out.println("After red12: " + word);
                                         word = Stemmer.removePartialReduplicates3(word);
                                         //System.out.println("After red3: " + word);
                                         word = Stemmer.removePartialReduplicates4(word);
                                         //System.out.println("After red4: " + word);
                                         word = Stemmer.removeFullReduplicates(word);
                                         //System.out.println("After full: " + word);
                                         
                                         word = Stemmer.removePrefixes(word);
                                         //System.out.println("After prefixes: " + word);
                                         
                                         word = Stemmer.removePartialReduplicates12(word);
                                         //System.out.println("After red12: " + word);
                                         word = Stemmer.removePartialReduplicates3(word);
                                         //System.out.println("After red3: " + word);
                                         word = Stemmer.removePartialReduplicates4(word);
                                         //System.out.println("After red4: " + word);
                                         word = Stemmer.removeFullReduplicates(word);
                                         //System.out.println("After full: " + word);
                                         //if word didn't end with punctuation then true;
                                     }
                                     canBeProper = !Stemmer.endsWithPunctuation(word);
                                 } catch (Exception e) {
                                     // TODO Auto-generated catch block
                                     e.printStackTrace();
                                 }

                             }
                             //System.out.println(word);
                             //output += word + " ";
                            db.insertWord(word);
                            int wordId = db.getLastInsertedId();
                            db.insertWordFile(fileId, wordId);
                            System.out.print(word + " ");
                        }
                    }
			System.out.println();
		}
                /** DB POPULATE MODULE **/
                
                
                /** SEARCH MODULE**/
                /*
                
                System.out.println("Please input search terms separated by \" \" to match document contents:");
                String input = sc.nextLine();
                System.out.println("Would you like to exclude some terms? (Y/N)");
                String exclusion = sc.nextLine();
                if(exclusion.equalsIgnoreCase("y")){
                    System.out.println("Please input terms to be excluded separated by \" \":");
                    String excludedTerms = sc.nextLine();
                }
                Iterator docTitles = db.searchDocs(input, excludedTerms);
                System.out.println("Matched documents:");
                for(String title : docTitles){
                    System.out.println("\""+title+"\"");
                }
                
                */
                /** SEARCH MODULE**/
                
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
	
	/*public static void main(String[] args) {
        //String input = "Kinalampag na ang Ombudsman dahil sa umano�y hindi pag-aksyon sa inihaing kaso rito laban sa tatlong konsehal ng Quezon City. Ayon kay Jimmy Lee Davis ng BFP Compound, East Avenue, QC at dating kawani ng QC Council, Marso 2011 pa ng isampa niya ang kasong graft and corruption sa tanggapan ni Ombudsman Concita Carpio-Morales na may Case No. CPL-11-1068, subalit hanggang ngayon ay hindi pa rin ito inaaksyunan. Maging ang abogado ni Davis na si dating Senator Aquilino �Nene� Pimentel ay nagtataka din dahil kung bakit sobrang kupad ng mga graft investigators na magresolba ng kasong isinasampa sa Ombudsman. Nangangamba si Davis na baka mauuwi lamang sa wala ang kanyang pagnanais na mapanagot sina QC Councilors Francisco Boy Calalay Jr., (1st district); Roderick Paulate (2nd district) at Marvin Rillo (4th district) kaugnay sa �ghost employees� at �ghost projects� sa pamahalaang lungsod. Sa hawak na dokumento ni Davis, lumilitaw na may 120 empleyado na nakaplantilya ang bawat Konsehal ng QC Council, subalit nadiskubre umano ni Davis na ang existing plantilla personnel nina Calalay at Paulate ay 20 katao lamang ang totoo at ang 100 diumano ay �ghost employees� na. Pinipeke rin umano nina Calalay, Paulate at Rillo ang pirma ng mga ghost employee nila upang �makakolekta� sa pondo ng QC government.";
		Scanner sc = new Scanner(System.in);
        String input = "saang";
        //String input = sc.nextLine();
		String output = "";
        String array[] = input.split(" ");
        boolean canBeProper = false;
        boolean isProper = false;
        

        for (String word : array) {
            if (isStemmable(removePunc(word))) {
                try {
                    //current word is a proper noun if the previous word ended with a punctuation mark,
                    //and the first character is in uppercase
                    isProper = Character.isUpperCase(word.charAt(0)) && canBeProper;
                    
                    if(!isProper){
                    	word = Stemmer.removeInfix(word);
                        //System.out.println("After infix: " + word);
                        word = Stemmer.removePartialReduplicates12(word);
                        //System.out.println("After red12: " + word);
                        word = Stemmer.removePartialReduplicates3(word);
                        //System.out.println("After red3: " + word);
                        word = Stemmer.removePartialReduplicates4(word);
                        //System.out.println("After red4: " + word);
                        word = Stemmer.removeFullReduplicates(word);
                        //System.out.println("After full: " + word);
                        
                    	word = Stemmer.removePrefixes(word);
                        //System.out.println("After prefix: " + word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        //System.out.println("After red12: " + word);
                        word = Stemmer.removePartialReduplicates3(word);
                        //System.out.println("After red3: " + word);
                        word = Stemmer.removePartialReduplicates4(word);
                        //System.out.println("After red4: " + word);
                        word = Stemmer.removeFullReduplicates(word);
                        //System.out.println("After full: " + word);
                        
                        System.out.println("before suffix: "+word);
                        word = Stemmer.removeSuffix(word);
                        System.out.println("after suffix: "+word);
                        //System.out.println("After suffix: " + word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        //System.out.println("After red12: " + word);
                        word = Stemmer.removePartialReduplicates3(word);
                        //System.out.println("After red3: " + word);
                        word = Stemmer.removePartialReduplicates4(word);
                        //System.out.println("After red4: " + word);
                        word = Stemmer.removeFullReduplicates(word);
                        //System.out.println("After full: " + word);
                        
                        word = Stemmer.removePrefixes(word);
                        //System.out.println("After prefixes: " + word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        //System.out.println("After red12: " + word);
                        word = Stemmer.removePartialReduplicates3(word);
                        //System.out.println("After red3: " + word);
                        word = Stemmer.removePartialReduplicates4(word);
                        //System.out.println("After red4: " + word);
                        word = Stemmer.removeFullReduplicates(word);
                        //System.out.println("After full: " + word);
                        //if word didn't end with punctuation then true;
                    }
                    canBeProper = !Stemmer.endsWithPunctuation(word);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            //System.out.println(word);
            output += word + " ";
        }
        System.out.println(output);
    }
	
	public static void main(String[] args) {
		TextFileReader r = new TextFileReader();
		//gawa kayo ng folder sa desktop maybe and put filipino texts and function words
		ArrayList<TextFile> t = r.readFolder("..\\NLP-IR\\IR");

		//DBModel db = new DBModel();
		
		for (int i = 0; i < t.size(); i++) {
			TextFile file = t.get(i);
			//db.inserFile(file.getName());
			//int fileId = db.getLastInsertedId();
			String[] words = file.getWords();
                    for (String word : words) {
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
	}*/
}
