package com.emrekoca.generator.unique;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.emrekoca.generator.Generator;

/**
 * Generates unique random IDs.
 * 
 * @author ekoca
 */
public final class UniqueIdGenerator implements Generator<Long> {
	public static final int NODE_SHIFT = 10;
	public static final int SEQ_SHIFT = 12;

	public static final short MAX_SEQUENCE = 1024;

	/**
	 * Sequence ID.
	 */
	private AtomicInteger sequence = new AtomicInteger(0);

	/**
	 * Unsigned 16 bits integer value (0-262143) is required. It must be unique for
	 * each node on the distributed system.
	 */
	private short uniqueNodeId;

	/**
	 * Base time for generating unique ID. Unit is in millisecond. Base time is set
	 * to 2018-05-03T00:00:00Z by default.
	 */
	private final long baseTime = ZonedDateTime.of(2018, 5, 2, 0, 0, 0, 0, ZoneId.of("UTC")).toInstant().toEpochMilli();

	/**
	 * This time indicates when the sequence ID was generated. This value will be
	 * updated at any time.
	 */
	private long elapsedTime;

	/**
	 * Lock object.
	 */
	private static final Object LOCK = new Object();

	public UniqueIdGenerator(final short uniqueNodeId) {
		long now = Instant.now().toEpochMilli();

		if (this.baseTime > now) {
			throw new IllegalArgumentException(
					"Base time should be after 1970-01-01T00:00:00Z, or before current time.");
		}

		// baseTime.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
		this.uniqueNodeId = uniqueNodeId;
		this.elapsedTime = now - this.baseTime;
	}

	/**
	 * Every time this method is invoked, the instance generates a unique different
	 * ID. This method ensure the validity of the generated sequence ID. The
	 * generated sequence ID can not exceeded maximum.
	 * 
	 * @return T
	 */
	@Override
	public Long next() {
		// elapsedTime
		long elapsed = Instant.now().toEpochMilli() - baseTime;
		// sequence
		int sequence = getSequence(elapsed);
		if (sequence > MAX_SEQUENCE) {
			throw new RuntimeException("Sequence exhausted at " + this.sequence);
		}

		long id = (elapsed << 12) | (sequence << 16) | uniqueNodeId;
		return id;
	}

	/**
	 * Generates sequence ID at a certain elapsed time. The maximum value of
	 * generated sequence ID is expected to 1023. It reset the sequence at any time.
	 *
	 * @param elapsed
	 *            Elapsed time from base time in milliseconds.
	 * @return sequence ID.
	 */
	private int getSequence(long elapsed) {

		synchronized (LOCK) {
			if (this.elapsedTime < elapsed) {
				// reset the sequence
				this.elapsedTime = elapsed;
				sequence.set(0);
			}

			return sequence.getAndIncrement();
		}
	}
}
