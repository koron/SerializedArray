package net.kaoriya.qb.serialized_array;

import java.io.Closeable;

public final class OffHeapArray<T>
    extends SerializedArray<T>
{
    public OffHeapArray(HeapArray<T> src)
    {
        super(src.getConverter(), new DirectMemoryStore(src.getMemoryStore()));
    }

    @Override
    public void close()
    {
        DirectMemoryStore store = (DirectMemoryStore)getBytesStore();
        if (store != null) {
            store.close();
        }
        super.close();
    }
}
