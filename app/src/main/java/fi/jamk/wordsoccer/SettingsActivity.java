package fi.jamk.wordsoccer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class SettingsActivity extends PreferenceActivity
{
	public static final String KEY_PLAYER_NAME = "player_name";
	public static final String KEY_AI_PLAYER_NAME = "ai_player_name";
	public static final String KEY_AI_PLAYER_LEVEL = "ai_player_level";

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(android.R.id.content, new MyPreferenceFragment());
		transaction.commit();
	}

	public static class MyPreferenceFragment extends PreferenceFragment
	{
		@Override
		public void onCreate(final Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}
}