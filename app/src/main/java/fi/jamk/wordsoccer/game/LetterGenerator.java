package fi.jamk.wordsoccer.game;

import java.util.HashMap;
import java.util.Random;

public class LetterGenerator
{
	private final HashMap<Character, Double> charsetFrequency;
	private final Random generator;

	public LetterGenerator(HashMap<Character, Double> letterFrequency)
	{
		this.charsetFrequency = letterFrequency;
		this.generator = new Random();
	}

	public char nextLetter()
	{
		double x = generator.nextDouble();
		double minBoundary = 0f, maxBoundary;

		for (char letter : charsetFrequency.keySet())
		{
			maxBoundary = minBoundary + charsetFrequency.get(letter);

			if (minBoundary < x && x < maxBoundary)
			{
				return letter;
			}

			minBoundary = maxBoundary;
		}

		throw new IllegalArgumentException();
	}
}