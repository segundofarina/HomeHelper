package ar.edu.itba.paw.model.utils;

import java.util.List;

public class SizeListTuple<T> {
    private int size;
    private List<T> list;

    public SizeListTuple(int size, List<T> list) {
        this.size = size;
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public List<T> getList() {
        return list;
    }

}
