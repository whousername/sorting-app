package edu.finalproject.search;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Класс для поиска объектов в списке.
 * @param <T> Тип Объекта
 * @param <F> Тип поля, по которому будет производиться поиск
 * @author fds
 */
public class SearchEngine <T, F> {
	
	private SearchBehavior<T, F> searchBehavior;
	
	/**
	 * Коструктор для бинарного поиска
	 * @param list отсортированный списиок с объектами
	 * @param getter функция, возвращающая сравниваемое поле
	 * @param comparator компаратор сортировки
	 */
	public SearchEngine(List<T> list,
						Function<T, F> getter, 
						Comparator<F> comparator) {
		searchBehavior = new BinarySearch<>(list, getter, comparator);
	}
	
	/**
	 * Конструктор для простого поиска.
	 * @param list Список с объектами
	 * @param getter функция, возвращающая сравниваемое поле
	 */
	public SearchEngine(List<T> list, Function<T, F> getter) {
		searchBehavior = new SimpleSearch(list, getter);
	}
	
	/**
	 * Поиск объекта по значению одного из полей
	 * @param field Значение поля искомого объекта.
	 * @return Найденный объект или null, если не нашлось совпадений
	 */
	public T find(F field) {
		return searchBehavior.find(field);
	}
}
