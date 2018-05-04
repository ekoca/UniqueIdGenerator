package com.emrekoca.generator;

/**
 * Interface to generates IDs.
 * 
 * @author ekoca
 *
 * @param <T>
 *            type
 */
public interface GroupGenerator<T> {

	/**
	 * Every time this method is invoked, the instance generates a different ID by
	 * key.
	 * 
	 * @return T
	 */
	T next(String key);
}
