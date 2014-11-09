package fi.jamk.wordsoccer.game.players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.Letter;

public class Player implements IPlayer
{
	private final String name;
	private final LinkedList<String> foundWords;
	private final ArrayList<Card> cards;
	private final Letter[] letters;
	private int score, numberOfUsedLetters, numberOfRedCards, numberOfYellowCard;

	public Player(String name)
	{
		this.name = name;
		this.foundWords = new LinkedList<String>();
		this.cards = new ArrayList<Card>();
		this.letters = new Letter[IGame.LETTERS];
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getScore()
	{
		return score;
	}

	@Override
	public Player addFoundWord(String word)
	{
		foundWords.add(word);

		for (int i = 0; i < word.length(); i++)
		{
			for (Letter letter : letters)
			{
				if (!letter.isUsed() && !letter.isDisabled() && word.charAt(i) == letter.getSign())
				{
					letter.setUsed(true);
					numberOfUsedLetters++;
					break;
				}
			}
		}

		return this;
	}

	@Override
	public List<String> getFoundWords()
	{
		return foundWords;
	}

	@Override
	public int getLongestFoundWordLength()
	{
		int longestWordLength = 0;

		for (String word : foundWords)
		{
			if (word.length() > longestWordLength)
			{
				longestWordLength = word.length();
			}
		}

		return longestWordLength;
	}

	@Override
	public int getNumberOfUsedLetters()
	{
		return numberOfUsedLetters;
	}

	@Override
	public List<Card> getCards()
	{
		return cards;
	}

	public int getNumberOfCards(Card.CardType cardType)
	{
		return cardType == Card.CardType.YELLOW ?
			numberOfYellowCard % 2 : numberOfYellowCard / 2 + numberOfRedCards;
	}

	public Player addCard(Card card)
	{
		if (getNumberOfCards(Card.CardType.RED) + 1 > IGame.MAX_CARDS)
		{
			return this;
		}

		cards.add(card);

		int indexOfLastEnableLetter = letters.length - 1 - getNumberOfCards(Card.CardType.RED);
		Letter letter = letters[indexOfLastEnableLetter];

		if (card.cardType == Card.CardType.YELLOW)
		{
			letter.setCardType(letter.getCardType() == null ? Card.CardType.YELLOW : Card.CardType.RED);

			numberOfYellowCard++;
		}
		else
		{
			if (letter.getCardType() == Card.CardType.YELLOW)
			{
				Letter prevLetter = letters[indexOfLastEnableLetter - 1];
				prevLetter.setCardType(Card.CardType.YELLOW);
			}

			letter.setCardType(Card.CardType.RED);

			numberOfRedCards++;
		}

		return this;
	}

	@Override
	public Letter getLetter(int i)
	{
		return letters[i];
	}

	@Override
	public void onStartGame(IGame game)
	{
		score = numberOfUsedLetters = numberOfRedCards = numberOfYellowCard = 0;
		foundWords.clear();
		cards.clear();

		for (int i = 0; i < letters.length; i++)
		{
			letters[i] = new Letter(game.getCurrentRoundLetters().charAt(i));
		}
	}

	@Override
	public void onStartRound(IGame game)
	{
		// reset used letters
		for (int i = 0; i < letters.length; i++)
		{
			letters[i].setSign(game.getCurrentRoundLetters().charAt(i))
				.setUsed(false);
		}

		numberOfUsedLetters = 0;

		// reset found words
		foundWords.clear();
	}
}