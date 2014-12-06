package fi.jamk.wordsoccer.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.Letter;

public class LetterButton extends Button
{
	protected static final int[] STATE_DISABLED = {R.attr.state_disabled};

	protected Letter letter;

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
		setEnabled(letter.getCard() != Card.RED);

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

		return drawableState;
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