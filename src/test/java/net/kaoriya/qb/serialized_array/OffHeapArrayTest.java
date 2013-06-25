package net.kaoriya.qb.serialized_array;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class OffHeapArrayTest
{
    @Test
    public void testStringsOnDirectMemory() throws Exception
    {
        HeapArray<String> array0 =
            new HeapArray<String>(new SerializableConverter<String>());
        array0.add("foo");
        array0.add("bar");
        array0.add("baz");
        array0.add("qux");
        OffHeapArray<String> array = new OffHeapArray<String>(array0);

        assertEquals(4, array.getSize());
        assertEquals("foo", array.get(0));
        assertEquals("bar", array.get(1));
        assertEquals("baz", array.get(2));
        assertEquals("qux", array.get(3));

        array.close();
    }
}
