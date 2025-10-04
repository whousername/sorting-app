package edu.finalproject.search;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author fds
 */
class SimpleSearch<T, F> implements SearchBehavior<T, F>{

	private final List<T> list;
	private final Function<T, F> getter;

	public SimpleSearch(List<T> list, Function<T, F> getter) {
		this.list = list;
		this.getter = getter;
	}
	
	@Override
	public T find(F field) {
		for (int i = 0; i < list.size(); i++) {
			T guess = list.get(i);
			if (field == getter.apply(guess)) {
				return guess;
			}
		}
		return null;
	}
}
