package fi.jamk.wordsoccer.database.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordModel extends BaseModel
{
	public static final String TABLE_NAME = "word";

	public WordModel(SQLiteDatabase database)
	{
		super(database);
	}

	@Override
	public String getTableName()
	{
		return TABLE_NAME;
	}

	@Override
	public IDatabaseColumn[] getColumns()
	{
		return Column.values();
	}

	public boolean hasWord(String word, String langCode)
	{
		String query = "SELECT word.* FROM word " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"WHERE word.word = ? AND lang.code = ?";

		Cursor cursor = database.rawQuery(query, new String[]{word, langCode});

		return cursor.getCount() > 0;
	}

	public List<String> findWords(char[] chars, String langCode)
	{
		String query = "SELECT word.word FROM word " +
			"INNER JOIN lang ON word.lang_id = lang._id " +
			"INNER JOIN sign ON word._id = sign.word_id " +
			"WHERE CASE (sign.sign) %s END AND lang.code = ? " +
			"GROUP BY word._id HAVING SUM(sign.number) = LENGTH(word.word);";

		StringBuilder conditions = new StringBuilder();
		HashMap<Character, Integer> charsCount = getCharsCount(chars);

		for (char sign : charsCount.keySet())
		{
			conditions.append(String.format("WHEN '%c' THEN sign.number <= %d ", sign, charsCount.get(sign)));
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

	private HashMap<Character, Integer> getCharsCount(char[] chars)
	{
		HashMap<Character, Integer> charsCount = new HashMap<Character, Integer>();

		for (char sign : chars)
		{
			charsCount.put(sign, charsCount.containsKey(sign) ? charsCount.get(sign) + 1 : 1);
		}

		return charsCount;
	}

	public enum Column implements IDatabaseColumn
	{
		ID("_id", "INTEGER", "PRIMARY KEY"),
		WORD("word", "VARCHAR", "UNIQUE"),
		LANG_ID("lang_id", "INTEGER", "");

		private final String name;
		private final String type;
		private final String attributes;

		private Column(String name, String type, String attributes)
		{
			this.name = name;
			this.type = type;
			this.attributes = attributes;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String getType()
		{
			return type;
		}

		@Override
		public String getAttributes()
		{
			return attributes;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
}