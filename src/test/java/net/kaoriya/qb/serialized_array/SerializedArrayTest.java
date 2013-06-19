package net.kaoriya.qb.serialized_array;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class SerializedArrayTest
{
    class MemorySeriarizableArray<T extends Serializable>
            extends SerializedArray<T>
    {
        MemorySeriarizableArray()
        {
            super(new SerializableConverter<T>(), new MemoryStore());
        }

        MemoryStore getMemoryStore()
        {
            return (MemoryStore)getBytesStore();
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

    class DirectMemoryArray<T extends Serializable> extends SerializedArray<T>
    {
        DirectMemoryArray(MemorySeriarizableArray array)
        {
            super(new SerializableConverter<T>(),
                new DirectMemoryStore(array.getMemoryStore()));
        }
    }

    @Test
    public void testStringsOnDirectMemory() throws Exception
    {
        MemorySeriarizableArray<String> array0 =
            new MemorySeriarizableArray<String>();
        array0.add("foo");
        array0.add("bar");
        array0.add("baz");
        array0.add("qux");
        DirectMemoryArray array = new DirectMemoryArray(array0);

        assertEquals(4, array.getSize());
        assertEquals("foo", array.get(0));
        assertEquals("bar", array.get(1));
        assertEquals("baz", array.get(2));
        assertEquals("qux", array.get(3));
    }
}
