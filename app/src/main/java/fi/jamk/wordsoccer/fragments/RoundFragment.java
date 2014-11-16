package fi.jamk.wordsoccer.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.adapters.WordListAdapter;
import fi.jamk.wordsoccer.controls.LetterButton;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.Letter;
import fi.jamk.wordsoccer.game.Word;

public class RoundFragment extends Fragment
{
	private IGame game;
	private WordListAdapter wordListAdapter;
	private LetterButton[] inputLetterButtons, selectedLetterButtons;
	private Button submitButton;

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

		// my word list
		wordListAdapter = new WordListAdapter(getActivity(), game.getPlayerA());

		ListView wordListView = (ListView) view.findViewById(R.id.wordsListView);
		wordListView.setAdapter(wordListAdapter);

		// letters bar
		inputLetterButtons = new LetterButton[IGame.LETTERS];
		selectedLetterButtons = new LetterButton[IGame.LETTERS];

		for (int i = 0; i < IGame.LETTERS; i++)
		{
			// input letter button i
			int id = getResources().getIdentifier("inputLetterButton" + i, "id", getActivity().getPackageName());
			inputLetterButtons[i] = (LetterButton) view.findViewById(id);

			initInputLetterButton(inputLetterButtons[i], game.getPlayerA().getLetters()[i]);

			// selected letter button i
			id = getResources().getIdentifier("selectedLetterButton" + i, "id", getActivity().getPackageName());
			selectedLetterButtons[i] = (LetterButton) view.findViewById(id);

			initSelectedLetterButton(selectedLetterButtons[i]);
		}

		submitButton = (Button) view.findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onWordSubmitted(view);
			}
		});

		updateSubmitButton();

		return view;
	}

	public void onWordSubmitted(View view)
	{
		final Word word = new Word(getSelectedWord());
		word.setListener(new Word.IWordListener()
		{
			@Override
			public void onStateChanged(Word.WordState state)
			{
				wordListAdapter.notifyDataSetChanged();

				Toast.makeText(getActivity(), String.format("Word '%s' is %svalid.",
					word.word, word.getState() == Word.WordState.VALID ? "" : "in"), Toast.LENGTH_LONG).show();
			}
		});

		wordListAdapter.addWord(word);

		for (LetterButton letterButton : selectedLetterButtons)
		{
			if (letterButton.hasLetter())
			{
				deselectLetter(letterButton);
			}
		}

		updateSubmitButton();
	}

	public void setLettersBarVisible(boolean visible)
	{
		View lettersBarLayout = getView().findViewById(R.id.lettersBarLinearLayout);
		lettersBarLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
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

				updateSubmitButton();
			}
		});
	}

	private void initSelectedLetterButton(final LetterButton button)
	{
		button.setEnabled(false);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				deselectLetter(button);

				updateSubmitButton();
			}
		});
	}

	private String getSelectedWord()
	{
		StringBuilder word = new StringBuilder();

		for (LetterButton letterButton : selectedLetterButtons)
		{
			word.append(letterButton.hasLetter() ? letterButton.getLetter().getSign() : ' ');
		}

		return word.toString().trim();
	}

	private void deselectLetter(LetterButton button)
	{
		Letter letter = button.removeLetter();
		inputLetterButtons[letter.getNumber()].setEnabled(true);
	}

	private void updateSubmitButton()
	{
		String currentWord = getSelectedWord();

		if (currentWord.isEmpty() || currentWord.contains(" "))
		{
			submitButton.setEnabled(false);
			return;
		}

		for (Word word : game.getPlayerA().getWords())
		{
			if (word.word.equals(currentWord))
			{
				submitButton.setEnabled(false);
				return;
			}
		}

		submitButton.setEnabled(true);
	}
}