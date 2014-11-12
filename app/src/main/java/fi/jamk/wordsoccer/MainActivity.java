package fi.jamk.wordsoccer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.List;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.fragments.RoundFragment;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;
import fi.jamk.wordsoccer.game.games.SinglePlayerGame;
import fi.jamk.wordsoccer.game.players.AIPlayer;
import fi.jamk.wordsoccer.game.players.Player;

public class MainActivity extends Activity
{
	private Fragment currentFragment;
	private IGame game;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);

		// if game == null ? try to restore game from bundle

		// this will be in MainActivity -> onStartSinglePlayerGame
		SQLiteDictionary dictionary = new SQLiteDictionary(new DatabaseHelper(this), "en");
		Player playerA = new Player("You");
		AIPlayer playerB = new AIPlayer("AI Johny", 0.4, 0.6);

		game = new SinglePlayerGame(dictionary, playerA, playerB);
		//game.addGameListener(new SinglePlayerListener());
		game.startNewGame();
		game.startNewRound();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void startGame(View view)
	{
		RoundFragment roundFragment = RoundFragment.newInstance(game);

		Log.i("WS-letters", game.getCurrentRoundLetters());
		List<String> words = game.getDictionary().getCorrectWordsFromLetters(game.getCurrentRoundLetters().toCharArray());

		for (String word : words)
		{
			Log.i("WS-word:", word);
		}

		currentFragment = roundFragment;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_placeholder, currentFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}