package fi.jamk.wordsoccer.controls;

import android.content.Context;
import android.util.AttributeSet;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.Letter;

public class InputLetterButton extends LetterButton
{
	protected static final int[] STATE_DEFAULT = {R.attr.state_default};
	protected static final int[] STATE_DEFAULT_WITH_YELLOW_CARD = {R.attr.state_default_yellow_card};
	protected static final int[] STATE_USED = {R.attr.state_used};
	protected static final int[] STATE_USED_WITH_YELLOW_CARD = {R.attr.state_used_yellow_card};
	protected static final int[] STATE_DISABLED_WITH_YELLOW_CARD = {R.attr.state_disabled_yellow_card};
	protected static final int[] STATE_DISABLED_WITH_RED_CARD = {R.attr.state_disabled_red_card};

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
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);

		refreshDrawableState();
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace)
	{
		if (!hasLetter())
		{
			return super.onCreateDrawableState(extraSpace);
		}

		int[] drawableState;

		if (letter.isDisabled())
		{
			drawableState = STATE_DISABLED_WITH_RED_CARD;
		}
		else if (isEnabled())
		{
			if (letter.isUsed())
			{
				drawableState = letter.getCard() == Card.YELLOW
					? STATE_USED_WITH_YELLOW_CARD : STATE_USED;
			}
			else
			{
				drawableState = letter.getCard() == Card.YELLOW
					? STATE_DEFAULT_WITH_YELLOW_CARD : STATE_DEFAULT;
			}
		}
		else
		{
			drawableState = letter.getCard() == Card.YELLOW
				? STATE_DISABLED_WITH_YELLOW_CARD : STATE_DISABLED;
		}

		return mergeDrawableStates(super.onCreateDrawableState(extraSpace + 1), drawableState);
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