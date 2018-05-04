package com.emrekoca.generator.incremental;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.emrekoca.generator.GroupGenerator;

/**
 * 
 * @author ekoca
 *
 */
@Component
public class GroupIncrementalIdGenerator implements GroupGenerator<Long> {

	private static final ConcurrentHashMap<String, AtomicLong> mapper = new ConcurrentHashMap<String, AtomicLong>();

	/**
	 * Every time this method is invoked, the instance generates a different ID.
	 * 
	 * @return T
	 */
	@Override
	public Long next(final String key) {
		return mapper.get(key).getAndIncrement();
	}

	/**
	 * Gets the current value.
	 */
	public void getCurrentValue(final String key) {
		mapper.get(key);
	}

	/**
	 * Reset the current value to 0.
	 */
	public void reset(final String key) {
		mapper.get(key).set(0);
	}
}
