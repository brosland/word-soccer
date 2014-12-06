package fi.jamk.wordsoccer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;

public class RoundResultsFragment extends Fragment
{
	private IGame game;

	public static RoundResultsFragment newInstance(IGame game)
	{
		RoundResultsFragment roundFragment = new RoundResultsFragment();
		roundFragment.setGame(game);

		return roundFragment;
	}

	public RoundResultsFragment setGame(IGame game)
	{
		this.game = game;

		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_round_results, container, false);

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

		IPlayer playerA = game.getPlayerA();
		IPlayer playerB = game.getPlayerB();

		// player A - total letters
		TextView playerATotalLettersTextView = (TextView) view.findViewById(R.id.playerATotalLettersTextView);
		playerATotalLettersTextView.setText(Integer.toString(playerA.getPoints()));

		// player A - longest valid word
		TextView playerALongestValidWordTextView = (TextView) view.findViewById(R.id.playerALongestValidWordTextView);
		playerALongestValidWordTextView.setText(Integer.toString(playerA.getCurrentLongestWord()));

		// player A - usage of letters
		double playerAUsageOfLetters = 100.0 * playerA.getNumberOfUsedLetters()
			/ (IGame.LETTERS - playerA.getNumberOfCards(Card.RED));

		TextView playerAUsageOfLettersTextView = (TextView) view.findViewById(R.id.playerAUsageOfLettersTextView);
		playerAUsageOfLettersTextView.setText(String.format("%.0f %%", playerAUsageOfLetters));

		// player B - total letters
		TextView playerBTotalLettersTextView = (TextView) view.findViewById(R.id.playerBTotalLettersTextView);
		playerBTotalLettersTextView.setText(Integer.toString(playerB.getPoints()));

		// player B - longest valid word
		TextView playerBLongestValidWordTextView = (TextView) view.findViewById(R.id.playerBLongestValidWordTextView);
		playerBLongestValidWordTextView.setText(Integer.toString(playerB.getCurrentLongestWord()));

		// player B - usage of letters
		double playerBUsageOfLetters = 100.0 * playerB.getNumberOfUsedLetters()
			/ (IGame.LETTERS - playerB.getNumberOfCards(Card.RED));

		TextView playerBUsageOfLettersTextView = (TextView) view.findViewById(R.id.playerBUsageOfLettersTextView);
		playerBUsageOfLettersTextView.setText(String.format("%.0f %%", playerBUsageOfLetters));

		// total letters
		if (playerA.getPoints() > playerB.getPoints())
		{
			playerATotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerATotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (playerA.getPoints() < playerB.getPoints())
		{
			playerBTotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBTotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		// longest valid word
		if (playerA.getCurrentLongestWord() > playerB.getCurrentLongestWord())
		{
			playerALongestValidWordTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerALongestValidWordTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);

			TextView playerBYellowCardTextView = (TextView) view.findViewById(R.id.playerBYellowCardTextView);
			playerBYellowCardTextView.setVisibility(View.VISIBLE);
		}
		else if (playerA.getCurrentLongestWord() < playerB.getCurrentLongestWord())
		{
			playerBLongestValidWordTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBLongestValidWordTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);

			TextView playerAYellowCardTextView = (TextView) view.findViewById(R.id.playerAYellowCardTextView);
			playerAYellowCardTextView.setVisibility(View.VISIBLE);
		}

		// usage of letters
		if (playerAUsageOfLetters > playerBUsageOfLetters)
		{
			playerAUsageOfLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerAUsageOfLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (playerAUsageOfLetters < playerBUsageOfLetters)
		{
			playerBUsageOfLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBUsageOfLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		if (playerA.hasUsedAllLetters())
		{
			TextView playerBRedCardTextView = (TextView) view.findViewById(R.id.playerBRedCardTextView);
			playerBRedCardTextView.setVisibility(View.VISIBLE);
		}

		if (playerB.hasUsedAllLetters())
		{
			TextView playerARedCardTextView = (TextView) view.findViewById(R.id.playerARedCardTextView);
			playerARedCardTextView.setVisibility(View.VISIBLE);
		}

		return view;
	}
}