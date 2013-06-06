package net.kaoriya.qb.serialized_array;

import java.util.ArrayList;
import java.util.List;

// FIXME: more efficient implementation.
class IntIndexTable
{
    ArrayList<Integer> list = new ArrayList<Integer>();

    IntIndexTable()
    {
        this.list.add(0);
    }

    void add(int count)
    {
        this.list.add(getLast() + count);
    }

    int getLast()
    {
        return this.list.get(this.list.size() - 1);
    }

    int getOffset(int index)
    {
        return this.list.get(index);
    }

    int getLength(int index)
    {
        return this.list.get(index + 1) - this.list.get(index);
    }

    List<Integer> getList()
    {
        return this.list;
    }

    int getSize()
    {
        return this.list.size();
    }
}
