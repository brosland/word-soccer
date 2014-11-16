package fi.jamk.wordsoccer.game;

import java.util.HashMap;
import java.util.List;

public interface IDictionary
{
	public String getLangCode();

	public boolean isWordValid(String word);
}