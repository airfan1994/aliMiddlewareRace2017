package com.alibaba.middleware.race.sync;

public class HashMap2
{
	public cunchu2[] table = new cunchu2[2097152];
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int indexFor(int h) {
        return h &2097151;
    }

    public byte[] get(int key) {
        for (cunchu2 e = table[hash(key)&2097151];
             e != null;
             e = e.next) {
            if (key==e.id)
                return e.data;
        }
        return null;
    }



    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public void put(int key, byte[] bytes) {
        int i =  hash(key)&2097151;
        for (cunchu2 e = table[i]; e != null; e = e.next) {
            if (e.id==key) {
                e.data = bytes;
                return;
            }
        }

        cunchu2 cc2 = new cunchu2(key,bytes);
        table[i] = cc2;
    }
    
   	public byte[] remove(int key) {
   		int i =  hash(key)&2097151;
        cunchu2 prev = table[i];
        cunchu2 e = prev;

        while (e != null) {
            cunchu2 next = e.next;
            if (e.id==key) {
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                //e.recordRemoval(this);
                return e.data;
            }
            prev = e;
            e = next;
        }

        return e.data;
    }


}

class cunchu2{
	public int id;
	public byte[] data;
	public cunchu2(int id,byte[] data)
	{
		this.id = id;
		this.data = data;
	}
	public cunchu2 next;
}