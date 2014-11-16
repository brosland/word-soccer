package fi.jamk.wordsoccer.game;

import java.util.List;

public interface IPlayer
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

	public void onStartGame(IGame game);

	public void onStartRound(IGame game);
}