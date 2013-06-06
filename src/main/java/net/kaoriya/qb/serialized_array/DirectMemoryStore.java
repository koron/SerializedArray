package net.kaoriya.qb.serialized_array;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;

public final class DirectMemoryStore implements BytesStore
{
    private ByteBuffer byteBuffer;

    private int[] indexes;

    public DirectMemoryStore(byte[] bytes, List<Integer> indexes)
    {
        this.byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        this.byteBuffer.put(bytes);

        this.indexes = new int[indexes.size()];
        for (int i = 0, max = indexes.size(); i < max; ++i) {
            this.indexes[i] = indexes.get(i);
        }
    }

    public DirectMemoryStore(MemoryStore store)
    {
        this(store.getBytesStream().getBuf(),
                store.getIntIndexTable().getList());
    }

    public OutputStream addBegin() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void addEnd(OutputStream stream) throws IOException {
        throw new UnsupportedOperationException();
    }

    public InputStream getBegin(int index) throws IOException {
        int off = this.indexes[index];
        int len = this.indexes[index + 1] - this.indexes[index];

        byte[] b = new byte[len];
        this.byteBuffer.position(off);
        this.byteBuffer.get(b);

        return new ByteArrayInputStream(b);
    }

    public void getEnd(InputStream stream) throws IOException {
        // nothing to do.
    }

    public void clearAll()
    {
        this.byteBuffer = null;
        this.indexes = null;
    }

    public int getSize()
    {
        return this.indexes.length - 1;
    }
}
