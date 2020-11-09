import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CharacterCounter {

	private Map<Character, Integer> countByCharacter = new HashMap<>();
	private Map<String, Integer> countByWord = new HashMap<>();
	
	private ValueComparator sortByValueDescendingComparator = new ValueComparator(countByWord);
	private TreeMap<String, Integer> sortedCountByWord = new TreeMap<String, Integer>(sortByValueDescendingComparator);

	private static final List<Character> VOWELS = new ArrayList<>();
	private static final List<Character> UMLAUTS = new ArrayList<>();

	static {
		VOWELS.add('a');
		VOWELS.add('A');
		VOWELS.add('e');
		VOWELS.add('E');
		VOWELS.add('i');
		VOWELS.add('I');
		VOWELS.add('o');
		VOWELS.add('O');
		VOWELS.add('u');
		VOWELS.add('U');

		UMLAUTS.add('ä');
		UMLAUTS.add('Ä');
		UMLAUTS.add('ö');
		UMLAUTS.add('Ö');
		UMLAUTS.add('ü');
		UMLAUTS.add('Ü');
	}

	private String text;
	private String[] words;

	private int consonantsCount = 0;
	private int vowelsCount = 0;
	private int umlautsCount = 0;
	private int otherCharactersCount = 0;

	public CharacterCounter(String fileName) {
		readAndOutputFileLineByLine(fileName);

		countCharacters();

		countWords();
	}

	private static boolean isConsonant(Character character) {
		// auf Buchstaben ist Ordnung definiert (https://www.ascii-code.com/)
		return !isVowel(character)
				&& ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z'));
	}

	private static boolean isVowel(Character character) {
		return VOWELS.contains(character);
	}

	private static boolean isUmlaut(Character character) {
		return UMLAUTS.contains(character);
	}

	private void readAndOutputFileLineByLine(String fileName) {
		try (BufferedReader in = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) { // UTF-8
																											// kodierten
																											// Text
																											// einlesen
			for (String line; (line = in.readLine()) != null;) { // zeilenweise einlesen
				System.out.println(line);
				text += line + "\n"; // text = text + line...
			}
		} catch (IOException e) { // Fehler beim Lesen aufgetreten
			e.printStackTrace(); // Fehler in Konsole ausgeben
		}
	}

	private void countCharacters() {
		for (Character character : text.toCharArray()) {
			if (!countByCharacter.containsKey(character))
				countByCharacter.put(character, 0);

			int count = countByCharacter.get(character).intValue();

			countByCharacter.put(character, ++count);

			if (isConsonant(character))
				consonantsCount++;
			else if (isVowel(character))
				vowelsCount++;
			else if (isUmlaut(character))
				umlautsCount++;
			else
				otherCharactersCount++; // ß ist auch ein sonstiges Zeichen
		}
	}

	private void countWords() {
		words = text.split("[.,:;!? \n]+");

		for (String word : words) {
			if (!countByWord.containsKey(word))
				countByWord.put(word, 0);

			int wordCount = countByWord.get(word).intValue();

			countByWord.put(word, ++wordCount);
		}
		
		sortedCountByWord.putAll(countByWord);
	}

	public void showCharactersCount() {
		System.out.println(String.format("Der Text hat %d Zeichen. ", text.length()));

		for (Character character : countByCharacter.keySet()) {
			String out = String.format("Character %s kommt %d mal vor. ", character, countByCharacter.get(character));
			System.out.println(out);
		}
	}

	public void showCharactersCountPercentage() {
		float consonantsPercent = (float) consonantsCount / text.length() * 100;
		float vowelsPercent = (float) vowelsCount / text.length() * 100;
		float umlautsPercent = (float) umlautsCount / text.length() * 100;
		float otherCharactersPercent = (float) otherCharactersCount / text.length() * 100;

		// jeweils zwei Nachkommastellen ausgeben
		System.out.println(String.format("Konsonanten kommen zu %.2f%% vor", consonantsPercent)); // Prozent-Zeichen
																									// muss escaped
																									// werden mit %%
		System.out.println(String.format("Vokale kommen zu %.2f%% vor", vowelsPercent));
		System.out.println(String.format("Umlaute kommen zu %.2f%% vor", umlautsPercent));
		System.out.println(String.format("Sonstige Zeichen kommen zu %.2f%% vor", otherCharactersPercent));
	}

	public void showWordsCountSorted() {
		System.out.println(String.format("Der Text hat %d Wörter. ", words.length));

		for (Map.Entry<String, Integer> entry : sortedCountByWord.entrySet()) {
			String word = entry.getKey();
			Integer wordCount = entry.getValue();
			
			String out = String.format("Wort \"%s\" kommt %d mal vor. ", word, wordCount);
			System.out.println(out);
		}
	}
}
