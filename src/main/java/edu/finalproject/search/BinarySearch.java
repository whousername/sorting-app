package edu.finalproject.search;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Поиск в отсортированном листе.
 * Необходимо, что бы компаратор совпадал с сортирующим.
 * @author fds
 */
class BinarySearch <T, F> implements SearchBehavior<T, F>{
	
	private final List<T> list;
	private final Function<T, F> getter;
	private final Comparator<? super F> comparator;
	private int occurrenceCounter;

	public BinarySearch(List<T> list,
			Function<T, F> getter, 
			Comparator<? super F> comparator) {
		this.list = list;
		this.getter = getter;
		this.comparator = comparator;
	}
	
	@Override
	public T find(F field) {
		int left = 0;
		int right = list.size() - 1;

		while (left <= right) {
			int middle = (left + right) / 2;
			T element = list.get(middle);
			int compareResult = comparator.compare(
					getter.apply(element),
					field);
			if (compareResult == 0) {
				return element;
			}
			if (compareResult > 0) {
				right = middle - 1;
			} else {
				left = middle + 1;
			}
		}
		return null;
	}
	
	@Override
	public int countOccurrences(T item) throws InterruptedException {
		int left = 0;
		int middle = 0;
		int right = list.size() - 1;
		boolean isFound = false;
		ExecutorService executor = Executors.newFixedThreadPool(2);
		//Находим элемент и двумя тредами смотрим влево-вправо от него
		while (left <= right) {
			middle = (left + right) / 2;
			T guess = list.get(middle);
			int compareResult = comparator.compare(
					getter.apply(guess),
					getter.apply(item));
			if (compareResult == 0) {	
				isFound = true;
				break;
			}
			if (compareResult > 0) {
				right = middle - 1;
			} else {
				left = middle + 1;
			}
		}
		if (isFound) {
			occurrenceCounter = 1;
			countOccurrencesJob goLeft = new countOccurrencesJob(
					-1, middle, item);
			countOccurrencesJob goRight = new countOccurrencesJob(
					 1 , middle, item);
			executor.execute(goLeft);
			executor.execute(goRight);
			executor.shutdown();
			executor.awaitTermination(10000, TimeUnit.DAYS);
		}
		return occurrenceCounter;
	}

	private class countOccurrencesJob implements Runnable{
		
		private final int increment;
		private final int startingPosition;
		private final T item;

		public countOccurrencesJob(int increment, int startingPosition, T item) {
			this.increment = increment;
			this.startingPosition = startingPosition;
			this.item = item;
		}

		@Override
		public void run() {
			int i = startingPosition + increment;
			int size = list.size();
			
			while (i >= 0 && i < size && list.get(i) == item) {
				occurrenceCounter++;
				i += increment;
			}
		}
	}
}
