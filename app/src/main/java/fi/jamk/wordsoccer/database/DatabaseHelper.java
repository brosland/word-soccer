package fi.jamk.wordsoccer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteAssetHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "word_soccer.db";

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void reloadDatabase()
	{
		SQLiteDatabase database = getWritableDatabase();
		database.setVersion(-1);
		database.close();

		getWritableDatabase();
	}
}