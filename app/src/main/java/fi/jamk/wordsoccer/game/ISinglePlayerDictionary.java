package fi.jamk.wordsoccer.game;

import java.util.HashMap;
import java.util.List;

public interface ISinglePlayerDictionary extends IDictionary
{
	public HashMap<Character, Double> getSignFrequency();

	public List<String> getValidWordsFromLetters(char[] letters);
}