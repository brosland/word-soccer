package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IPlayer extends IGame.IGameListener
{
	public String getName();

	public int getScore();

	public IPlayer setScore(int score);

	public IPlayer addFoundWord(String word);

	public List<String> getFoundWords();

	public int getLongestFoundWordLength();

	public int getNumberOfUsedLetters();

	public IPlayer addCard(Card card);

	public List<Card> getCards();

	public int getNumberOfCards(Card.CardType cardType);

	public Letter getLetter(int i);

	public IPlayer setListener(IPlayerListener listener);

	public interface IPlayerListener
	{
		public void onAddedCard(IPlayer player, Card card);

		public void onChangedScore(IPlayer player);
	}
}