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
		double boundary = 0f;

		for (char letter : charsetFrequency.keySet())
		{
			boundary += charsetFrequency.get(letter);

			if (x < boundary)
			{
				return letter;
			}
		}

		throw new IllegalArgumentException();
	}
}