package net.kaoriya.qb.serialized_array;

public final class HeapArray<T> extends SerializedArray<T>
{
    public HeapArray(Converter<T> converter)
    {
        super(converter, new MemoryStore());
    }

    public MemoryStore getMemoryStore()
    {
        return (MemoryStore)getBytesStore();
    }
}
