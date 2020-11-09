import java.util.Comparator;
import java.util.Map;

// https://stackoverflow.com/questions/1318980/how-to-iterate-over-a-treemap

public class ValueComparator implements Comparator<String> {
	private Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String word1, String word2) {
		return (base.get(word1) >= base.get(word2)) ? -1 : 1;
	}
}