package com.emrekoca.generator.incremental;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.emrekoca.generator.Generator;

/**
 * 
 * @author ekoca
 *
 */
@Component
public class IncrementalIdGenerator implements Generator<Long> {

	private static final AtomicLong counter = new AtomicLong(1);

	/**
	 * Every time this method is invoked, the instance generates a different ID.
	 * 
	 * @return T
	 */
	@Override
	public Long next() {
		return counter.getAndIncrement();
	}

	/**
	 * Gets the current value.
	 */
	public void getCurrentValue() {
		counter.get();
	}

	/**
	 * Reset the current value to 0.
	 */
	public void reset() {
		counter.set(0);
	}
}
