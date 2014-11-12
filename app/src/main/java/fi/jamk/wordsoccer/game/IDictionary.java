package fi.jamk.wordsoccer.game;

import java.util.HashMap;
import java.util.List;

public interface IDictionary
{
	public String getLangCode();

	public boolean isWordCorrect(String word);
// pubic void requestWordCheck(String word, ResultListener l);
	// interface ResultListener{ void wordChecked(boolean result);}
	public List<String> getCorrectWordsFromLetters(char[] letters);
}