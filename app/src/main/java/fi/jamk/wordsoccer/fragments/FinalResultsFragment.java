package fi.jamk.wordsoccer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.IGame;

public class FinalResultsFragment extends Fragment
{
	private IGame game;

	public static FinalResultsFragment newInstance(IGame game)
	{
		FinalResultsFragment roundFragment = new FinalResultsFragment();
		roundFragment.setGame(game);

		return roundFragment;
	}

	public FinalResultsFragment setGame(IGame game)
	{
		this.game = game;

		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_final_results, container, false);

		// player A - total letters
		TextView playerATotalLettersTextView = (TextView) view.findViewById(R.id.playerATotalLettersTextView);
		playerATotalLettersTextView.setText(Integer.toString(game.getPlayerA().getTotalPoints()));

		// player A - score
		TextView playerAScoreTextView = (TextView) view.findViewById(R.id.playerAScoreTextView);
		playerAScoreTextView.setText(Integer.toString(game.getPlayerA().getScore()));

		// player B - total letters
		TextView playerBTotalLettersTextView = (TextView) view.findViewById(R.id.playerBTotalLettersTextView);
		playerBTotalLettersTextView.setText(Integer.toString(game.getPlayerB().getTotalPoints()));

		// player B - score
		TextView playerBScoreTextView = (TextView) view.findViewById(R.id.playerBScoreTextView);
		playerBScoreTextView.setText(Integer.toString(game.getPlayerB().getScore()));

		// total letters
		if (game.getPlayerA().getTotalPoints() > game.getPlayerB().getTotalPoints())
		{
			playerATotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerATotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (game.getPlayerA().getTotalPoints() < game.getPlayerB().getTotalPoints())
		{
			playerBTotalLettersTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBTotalLettersTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		// score
		if (game.getPlayerA().getScore() > game.getPlayerB().getScore())
		{
			playerAScoreTextView.setBackgroundColor(getResources().getColor(R.color.playerA_color));
			playerAScoreTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerAHighlightedValueCell);
		}
		else if (game.getPlayerA().getScore() < game.getPlayerB().getScore())
		{
			playerBScoreTextView.setBackgroundColor(getResources().getColor(R.color.playerB_color));
			playerBScoreTextView.setTextAppearance(getActivity(), R.style.ResultsTablePlayerBHighlightedValueCell);
		}

		return view;
	}
}