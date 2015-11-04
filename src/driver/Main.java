package driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import model.Document;
import model.Stemmer;
import reader.DBModel;
import reader.TextFile;
import reader.TextFileReader;

public class Main {

	  public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u'};

	    private static String removePunc(String word) {
	        return word.replaceAll("[?!.,;]", "");
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

	/*    public static void main(String[] args) {
	        boolean canBeProper = false;
	        boolean isProper = false;
	        String terms = "";
	        String exclusions = "";
	        DBModel db = new DBModel();
	        Scanner sc = new Scanner(System.in);
	        
	        System.out.println("Enter words included:");
	        System.out.println("(Note: separate words by spaces only and don't enter anything if none.)");
	        terms = sc.nextLine();
	        System.out.println("Enter words excluded: ");
	        System.out.println("(Note: separate words by spaces only and don't enter anything if none.)");
	        exclusions = sc.nextLine();
	        
	    }*/
	    
	    public static void main(String[] args) {
			
	    	DBModel db = new DBModel();
	    	
	    	Scanner sc = new Scanner(System.in);
	        String terms = "";
	        String exclusions = "";
			String outputTerms = "";
			String outputExclusions = "";
			
	        System.out.println("Enter words included:");
	        System.out.println("(Note: separate words by spaces only and don't enter anything if none.)");
	        terms = sc.nextLine();
	        System.out.println("Enter words excluded: ");
	        System.out.println("(Note: separate words by spaces only and don't enter anything if none.)");
	        exclusions = sc.nextLine();

        	String arrayTerms[] = terms.split(" ");	    
			String arrayExclusions[] = exclusions.split(" ");
		    boolean canBeProper = false;
	        boolean isProper = false;
	        

	        for (String word : arrayTerms) {
	            if (isStemmable(removePunc(word))) {
	                try {
	                    isProper = Character.isUpperCase(word.charAt(0)) && canBeProper;
	                    
	                    if(!isProper){
	                    	word = Stemmer.removeInfix(word);
	                        word = Stemmer.removePartialReduplicates12(word);
	                        word = Stemmer.removePartialReduplicates3(word);
	                        word = Stemmer.removePartialReduplicates4(word);
	                        word = Stemmer.removeFullReduplicates(word);
	                        
	                    	word = Stemmer.removePrefixes(word);
	                        
	                        word = Stemmer.removePartialReduplicates12(word);
	                        word = Stemmer.removePartialReduplicates3(word);
	                        word = Stemmer.removePartialReduplicates4(word);
	                        word = Stemmer.removeFullReduplicates(word);
	                        
	                        word = Stemmer.removeSuffix(word);
	                        
	                        word = Stemmer.removePartialReduplicates12(word);
	                        word = Stemmer.removePartialReduplicates3(word);
	                        word = Stemmer.removePartialReduplicates4(word);
	                        word = Stemmer.removeFullReduplicates(word);
	                        
	                        word = Stemmer.removePrefixes(word);
	                        
	                        word = Stemmer.removePartialReduplicates12(word);
	                        word = Stemmer.removePartialReduplicates3(word);
	                        word = Stemmer.removePartialReduplicates4(word);
	                        word = Stemmer.removeFullReduplicates(word);
	                    }
	                    canBeProper = !Stemmer.endsWithPunctuation(word);
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }

	            }
	            outputTerms += word + " ";
	        }
	        //System.out.println("Output Terms " + outputTerms);
	    
	    /********** exclusions ******************/
	    canBeProper = false;
        isProper = false;
        

        for (String word : arrayExclusions) {
            if (isStemmable(removePunc(word))) {
                try {
                    isProper = Character.isUpperCase(word.charAt(0)) && canBeProper;
                    
                    if(!isProper){
                    	word = Stemmer.removeInfix(word);
                        word = Stemmer.removePartialReduplicates12(word);
                        word = Stemmer.removePartialReduplicates3(word);
                        word = Stemmer.removePartialReduplicates4(word);
                        word = Stemmer.removeFullReduplicates(word);
                        
                    	word = Stemmer.removePrefixes(word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        word = Stemmer.removePartialReduplicates3(word);
                        word = Stemmer.removePartialReduplicates4(word);
                        word = Stemmer.removeFullReduplicates(word);
                        
                        word = Stemmer.removeSuffix(word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        word = Stemmer.removePartialReduplicates3(word);
                        word = Stemmer.removePartialReduplicates4(word);
                        word = Stemmer.removeFullReduplicates(word);
                        
                        word = Stemmer.removePrefixes(word);
                        
                        word = Stemmer.removePartialReduplicates12(word);
                        word = Stemmer.removePartialReduplicates3(word);
                        word = Stemmer.removePartialReduplicates4(word);
                        word = Stemmer.removeFullReduplicates(word);
                    }
                    canBeProper = !Stemmer.endsWithPunctuation(word);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            outputExclusions += word + " ";
        }
        System.out.println("Output Inclusions " + outputTerms);

        System.out.println("Output Exclusions " + outputExclusions);
        
        Iterator<Document> files = db.searchDocs(outputTerms, outputExclusions);
        System.out.println("\n\nFiles from query: ");
        while(files.hasNext())
        {
        	Document file = files.next();
        	System.out.println("DocID: " + file.getdocID());
        	System.out.println("DocName: " + file.getdocName());
        }
    }
}
