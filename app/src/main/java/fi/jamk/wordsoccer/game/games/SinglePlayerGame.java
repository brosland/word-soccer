package fi.jamk.wordsoccer.game.games;

import java.util.ArrayList;
import java.util.List;

import fi.jamk.wordsoccer.game.IDictionary;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.LetterGenerator;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;

public class SinglePlayerGame implements IGame
{
	private final SQLiteDictionary dictionary;
	private final LetterGenerator generator;
	private final IPlayer playerA, playerB;
	private List<IGameListener> listeners;
	private int currentRoundNumber;
	private String currentRoundLetters;

	public SinglePlayerGame(SQLiteDictionary dictionary, IPlayer playerA, IPlayer playerB)
	{
		this.dictionary = dictionary;
		generator = new LetterGenerator(dictionary.getLetterFrequency());

		this.playerA = playerA;
		this.playerB = playerB;

		listeners = new ArrayList<IGameListener>();
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
		currentRoundLetters = "";

		for (int i = 0; i < LETTERS; i++)
		{
			currentRoundLetters += generator.nextLetter();
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