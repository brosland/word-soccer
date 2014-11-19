package fi.jamk.wordsoccer.game;

public class Word implements Comparable<Word>
{
	public enum WordState
	{
		PENDING(1), VALID(2), REMOVED(3), INVALID(4);

		public final int priority;

		private WordState(int priority)
		{
			this.priority = priority;
		}
	}

	public final String word;
	private WordState state = WordState.PENDING;
	private IWordListener listener;

	public Word(String word)
	{
		this.word = word;
	}

	public WordState getState()
	{
		return state;
	}

	public Word setState(WordState state)
	{
		this.state = state;

		if (listener != null)
		{
			listener.onStateChanged(state);
		}

		return this;
	}

	public Word setListener(IWordListener listener)
	{
		this.listener = listener;

		return this;
	}

	public interface IWordListener
	{
		public void onStateChanged(WordState state);
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof Word && compareTo((Word) object) == 0;
	}

	@Override
	public int compareTo(Word wordB)
	{
		if (this.state.priority != wordB.getState().priority)
		{
			return this.state.priority < wordB.getState().priority ? -1 : 1;
		}
		else if (this.word.length() != wordB.word.length())
		{
			return this.word.length() < wordB.word.length() ? 1 : -1;
		}

		return this.word.compareTo(wordB.word);
	}
}