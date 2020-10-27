import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterCounter {

	private Map<Character, Integer> countByCharacter = new HashMap<>();

	private String text = "";
	
	private int consonantsCount = 0;
	private int vowelsCount = 0;
	private int umlautsCount = 0;
	private int otherCharactersCount = 0;
	
	public CharacterCounter(String fileName)
	{
		readAndOutputFileLineByLine(fileName);
		
		countCharacters();
	}
	
	private void readAndOutputFileLineByLine(String fileName) {
		try (BufferedReader in = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) { // UTF-8 kodierten Text einlesen
			for (String line; (line = in.readLine()) != null;) { // zeilenweise einlesen
				System.out.println(line);
				text += line + "\n"; // text = text + line...
			}
		} catch (IOException e) { // Fehler beim Lesen aufgetreten
			e.printStackTrace(); // Fehler in Konsole ausgeben
		}
	}
	
	public void showCharactersCount()
	{
		System.out.println(String.format("Der Text hat %d Zeichen. ", text.length()));
		
		for(Character character : countByCharacter.keySet())
		{
			String out = String.format("Character %s kommt %d mal vor. ", character, countByCharacter.get(character));
			System.out.println(out);
		}
	}
	
	public void showCharactersCountPercentage()
	{		
		float consonantsPercent = (float)consonantsCount / text.length() * 100;
		float vowelsPercent = (float)vowelsCount / text.length() * 100;
		float umlautsPercent = (float)umlautsCount / text.length() * 100;
		float otherCharactersPercent = (float)otherCharactersCount / text.length() * 100;
		
		// jeweils zwei Nachkommastellen ausgeben
		System.out.println(String.format("Konsonanten kommen zu %.2f%% vor", consonantsPercent)); // Prozent-Zeichen muss escaped werden mit %%
		System.out.println(String.format("Vokale kommen zu %.2f%% vor", vowelsPercent));
		System.out.println(String.format("Umlaute kommen zu %.2f%% vor", umlautsPercent));
		System.out.println(String.format("Sonstige Zeichen kommen zu %.2f%% vor", otherCharactersPercent));
	}
	
	private void countCharacters()
	{		
		for(Character character : text.toCharArray())
		{
			if(!countByCharacter.containsKey(character))
				countByCharacter.put(character, 0);
				
			int count = countByCharacter.get(character).intValue();
			
			countByCharacter.put(character, ++count);
			
			if(isConsonant(character))
				consonantsCount++;
			else if(isVowel(character))
				vowelsCount++;
			else if(isUmlaut(character))
				umlautsCount++;
			else
				otherCharactersCount++; // ß ist auch ein sonstiges Zeichen
		}
	}
	
	private boolean isConsonant(Character character)
	{
		// auf Buchstaben ist Ordnung definiert (https://www.ascii-code.com/)
		return !isVowel(character) && 
				((character >= 'a' && character <= 'z') 
				|| (character >= 'A' && character <= 'Z'));
	}
	
	private boolean isVowel(Character character)
	{
		List<Character> vowels = new ArrayList<>();
		// aeiou
		vowels.add('a');
		vowels.add('A');
		vowels.add('e');
		vowels.add('E');
		vowels.add('i');
		vowels.add('I');
		vowels.add('o');
		vowels.add('O');
		vowels.add('u');
		vowels.add('U');
		
		return vowels.contains(character);
	}
	
	private boolean isUmlaut(Character character)
	{
		List<Character> umlauts = new ArrayList<>();
		// äöü
		umlauts.add('ä');
		umlauts.add('Ä');
		umlauts.add('ö');
		umlauts.add('Ö');
		umlauts.add('ü');
		umlauts.add('Ü');
		
		return umlauts.contains(character);
	}
	
	public static void main(String[] args) {
		String fileName = args[0];

		CharacterCounter characterCounter = new CharacterCounter(fileName);

		characterCounter.showCharactersCount();
	
		characterCounter.showCharactersCountPercentage();
	}

}
