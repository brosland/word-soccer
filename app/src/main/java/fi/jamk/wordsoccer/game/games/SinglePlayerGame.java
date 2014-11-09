package fi.jamk.wordsoccer.game.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fi.jamk.wordsoccer.game.IDictionary;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;

public class SinglePlayerGame implements IGame
{
	private final int rounds;
	private final IDictionary dictionary;
	private final IPlayer playerA, playerB;
	private final Random generator;
	private List<IGameListener> listeners;
	private int currentRoundNumber;
	private String currentRoundLetters;

	public SinglePlayerGame(int rounds, IDictionary dictionary, IPlayer playerA, IPlayer playerB)
	{
		this.rounds = rounds;
		this.dictionary = dictionary;
		this.playerA = playerA;
		this.playerB = playerB;
		this.generator = new Random();
		this.listeners = new ArrayList<IGameListener>();

		listeners.add(playerA);
		listeners.add(playerB);
	}

	@Override
	public void startNewGame()
	{
		currentRoundNumber = 0;

		for (IGameListener listener : listeners)
		{
			listener.onStartGame(this);
		}
	}

	@Override
	public void startNewRound()
	{
		currentRoundNumber++;

		char[] charset = dictionary.getCharset();

		for (int i = 0; i < LETTERS; i++)
		{
			int index = generator.nextInt(charset.length);
			currentRoundLetters += charset[index];
		}

		for (IGameListener listener : listeners)
		{
			listener.onStartRound(this);
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
