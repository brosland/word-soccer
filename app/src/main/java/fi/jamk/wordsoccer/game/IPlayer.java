package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IPlayer
{
	public String getName();

	public int getScore();

	public IPlayer setScore(int score);

	public IPlayer addWord(Word word);

	public List<Word> getWords();

	public int getCurrentLongestWord();

	public int getPoints();

	public IPlayer resetPoints();

	public int getTotalPoints();

	public Letter[] getLetters();

	public boolean hasUsedAllLetters();

	public int getNumberOfUsedLetters();

	public IPlayer addCard(Card card);

	public List<Card> getCards();

	public int getNumberOfCards(Card.CardType cardType);

	public void onStartGame(IGame game);

	public void onStartRound(IGame game);

	public IPlayer setListener(IPlayerListener listener);

	public interface IPlayerListener
	{
		public void onWordAdded(Word word);
	}
}