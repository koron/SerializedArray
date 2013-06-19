package net.kaoriya.qb.serialized_array;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class DirectMemoryStoreTest
{
    @Test
    public void testCloseMultiple()
    {
        DirectMemoryStore store = new DirectMemoryStore(
                new byte[1024*1024*128], new ArrayList<Integer>());
        store.close();
        store.close();
        store.close();
    }
}
