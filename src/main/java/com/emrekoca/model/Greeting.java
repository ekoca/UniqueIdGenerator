package com.emrekoca.model;

public class Greeting<T> {

	private final T id;
	private final String content;

	public Greeting(T id, String content) {
		this.id = id;
		this.content = content;
	}

	public T getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
