public class Program {
	
	public static void main(String[] args) {
		String fileName = args[0];

		CharacterCounter characterCounter = new CharacterCounter(fileName);
		
		characterCounter.showCharactersCount();
	
		characterCounter.showCharactersCountPercentage();
		
		characterCounter.showWordsCountSorted();
	}
}
