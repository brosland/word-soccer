package fi.jamk.wordsoccer.game;

public class Card
{
	public enum CardType
	{
		RED, YELLOW
	}

	public final CardType cardType;
	public final String reason;

	public Card(CardType cardType, String reason)
	{
		this.cardType = cardType;
		this.reason = reason;
	}
}
