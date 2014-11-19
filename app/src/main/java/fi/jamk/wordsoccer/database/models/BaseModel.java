package fi.jamk.wordsoccer.database.models;

import android.database.sqlite.SQLiteDatabase;

import fi.jamk.wordsoccer.database.IModel;

public abstract class BaseModel implements IModel
{
	protected SQLiteDatabase database;

	public BaseModel(SQLiteDatabase database)
	{
		this.database = database;
	}
}