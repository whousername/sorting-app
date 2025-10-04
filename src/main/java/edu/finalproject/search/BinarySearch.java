package edu.finalproject.search;

import java.util.Comparator;
import java.util.List;
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
}
