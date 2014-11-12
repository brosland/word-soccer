package fi.jamk.wordsoccer.database.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class LangModel extends BaseModel
{
	public LangModel(SQLiteDatabase database)
	{
		super(database);
	}

	public char[] findCharset(String langCode)
	{
		String query = "SELECT DISTINCT sign.sign FROM sign " +
			"INNER JOIN word ON sign.word_id = word._id " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"WHERE lang.code = ? " +
			"ORDER BY sign.sign;";

		Cursor cursor = database.rawQuery(query, new String[]{langCode});
		cursor.moveToFirst();

		char[] charset = new char[cursor.getCount()];

		for (int i = 0; i < charset.length; i++, cursor.moveToNext())
		{
			charset[i] = cursor.getString(0).charAt(0);
		}

		return charset;
	}

	public HashMap<Character, Double> findLetterFrequency(String langCode)
	{
		HashMap<Character, Double> letterFrequency = new HashMap<Character, Double>();

		String lengthQuery = "SELECT SUM(LENGTH(word.word)) " +
			"FROM word " +
			"INNER JOIN Lang ON word.lang_id = Lang._id " +
			"WHERE Lang.code = ?;";

		Cursor lengthCursor = database.rawQuery(lengthQuery, new String[]{langCode});
		lengthCursor.moveToFirst();

		int length = lengthCursor.getInt(0);

		String charsetFrequencyQuery = "SELECT DISTINCT sign.sign, SUM(sign.number) " +
			"FROM sign " +
			"INNER JOIN word ON sign.word_id = word._id " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"WHERE lang.code = ? " +
			"GROUP BY sign.sign " +
			"ORDER BY sign.sign;";

		Cursor cursor = database.rawQuery(charsetFrequencyQuery, new String[]{langCode});
		cursor.moveToFirst();

		while (!cursor.isAfterLast())
		{
			letterFrequency.put(cursor.getString(0).charAt(0), 1.0 * cursor.getInt(1) / length);
			cursor.moveToNext();
		}

		return letterFrequency;
	}
}