package com.epicness.dualspatialgrid.utils;

import java.util.*;

public class IndexedSet<T> extends AbstractList<T> {

    private final List<T> list = new ArrayList<>();
    private final Set<T> set = new HashSet<>();

    @Override
    public boolean add(T element) {
        if (set.add(element)) {  // Only add if not in the set
            list.add(element);
            return true;
        }
        return false;
    }

    @Override
    public T remove(int i) {
        T t = list.remove(i);
        set.remove(t);
        return t;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object element) {
        return set.contains(element);
    }
}
