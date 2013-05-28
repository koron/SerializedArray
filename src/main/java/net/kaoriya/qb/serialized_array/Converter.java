package net.kaoriya.qb.serialized_array;

import java.nio.ByteBuffer;

public interface Converter<T>
{
    ByteBuffer toBytes(T src);

    T fromBytes(ByteBuffer b);
}
