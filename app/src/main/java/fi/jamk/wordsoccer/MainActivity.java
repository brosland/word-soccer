package fi.jamk.wordsoccer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import fi.jamk.wordsoccer.database.DatabaseHelper;
import fi.jamk.wordsoccer.fragments.FinalResultsFragment;
import fi.jamk.wordsoccer.fragments.ResultsFragment;
import fi.jamk.wordsoccer.fragments.RoundFragment;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;
import fi.jamk.wordsoccer.game.games.SinglePlayerGame;
import fi.jamk.wordsoccer.game.players.AIPlayer;
import fi.jamk.wordsoccer.game.players.Player;

public class MainActivity extends Activity implements IGame.IGameListener
{
	private TextView playerAScoreTextView, playerARedCardTextView, playerAYellowCardTextView;
	private TextView playerBScoreTextView, playerBRedCardTextView, playerBYellowCardTextView;
	private TextView roundTextView, timeTextView, statusTextView;
	private ProgressBar statusProgressBar;
	private Button continueButton;
	private View headerBarView, footerBarView;
	private Fragment currentFragment;
	private IGame game;
	private CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);

		playerAScoreTextView = (TextView) findViewById(R.id.playerAScoreTextView);
		playerAYellowCardTextView = (TextView) findViewById(R.id.playerAYellowCardsTextView);
		playerARedCardTextView = (TextView) findViewById(R.id.playerARedCardsTextView);

		playerBScoreTextView = (TextView) findViewById(R.id.playerBScoreTextView);
		playerBYellowCardTextView = (TextView) findViewById(R.id.playerBYellowCardsTextView);
		playerBRedCardTextView = (TextView) findViewById(R.id.playerBRedCardsTextView);

		roundTextView = (TextView) findViewById(R.id.roundTextView);
		timeTextView = (TextView) findViewById(R.id.timeTextView);
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		statusProgressBar = (ProgressBar) findViewById(R.id.statusProgressBar);

		continueButton = (Button) findViewById(R.id.continueButton);

		headerBarView = findViewById(R.id.headerBarRelativeLayout);
		footerBarView = findViewById(R.id.footerBarRelativeLayout);

		createGame();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if (timer != null)
		{
			timer.cancel();
		}

		if (game != null)
		{
			game.removeGameListener(this);
		}
	}

	private void createGame() // TODO via settings in bundle
	{
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
//		databaseHelper.reloadDatabase();
		SQLiteDictionary dictionary = new SQLiteDictionary(databaseHelper, "en");

		AIPlayer playerA = new AIPlayer("AI Brita", 0.1, 0.2);
		AIPlayer playerB = new AIPlayer("AI Johny", 0.1, 0.2);

		game = new SinglePlayerGame(dictionary, playerA, playerB);
		game.addGameListener(this);
		game.init();

		updateStatusBar("Preparing game...", true);
		setFooterBarVisibility(true);
	}

	@Override
	public void onInit(IGame game)
	{
		TextView playerANameTextView = (TextView) findViewById(R.id.playerANameTextView);
		playerANameTextView.setText(game.getPlayerA().getName());

		TextView playerBNameTextView = (TextView) findViewById(R.id.playerBNameTextView);
		playerBNameTextView.setText(game.getPlayerB().getName());

		headerBarView.setVisibility(View.VISIBLE);

		game.startNewGame();
	}

	@Override
	public void onStartGame(IGame game)
	{
		updateStatusBar("Preparing new round...", true);

		game.startNewRound();
	}

	@Override
	public void onStartRound(final IGame game)
	{
		updateStatusBar("", false);
		setFooterBarVisibility(false);

		if (game.getCurrentRoundNumber() > IGame.ROUNDS)
		{
			roundTextView.setText(String.format("+%d.", game.getCurrentRoundNumber() - IGame.ROUNDS));
		}
		else
		{
			roundTextView.setText(String.format("%d", game.getCurrentRoundNumber()));
		}

		final RoundFragment roundFragment = RoundFragment.newInstance(game);
		currentFragment = roundFragment;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_placeholder, currentFragment);
		transaction.commit();

		timer = new CountDownTimer(IGame.ROUND_DURATION, 1000)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				int seconds = (int) (millisUntilFinished / 1000);

				timeTextView.setText(String.format("%d:%02d", seconds / 60, seconds % 60));
				timeTextView.setTextAppearance(MainActivity.this, seconds < 10
					? R.style.CriticalTimeTextView : R.style.TimeTextView);
			}

			@Override
			public void onFinish()
			{
				timeTextView.setText(String.format("0:00"));

				roundFragment.setLettersBarVisibility(false);

				game.finishRound();
			}
		}.start();
	}

	@Override
	public void onFinishRound(IGame game)
	{
		updateStatusBar("Loading opponent words...", true);
		setFooterBarVisibility(true);
	}

	@Override
	public void onOpponentWordsLoaded(IGame game)
	{
		((RoundFragment) currentFragment).showOpponentWordList(game.getPlayerB());

		updateStatusBar("Evaluating round...", true);

		game.evaluateRound();
	}

	@Override
	public void onEvaluateRound(final IGame game)
	{
		updateStatusBar("Press continue", false);

		continueButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final ResultsFragment resultsFragment = ResultsFragment.newInstance(game);
				currentFragment = resultsFragment;

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_placeholder, currentFragment);
				transaction.commit();

				continueButton.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						game.updateScore();
					}
				});
			}
		});
		continueButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onUpdateScore(IGame game)
	{
		// player A
		playerAScoreTextView.setText(Integer.toString(game.getPlayerA().getScore()));

		playerAYellowCardTextView.setVisibility(game.getPlayerA().getNumberOfCards(Card.CardType.YELLOW) > 0 ? View.VISIBLE : View.GONE);

		playerARedCardTextView.setVisibility(game.getPlayerA().getNumberOfCards(Card.CardType.RED) > 0 ? View.VISIBLE : View.GONE);
		playerARedCardTextView.setText(game.getPlayerA().getNumberOfCards(Card.CardType.RED) > 1
			? Integer.toString(game.getPlayerA().getNumberOfCards(Card.CardType.RED)) : "");

		// player B
		playerBScoreTextView.setText(Integer.toString(game.getPlayerB().getScore()));

		playerBYellowCardTextView.setVisibility(game.getPlayerB().getNumberOfCards(Card.CardType.YELLOW) > 0 ? View.VISIBLE : View.GONE);

		playerBRedCardTextView.setVisibility(game.getPlayerB().getNumberOfCards(Card.CardType.RED) > 0 ? View.VISIBLE : View.GONE);
		playerBRedCardTextView.setText(game.getPlayerB().getNumberOfCards(Card.CardType.RED) > 1
			? Integer.toString(game.getPlayerB().getNumberOfCards(Card.CardType.RED)) : "");

		updateStatusBar("Preparing new round...", true);

		if (game.hasNextRound())
		{
			game.startNewRound();
		}
		else
		{
			game.finishGame();
		}
	}

	@Override
	public void onFinishGame(IGame game)
	{
		FinalResultsFragment finalResultsFragment = FinalResultsFragment.newInstance(game);
		currentFragment = finalResultsFragment;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_placeholder, currentFragment);
		transaction.commit();

		updateStatusBar("Press continue to close game.", false);

		continueButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	private void updateStatusBar(String message, boolean showProgressBar)
	{
		statusTextView.setText(message);
		statusProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
	}

	private void setFooterBarVisibility(boolean visible)
	{
		footerBarView.setVisibility(visible ? View.VISIBLE : View.GONE);
	}
}