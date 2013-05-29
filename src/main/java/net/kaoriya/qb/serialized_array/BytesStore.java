package net.kaoriya.qb.serialized_array;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BytesStore
{
    OutputStream addBegin() throws IOException;

    void addEnd(OutputStream stream) throws IOException;

    InputStream getBegin(int index) throws IOException;

    void getEnd(InputStream stream);

    void clearAll();
}
