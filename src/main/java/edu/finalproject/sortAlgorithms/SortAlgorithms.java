package edu.finalproject.sortAlgorithms;

import java.util.Comparator;

public class SortAlgorithms<E> {
    private SortStrategy<E> strategy;
    public SortAlgorithms(SortStrategy<E> strategy)
    {
        this.strategy = strategy;
    }
    public void setStrategy(SortStrategy<E> strategy){
        this.strategy = strategy;
    }
    public void sort(CustomUserCollection<E> users, Comparator<E> comparator){
        strategy.sort(users, comparator);
    }
}