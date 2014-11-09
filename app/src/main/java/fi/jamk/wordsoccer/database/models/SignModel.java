package fi.jamk.wordsoccer.database.models;

import android.database.sqlite.SQLiteDatabase;

public class SignModel extends BaseModel
{
	public static final String TABLE_NAME = "sign";

	public SignModel(SQLiteDatabase database)
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

	public enum Column implements IDatabaseColumn
	{
		ID("_id", "INTEGER", "PRIMARY KEY"),
		WORD_ID("word_id", "INTEGER", ""),
		SIGN("sign", "VARCHAR", ""),
		NUMBER("number", "INTEGER", "");

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