package com.emrekoca.generator;

/**
 * Interface to generates IDs.
 * 
 * @author ekoca
 *
 * @param <T>
 *            type
 */
public interface Generator<T> {
	/**
	 * Every time this method is invoked, the instance generates a different ID.
	 * 
	 * @return T
	 */
	public T next();
}
