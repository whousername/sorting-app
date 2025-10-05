package edu.finalproject.search;

/**
 * Интерфейс, для обощения простого и бинарного поиска
 * @author fds
 */
interface SearchBehavior <T, F> {
	public T find(F field);
	public int countOccurrences(T item) throws InterruptedException;
}