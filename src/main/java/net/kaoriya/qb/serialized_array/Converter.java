package net.kaoriya.qb.serialized_array;

public interface Converter<T>
{
    byte[] toBytes(T src);

    T fromBytes(byte[] b);
}
