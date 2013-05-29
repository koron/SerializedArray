package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Converter<T>
{
    void writeObject(OutputStream dest, T src) throws IOException;

    T readObject(InputStream src) throws IOException;
}
