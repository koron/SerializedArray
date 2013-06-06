package net.kaoriya.qb.serialized_array;

import java.io.IOException;

import org.msgpack.MessagePackable;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessagePackedArrayTest
{
    static class MessagePackedArray<T extends MessagePackable>
            extends SerializedArray<T>
    {
        MessagePackedArray(Class<T> klass)
        {
            super(new MessagePackableConverter<T>(klass), new MemoryStore());
        }
    }

    static class MyClass implements MessagePackable
    {
        int intValue;
        String stringValue;
        int[] intArray;

        MyClass() {}

        MyClass(int intValue, String stringValue, int[] intArray)
        {
            this.intValue = intValue;
            this.stringValue = stringValue;
            this.intArray = intArray;
        }

        public void writeTo(Packer pk) throws IOException
        {
            pk.writeArrayBegin(3);
            pk.write(this.intValue);
            pk.write(this.stringValue);
            if (this.intArray != null) {
                pk.writeArrayBegin(this.intArray.length);
                for (int n : this.intArray) {
                    pk.write(n);
                }
                pk.writeArrayEnd();
            } else {
                pk.writeNil();
            }
            pk.writeArrayEnd();
        }

        public void readFrom(Unpacker u) throws IOException
        {
            u.readArrayBegin();
            this.intValue = u.readInt();
            this.stringValue = u.readString();
            switch (u.getNextType()) {
                case NIL:
                    this.intArray = null;
                    break;
                case ARRAY:
                    int[] array = new int[u.readArrayBegin()];
                    for (int i = 0, max = array.length; i < max; ++i) {
                        array[i] = u.readInt();
                    }
                    this.intArray = array;
                    u.readArrayEnd(true);
                    break;
            }
            u.readArrayEnd(true);
        }

        @Override
        public boolean equals(Object a)
        {
            MyClass target = (MyClass)a;
            if (this == target) {
                return true;
            }
            if (target.intValue != this.intValue) {
                return false;
            }
            if ((target.stringValue == null && this.stringValue != null)
                    || !target.stringValue.equals(this.stringValue)) {
                return false;
            }
            if (target.intArray != null && this.intArray != null) {
                if (target.intArray.length != this.intArray.length) {
                    return false;
                }
                for (int i = 0, max = target.intArray.length; i < max; ++i) {
                    if (target.intArray[i] != this.intArray[i]) {
                        return false;
                    }
                }
            } else if (target.intArray != this.intArray) {
                return false;
            }
            return true;
        }

        @Override
        public String toString()
        {
            StringBuilder s = new StringBuilder();
            s.append("MyClass {")
                .append(" intValue=").append(this.intValue)
                .append(" stringValue=").append(this.stringValue)
                .append(" intArray=").append(this.intArray)
                .append(" }");
            return s.toString();
        }
    }

    @Test
    public void testEquals()
    {
        assertEquals(
                new MyClass(1, "foo", new int[]{ 1, 2, 3 }),
                new MyClass(1, "foo", new int[]{ 1, 2, 3 }));
    }

    @Test
    public void testSimple() throws Exception
    {
        MessagePackedArray<MyClass> array =
            new MessagePackedArray<MyClass>(MyClass.class);
        array.add(new MyClass(1, "foo", new int[]{ 1, 2, 3 } ));
        array.add(new MyClass(2, "bar", new int[]{ } ));

        assertEquals(2, array.getSize());
        assertEquals(
                new MyClass(1, "foo", new int[]{ 1, 2, 3 }),
                array.get(0));
        assertEquals(
                new MyClass(2, "bar", new int[]{ }),
                array.get(1));
    }
}
