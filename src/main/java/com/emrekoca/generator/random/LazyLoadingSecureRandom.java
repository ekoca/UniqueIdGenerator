package com.emrekoca.generator.random;

import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads a secure random lazily to ensure optimal performance. This class is
 * thread safe and singleton.
 * 
 * @author Emre Koca
 */
public enum LazyLoadingSecureRandom {
	INSTANCE;

	private static final Logger logger = LoggerFactory.getLogger(LazyLoadingSecureRandom.class);

	/**
	 * If we don't make random volatile, as another thread can see a half
	 * initialized instance of random object!
	 */
	private volatile Random random;

	/**
	 * Get the secure random object. It is also loading lazily, to ensure optimal
	 * performance.
	 * 
	 * @return Random
	 */
	public Random getRandom() {
		if (random == null) {
			loadSecureRandomLoader();
		}
		return random;
	}

	/**
	 * Intention is to minimize cost of synchronization and improve performance, by
	 * only locking this function, the code which creates instance of SecureRandom
	 * class.
	 * 
	 * On some boxes a secure random can take up to a minute, and block server
	 * startup and unit tests to load SecureRandom object.
	 */
	private synchronized void loadSecureRandomLoader() {
		try {
			long time = System.currentTimeMillis();
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			time = System.currentTimeMillis() - time;
			logger.info("Created a new secure random " + time + " ms");
		} catch (Exception e) {
			logger.error("Cannot load secure random", e);
		}
	}
}
