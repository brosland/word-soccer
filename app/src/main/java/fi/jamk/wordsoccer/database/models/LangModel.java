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

	public HashMap<Character, Double> findLetterFrequency(String langCode)
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
}