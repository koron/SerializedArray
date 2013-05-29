package net.kaoriya.qb.serialized_array;

import java.util.ArrayList;

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

    int getIndex(int index)
    {
        return this.list.get(index);
    }

    int getLength(int index)
    {
        return this.list.get(index + 1) - this.list.get(index);
    }
}
