package net.kaoriya.qb.serialized_array;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public final class MemoryStore implements BytesStore
{
    static class ByteStream extends ByteArrayOutputStream
    {
        public byte[] getBuf() { return buf; }
        public int getIndex() { return count; }
    }

    private ByteStream byteStream = new ByteStream();

    public int add(ByteBuffer b)
    {
        // TODO:
        int index = this.byteStream.getIndex();
        //this.byteStream.write()
        return 0;
    }

    public ByteBuffer get(int index)
    {
        // TODO:
        return null;
    }

    public void clearAll()
    {
        // TODO:
    }
}
