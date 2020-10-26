import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CharacterCounter {

	public void readFileLineByLine(String fileName) {
		try (BufferedReader in = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
			for (String line; (line = in.readLine()) != null;)
				System.out.println(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String fileName = args[0];

		CharacterCounter characterCounter = new CharacterCounter();

		characterCounter.readFileLineByLine(fileName);
		
	}

}
