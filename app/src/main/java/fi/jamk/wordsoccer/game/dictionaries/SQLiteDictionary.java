package fi.jamk.wordsoccer.game.dictionaries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.game.ISinglePlayerDictionary;

public class SQLiteDictionary implements ISinglePlayerDictionary
{
	private final String langCode;
	private final SQLiteDatabase database;

	public SQLiteDictionary(DatabaseHelper databaseHelper, String langCode)
	{
		this.database = databaseHelper.getReadableDatabase();
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
		String query = "SELECT word.* FROM word " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"WHERE word.word = ? AND lang.code = ?";

		Cursor cursor = database.rawQuery(query, new String[]{word, langCode});

		return cursor.getCount() > 0;
	}

	@Override
	public HashMap<Character, Double> getSignFrequency()
	{
		HashMap<Character, Double> letterFrequency = new HashMap<Character, Double>();

		String query = "SELECT sf.sign, sf.frequency " +
			"FROM signfrequency sf " +
			"INNER JOIN lang ON sf.lang_id = lang._id " +
			"WHERE lang.code = ?;";

		Cursor cursor = database.rawQuery(query, new String[]{langCode});
		cursor.moveToFirst();

		while (!cursor.isAfterLast())
		{
			letterFrequency.put(cursor.getString(0).charAt(0), cursor.getDouble(1));
			cursor.moveToNext();
		}

		return letterFrequency;
	}

	@Override
	public List<String> getValidWordsFromLetters(char[] letters)
	{
		String query = "SELECT word.word FROM word " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"INNER JOIN sign ON word._id = sign.word_id " +
			"WHERE CASE (sign.sign) %s END AND lang.code = ? " +
			"GROUP BY word._id HAVING SUM(sign.number) = LENGTH(word.word);";

		StringBuilder conditions = new StringBuilder();
		HashMap<Character, Integer> charCounts = getSignCounts(letters);

		for (char sign : charCounts.keySet())
		{
			conditions.append(String.format("WHEN '%c' THEN sign.number <= %d ", sign, charCounts.get(sign)));
		}

		ArrayList<String> words = new ArrayList<String>(100);

		Cursor cursor = database.rawQuery(String.format(query, conditions), new String[]{langCode});
		cursor.moveToFirst();

		while (!cursor.isAfterLast())
		{
			words.add(cursor.getString(0));
			cursor.moveToNext();
		}

		return words;
	}

	private HashMap<Character, Integer> getSignCounts(char[] chars)
	{
		HashMap<Character, Integer> signCounts = new HashMap<Character, Integer>();

		for (char sign : chars)
		{
			signCounts.put(sign, signCounts.containsKey(sign) ? signCounts.get(sign) + 1 : 1);
		}

		return signCounts;
	}
}