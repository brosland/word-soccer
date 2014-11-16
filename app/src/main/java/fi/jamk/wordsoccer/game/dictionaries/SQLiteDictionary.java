package fi.jamk.wordsoccer.game.dictionaries;

import java.util.HashMap;
import java.util.List;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.database.models.LangModel;
import fi.jamk.wordsoccer.database.models.WordModel;
import fi.jamk.wordsoccer.game.IDictionary;

public class SQLiteDictionary implements IDictionary
{
	private final WordModel wordModel;
	private final LangModel langModel;
	private final String langCode;

	public SQLiteDictionary(DatabaseHelper databaseHelper, String langCode)
	{
		this.wordModel = (WordModel) databaseHelper.getModel(WordModel.class);
		this.langModel = (LangModel) databaseHelper.getModel(LangModel.class);
		this.langCode = langCode;
	}

	@Override
	public String getLangCode()
	{
		return langCode;
	}

	@Override
	public boolean isWordValid(String word)
	{
		return wordModel.hasWord(word, langCode);
	}

	public HashMap<Character, Double> getLetterFrequency()
	{
		return langModel.findLetterFrequency(langCode);
	}

	public List<String> getValidWordsFromLetters(char[] letters)
	{
		return wordModel.findWords(letters, langCode);
	}
}