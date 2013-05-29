package net.kaoriya.qb.serialized_array;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SerializedArrayTest
{
    class MemorySeriarizableArray<T extends Serializable>
            extends SerializedArray<T>
    {
        MemorySeriarizableArray()
        {
            super(new SerializableConverter<T>(), new MemoryStore());
        }
    }

    @Test
    public void testStringsOnMemoryWithSerializable() throws Exception
    {
        MemorySeriarizableArray<String> array =
            new MemorySeriarizableArray<String>();
        array.add("foo");
        array.add("bar");
        array.add("baz");
        array.add("qux");

        assertEquals(4, array.getSize());
        assertEquals("foo", array.get(0));
        assertEquals("bar", array.get(1));
        assertEquals("baz", array.get(2));
        assertEquals("qux", array.get(3));
    }
}
