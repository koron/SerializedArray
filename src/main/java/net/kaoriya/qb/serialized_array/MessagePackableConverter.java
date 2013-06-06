package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.msgpack.MessagePack;
import org.msgpack.MessagePackable;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

public final class MessagePackableConverter<T extends MessagePackable>
    implements Converter<T>
{
    private final MessagePack messagePack = new MessagePack();

    private final Class<T> klass;

    public MessagePackableConverter(Class<T> klass)
    {
        this.klass = klass;
    }

    public void writeObject(OutputStream dest, T src) throws IOException
    {
        Packer packer = null;
        try {
            packer = this.messagePack.createPacker(dest);
            src.writeTo(packer);
        } finally {
            if (packer != null) {
                packer.flush();
                packer.close();
            }
        }
    }

    public T readObject(InputStream src) throws IOException
    {
        T dst = null;
        try {
            dst = this.klass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("failed to newInstance()", e);
        }

        Unpacker unpacker = null;
        try {
            unpacker = this.messagePack.createUnpacker(src);
            dst.readFrom(unpacker);
            return dst;
        } finally {
            if (unpacker != null) {
                unpacker.close();
            }
        }
    }
}
