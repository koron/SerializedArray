package net.kaoriya.qb.serialized_array;

import java.nio.ByteBuffer;

public interface BytesStore
{
    int add(ByteBuffer b);

    ByteBuffer get(int index);

    void clearAll();
}
