package fi.jamk.wordsoccer.game.games;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fi.jamk.wordsoccer.game.IDictionary;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.LetterGenerator;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;

public class SinglePlayerGame implements IGame
{
	private final SQLiteDictionary dictionary;
	private final IPlayer playerA, playerB;
	private final List<IGameListener> listeners;
	private LetterGenerator generator;
	private int currentRoundNumber;
	private String currentRoundLetters;

	public SinglePlayerGame(SQLiteDictionary dictionary, IPlayer playerA, IPlayer playerB)
	{
		this.dictionary = dictionary;
		this.playerA = playerA;
		this.playerB = playerB;
		this.listeners = new ArrayList<IGameListener>();
	}

	@Override
	public void init()
	{
		new AsyncTask<SQLiteDictionary, Void, HashMap<Character, Double>>()
		{
			@Override
			protected HashMap<Character, Double> doInBackground(SQLiteDictionary... params)
			{
				return params[0].getLetterFrequency();
			}

			@Override
			protected void onPostExecute(HashMap<Character, Double> letterFrequency)
			{
				generator = new LetterGenerator(letterFrequency);

				for (IGameListener listener : listeners)
				{
					listener.onInit(SinglePlayerGame.this);
				}
			}
		}.execute(dictionary);
	}

	@Override
	public void startNewGame()
	{
		currentRoundNumber = 0;

		playerA.onStartGame(this);
		playerB.onStartGame(this);

		for (IGameListener listener : listeners)
		{
			listener.onStartGame(this);
		}
	}

	@Override
	public void startNewRound()
	{
		currentRoundNumber++;
		currentRoundLetters = "";

		for (int i = 0; i < LETTERS; i++)
		{
			currentRoundLetters += generator.nextLetter();
		}

		playerA.onStartRound(this);
		playerB.onStartRound(this);

		for (IGameListener listener : listeners)
		{
			listener.onStartRound(this);
		}
	}

	@Override
	public void finishRound()
	{
		for (IGameListener listener : listeners)
		{
			listener.onFinishRound(this);
		}

		for (IGameListener listener : listeners)
		{
			listener.onOpponentWordsLoaded(SinglePlayerGame.this);
		}
	}

	@Override
	public void evaluateRound()
	{
		for (IGameListener listener : listeners)
		{
			listener.onEvaluateRound(this);
		}
	}

	@Override
	public void updateScore()
	{
		for (IGameListener listener : listeners)
		{
			listener.onUpdateScore(this);
		}
	}

	@Override
	public void finishGame()
	{
		for (IGameListener listener : listeners)
		{
			listener.onFinishGame(this);
		}
	}

	@Override
	public IDictionary getDictionary()
	{
		return dictionary;
	}

	@Override
	public IPlayer getPlayerA()
	{
		return playerA;
	}

	@Override
	public IPlayer getPlayerB()
	{
		return playerB;
	}

	@Override
	public int getCurrentRoundNumber()
	{
		return currentRoundNumber;
	}

	@Override
	public String getCurrentRoundLetters()
	{
		return currentRoundLetters;
	}

	@Override
	public IGame addGameListener(IGameListener listener)
	{
		listeners.add(listener);

		return this;
	}

	@Override
	public IGame removeGameListener(IGameListener listener)
	{
		listeners.remove(listener);

		return this;
	}
}