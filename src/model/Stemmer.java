package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stemmer {

	public static final char[] VOWELS = { 'a', 'e', 'i', 'o', 'u' };

	public static final String[] PREFIXES = { "Mang", "mang", "Magsi", "magsi",
			"Mag", "mag", "Mam", "mam", "Man", "man", "Ma", "ma", "Napaka",
			"napaka", "Nang", "nang", "Nagsa", "nagsa", "Nagsi", "nagsi",
			"Nag", "nag", "Nan", "nan", "Na", "na", "Ipagpa", "ipagpa", "Pang",
			"pang", "Pagka", "pagka", "Pagsasa", "pagsasa", "Pag", "pag",
			"Pam", "pam", "Pan", "pan", "Pakiki", "pakiki", "Paki", "paki",
			"Pala", "pala", "Pasa", "pasa", "Pa", "pa", "Ka", "ka", "Isa",
			"isa", "In", "in", "I", "i", "Um", "um", "Sang", "sang", "Taga",
			"taga", "Tag", "tag" };

	public static final String[] SUFFIXES = { "hang", "hing", "ang", "ing", "hin", "han", "in", "an" };

	public static boolean endsWithPunctuation(String word) {
		return word.endsWith("?") || word.endsWith(".") || word.endsWith("!");
	}

	private static String removePunc(String word)
	{
		return word.replaceAll("[?!.,;]","");
	}
	private static boolean endsWithVowel(String word) {
		for (char vowel : VOWELS) {
			if (word.charAt(word.length() - 1) == vowel) {
				return true;
			}
		}
		return false;
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
		System.out.println(word);
		if (word.length() >= 4 && isVowel(word.charAt(0))) {
			return true;
		}
		if (word.length() >= 5 && !isVowel(word.charAt(0))) {
			return true;
		}
		return false;
	}
        
        private static boolean isQualified(String word) {
		System.out.println(word);
		if (word.length() >= 3 && isVowel(word.charAt(0))) {
			return true;
		}
		if (word.length() >= 4 && !isVowel(word.charAt(0))) {
			return true;
		}
		return false;
	}
	
	public static String removePrefix(String word) throws Exception {
		String stemmedWord = word;
		for (String prefix : PREFIXES) {
			if (stemmedWord.startsWith(prefix)) {
				System.out.println("PASOK");
				stemmedWord = stemmedWord.replaceFirst(prefix, "");
				if (endsWithVowel(prefix) && stemmedWord.startsWith("r")
						&& isVowel(stemmedWord.charAt(1))) {
					stemmedWord = stemmedWord.replaceFirst("r", "d");
				}
				break;
			}
		}

		if (isStemmable(stemmedWord)) {
			return stemmedWord;
		}
		
		return word;
	}

	public static String removePrefixes(String word) throws Exception {
		String stemmedWord = word;
		for (String prefix : PREFIXES) {
			if (stemmedWord.startsWith(prefix)) {
				stemmedWord = stemmedWord.replaceFirst(prefix, "");
				if (endsWithVowel(prefix) && stemmedWord.startsWith("r")
						&& isVowel(stemmedWord.charAt(1))) {
					stemmedWord = stemmedWord.replaceFirst("r", "d");
				}
				break;
			}
		}

		if (isStemmable(stemmedWord)) {
			boolean hasPrefix = false;
			for (String prefix : PREFIXES) {
				if (stemmedWord.startsWith(prefix)) {
					hasPrefix = true;
					break;
				}
			}
			if (hasPrefix) {
				stemmedWord = removePrefixes(stemmedWord);
				word = stemmedWord;
			} else {
				if (stemmedWord.startsWith("-")) {
					stemmedWord = stemmedWord.replaceFirst("-", "");
				}
				return stemmedWord;
			}
		}
                
                if(isQualified(stemmedWord))
                    return stemmedWord;

		return word;
	}

	public static String removeInfix(String word) throws Exception {
		String stemmedWord = word;
		Pattern patternin = Pattern.compile("\\w+in\\w+");
		Matcher matcherin = patternin.matcher(stemmedWord);

		Pattern patternum = Pattern.compile("\\w+um\\w+");
		Matcher matcherum = patternum.matcher(stemmedWord);

		String infix;

		if (matcherin.find()) {
			infix = "in";
		} else if (matcherum.find()) {
			infix = "um";
		} else {
			return stemmedWord;
		}

		System.out.println("INDEX" + stemmedWord.indexOf(infix));
		if (stemmedWord.indexOf(infix) < stemmedWord.length() / 2) {
			stemmedWord = stemmedWord.replaceFirst(infix, "");
		}

		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}

		return word;
	}

	public static String removeSuffix(String word) throws Exception {
		String stemmedWord = word;
		
                if (isStemmable(stemmedWord)) {
                    System.out.println("ENTERED");
                    if(isVowel(stemmedWord.charAt(0)) && stemmedWord.length() == 5 && stemmedWord.endsWith("ng"))
                        stemmedWord = stemmedWord.replace("ng", "");
                    if(!isVowel(stemmedWord.charAt(0)) && stemmedWord.length() == 5 && stemmedWord.endsWith("ng"))
                        stemmedWord = stemmedWord.replace("g", "");
                    if(stemmedWord.length() == 7 && stemmedWord.endsWith("ng"))
                        stemmedWord = stemmedWord.replace("ng", "");
                    if(stemmedWord.length() == 6 && stemmedWord.endsWith("ng"))
                        stemmedWord = stemmedWord.substring(0,stemmedWord.length()-1);
                    word = stemmedWord;
                }
                
		for (String suffix : SUFFIXES) {
            System.out.println("SUFFIX SHIT: "+stemmedWord);
            if(stemmedWord.endsWith(suffix) && suffix.equals("ng") && isStemmable(stemmedWord))
			{
				if(isVowel(stemmedWord.charAt(stemmedWord.length()-4)) && isVowel(stemmedWord.charAt(stemmedWord.length()-3)))
				{	stemmedWord = stemmedWord.substring(0,stemmedWord.length()-1);
				}
				else
				{	stemmedWord = stemmedWord.substring(0,stemmedWord.length()-2);
				}
			}
			else if (stemmedWord.endsWith(suffix) && isStemmable(stemmedWord)) {
				stemmedWord = stemmedWord.substring(0, stemmedWord.length() - suffix.length());
				if (stemmedWord.endsWith("r")
						&& (suffix.equals("in") || suffix.equals("an"))) {
					stemmedWord = stemmedWord.substring(0,
							stemmedWord.length() - 1) + "d";
				}
			}
		}

		if(stemmedWord.length()!=0)
		{
			if (stemmedWord.charAt(stemmedWord.length() - 1) == 'u') {
				stemmedWord = stemmedWord.substring(0, stemmedWord.length() - 1) + 'o';
			} else if (stemmedWord.charAt(stemmedWord.length() - 2) == 'u') {
				stemmedWord = stemmedWord.substring(0, stemmedWord.length() - 2)
						+ 'o' + stemmedWord.charAt(stemmedWord.length() - 1);
			}
		}
                
                
		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}

		return word;
	}

	public static String removePartialReduplicates12(String word)
			throws Exception {
		String stemmedWord = word;
		String consorep = "";
		// Case 1
		if (isVowel(stemmedWord.charAt(0))) {
			if (stemmedWord.charAt(1) == stemmedWord.charAt(0))
				stemmedWord = stemmedWord.substring(1, stemmedWord.length());
		}
		// Case 2
		else {
			int x = 0;
			int vowelcount = 0;
			while (stemmedWord.length() > x) {
				if (!isVowel(stemmedWord.charAt(x)) && vowelcount == 0)
					consorep += stemmedWord.charAt(x);
				else if (vowelcount == 0) {
					consorep += stemmedWord.charAt(x);
					vowelcount++;
				}
				x++;
			}
			if (stemmedWord.substring(consorep.length(),
					consorep.length() + consorep.length()).equals(consorep)) {
				stemmedWord = stemmedWord.substring(consorep.length(),
						stemmedWord.length());
			}
		}

		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}
		return word;
	}

	public static String removePartialReduplicates3(String word)
			throws Exception {
		// baka pede sa java nlng ikabit yung R hahahaha di ko gets HAHA
		String stemmedWord = word;
		if (stemmedWord.charAt(0) == stemmedWord.charAt(2)
				&& stemmedWord.charAt(1) == stemmedWord.charAt(4)) {
			stemmedWord = stemmedWord.substring(2, stemmedWord.length());
		}

		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}
		return word;
	}

	public static String removePartialReduplicates4(String word)
			throws Exception {
		String stemmedWord = word;
		Pattern pattern = Pattern.compile("(\\w+)-\\1\\w+");
		Matcher matcher = pattern.matcher(stemmedWord);

		if (matcher.matches()) {
			stemmedWord = stemmedWord.split("-")[1];
		}

		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}
		return word;
	}

	public static String removeFullReduplicates(String word) throws Exception {
		String stemmedWord = word;
		Pattern pattern = Pattern.compile("(\\w+)\\w-?\\1\\w");
		Matcher matcher = pattern.matcher(stemmedWord);

		if (matcher.matches()) {
			if (stemmedWord.contains("-")) {
				stemmedWord = stemmedWord.split("-")[1];
			} else {
				stemmedWord = stemmedWord.substring(stemmedWord.length() / 2,
						stemmedWord.length());
			}
		}

		if (isQualified(stemmedWord)) {
			return stemmedWord;
		}
		return word;
	}
}
