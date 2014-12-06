package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IPlayer
{
	public String getName();

	public int getScore();

	public void setScore(int score);

	public void addWord(Word word);

	public List<Word> getWords();

	public int getCurrentLongestWord();

	public int getPoints();

	public void resetPoints();

	public int getTotalPoints();

	public Letter[] getLetters();

	public boolean hasUsedAllLetters();

	public int getNumberOfUsedLetters();

	public void addCard(Card card);

	public List<Card> getCards();

	public int getNumberOfCards(Card card);

	public void onStartGame(IGame game);

	public void onStartRound(IGame game);

	public void setListener(IPlayerListener listener);

	public interface IPlayerListener
	{
		public void onWordAdded(Word word);
	}
}