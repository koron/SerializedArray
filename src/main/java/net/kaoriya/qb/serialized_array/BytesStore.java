package net.kaoriya.qb.serialized_array;

public interface BytesStore
{
    int add(byte[] b);

    byte[] get(int index);

    void clearAll();
}
