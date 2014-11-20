package fi.jamk.wordsoccer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
	}

	public void onClickPlayGameButton(View view)
	{
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	public void onClickSettingsButton(View view)
	{
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void onClickExitButton(View view)
	{
		finish();
	}
}