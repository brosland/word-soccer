package fi.jamk.wordsoccer.game.dictionaries;

import java.util.List;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.database.models.LangModel;
import fi.jamk.wordsoccer.database.models.WordModel;
import fi.jamk.wordsoccer.game.IDictionary;

public class SQLiteDictionary implements IDictionary
{
	private WordModel wordModel;
	private LangModel langModel;
	private final String langCode;
	private final char[] charset;

	public SQLiteDictionary(DatabaseHelper databaseHelper, String langCode)
	{
		this.wordModel = (WordModel) databaseHelper.getModel(WordModel.class);
		this.langModel = (LangModel) databaseHelper.getModel(LangModel.class);
		this.langCode = langCode;
		this.charset = langModel.findCharset(langCode);
	}

	@Override
	public char[] getCharset()
	{
		return charset;
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