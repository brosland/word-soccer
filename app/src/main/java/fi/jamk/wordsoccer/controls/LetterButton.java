package fi.jamk.wordsoccer.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.Letter;

public class LetterButton extends Button
{
	private static final int[] STATE_DEFAULT_YELLOW_CARD = {R.attr.state_default_yellow_card};
	private static final int[] STATE_USED = {R.attr.state_used};
	private static final int[] STATE_USED_YELLOW_CARD = {R.attr.state_used_yellow_card};
	private static final int[] STATE_DISABLED = {R.attr.state_disabled};
	private static final int[] STATE_DISABLED_YELLOW_CARD = {R.attr.state_disabled_yellow_card};
	private static final int[] STATE_DISABLED_RED_CARD = {R.attr.state_disabled_red_card};

	private Letter letter;

	public LetterButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public LetterButton(Context context)
	{
		super(context);
	}

	public LetterButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public boolean hasLetter()
	{
		return letter != null;
	}

	public Letter getLetter()
	{
		return letter;
	}

	public LetterButton setLetter(Letter letter)
	{
		this.letter = letter;

		setText(Character.toString(letter.getSign()).toUpperCase());
		setEnabled(letter.getCardType() != Card.CardType.RED);

		return this;
	}

	public Letter removeLetter()
	{
		Letter letter = this.letter;
		this.letter = null;

		setText("");
		setEnabled(false);

		return letter;
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace)
	{
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

		if (!hasLetter())
		{
			mergeDrawableStates(drawableState, STATE_DISABLED);
		}
		else if (isEnabled())
		{
			if (letter.isUsed() && letter.getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_USED_YELLOW_CARD);
				// TODO style
			}
			else if (letter.isUsed())
			{
				mergeDrawableStates(drawableState, STATE_USED);
				// TODO style
			}
			else if (letter.getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DEFAULT_YELLOW_CARD);
			}
		}
		else // disabled
		{
			if (letter.isUsed() && letter.getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DISABLED_YELLOW_CARD);
				// TODO style
			}
			else if (letter.isUsed())
			{
				mergeDrawableStates(drawableState, STATE_DISABLED);
				// TODO style
			}
			else if (letter.getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DISABLED_YELLOW_CARD);
			}
			else if (letter.getCardType() == Card.CardType.RED)
			{
				mergeDrawableStates(drawableState, STATE_DISABLED_RED_CARD);
			}
			else
			{
				mergeDrawableStates(drawableState, STATE_DISABLED);
			}
		}

		return drawableState;
	}

	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);

		refreshDrawableState();
	}

//	@Override
//	public void draw(Canvas canvas)
//	{
//		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getWidth(), getWidth(), 1);
//		layoutParams.setMargins(10, 0, 0, 0);
//		layoutParams.setMarginStart(10);
//		layoutParams.setMarginEnd(10);
//		setLayoutParams(layoutParams);
//
//		super.draw(canvas);
//	}
}