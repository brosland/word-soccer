package fi.jamk.wordsoccer.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.Letter;

public class InputLetterButton extends LetterButton
{
	protected static final int[] STATE_DEFAULT_YELLOW_CARD = {R.attr.state_default_yellow_card};
	protected static final int[] STATE_USED = {R.attr.state_used};
	protected static final int[] STATE_USED_YELLOW_CARD = {R.attr.state_used_yellow_card};
	protected static final int[] STATE_DISABLED_YELLOW_CARD = {R.attr.state_disabled_yellow_card};
	protected static final int[] STATE_DISABLED_RED_CARD = {R.attr.state_disabled_red_card};

	public InputLetterButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public InputLetterButton(Context context)
	{
		super(context);
	}

	public InputLetterButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public InputLetterButton setLetter(Letter letter)
	{
		super.setLetter(letter);

		letter.setListener(new Letter.ILetterListener()
		{
			@Override
			public void onChanged()
			{
				refreshDrawableState();
			}
		});

		return this;
	}

	public Letter removeLetter()
	{
		Letter letter = super.removeLetter();
		letter.setListener(null);

		return letter;
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace)
	{
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

		if (!hasLetter())
		{
			return drawableState;
		}
		else if (isEnabled())
		{
			if (getLetter().isUsed() && getLetter().getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_USED_YELLOW_CARD);
				// TODO style
			}
			else if (getLetter().isUsed())
			{
				mergeDrawableStates(drawableState, STATE_USED);
				// TODO style
			}
			else if (getLetter().getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DEFAULT_YELLOW_CARD);
			}
		}
		else // disabled
		{
			if (getLetter().isUsed() && getLetter().getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DISABLED_YELLOW_CARD);
				// TODO style
			}
			else if (getLetter().isUsed())
			{
				mergeDrawableStates(drawableState, STATE_DISABLED);
				// TODO style
			}
			else if (getLetter().getCardType() == Card.CardType.YELLOW)
			{
				mergeDrawableStates(drawableState, STATE_DISABLED_YELLOW_CARD);
			}
			else if (getLetter().getCardType() == Card.CardType.RED)
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

	@Override
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