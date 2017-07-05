package com.alibaba.middleware.race.sync;

import java.util.*;
import java.io.*;

public class HashMap4
{

    transient Entry[] table = new Entry[2097152];

    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h) {
        return h & 2097151;
    }

    public byte[] get(int key) {
        int hash = hash(key);
        for (Entry e = table[indexFor(hash)];
             e != null;
             e = e.next) {
            if (e.key ==key)
                return e.value;
        }
        return null;
    }

//    public byte[] get(Object key) {
//        if (key == null)
//            return getForNullKey();
//        int hash = hash(key.hashCode());
//        for (Entry e = table[indexFor(hash)];
//             e != null;
//             e = e.next) {
//            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
//                return e.value;
//        }
//        return null;
//    }
    
    private byte[] getForNullKey() {
        for (Entry e = table[0]; e != null; e = e.next) {
            if (e.key==0)
                return e.value;
        }
        return null;
    }


    final Entry getEntry(int key) {
        int hash = hash(key);
        for (Entry e = table[indexFor(hash)];
             e != null;
             e = e.next) {
            Object k;
            if (e.key==key)
                return e;
        }
        return null;
    }

    public byte[] put(int key, byte[] value) {
        int hash = hash(key);
        int i = indexFor(hash);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.key==key) {
                byte[] oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        addEntry(hash, key, value, i);
        return null;
    }

    private void putForCreate(int key, byte[] value) {
        int hash =  hash(key);
        int i = indexFor(hash);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.key==key) {
                e.value = value;
                return;
            }
        }

        createEntry(hash, key, value, i);
    }
    public byte[] remove(int key) {
        Entry e = removeEntryForKey(key);
        return (e == null ? null : e.value);
    }

    /**
     * Removes and returns the entry associated with the specified key
     * in the HashMap4.  Returns null if the HashMap4 contains no mapping
     * for this key.
     */
    final Entry removeEntryForKey(int key) {
        int hash = hash(key);
        int i = indexFor(hash);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.key==key) {
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }

    final Entry removeMapping(Entry entry) {

        int key = entry.getKey();
        int hash = hash(key);
        int i = indexFor(hash);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.key == hash) {
            //if (e.hash == hash && e.equals(entry)) {
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }



    static class Entry {
        final int key;
        byte[] value;
        Entry next;
       // final int hash;

        /**
         * Creates new entry.
         */
        Entry(int h, int k, byte[] v, Entry n) {
            value = v;
            next = n;
            key = k;
           // hash = h;
        }

        public final int getKey() {
            return key;
        }

        public final byte[] getValue() {
            return value;
        }

        public final byte[] setValue(byte[] newValue) {
            byte[] oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Entry e) {
            if(e.key==key&&e.value==value) return true;
        	return false;
        }

        void recordRemoval(HashMap4 m) {
        }

    }

    void addEntry(int hash, int key, byte[] value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
    }


    void createEntry(int hash, int key, byte[] value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
    }
}