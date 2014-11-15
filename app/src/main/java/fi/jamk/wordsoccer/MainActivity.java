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
import android.widget.TextView;

import java.util.List;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.fragments.RoundFragment;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
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
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
//		databaseHelper.reloadDatabase();

		SQLiteDictionary dictionary = new SQLiteDictionary(databaseHelper, "en");

		Player playerA = new Player("You");
		playerA.addListener(new PlayerListener(
			(TextView) findViewById(R.id.playerARedCardsTextView),
			(TextView) findViewById(R.id.playerAYellowCardsTextView),
			(TextView) findViewById(R.id.playerAScoreTextView)));

		AIPlayer playerB = new AIPlayer("AI Johny", 0.4, 0.6);
		playerB.addListener(new PlayerListener(
			(TextView) findViewById(R.id.playerBRedCardsTextView),
			(TextView) findViewById(R.id.playerBYellowCardsTextView),
			(TextView) findViewById(R.id.playerBScoreTextView)));

		game = new SinglePlayerGame(dictionary, playerA, playerB);
		//game.addGameListener(new SinglePlayerListener());
		game.startNewGame();

		playerB.addCard(new Card(Card.CardType.RED, ""));
//		playerA.addCard(new Card(Card.CardType.YELLOW, ""));
//		playerA.addCard(new Card(Card.CardType.YELLOW, ""));
		playerA.addCard(new Card(Card.CardType.YELLOW, ""));


		((TextView) findViewById(R.id.playerANameTextView)).setText(playerA.getName());
		((TextView) findViewById(R.id.playerBNameTextView)).setText(playerB.getName());

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

//		Log.i("WS-letters", game.getCurrentRoundLetters());
//		List<String> words = game.getDictionary().getValidWordsFromLetters(game.getCurrentRoundLetters().toCharArray());
//
//		for (String word : words)
//		{
//			Log.i("WS-word:", word);
//		}

		currentFragment = roundFragment;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_placeholder, currentFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private class PlayerListener implements IPlayer.IPlayerListener
	{
		private TextView redCardsTextView, yellowCardsTextView, scoreTextView;

		public PlayerListener(TextView redCardsTextView, TextView yellowCardsTextView, TextView scoreTextView)
		{
			this.redCardsTextView = redCardsTextView;
			this.yellowCardsTextView = yellowCardsTextView;
			this.scoreTextView = scoreTextView;
		}

		@Override
		public void onCardAdded(IPlayer player, Card card)
		{
			redCardsTextView.setVisibility(player.getNumberOfCards(Card.CardType.RED) > 0
				? View.VISIBLE : View.GONE);
			redCardsTextView.setText(player.getNumberOfCards(Card.CardType.RED) > 1
				? Integer.toString(player.getNumberOfCards(Card.CardType.RED)) : "");
			yellowCardsTextView.setVisibility(player.getNumberOfCards(Card.CardType.YELLOW) > 0
				? View.VISIBLE : View.GONE);
		}

		@Override
		public void onScoreChanged(IPlayer player)
		{
			scoreTextView.setText(Integer.toString(player.getScore()));
		}
	}
}