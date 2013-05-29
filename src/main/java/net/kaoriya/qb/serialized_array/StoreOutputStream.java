package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.OutputStream;

public final class StoreOutputStream extends OutputStream
{
    private OutputStream dest;

    private long count = 0;

    public StoreOutputStream(OutputStream out)
    {
        this.dest = out;
    }

    @Override
    public final void write(byte[] b) throws IOException
    {
        this.dest.write(b);
        this.count += b.length;
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException
    {
        this.dest.write(b, off, len);
        this.count += len;
    }

    @Override
    public final void write(int b) throws IOException
    {
        this.dest.write(b);
        this.count += 1;
    }

    @Override
    public final void flush() throws IOException
    {
        if (this.dest != null) {
            this.dest.flush();
        }
    }
    @Override
    public final void close() throws IOException
    {
        // don't call this.dest.close()
        flush();
        this.dest = null;
    }

    public long getCount()
    {
        return this.count;
    }
}
