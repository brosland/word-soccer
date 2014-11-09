package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IDictionary
{
	public char[] getCharset();

	public boolean isWordCorrect(String word);

	public List<String> getCorrectWordsFromLetters(char[] letters);
}