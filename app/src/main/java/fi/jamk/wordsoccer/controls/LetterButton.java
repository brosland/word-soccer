package fi.jamk.wordsoccer.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import fi.jamk.wordsoccer.game.Letter;

public class LetterButton extends Button
{
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

		this.setText(Character.toString(letter.getSign()));

		return this;
	}
}