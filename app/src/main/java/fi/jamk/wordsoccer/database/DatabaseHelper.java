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
	private final HashMap<Class, IModel> models;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		models = new HashMap<Class, IModel>();
	}

	public void reloadDatabase()
	{
		SQLiteDatabase database = getWritableDatabase();
		database.setVersion(-1);
		database.close();

		getWritableDatabase();
	}

	public IModel getModel(Class classType)
	{
		if (!models.containsKey(classType))
		{
			try
			{
				Constructor<IModel> constructor = classType.getConstructor(new Class[]{SQLiteDatabase.class});
				IModel model = constructor.newInstance(new Object[]{getReadableDatabase()});

				models.put(classType, model);
			} catch (Exception e)
			{
				throw new IllegalStateException(e.getMessage());
			}
		}

		return models.get(classType);
	}
}