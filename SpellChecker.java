
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.isEmpty()) {
			return str;
		}
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		String word1 = word1.toLowerCase(); //wordlc = word lowercase (I believe it should be in the main :( )
		String word2 = word2.toLowerCase();
		int differences = 0;
		if (word1.length() == 0) {
			return word2.length();
		}
		if (word2.length() == 0) {
			return word1.length ();
		}
		if (word1.charAt(0) == word2.charAt(0)) {
			return levenshtein(tail(word1), tail(word2));
		}
		else {
			int tail1 = levenshtein(tail(word1), word2);
			int tail2 = levenshtein(word1, tail(word2));
			int tail12 = levenshtein(tail(word1), tail(word2));
			differences = 1 + Math.min(Math.min(tail1, tail2), tail12);

		}
		return differences;
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < 3000; i++) {
			dictionary[i] = in.readString();
		}
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int similarWordIndex = 0;
		int similarWordValue = word.length();
		int differences[] = new int[dictionary.length];
		for (int i = 0; i < dictionary.length; i++) {
			differences[i] = levenshtein(word, dictionary[i]);
		}
		for (int i = 0; i < differences.length; i++) {
			if (differences[i] < similarWordValue) {
				similarWordValue = differences[i];
				similarWordIndex = i;
			}
		}
		if (similarWordValue <= threshold) {
			return dictionary[similarWordIndex];
		}
		return word;
	}

}
