package fi.jamk.wordsoccer.game.players;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.Letter;
import fi.jamk.wordsoccer.game.Word;

public class Player implements IPlayer
{
	private final String name;
	private final LinkedList<Word> words;
	private final ArrayList<Card> cards;
	private final Letter[] letters;
	private int points, totalPoints, score, usedLetters, redCards, yellowCards;
	private IGame game;
	private IPlayerListener listener;

	public Player(String name)
	{
		this.name = name;
		this.words = new LinkedList<Word>();
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
	public IPlayer setScore(int score)
	{
		this.score = score;

		return this;
	}

	@Override
	public Player addWord(final Word word)
	{
		if (game == null)
		{
			throw new IllegalStateException("Game is not started properly.");
		}

		words.add(word);

		Collections.sort(words);

		if (listener != null)
		{
			listener.onWordAdded(word);
		}

		new AsyncTask<Word, Integer, Word.WordState>()
		{
			@Override
			protected Word.WordState doInBackground(Word... params)
			{
				Word word = params[0];

				return game.getDictionary().isWordValid(word.word) ? Word.WordState.VALID : Word.WordState.INVALID;
			}

			@Override
			protected void onPostExecute(Word.WordState state)
			{
				if (state == Word.WordState.VALID)
				{
					addUsedLetters(word);

					points += word.word.length();
					totalPoints += word.word.length();
				}

				word.setState(state);

				Collections.sort(words);
			}
		}.execute(word);

		return this;
	}

	@Override
	public List<Word> getWords()
	{
		return words;
	}

	@Override
	public int getCurrentLongestWord()
	{
		int length = 0;

		for (Word word : words)
		{
			if (word.getState() == Word.WordState.VALID && word.word.length() > length)
			{
				length = word.word.length();
			}
		}

		return length;
	}

	@Override
	public int getPoints()
	{
		return points;
	}

	@Override
	public IPlayer resetPoints()
	{
		points = 0;

		return this;
	}

	@Override
	public int getTotalPoints()
	{
		return totalPoints;
	}

	@Override
	public Letter[] getLetters()
	{
		return letters;
	}

	@Override
	public boolean hasUsedAllLetters()
	{
		return usedLetters == IGame.LETTERS;
	}

	@Override
	public int getNumberOfUsedLetters()
	{
		return usedLetters;
	}

	public Player addCard(Card card)
	{
		if (getNumberOfCards(Card.CardType.RED) + 1 > IGame.MAX_RED_CARDS)
		{
			return this;
		}

		cards.add(card);

		int indexOfLastEnableLetter = letters.length - 1 - getNumberOfCards(Card.CardType.RED);
		Letter letter = letters[indexOfLastEnableLetter];

		if (card.cardType == Card.CardType.YELLOW)
		{
			letter.setCardType(letter.getCardType() == null ? Card.CardType.YELLOW : Card.CardType.RED);

			yellowCards++;
		} else
		{
			if (letter.getCardType() == Card.CardType.YELLOW)
			{
				Letter prevLetter = letters[indexOfLastEnableLetter - 1];
				prevLetter.setCardType(Card.CardType.YELLOW);
			}

			letter.setCardType(Card.CardType.RED);

			redCards++;
		}

		return this;
	}

	@Override
	public List<Card> getCards()
	{
		return cards;
	}

	public int getNumberOfCards(Card.CardType cardType)
	{
		return cardType == Card.CardType.YELLOW ?
			yellowCards % 2 : yellowCards / 2 + redCards;
	}

	@Override
	public void onStartGame(IGame game)
	{
		this.game = game;
		score = points = totalPoints = usedLetters = redCards = yellowCards = 0;

		words.clear();
		cards.clear();

		for (int i = 0; i < letters.length; i++)
		{
			letters[i] = new Letter(i);
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

		usedLetters = 0;

		// reset found words
		words.clear();
	}

	@Override
	public IPlayer setListener(IPlayerListener listener)
	{
		this.listener = listener;

		return this;
	}

	private void addUsedLetters(Word word)
	{
		for (int i = 0; i < word.word.length(); i++)
		{
			for (Letter letter : letters)
			{
				if (!letter.isUsed() && !letter.isDisabled() && word.word.charAt(i) == letter.getSign())
				{
					letter.setUsed(true);
					usedLetters++;
					break;
				}
			}
		}
	}
}