package fi.jamk.wordsoccer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.IGame;

public class ResultsFragment extends Fragment
{
	private IGame game;

	public static ResultsFragment newInstance(IGame game)
	{
		ResultsFragment roundFragment = new ResultsFragment();
		roundFragment.setGame(game);

		return roundFragment;
	}

	public ResultsFragment setGame(IGame game)
	{
		this.game = game;

		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_results, container, false);

		// round number
		TextView resultsRoundTextView = (TextView) view.findViewById(R.id.resultsRoundTextView);

		if (game.getCurrentRoundNumber() > IGame.ROUNDS)
		{
			resultsRoundTextView.setText(String.format("Round +%d", game.getCurrentRoundNumber() - IGame.ROUNDS));
		}
		else
		{
			resultsRoundTextView.setText(String.format("Round %d / %d", game.getCurrentRoundNumber(), IGame.ROUNDS));
		}

		// player A - total letters
		TextView playerATotalLettersTextView = (TextView) view.findViewById(R.id.playerATotalLettersTextView);
		playerATotalLettersTextView.setText(Integer.toString(game.getPlayerA().getPoints()));

		// player A - longest valid word
		TextView playerALongestValidWordTextView = (TextView) view.findViewById(R.id.playerALongestValidWordTextView);
		playerALongestValidWordTextView.setText(Integer.toString(game.getPlayerA().getCurrentLongestWord()));

		// player A - usage of letters
		double playerAUsageOfLetters = 100.0 * game.getPlayerA().getNumberOfUsedLetters() / IGame.LETTERS;

		TextView playerAUsageOfLettersTextView = (TextView) view.findViewById(R.id.playerAUsageOfLettersTextView);
		playerAUsageOfLettersTextView.setText(String.format("%.0f %%", playerAUsageOfLetters));

		// player B - total letters
		TextView playerBTotalLettersTextView = (TextView) view.findViewById(R.id.playerBTotalLettersTextView);
		playerBTotalLettersTextView.setText(Integer.toString(game.getPlayerB().getPoints()));

		// player B - longest valid word
		TextView playerBLongestValidWordTextView = (TextView) view.findViewById(R.id.playerBLongestValidWordTextView);
		playerBLongestValidWordTextView.setText(Integer.toString(game.getPlayerB().getCurrentLongestWord()));

		// player B - usage of letters
		double playerBUsageOfLetters = 100.0 * game.getPlayerB().getNumberOfUsedLetters() / IGame.LETTERS;

		TextView playerBUsageOfLettersTextView = (TextView) view.findViewById(R.id.playerBUsageOfLettersTextView);
		playerBUsageOfLettersTextView.setText(String.format("%.0f %%", playerBUsageOfLetters));

		// total letters
		if (game.getPlayerA().getPoints() > game.getPlayerB().getPoints())
		{
			playerATotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerATotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (game.getPlayerA().getPoints() < game.getPlayerB().getPoints())
		{
			playerBTotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBTotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		// longest valid word
		if (game.getPlayerA().getCurrentLongestWord() > game.getPlayerB().getCurrentLongestWord())
		{
			playerALongestValidWordTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerALongestValidWordTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);

			TextView playerBYellowCardTextView = (TextView) view.findViewById(R.id.playerBYellowCardTextView);
			playerBYellowCardTextView.setVisibility(View.VISIBLE);
		}
		else if (game.getPlayerA().getCurrentLongestWord() < game.getPlayerB().getCurrentLongestWord())
		{
			playerBLongestValidWordTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBLongestValidWordTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);

			TextView playerAYellowCardTextView = (TextView) view.findViewById(R.id.playerAYellowCardTextView);
			playerAYellowCardTextView.setVisibility(View.VISIBLE);
		}

		// usage of letters
		if (game.getPlayerA().getNumberOfUsedLetters() > game.getPlayerB().getNumberOfUsedLetters())
		{
			playerAUsageOfLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerAUsageOfLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (game.getPlayerA().getNumberOfUsedLetters() < game.getPlayerB().getNumberOfUsedLetters())
		{
			playerBUsageOfLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBUsageOfLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		if (game.getPlayerA().getNumberOfUsedLetters() == IGame.LETTERS)
		{
			TextView playerBRedCardTextView = (TextView) view.findViewById(R.id.playerBRedCardTextView);
			playerBRedCardTextView.setVisibility(View.VISIBLE);
		}

		if (game.getPlayerB().getNumberOfUsedLetters() == IGame.LETTERS)
		{
			TextView playerARedCardTextView = (TextView) view.findViewById(R.id.playerARedCardTextView);
			playerARedCardTextView.setVisibility(View.VISIBLE);
		}

		return view;
	}
}