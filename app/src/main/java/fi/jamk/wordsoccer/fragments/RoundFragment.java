package fi.jamk.wordsoccer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.controls.LetterButton;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.Letter;

public class RoundFragment extends Fragment
{
	private IGame game;
	private Letter[] selectedLetters;
	private LetterButton[] inputLetterButtons, selectedLetterButtons;

	public static RoundFragment newInstance(IGame game)
	{
		RoundFragment roundFragment = new RoundFragment();
		roundFragment.setGame(game);

		return roundFragment;
	}

	public RoundFragment setGame(IGame game)
	{
		this.game = game;

		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		selectedLetters = new Letter[IGame.LETTERS];

		if (getArguments() != null)
		{
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_round, container, false);

		inputLetterButtons = new LetterButton[IGame.LETTERS];
		selectedLetterButtons = new LetterButton[IGame.LETTERS];

		for(int i = 0; i < IGame.LETTERS; i++)
		{
			// input letter button i
			int id = getResources().getIdentifier("inputLetterButton" + i, "id", getActivity().getPackageName());
			inputLetterButtons[i] = (LetterButton) view.findViewById(id);

			initInputLetterButton(inputLetterButtons[i], game.getPlayerA().getLetter(i));

			// selected letter button i
			id = getResources().getIdentifier("selectedLetterButton" + i, "id", getActivity().getPackageName());
			selectedLetterButtons[i] = (LetterButton) view.findViewById(id);

			initSelectedLetterButton(i, selectedLetterButtons[i]);
		}

		return view;
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

//		try
//		{
//			mListener = (IRoundFragmentListener) activity;
//		}
//		catch (ClassCastException e)
//		{
//			throw new ClassCastException(activity.toString()
//					+ " must implement IRoundFragmentListener");
//		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
	}

	private void initInputLetterButton(final LetterButton button, final Letter letter)
	{
		button.setLetter(letter);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				button.setEnabled(false);

				for (LetterButton selectedLetterButton : selectedLetterButtons)
				{
					if (!selectedLetterButton.hasLetter())
					{
						selectedLetterButton.setLetter(letter);
						break;
					}
				}
			}
		});
	}

	private void initSelectedLetterButton(final int index, final LetterButton button)
	{
		button.setEnabled(false);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Letter letter = button.removeLetter();
				inputLetterButtons[letter.getNumber()].setEnabled(true);
			}
		});
	}

	public interface IRoundFragmentListener
	{
		public void onFinish();
	}
}
