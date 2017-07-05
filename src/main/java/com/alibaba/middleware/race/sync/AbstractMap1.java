package com.alibaba.middleware.race.sync;
import java.util.Map.Entry;  
import java.util.*;
/** 
 * This class provides a skeletal implementation of the <tt>Map</tt> 
 * interface, to minimize the effort required to implement this interface. 
 * 
 * <p>To implement an unmodifiable map, the programmer needs only to extend this 
 * class and provide an implementation for the <tt>entrySet</tt> method, which 
 * returns a set-view of the map's mappings.  Typically, the returned set 
 * will, in turn, be implemented atop <tt>AbstractSet</tt>.  This set should 
 * not support the <tt>add</tt> or <tt>remove</tt> methods, and its iterator 
 * should not support the <tt>remove</tt> method. 
 * 
 * <p>To implement a modifiable map, the programmer must additionally override 
 * this class's <tt>put</tt> method (which otherwise throws an 
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by 
 * <tt>entrySet().iterator()</tt> must additionally implement its 
 * <tt>remove</tt> method. 
 * 
 * <p>The programmer should generally provide a void (no argument) and map 
 * constructor, as per the recommendation in the <tt>Map</tt> interface 
 * specification. 
 * 
 * <p>The documentation for each non-abstract method in this class describes its 
 * implementation in detail.  Each of these methods may be overridden if the 
 * map being implemented admits a more efficient implementation. 
 * 
 * <p>This class is a member of the 
 * <a href="{@docRoot}/../technotes/guides/collections/index.html"> 
 * Java Collections Framework</a>. 
 * 
 * @param <K> the type of keys maintained by this map 
 * @param <V> the type of mapped values 
 * 
 * @author  Josh Bloch 
 * @author  Neal Gafter 
 * @see Map 
 * @see Collection 
 * @since 1.2 
 */  
/* 
此类提供 Map 接口的骨干实现，以最大限度地减少实现此接口所需的工作。 
 
要实现不可修改的映射，编程人员只需扩展此类并提供 entrySet 方法的实现即可， 
该方法将返回映射的映射关系 set 视图。通常，返回的 set 将依次在 AbstractSet 上实现。 
此 set 不支持 add 或 remove 方法，其迭代器也不支持 remove 方法。 
 
要实现可修改的映射，编程人员必须另外重写此类的 put 方法（否则将抛出 
UnsupportedOperationException），entrySet().iterator() 返回的迭代器也必须另外实现其 remove 方法。 
 
按照 Map 接口规范中的建议，编程人员通常应该提供一个 void（无参数）构造方法和 map 构造方法。 
 
此类中每个非抽象方法的文档详细描述了其实现。如果要实现的映射允许更有效的实现，则可以重写所有这些方法。 
 
 */  
public abstract class AbstractMap1<K,V> implements Map<K,V> {  
    /** 
     * Sole constructor.  (For invocation by subclass constructors, typically 
     * implicit.) 
     */  
    protected AbstractMap1() {  
    }  
  
