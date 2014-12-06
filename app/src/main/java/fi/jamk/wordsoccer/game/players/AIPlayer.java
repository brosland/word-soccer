package fi.jamk.wordsoccer.game.players;

import android.os.AsyncTask;

import java.util.List;
import java.util.Random;

import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.Word;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;

public class AIPlayer extends Player
{
	private static final int MAX_WORDS = 100;
	private final Random percentageGenerator, selectiveGenerator;
	private final double minPercentage, maxPercentage;

	public AIPlayer(String name, Level level)
	{
		this(name, level.minPercentage, level.maxPercentage);
	}

	public AIPlayer(String name, double minPercentage, double maxPercentage)
	{
		super(name);

		this.percentageGenerator = new Random();
		this.selectiveGenerator = new Random();
		this.minPercentage = minPercentage;
		this.maxPercentage = maxPercentage;
	}

	@Override
	public void onStartRound(IGame game)
	{
		super.onStartRound(game);

		int letterCount = IGame.LETTERS - getNumberOfCards(Card.RED);
		String letters = game.getCurrentRoundLetters().substring(0, letterCount);

		new AsyncTask<Object, Void, List<String>>()
		{
			@Override
			protected List<String> doInBackground(Object... params)
			{
				char[] letters = ((String) params[0]).toCharArray();
				SQLiteDictionary dictionary = (SQLiteDictionary) params[1];

				return dictionary.getValidWordsFromLetters(letters);
			}

			@Override
			protected void onPostExecute(List<String> strings)
			{
				double percentage = minPercentage + (maxPercentage - minPercentage) * percentageGenerator.nextDouble();
				int count = (int) ((strings.size() > MAX_WORDS ? MAX_WORDS : strings.size()) * percentage);

				for (int i = 0; i < count; i++)
				{
					int index = selectiveGenerator.nextInt(strings.size());

					addWord(new Word(strings.remove(index)));
				}
			}
		}.execute(letters, game.getDictionary());
	}

	public enum Level
	{
		EASY(0.05, 0.10), MEDIUM(0.10, 0.20), HARD(0.15, 0.30), EXPERT(0.30, 0.50), IMPOSSIBLE(0.50, 0.80);

		public final double minPercentage, maxPercentage;

		private Level(double minPercentage, double maxPercentage)
		{
			this.minPercentage = minPercentage;
			this.maxPercentage = maxPercentage;
		}
	}
}