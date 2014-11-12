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
	private final String langCode;
	private final HashMap<Character, Double> letterFrequency;

	public SQLiteDictionary(DatabaseHelper databaseHelper, String langCode)
	{
		this.wordModel = (WordModel) databaseHelper.getModel(WordModel.class);
		this.langCode = langCode;

		LangModel langModel = (LangModel) databaseHelper.getModel(LangModel.class);
		this.letterFrequency = langModel.findLetterFrequency(langCode);
	}

	@Override
	public String getLangCode()
	{
		return langCode;
	}

	public HashMap<Character, Double> getLetterFrequency()
	{
		return letterFrequency;
	}

	@Override
	public boolean isWordCorrect(String word)
	{
		return wordModel.hasWord(word, langCode);
	}

	@Override
	public List<String> getCorrectWordsFromLetters(char[] letters)
	{
		return wordModel.findWords(letters, langCode);
	}
}