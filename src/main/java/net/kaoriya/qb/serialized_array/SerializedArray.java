package net.kaoriya.qb.serialized_array;

public class SerializedArray<T>
{
    private Converter<T> converter;

    private BytesStore bytesStore;

    private int size = 0;

    public SerializedArray(Converter<T> converter, BytesStore bytesStore)
    {
        this.converter = converter;
        this.bytesStore = bytesStore;
    }

    public final void add(T value)
    {
        this.bytesStore.add(this.converter.toBytes(value));
        this.size += 1;
    }

    public final T get(int index)
    {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("#get: " + index);
        }
        return this.converter.fromBytes(this.bytesStore.get(index));
    }

    public final int getSize()
    {
        return this.size;
    }

    public final void clearAll()
    {
        this.bytesStore.clearAll();
        this.size = 0;
    }
}
