package fi.jamk.wordsoccer.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.StringTokenizer;

import fi.jamk.wordsoccer.database.models.*;

public class DatabaseHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "word_soccer.db";
	public static final Class<IModel>[] MODEL_CLASS_TYPES = new Class[]
		{LangModel.class, WordModel.class, SignModel.class};
	private final Context context;
	private final HashMap<Class, IModel> models;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		this.context = context;
		this.models = new HashMap<Class, IModel>();
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		StringBuilder columns = new StringBuilder();

		for (Class<IModel> modelClass : MODEL_CLASS_TYPES)
		{
			IModel model = getModel(modelClass);
			StringBuilder columnDefinitions = new StringBuilder();

			for (IModel.IDatabaseColumn column : model.getColumns())
			{
				columns.append(columnDefinitions.length() > 0 ? ", " : "")
					.append(column.getName()).append(" ")
					.append(column.getType()).append(" ")
					.append(column.getAttributes());
			}

			String query = String.format("CREATE TABLE %s (%s);", model.getTableName(), columns);
			database.execSQL(query);

			importDataFromCSV(model, "database/" + model.getTableName() + ".csv");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		for (Class tableClass : MODEL_CLASS_TYPES)
		{
			IModel model = getModel(tableClass);

			database.execSQL(String.format("DROP TABLE IF EXISTS %s;", model.getTableName()));
		}

		onCreate(database);
	}

	public void importDataFromCSV(IModel table, String fileName)
	{
		SQLiteDatabase database = getWritableDatabase();
		AssetManager assetManager = context.getAssets();

		try
		{
			database.beginTransaction();

			InputStream inputStream = assetManager.open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

			String line;
			StringTokenizer stringTokenizer;
			ContentValues values;

			while ((line = reader.readLine()) != null)
			{
				stringTokenizer = new StringTokenizer(line, "|");
				values = new ContentValues();

				for (IModel.IDatabaseColumn column : table.getColumns())
				{
					values.put(column.getName(), stringTokenizer.nextToken());
				}

				database.insert(table.getTableName(), null, values);
			}

			database.setTransactionSuccessful();
		}
		catch (IOException e)
		{
			database.endTransaction();

			e.printStackTrace();
		}

		database.close();
	}

	public IModel getModel(Class classType)
	{
		if (!models.containsKey(classType))
		{
			try
			{
				Constructor<IModel> constructor = classType.getConstructor(new Class[]{SQLiteDatabase.class});
				IModel model = constructor.newInstance(new Object[]{getWritableDatabase()});

				models.put(classType, model);
			}
			catch (Exception e)
			{
				throw new IllegalStateException(e.getMessage());
			}
		}

		return models.get(classType);
	}
}