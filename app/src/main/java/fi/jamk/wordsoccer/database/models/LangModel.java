package fi.jamk.wordsoccer.database.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LangModel extends BaseModel
{
	public static final String TABLE_NAME = "lang";

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

	public enum Column implements IDatabaseColumn
	{
		ID("_id", "INTEGER", "PRIMARY KEY"),
		CODE("code", "VARCHAR", "UNIQUE"),
		NAME("name", "VARCHAR", "UNIQUE");

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