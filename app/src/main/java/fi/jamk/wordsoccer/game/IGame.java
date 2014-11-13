package fi.jamk.wordsoccer.game;

public interface IGame
{
	public static final int LETTERS = 11;
	public static final int ROUNDS = 6;
	public static final int MAX_RED_CARDS = 6;

	public interface IGameListener
	{
		public void onStartGame(IGame game);

		public void onStartRound(IGame game);
	}

	public void startNewGame();

	public void startNewRound();

	public IDictionary getDictionary();

	public IPlayer getPlayerA();

	public IPlayer getPlayerB();

	public int getCurrentRoundNumber();

	public String getCurrentRoundLetters();

	public IGame addGameListener(IGameListener listener);

	public IGame removeGameListener(IGameListener listener);
}
