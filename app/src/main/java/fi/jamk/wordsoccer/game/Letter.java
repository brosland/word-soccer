package fi.jamk.wordsoccer.game;

public class Letter
{
	private final int number;
	private char sign = ' ';
	private boolean used;
	private Card.CardType cardType;

	public Letter(int number)
	{
		this.number = number;
		used = false;
	}

	public int getNumber()
	{
		return number;
	}

	public char getSign()
	{
		return sign;
	}

	public Letter setSign(char sign)
	{
		this.sign = sign;

		return this;
	}

	public boolean isUsed()
	{
		return used;
	}

	public Letter setUsed(boolean used)
	{
		if (isDisabled() && used)
		{
			throw new IllegalStateException("Cannot use a disabled letter.");
		}

		return this;
	}

	public boolean isDisabled()
	{
		return cardType == Card.CardType.RED;
	}

	public Card.CardType getCardType()
	{
		return cardType;
	}

	public Letter setCardType(Card.CardType cardType)
	{
		this.cardType = cardType;

		if (isDisabled())
		{
			used = false;
		}

		return this;
	}
}