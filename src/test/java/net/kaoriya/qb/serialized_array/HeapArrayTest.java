package net.kaoriya.qb.serialized_array;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class HeapArrayTest
{
    @Test
    public void testStringsOnMemoryWithSerializable() throws Exception
    {
        HeapArray<String> array =
            new HeapArray<String>(new SerializableConverter<String>());
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
