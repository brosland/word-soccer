package fi.jamk.wordsoccer.game.players;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IDictionary;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.Word;
import fi.jamk.wordsoccer.game.dictionaries.SQLiteDictionary;

public class AIPlayer extends Player
{
	private final Random percentageGenerator, selectiveGenerator;
	private final double minPercentage, maxPercentage;

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

		int letterCount = IGame.LETTERS - getNumberOfCards(Card.CardType.RED);
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
				int count = (int) (strings.size() * percentage);

				for (int i = 0; i < count; i++)
				{
					int index = selectiveGenerator.nextInt(strings.size());

					addWord(new Word(strings.remove(index)));
				}
			}
		}.execute(letters, game.getDictionary());
	}
}