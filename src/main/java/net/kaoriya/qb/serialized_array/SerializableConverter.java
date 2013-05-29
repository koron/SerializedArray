package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public final class SerializableConverter<T extends Serializable>
    implements Converter<T>
{
    public void writeObject(OutputStream dest, T src) throws IOException
    {
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(dest);
            stream.writeObject(src);
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException e) {}
            }
        }
    }

    public T readObject(InputStream src) throws IOException
    {
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(src);
            return (T)stream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException e) {}
            }
        }
    }
}
