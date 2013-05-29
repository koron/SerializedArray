package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public final class MemoryStore implements BytesStore
{
    static class BytesStream extends ByteArrayOutputStream
    {
    }

    private BytesStream bytesStream = new BytesStream();

    private IntIndexTable indexTable = new IntIndexTable();

    private StoreOutputStream lastOutputStream = null;

    public OutputStream addBegin() throws IOException
    {
        synchronized (this.bytesStream)
        {
            if (this.lastOutputStream != null) {
                throw new IOException("didn't call addEnd previously");
            }

            this.lastOutputStream = new StoreOutputStream(this.bytesStream);
            return this.lastOutputStream;
        }
    }

    public void addEnd(OutputStream stream) throws IOException
    {
        synchronized (this.bytesStream)
        {
            if (this.lastOutputStream == null) {
                throw new IOException("didn't call addBegin previously");
            } else if (stream != this.lastOutputStream) {
                throw new IOException("stream is not match.");
            }
            this.lastOutputStream.close();
            this.indexTable.add((int)this.lastOutputStream.getCount());
            this.lastOutputStream = null;
        }
    }

    public InputStream getBegin(int index) throws IOException
    {
        // TODO:
        return null;
    }

    public void getEnd(InputStream stream)
    {
        // TODO:
    }

    public void clearAll()
    {
        synchronized (this.bytesStream)
        {
            this.bytesStream = new BytesStream();
            this.indexTable = new IntIndexTable();
            this.lastOutputStream = null;
        }
    }
}
