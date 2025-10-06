package edu.finalproject.search;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 *
 * @author fds
 */
class SimpleSearch<T, F> implements SearchBehavior<T, F>{

	private final List<T> list;
	private final Function<T, F> getter;
	private int occurrenceCounter = 0;

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

	/**
	 * 
	 * @param item
	 * @return
	 * @throws InterruptedException 
	 */
	@Override
	public int countOccurrences(T item) throws InterruptedException{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		int middle = list.size() / 2;
		CountOccurrencesJob goFirstHalf = new CountOccurrencesJob(
				item, 0, middle);
		CountOccurrencesJob goSecondHalf = new CountOccurrencesJob(
				item, middle, list.size());
		//Делим список на две части и проходимся по ним двумя потоками
		occurrenceCounter = 0;
		executor.execute(goFirstHalf);
		executor.execute(goSecondHalf);
		executor.shutdown();
		executor.awaitTermination(10000, TimeUnit.DAYS);
		return occurrenceCounter;
	}
	
	private class CountOccurrencesJob implements Runnable {
		private final T item;
		private final int fromIndex;
		private final int toIndex;

		public CountOccurrencesJob(T item, int fromIndex, int toIndex) {
			this.item = item;
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
		}
		
		public void run() {
			int i = fromIndex;
			while(i < toIndex && item == list.get(i)) {
				occurrenceCounter++;
				i++;
			}
		}
	}
}
