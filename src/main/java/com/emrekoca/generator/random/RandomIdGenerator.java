package com.emrekoca.generator.random;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.emrekoca.generator.Generator;

/**
 * Generates secure random IDs. It is not unique so be careful for collisions!
 * 
 * @author ekoca
 *
 */
@Component
public class RandomIdGenerator implements Generator<String> {

	private LazyLoadingSecureRandom random = LazyLoadingSecureRandom.INSTANCE;

	/**
	 * Every time this method is invoked, the instance generates a different ID. It
	 * does not guarantee uniqueness of any short ID
	 * 
	 * @return T
	 */
	@Override
	public String next() {
		return getNewId();
	}

	/**
	 * Utility method to generate random IDs.
	 */
	private String getNewId() {
		return new BigInteger(128, random.getRandom()).toString(36);
	}
}
