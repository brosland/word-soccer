//package fi.jamk.wordsoccer.listeners;
//
//import android.view.View;
//import android.widget.TextView;
//
//import fi.jamk.wordsoccer.game.Card;
//import fi.jamk.wordsoccer.game.IPlayer;
//
//public class UIPlayerListener implements IPlayer.IPlayerListener
//{
//	private TextView redCardsTextView, yellowCardsTextView, scoreTextView;
//
//	public UIPlayerListener(TextView redCardsTextView, TextView yellowCardsTextView, TextView scoreTextView)
//	{
//		this.redCardsTextView = redCardsTextView;
//		this.yellowCardsTextView = yellowCardsTextView;
//		this.scoreTextView = scoreTextView;
//	}
//
//	@Override
//	public void onCardAdded(IPlayer player, Card card)
//	{
//		redCardsTextView.setVisibility(player.getNumberOfCards(Card.CardType.RED) > 0
//			? View.VISIBLE : View.GONE);
//		redCardsTextView.setText(player.getNumberOfCards(Card.CardType.RED) > 1
//			? Integer.toString(player.getNumberOfCards(Card.CardType.RED)) : "");
//		yellowCardsTextView.setVisibility(player.getNumberOfCards(Card.CardType.YELLOW) > 0
//			? View.VISIBLE : View.GONE);
//	}
//
//	@Override
//	public void onScoreChanged(IPlayer player)
//	{
//		scoreTextView.setText(Integer.toString(player.getScore()));
//	}
//}