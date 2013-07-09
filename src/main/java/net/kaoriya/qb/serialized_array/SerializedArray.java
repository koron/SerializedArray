package net.kaoriya.qb.serialized_array;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializedArray<T> implements Closeable
{
    private Converter<T> converter;

    private BytesStore bytesStore;

    private int size = 0;

    public SerializedArray(Converter<T> converter, BytesStore bytesStore)
    {
        this.converter = converter;
        this.bytesStore = bytesStore;
        this.size = bytesStore.getSize();
    }

    public final void add(T value) throws IOException
    {
        OutputStream stream = null;
        try {
            stream = this.bytesStore.addBegin();
            this.converter.writeObject(stream, value);
            this.size += 1;
        } finally {
            if (stream != null) { this.bytesStore.addEnd(stream); }
        }
    }

    public final T get(int index) throws IOException
    {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("#get: " + index);
        }
        InputStream stream = null;
        try {
            stream = this.bytesStore.getBegin(index);
            return this.converter.readObject(stream);
        } finally {
            if (stream != null) { this.bytesStore.getEnd(stream); }
        }
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

    protected Converter<T> getConverter()
    {
        return this.converter;
    }

    protected BytesStore getBytesStore()
    {
        return this.bytesStore;
    }

    public void close()
    {
        this.bytesStore = null;
    }
}
