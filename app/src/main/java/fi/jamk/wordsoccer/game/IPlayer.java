package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IPlayer extends IGame.IGameListener
{
	public String getName();

	public int getScore();

	public IPlayer setScore(int score);

	public IPlayer addWord(Word word);

	public List<Word> getWords();

	public int getLongestValidWordLength();

	public int getNumberOfUsedLetters();

	public IPlayer addCard(Card card);

	public List<Card> getCards();

	public int getNumberOfCards(Card.CardType cardType);

	public Letter[] getLetters();

	public IPlayer addListener(IPlayerListener listener);

	public IPlayer removeListener(IPlayerListener listener);

	public interface IPlayerListener
	{
		public void onCardAdded(IPlayer player, Card card);

		public void onScoreChanged(IPlayer player);
	}
}