package fi.jamk.wordsoccer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import fi.jamk.wordsoccer.R;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.Word;

public class WordListAdapter extends BaseAdapter implements IPlayer.IPlayerListener
{
	private final Context context;
	private final IPlayer player;

	public WordListAdapter(Context context, IPlayer player)
	{
		this.context = context;
		this.player = player;
		player.setListener(this);
	}

	@Override
	public void onWordListChange()
	{
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

		switch (word.getState())
		{
			case VALID:
				labelView.setTextAppearance(context, R.style.WordListValidWord);
				break;

			case INVALID:
				labelView.setTextAppearance(context, R.style.WordListInvalidWord);
				break;

			case REMOVED:
				labelView.setTextAppearance(context, R.style.WordListRemovedWord);
				labelView.setPaintFlags(labelView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				break;

			default:
				labelView.setTextAppearance(context, R.style.WordListPendingWord);
		}

		return rowView;
	}
}