package com.emrekoca.generator.random;

import java.util.Random;

import com.emrekoca.generator.Generator;

/**
 * It generates short IDs (8 character ID). It is not unique so be careful for
 * collisions!
 * 
 * @author ekoca
 *
 */
public class ShortIdGenerator implements Generator<String> {
	// Base62 alphabet
	private final static String ALPHABET = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
	private final static int ID_SIZE = 8;

	/**
	 * Every time this method is invoked, the instance generates a different ID. It
	 * does not guarantee uniqueness of any short ID.
	 * 
	 * @return T
	 */
	@Override
	public String next() {
		StringBuilder shortId = new StringBuilder();
		Random r = LazyLoadingSecureRandom.INSTANCE.getRandom();
		for (int i = 0; i < ID_SIZE; i++) {
			shortId.append(ALPHABET.charAt(r.nextInt(ALPHABET.length())));
		}
		return shortId.toString();
	}
}
