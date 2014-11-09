package fi.jamk.wordsoccer.game.players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fi.jamk.wordsoccer.game.Card;
import fi.jamk.wordsoccer.game.IGame;
import fi.jamk.wordsoccer.game.IPlayer;
import fi.jamk.wordsoccer.game.Letter;

public class AIPlayer extends Player
{
	private final Random generator;
	private final double minPercentage, maxPercentage;

	public AIPlayer(String name, double minPercentage, double maxPercentage)
	{
		super(name);

		this.generator = new Random();
		this.minPercentage = minPercentage;
		this.maxPercentage = maxPercentage;
	}

	@Override
	public void onStartRound(IGame game)
	{
		// TODO
	}
}