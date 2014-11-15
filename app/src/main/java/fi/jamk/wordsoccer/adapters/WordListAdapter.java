package fi.jamk.wordsoccer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.Word;

public class WordListAdapter extends BaseAdapter
{
	private final Context context;
	private final IPlayer player;
	private final HashMap<Word, Word.WordState> wordStates;

	public WordListAdapter(Context context, IPlayer player)
	{
		this.context = context;
		this.player = player;
		this.wordStates = new HashMap<Word, Word.WordState>();

		for (Word word : player.getWords())
		{
			wordStates.put(word, word.getState());
		}
	}

	public void addWord(Word word)
	{
		wordStates.put(word, Word.WordState.PENDING);

		player.addWord(word);

		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return player.getWords().size();
	}

	@Override
	public Object getItem(int position)
	{
		return player.getWords().get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.word_list_item, parent, false);

		Word word = (Word) getItem(position);

		TextView numberView = (TextView) rowView.findViewById(R.id.wordListItemNumberView);
		numberView.setText(Integer.toString(position + 1));

		TextView labelView = (TextView) rowView.findViewById(R.id.wordListItemLabelView);
		labelView.setText(word.word.toUpperCase());

		if (word.getState() != wordStates.get(word))
		{
			wordStates.put(word, word.getState());
		}

		labelView.setTextAppearance(context, getStyleIdByWordState(word.getState()));

		return rowView;
	}

	private int getStyleIdByWordState(Word.WordState state)
	{
		switch (state)
		{
			case VALID:
				return R.style.WordListValidWord;
			case INVALID:
				return R.style.WordListInvalidWord;
			default:
				return R.style.WordListPendingWord;
		}
	}
}