package com.emrekoca.generator;

/**
 * 
 * @author ekoca
 *
 * @param <T>
 *            type
 */
public interface Generator<T> {
	public T next();
}
