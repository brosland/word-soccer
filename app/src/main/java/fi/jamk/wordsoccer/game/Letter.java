package fi.jamk.wordsoccer.game;

public class Letter
{
	private final int number;
	private char sign = ' ';
	private boolean used;
	private Card card;
	private ILetterListener listener;

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

	public void setSign(char sign)
	{
		this.sign = sign;

		onChange();
	}

	public boolean isUsed()
	{
		return used;
	}

	public void setUsed(boolean used)
	{
		if (isDisabled() && used)
		{
			throw new IllegalStateException("Cannot use a disabled letter.");
		}

		this.used = used;

		onChange();
	}

	public boolean isDisabled()
	{
		return card == Card.RED;
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;

		if (isDisabled())
		{
			used = false;
		}

		onChange();
	}

	private void onChange()
	{
		if (listener != null)
		{
			listener.onChanged();
		}
	}

	public void setListener(ILetterListener listener)
	{
		this.listener = listener;
	}

	public interface ILetterListener
	{
		public void onChanged();
	}
}