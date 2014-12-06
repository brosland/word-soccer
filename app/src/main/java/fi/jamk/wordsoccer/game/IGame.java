package fi.jamk.wordsoccer.game;

public interface IGame
{
	public static final int LETTERS = 11;
	public static final int ROUNDS = 6;
	public static final int MAX_RED_CARDS = 6;
	public static final int ROUND_DURATION = 90000; // 90s = 1:30
	public static final int MIN_GOAL_LETTERS = 21;

	public void init();

	public void startNewGame();

	public void startNewRound();

	public void finishRound();

	public void evaluateRound();

	public void updateScore();

	public void finishGame();

	public boolean hasNextRound();

	public IDictionary getDictionary();

	public IPlayer getPlayerA();

	public IPlayer getPlayerB();

	public int getCurrentRoundNumber();

	public String getCurrentRoundLetters();

	public void addGameListener(IGameListener listener);

	public void removeGameListener(IGameListener listener);

	public interface IGameListener
	{
		public void onInit(IGame game);

		public void onStartGame(IGame game);

		public void onStartRound(IGame game);

		public void onFinishRound(IGame game);

		public void onOpponentWordsLoaded(IGame game);

		public void onEvaluateRound(IGame game);

		public void onUpdateScore(IGame game);

		public void onFinishGame(IGame game);
	}
}