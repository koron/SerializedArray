package net.kaoriya.qb.serialized_array;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class MemoryStore implements BytesStore
{
    static class BytesStream extends ByteArrayOutputStream
    {
        byte[] getBuf() {
            return this.buf;
        }
    }

    private BytesStream bytesStream = new BytesStream();

    private IntIndexTable indexTable = new IntIndexTable();

    private StoreOutputStream lastOutputStream = null;

    private ByteArrayInputStream lastInputStream = null;

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
                throw new IOException("stream is not match");
            }

            this.lastOutputStream.close();
            this.indexTable.add((int)this.lastOutputStream.getCount());
            this.lastOutputStream = null;
        }
    }

    public InputStream getBegin(int index) throws IOException
    {
        synchronized (this.bytesStream)
        {
            if (this.lastInputStream != null) {
                throw new IOException("didn't call getEnd previously");
            }

            int off = this.indexTable.getOffset(index);
            int len = this.indexTable.getLength(index);
            this.lastInputStream = new ByteArrayInputStream(
                    this.bytesStream.getBuf(), off, len);
            return this.lastInputStream;
        }
    }

    public void getEnd(InputStream stream) throws IOException
    {
        synchronized (this.bytesStream)
        {
            if (this.lastInputStream == null) {
                throw new IOException("didn't call getBegin previously");
            } else if (stream != this.lastInputStream) {
                throw new IOException("stream is not match");
            }

            this.lastInputStream.close();
            this.lastInputStream = null;
        }
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

    BytesStream getBytesStream()
    {
        return this.bytesStream;
    }

    IntIndexTable getIntIndexTable()
    {
        return this.indexTable;
    }

    public int getSize()
    {
        return this.indexTable.getSize() - 1;
    }
}
