package com.emrekoca.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emrekoca.generator.incremental.GroupIncrementalIdGenerator;
import com.emrekoca.generator.incremental.IncrementalIdGenerator;
import com.emrekoca.generator.random.RandomIdGenerator;
import com.emrekoca.generator.random.ShortIdGenerator;
import com.emrekoca.generator.unique.UniqueIdGenerator;
import com.emrekoca.model.Greeting;

/**
 * 
 * @author ekoca
 *
 */
@RestController
public class GreetingController {
	@Autowired
	IncrementalIdGenerator incremental;

	@Autowired
	GroupIncrementalIdGenerator group;

	@Autowired
	RandomIdGenerator random;

	@Autowired
	ShortIdGenerator shortz;

	@Autowired
	UniqueIdGenerator unique;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting<Long> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting<Long>(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping("/id/incremental")
	public Greeting<Long> incremental() {
		return new Greeting<Long>(incremental.next(), "Incremental IDs...");
	}

	@RequestMapping("/id/incrementals")
	public Greeting<Long> incrementals(@RequestParam(value = "key", defaultValue = "test") String key) {
		return new Greeting<Long>(group.next(key), "Group incremental IDs...");
	}

	@RequestMapping("/id/random")
	public Greeting<String> random() {
		return new Greeting<String>(random.next(), "Random IDs...");
	}

	@RequestMapping("/id/short")
	public Greeting<String> shortz() {
		return new Greeting<String>(shortz.next(), "Short (8-length) IDs...");
	}

	@RequestMapping("/id/unique")
	public Greeting<Long> unique() {
		return new Greeting<Long>(unique.next(), "Unique IDs...");
	}
}