    // Query Operations  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation returns <tt>entrySet().size()</tt>. 
     */  
    //返回容量  
    public int size() {  
        //entrySet()方法是继承该类时需要实现的方法  
        return entrySet().size();  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation returns <tt>size() == 0</tt>. 
     */  
    //是否为空  
    public boolean isEmpty() {  
        return size() == 0;  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation iterates over <tt>entrySet()</tt> searching 
     * for an entry with the specified value.  If such an entry is found, 
     * <tt>true</tt> is returned.  If the iteration terminates without 
     * finding such an entry, <tt>false</tt> is returned.  Note that this 
     * implementation requires linear time in the size of the map. 
     * 
     * @throws ClassCastException   {@inheritDoc} 
     * @throws NullPointerException {@inheritDoc} 
     */  
    //是否包含值value  
    public boolean containsValue(Object value) {  
        //获取键值对的迭代器  
        Iterator<Entry<K,V>> i = entrySet().iterator();  
        if (value==null) {  
            //如果value为null,则通过 == 对比  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (e.getValue()==null)  
                    return true;  
            }  
        } else {  
            //value不为null,通过equals对比  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (value.equals(e.getValue()))  
                    return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation iterates over <tt>entrySet()</tt> searching 
     * for an entry with the specified key.  If such an entry is found, 
     * <tt>true</tt> is returned.  If the iteration terminates without 
     * finding such an entry, <tt>false</tt> is returned.  Note that this 
     * implementation requires linear time in the size of the map; many 
     * implementations will override this method. 
     * 
     * @throws ClassCastException   {@inheritDoc} 
     * @throws NullPointerException {@inheritDoc} 
     */  
    //判断键是否包含key  
    public boolean containsKey(Object key) {  
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();  
        if (key==null) {  
            //通过 == 对比  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (e.getKey()==null)  
                    return true;  
            }  
        } else {  
            //通过 equals 对比  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (key.equals(e.getKey()))  
                    return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation iterates over <tt>entrySet()</tt> searching 
     * for an entry with the specified key.  If such an entry is found, 
     * the entry's value is returned.  If the iteration terminates without 
     * finding such an entry, <tt>null</tt> is returned.  Note that this 
     * implementation requires linear time in the size of the map; many 
     * implementations will override this method. 
     * 
     * @throws ClassCastException            {@inheritDoc} 
     * @throws NullPointerException          {@inheritDoc} 
     */  
    //通过key找到对应的value  
    public V get(Object key) {  
        Iterator<Entry<K,V>> i = entrySet().iterator();  
        if (key==null) {  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (e.getKey()==null)  
                    return e.getValue();  
            }  
        } else {  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (key.equals(e.getKey()))  
                    return e.getValue();  
            }  
        }  
        return null;  
    }  
  
  
    // Modification Operations  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation always throws an 
     * <tt>UnsupportedOperationException</tt>. 
     * 
     * @throws UnsupportedOperationException {@inheritDoc} 
     * @throws ClassCastException            {@inheritDoc} 
     * @throws NullPointerException          {@inheritDoc} 
     * @throws IllegalArgumentException      {@inheritDoc} 
     */  
    //添加，此类不具体实现  
    public V put(K key, V value) {  
        throw new UnsupportedOperationException();  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation iterates over <tt>entrySet()</tt> searching for an 
     * entry with the specified key.  If such an entry is found, its value is 
     * obtained with its <tt>getValue</tt> operation, the entry is removed 
     * from the collection (and the backing map) with the iterator's 
     * <tt>remove</tt> operation, and the saved value is returned.  If the 
     * iteration terminates without finding such an entry, <tt>null</tt> is 
     * returned.  Note that this implementation requires linear time in the 
     * size of the map; many implementations will override this method. 
     * 
     * <p>Note that this implementation throws an 
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt> 
     * iterator does not support the <tt>remove</tt> method and this map 
     * contains a mapping for the specified key. 
     * 
     * @throws UnsupportedOperationException {@inheritDoc} 
     * @throws ClassCastException            {@inheritDoc} 
     * @throws NullPointerException          {@inheritDoc} 
     */  
    //移除掉键为key的键值对  
    public V remove(Object key) {  
        //获取迭代器  
        Iterator<Entry<K,V>> i = entrySet().iterator();  
        //用来记录键为key的键值对  
        Entry<K,V> correctEntry = null;  
        if (key==null) {  
            while (correctEntry==null && i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (e.getKey()==null)  
                    correctEntry = e;  
            }  
        } else {  
            while (correctEntry==null && i.hasNext()) {  
                Entry<K,V> e = i.next();  
                if (key.equals(e.getKey()))  
                    correctEntry = e;  
            }  
        }  
  
        //记录被移除的键值对的值  
        V oldValue = null;  
        if (correctEntry !=null) {  
            //有该key的键值对  
            oldValue = correctEntry.getValue();  
            //移除掉  
            i.remove();  
        }  
        return oldValue;  
    }  
  
  
    // Bulk Operations  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation iterates over the specified map's 
     * <tt>entrySet()</tt> collection, and calls this map's <tt>put</tt> 
     * operation once for each entry returned by the iteration. 
     * 
     * <p>Note that this implementation throws an 
     * <tt>UnsupportedOperationException</tt> if this map does not support 
     * the <tt>put</tt> operation and the specified map is nonempty. 
     * 
     * @throws UnsupportedOperationException {@inheritDoc} 
     * @throws ClassCastException            {@inheritDoc} 
     * @throws NullPointerException          {@inheritDoc} 
     * @throws IllegalArgumentException      {@inheritDoc} 
     */  
    //将所有m中的键值对添加到当前Map中  
    public void putAll(Map<? extends K, ? extends V> m) {  
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())  
        //循环添加  
            put(e.getKey(), e.getValue());  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation calls <tt>entrySet().clear()</tt>. 
     * 
     * <p>Note that this implementation throws an 
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt> 
     * does not support the <tt>clear</tt> operation. 
     * 
     * @throws UnsupportedOperationException {@inheritDoc} 
     */  
    //清除掉  
    public void clear() {  
        entrySet().clear();  
    }  
  
  
    // Views  
  
    /** 
     * Each of these fields are initialized to contain an instance of the 
     * appropriate view the first time this view is requested.  The views are 
     * stateless, so there's no reason to create more than one of each. 
     */  
  
    transient volatile Set<K>        keySet = null;  
    transient volatile Collection<V> values = null;  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation returns a set that subclasses {@link AbstractSet}. 
     * The subclass's iterator method returns a "wrapper object" over this 
     * map's <tt>entrySet()</tt> iterator.  The <tt>size</tt> method 
     * delegates to this map's <tt>size</tt> method and the 
     * <tt>contains</tt> method delegates to this map's 
     * <tt>containsKey</tt> method. 
     * 
     * <p>The set is created the first time this method is called, 
     * and returned in response to all subsequent calls.  No synchronization 
     * is performed, so there is a slight chance that multiple calls to this 
     * method will not all return the same set. 
     */  
    //返回所有key组成的set  
    public Set<K> keySet() {  
        if (keySet == null) {  
            keySet = new AbstractSet<K>() {  
                //实现iterator方法  
                public Iterator<K> iterator() {  
                    return new Iterator<K>() {  
                        //创建Iterator  
                        private Iterator<Entry<K,V>> i = entrySet().iterator();  
  
                        public boolean hasNext() {  
                            return i.hasNext();  
                        }  
  
                        public K next() {  
                            return i.next().getKey();  
                        }  
  
                        public void remove() {  
                            i.remove();  
                        }  
                    };  
                }  
  
                //以下的方法调用本身的方法  
                public int size() {  
                    return AbstractMap1.this.size();  
                }  
  
                public boolean isEmpty() {  
                    return AbstractMap1.this.isEmpty();  
                }  
  
                public void clear() {  
                    AbstractMap1.this.clear();  
                }  
  
                public boolean contains(Object k) {  
                    return AbstractMap1.this.containsKey(k);  
                }  
            };  
        }  
        return keySet;  
    }  
  
    /** 
     * {@inheritDoc} 
     * 
     * @implSpec 
     * This implementation returns a collection that subclasses {@link 
     * AbstractCollection}.  The subclass's iterator method returns a 
     * "wrapper object" over this map's <tt>entrySet()</tt> iterator. 
     * The <tt>size</tt> method delegates to this map's <tt>size</tt> 
     * method and the <tt>contains</tt> method delegates to this map's 
     * <tt>containsValue</tt> method. 
     * 
     * <p>The collection is created the first time this method is called, and 
     * returned in response to all subsequent calls.  No synchronization is 
     * performed, so there is a slight chance that multiple calls to this 
     * method will not all return the same collection. 
     */  
    //获取所有value  
    public Collection<V> values() {  
        if (values == null) {  
            values = new AbstractCollection<V>() {  
                public Iterator<V> iterator() {  
                    return new Iterator<V>() {  
                        private Iterator<Entry<K,V>> i = entrySet().iterator();  
  
                        public boolean hasNext() {  
                            return i.hasNext();  
                        }  
  
                        public V next() {  
                            return i.next().getValue();  
                        }  
  
                        public void remove() {  
                            i.remove();  
                        }  
                    };  
                }  
  
                public int size() {  
                    return AbstractMap1.this.size();  
                }  
  
                public boolean isEmpty() {  
                    return AbstractMap1.this.isEmpty();  
                }  
  
                public void clear() {  
                    AbstractMap1.this.clear();  
                }  
  
                public boolean contains(Object v) {  
                    return AbstractMap1.this.containsValue(v);  
                }  
            };  
        }  
        return values;  
    }  
  
    public abstract Set<Entry<K,V>> entrySet();  
  
  
    // Comparison and hashing  
  
    /** 
     * Compares the specified object with this map for equality.  Returns 
     * <tt>true</tt> if the given object is also a map and the two maps 
     * represent the same mappings.  More formally, two maps <tt>m1</tt> and 
     * <tt>m2</tt> represent the same mappings if 
     * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the 
     * <tt>equals</tt> method works properly across different implementations 
     * of the <tt>Map</tt> interface. 
     * 
     * @implSpec 
     * This implementation first checks if the specified object is this map; 
     * if so it returns <tt>true</tt>.  Then, it checks if the specified 
     * object is a map whose size is identical to the size of this map; if 
     * not, it returns <tt>false</tt>.  If so, it iterates over this map's 
     * <tt>entrySet</tt> collection, and checks that the specified map 
     * contains each mapping that this map contains.  If the specified map 
     * fails to contain such a mapping, <tt>false</tt> is returned.  If the 
     * iteration completes, <tt>true</tt> is returned. 
     * 
     * @param o object to be compared for equality with this map 
     * @return <tt>true</tt> if the specified object is equal to this map 
     */  
    //判断对象是否相等  
    public boolean equals(Object o) {  
        // == 引用相同则相等  
        if (o == this)  
            return true;  
  
        //不是Map类型的对象则不相等  
        if (!(o instanceof Map))  
            return false;  
  
        Map<?,?> m = (Map<?,?>) o;  
        //容量不同则不相等  
        if (m.size() != size())  
            return false;  
  
        try {  
            Iterator<Entry<K,V>> i = entrySet().iterator();  
            while (i.hasNext()) {  
                Entry<K,V> e = i.next();  
                K key = e.getKey();  
                V value = e.getValue();  
                //一个一个元素遍历，如果有不相同，则返回false  
                if (value == null) {  
                    if (!(m.get(key)==null && m.containsKey(key)))  
                        return false;  
                } else {  
                    if (!value.equals(m.get(key)))  
                        return false;  
                }  
            }  
        } catch (ClassCastException unused) {  
            return false;  
        } catch (NullPointerException unused) {  
            return false;  
        }  
  
        return true;  
    }  
  
    /** 
     * Returns the hash code value for this map.  The hash code of a map is 
     * defined to be the sum of the hash codes of each entry in the map's 
     * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt> 
     * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps 
     * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of 
     * {@link Object#hashCode}. 
     * 
     * @implSpec 
     * This implementation iterates over <tt>entrySet()</tt>, calling 
     * {@link Map.Entry#hashCode hashCode()} on each element (entry) in the 
     * set, and adding up the results. 
     * 
     * @return the hash code value for this map 
     * @see Map.Entry#hashCode() 
     * @see Object#equals(Object) 
     * @see Set#equals(Object) 
     */  
    //计算hashCode  
    public int hashCode() {  
        int h = 0;  
        Iterator<Entry<K,V>> i = entrySet().iterator();  
        //将每个键值对的hashCode相加  
        while (i.hasNext())  
            h += i.next().hashCode();  
        return h;  
    }  
  
    /** 
     * Returns a string representation of this map.  The string representation 
     * consists of a list of key-value mappings in the order returned by the 
     * map's <tt>entrySet</tt> view's iterator, enclosed in braces 
     * (<tt>"{}"</tt>).  Adjacent mappings are separated by the characters 
     * <tt>", "</tt> (comma and space).  Each key-value mapping is rendered as 
     * the key followed by an equals sign (<tt>"="</tt>) followed by the 
     * associated value.  Keys and values are converted to strings as by 
     * {@link String#valueOf(Object)}. 
     * 
     * @return a string representation of this map 
     */  
    //重写toString方法  
    public String toString() {  
        Iterator<Entry<K,V>> i = entrySet().iterator();  
        if (! i.hasNext())  
            return "{}";  
  
        StringBuilder sb = new StringBuilder();  
        sb.append('{');  
        for (;;) {  
            Entry<K,V> e = i.next();  
            K key = e.getKey();  
            V value = e.getValue();  
            sb.append(key   == this ? "(this Map)" : key);  
            sb.append('=');  
            sb.append(value == this ? "(this Map)" : value);  
            if (! i.hasNext())  
                return sb.append('}').toString();  
            sb.append(',').append(' ');  
        }  
    }  
  
    /** 
     * Returns a shallow copy of this <tt>AbstractMap1</tt> instance: the keys 
     * and values themselves are not cloned. 
     * 
     * @return a shallow copy of this map 
     */  
    //克隆  
    protected Object clone() throws CloneNotSupportedException {  
        AbstractMap1<?,?> result = (AbstractMap1<?,?>)super.clone();  
        result.keySet = null;  
        result.values = null;  
        return result;  
    }  
  
    /** 
     * Utility method for SimpleEntry and SimpleImmutableEntry. 
     * Test for equality, checking for nulls. 
     * 
     * NB: Do not replace with Object.equals until JDK-8015417 is resolved. 
     */  
    //用于SimpleEntry和SimpleImmutableEntry的工具方法，判断2个对象是否相同  
    private static boolean eq(Object o1, Object o2) {  
        return o1 == null ? o2 == null : o1.equals(o2);  
    }  
  
    // Implementation Note: SimpleEntry and SimpleImmutableEntry  
    // are distinct unrelated classes, even though they share  
    // some code. Since you can't add or subtract final-ness  
    // of a field in a subclass, they can't share representations,  
    // and the amount of duplicated code is too small to warrant  
    // exposing a common abstract class.  
  
  
    /** 
     * An Entry maintaining a key and a value.  The value may be 
     * changed using the <tt>setValue</tt> method.  This class 
     * facilitates the process of building custom map 
     * implementations. For example, it may be convenient to return 
     * arrays of <tt>SimpleEntry</tt> instances in method 
     * <tt>Map.entrySet().toArray</tt>. 
     * 
     * @since 1.6 
     */  
    /* 
    维护键和值的 Entry。可以使用 setValue 方法更改值。此类简化了构建自定义映射实现的过程。 
    例如，可以使用 Map.entrySet().toArray 方法方便地返回 SimpleEntry 实例数组 
     */  
    public static class SimpleEntry<K,V>  
        implements Entry<K,V>, java.io.Serializable  
    {  
        private static final long serialVersionUID = -8499721149061103585L;  
  
        // key 为不可变  
        private final K key;  
        private V value;  
  
        /** 
         * Creates an entry representing a mapping from the specified 
         * key to the specified value. 
         * 
         * @param key the key represented by this entry 
         * @param value the value represented by this entry 
         */  
        public SimpleEntry(K key, V value) {  
            this.key   = key;  
            this.value = value;  
        }  
  
        /** 
         * Creates an entry representing the same mapping as the 
         * specified entry. 
         * 
         * @param entry the entry to copy 
         */  
        public SimpleEntry(Entry<? extends K, ? extends V> entry) {  
            this.key   = entry.getKey();  
            this.value = entry.getValue();  
        }  
  
        /** 
         * Returns the key corresponding to this entry. 
         * 
         * @return the key corresponding to this entry 
         */  
        //获取key  
        public K getKey() {  
            return key;  
        }  
  
        /** 
         * Returns the value corresponding to this entry. 
         * 
         * @return the value corresponding to this entry 
         */  
        //获取value  
        public V getValue() {  
            return value;  
        }  
  
        /** 
         * Replaces the value corresponding to this entry with the specified 
         * value. 
         * 
         * @param value new value to be stored in this entry 
         * @return the old value corresponding to the entry 
         */  
        //设置value  
        public V setValue(V value) {  
            V oldValue = this.value;  
            this.value = value;  
            return oldValue;  
        }  
  
        /** 
         * Compares the specified object with this entry for equality. 
         * Returns {@code true} if the given object is also a map entry and 
         * the two entries represent the same mapping.  More formally, two 
         * entries {@code e1} and {@code e2} represent the same mapping 
         * if<pre> 
         *   (e1.getKey()==null ? 
         *    e2.getKey()==null : 
         *    e1.getKey().equals(e2.getKey())) 
         *   && 
         *   (e1.getValue()==null ? 
         *    e2.getValue()==null : 
         *    e1.getValue().equals(e2.getValue()))</pre> 
         * This ensures that the {@code equals} method works properly across 
         * different implementations of the {@code Map.Entry} interface. 
         * 
         * @param o object to be compared for equality with this map entry 
         * @return {@code true} if the specified object is equal to this map 
         *         entry 
         * @see    #hashCode 
         */  
        //判断是否相等  
        public boolean equals(Object o) {  
            //如果不是Map.Entry  
            if (!(o instanceof Map.Entry))  
                return false;  
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;  
            //键跟值都相等才相等  
            return eq(key, e.getKey()) && eq(value, e.getValue());  
        }  
  
        /** 
         * Returns the hash code value for this map entry.  The hash code 
         * of a map entry {@code e} is defined to be: <pre> 
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^ 
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre> 
         * This ensures that {@code e1.equals(e2)} implies that 
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries 
         * {@code e1} and {@code e2}, as required by the general 
         * contract of {@link Object#hashCode}. 
         * 
         * @return the hash code value for this map entry 
         * @see    #equals 
         */  
        public int hashCode() {  
            return (key   == null ? 0 :   key.hashCode()) ^  
                   (value == null ? 0 : value.hashCode());  
        }  
  
        /** 
         * Returns a String representation of this map entry.  This 
         * implementation returns the string representation of this 
         * entry's key followed by the equals character ("<tt>=</tt>") 
         * followed by the string representation of this entry's value. 
         * 
         * @return a String representation of this map entry 
         */  
        public String toString() {  
            return key + "=" + value;  
        }  
  
    }  
  
    /** 
     * An Entry maintaining an immutable key and value.  This class 
     * does not support method <tt>setValue</tt>.  This class may be 
     * convenient in methods that return thread-safe snapshots of 
     * key-value mappings. 
     * 
     * @since 1.6 
     */  
    /* 
    维护不可变的键和值的 Entry。此类不支持 setValue 方法。在返回线程安全的键-值映射关系快照的方法中，此类也许很方便 
     */  
    public static class SimpleImmutableEntry<K,V>  
        implements Entry<K,V>, java.io.Serializable  
    {  
        private static final long serialVersionUID = 7138329143949025153L;  
  
        //key value 都是不可变的  
        private final K key;  
        private final V value;  
  
        /** 
         * Creates an entry representing a mapping from the specified 
         * key to the specified value. 
         * 
         * @param key the key represented by this entry 
         * @param value the value represented by this entry 
         */  
        public SimpleImmutableEntry(K key, V value) {  
            this.key   = key;  
            this.value = value;  
        }  
  
        /** 
         * Creates an entry representing the same mapping as the 
         * specified entry. 
         * 
         * @param entry the entry to copy 
         */  
        public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {  
            this.key   = entry.getKey();  
            this.value = entry.getValue();  
        }  
  
        /** 
         * Returns the key corresponding to this entry. 
         * 
         * @return the key corresponding to this entry 
         */  
        public K getKey() {  
            return key;  
        }  
  
        /** 
         * Returns the value corresponding to this entry. 
         * 
         * @return the value corresponding to this entry 
         */  
        public V getValue() {  
            return value;  
        }  
  
        /** 
         * Replaces the value corresponding to this entry with the specified 
         * value (optional operation).  This implementation simply throws 
         * <tt>UnsupportedOperationException</tt>, as this class implements 
         * an <i>immutable</i> map entry. 
         * 
         * @param value new value to be stored in this entry 
         * @return (Does not return) 
         * @throws UnsupportedOperationException always 
         */  
        public V setValue(V value) {  
            throw new UnsupportedOperationException();  
        }  
  
        /** 
         * Compares the specified object with this entry for equality. 
         * Returns {@code true} if the given object is also a map entry and 
         * the two entries represent the same mapping.  More formally, two 
         * entries {@code e1} and {@code e2} represent the same mapping 
         * if<pre> 
         *   (e1.getKey()==null ? 
         *    e2.getKey()==null : 
         *    e1.getKey().equals(e2.getKey())) 
         *   && 
         *   (e1.getValue()==null ? 
         *    e2.getValue()==null : 
         *    e1.getValue().equals(e2.getValue()))</pre> 
         * This ensures that the {@code equals} method works properly across 
         * different implementations of the {@code Map.Entry} interface. 
         * 
         * @param o object to be compared for equality with this map entry 
         * @return {@code true} if the specified object is equal to this map 
         *         entry 
         * @see    #hashCode 
         */  
        public boolean equals(Object o) {  
            if (!(o instanceof Map.Entry))  
                return false;  
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;  
            return eq(key, e.getKey()) && eq(value, e.getValue());  
        }  
  
        /** 
         * Returns the hash code value for this map entry.  The hash code 
         * of a map entry {@code e} is defined to be: <pre> 
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^ 
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre> 
         * This ensures that {@code e1.equals(e2)} implies that 
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries 
         * {@code e1} and {@code e2}, as required by the general 
         * contract of {@link Object#hashCode}. 
         * 
         * @return the hash code value for this map entry 
         * @see    #equals 
         */  
        public int hashCode() {  
            return (key   == null ? 0 :   key.hashCode()) ^  
                   (value == null ? 0 : value.hashCode());  
        }  
  
        /** 
         * Returns a String representation of this map entry.  This 
         * implementation returns the string representation of this 
         * entry's key followed by the equals character ("<tt>=</tt>") 
         * followed by the string representation of this entry's value. 
         * 
         * @return a String representation of this map entry 
         */  
        public String toString() {  
            return key + "=" + value;  
        }  
  
    }  
  
}  