package net.kaoriya.qb.serialized_array;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class StoreOutputStream extends FilterOutputStream
{
    private long count = 0;

    public StoreOutputStream(OutputStream out)
    {
        super(out);
    }

    @Override
    public final void write(byte[] b) throws IOException
    {
        super.write(b);
        this.count += b.length;
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException
    {
        super.write(b, off, len);
        this.count += len;
    }

    @Override
    public final void write(int b) throws IOException
    {
        super.write(b);
        this.count += 1;
    }

    @Override
    public final void close() throws IOException
    {
        // don't call super.close()
        flush();
        this.out = null;
    }

    public long getCount()
    {
        return this.count;
    }
}
