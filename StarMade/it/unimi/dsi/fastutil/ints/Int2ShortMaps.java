/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollections;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSets;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Int2ShortMaps
/*     */ {
/*  98 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */   public static Int2ShortMap singleton(int key, short value)
/*     */   {
/* 185 */     return new Singleton(key, value);
/*     */   }
/*     */ 
/*     */   public static Int2ShortMap singleton(Integer key, Short value)
/*     */   {
/* 200 */     return new Singleton(key.intValue(), value.shortValue());
/*     */   }
/*     */ 
/*     */   public static Int2ShortMap synchronize(Int2ShortMap m)
/*     */   {
/* 279 */     return new SynchronizedMap(m);
/*     */   }
/*     */ 
/*     */   public static Int2ShortMap synchronize(Int2ShortMap m, Object sync)
/*     */   {
/* 289 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */ 
/*     */   public static Int2ShortMap unmodifiable(Int2ShortMap m)
/*     */   {
/* 358 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ 
/*     */   public static class UnmodifiableMap extends Int2ShortFunctions.UnmodifiableFunction
/*     */     implements Int2ShortMap, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ShortMap map;
/*     */     protected volatile transient ObjectSet<Int2ShortMap.Entry> entries;
/*     */     protected volatile transient IntSet keys;
/*     */     protected volatile transient ShortCollection values;
/*     */ 
/*     */     protected UnmodifiableMap(Int2ShortMap m)
/*     */     {
/* 306 */       super();
/* 307 */       this.map = m;
/*     */     }
/*     */     public int size() {
/* 310 */       return this.map.size(); } 
/* 311 */     public boolean containsKey(int k) { return this.map.containsKey(k); } 
/* 312 */     public boolean containsValue(short v) { return this.map.containsValue(v); } 
/*     */     public short defaultReturnValue() {
/* 314 */       throw new UnsupportedOperationException(); } 
/* 315 */     public void defaultReturnValue(short defRetValue) { throw new UnsupportedOperationException(); } 
/*     */     public short put(int k, short v) {
/* 317 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public void putAll(Map<? extends Integer, ? extends Short> m) {
/* 320 */       throw new UnsupportedOperationException();
/*     */     }
/* 322 */     public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() { if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.int2ShortEntrySet()); return this.entries; } 
/* 323 */     public IntSet keySet() { if (this.keys == null) this.keys = IntSets.unmodifiable(this.map.keySet()); return this.keys; } 
/* 324 */     public ShortCollection values() { if (this.values == null) return ShortCollections.unmodifiable(this.map.values()); return this.values; } 
/*     */     public void clear() {
/* 326 */       throw new UnsupportedOperationException(); } 
/* 327 */     public String toString() { return this.map.toString(); }
/*     */ 
/*     */     public Short put(Integer k, Short v) {
/* 330 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public short remove(int k) {
/* 334 */       throw new UnsupportedOperationException(); } 
/* 335 */     public short get(int k) { return this.map.get(k); } 
/* 336 */     public boolean containsKey(Object ok) { return this.map.containsKey(ok); }
/*     */ 
/*     */     public boolean containsValue(Object ov)
/*     */     {
/* 340 */       return this.map.containsValue(ov);
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 348 */       return this.map.isEmpty(); } 
/* 349 */     public ObjectSet<Map.Entry<Integer, Short>> entrySet() { return ObjectSets.unmodifiable(this.map.entrySet()); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class SynchronizedMap extends Int2ShortFunctions.SynchronizedFunction
/*     */     implements Int2ShortMap, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ShortMap map;
/*     */     protected volatile transient ObjectSet<Int2ShortMap.Entry> entries;
/*     */     protected volatile transient IntSet keys;
/*     */     protected volatile transient ShortCollection values;
/*     */ 
/*     */     protected SynchronizedMap(Int2ShortMap m, Object sync)
/*     */     {
/* 219 */       super(sync);
/* 220 */       this.map = m;
/*     */     }
/*     */ 
/*     */     protected SynchronizedMap(Int2ShortMap m) {
/* 224 */       super();
/* 225 */       this.map = m;
/*     */     }
/*     */     public int size() {
/* 228 */       synchronized (this.sync) { return this.map.size(); }  } 
/* 229 */     public boolean containsKey(int k) { synchronized (this.sync) { return this.map.containsKey(k); }  } 
/* 230 */     public boolean containsValue(short v) { synchronized (this.sync) { return this.map.containsValue(v); }  } 
/*     */     public short defaultReturnValue() {
/* 232 */       synchronized (this.sync) { return this.map.defaultReturnValue(); }  } 
/* 233 */     public void defaultReturnValue(short defRetValue) { synchronized (this.sync) { this.map.defaultReturnValue(defRetValue); }  } 
/*     */     public short put(int k, short v) {
/* 235 */       synchronized (this.sync) { return this.map.put(k, v); } 
/*     */     }
/*     */ 
/* 238 */     public void putAll(Map<? extends Integer, ? extends Short> m) { synchronized (this.sync) { this.map.putAll(m); }  } 
/*     */     public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
/* 240 */       if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.int2ShortEntrySet(), this.sync); return this.entries; } 
/* 241 */     public IntSet keySet() { if (this.keys == null) this.keys = IntSets.synchronize(this.map.keySet(), this.sync); return this.keys; } 
/* 242 */     public ShortCollection values() { if (this.values == null) return ShortCollections.synchronize(this.map.values(), this.sync); return this.values; } 
/*     */     public void clear() {
/* 244 */       synchronized (this.sync) { this.map.clear(); }  } 
/* 245 */     public String toString() { synchronized (this.sync) { return this.map.toString(); } }
/*     */ 
/*     */     public Short put(Integer k, Short v) {
/* 248 */       synchronized (this.sync) { return (Short)this.map.put(k, v); }
/*     */     }
/*     */ 
/*     */     public short remove(int k) {
/* 252 */       synchronized (this.sync) { return this.map.remove(k); }  } 
/* 253 */     public short get(int k) { synchronized (this.sync) { return this.map.get(k); }  } 
/* 254 */     public boolean containsKey(Object ok) { synchronized (this.sync) { return this.map.containsKey(ok); } }
/*     */ 
/*     */     public boolean containsValue(Object ov)
/*     */     {
/* 258 */       synchronized (this.sync) { return this.map.containsValue(ov); }
/*     */ 
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 266 */       synchronized (this.sync) { return this.map.isEmpty(); }  } 
/* 267 */     public ObjectSet<Map.Entry<Integer, Short>> entrySet() { synchronized (this.sync) { return this.map.entrySet(); }  } 
/*     */     public int hashCode() {
/* 269 */       synchronized (this.sync) { return this.map.hashCode(); }  } 
/* 270 */     public boolean equals(Object o) { synchronized (this.sync) { return this.map.equals(o); }
/*     */ 
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Singleton extends Int2ShortFunctions.Singleton
/*     */     implements Int2ShortMap, Serializable, Cloneable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected volatile transient ObjectSet<Int2ShortMap.Entry> entries;
/*     */     protected volatile transient IntSet keys;
/*     */     protected volatile transient ShortCollection values;
/*     */ 
/*     */     protected Singleton(int key, short value)
/*     */     {
/* 116 */       super(value);
/*     */     }
/*     */     public boolean containsValue(short v) {
/* 119 */       return this.value == v;
/*     */     }
/* 121 */     public boolean containsValue(Object ov) { return ((Short)ov).shortValue() == this.value; }
/*     */ 
/*     */     public void putAll(Map<? extends Integer, ? extends Short> m) {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/* 126 */     public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() { if (this.entries == null) this.entries = ObjectSets.singleton(new SingletonEntry()); return this.entries; } 
/* 127 */     public IntSet keySet() { if (this.keys == null) this.keys = IntSets.singleton(this.key); return this.keys; } 
/* 128 */     public ShortCollection values() { if (this.values == null) this.values = ShortSets.singleton(this.value); return this.values;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 156 */       return false;
/*     */     }
/*     */     public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
/* 159 */       return int2ShortEntrySet();
/*     */     }
/* 161 */     public int hashCode() { return this.key ^ this.value; }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 164 */       if (o == this) return true;
/* 165 */       if (!(o instanceof Map)) return false;
/*     */ 
/* 167 */       Map m = (Map)o;
/* 168 */       if (m.size() != 1) return false;
/* 169 */       return ((Map.Entry)entrySet().iterator().next()).equals(m.entrySet().iterator().next());
/*     */     }
/*     */     public String toString() {
/* 172 */       return "{" + this.key + "=>" + this.value + "}";
/*     */     }
/*     */ 
/*     */     protected class SingletonEntry
/*     */       implements Int2ShortMap.Entry, Map.Entry<Integer, Short>
/*     */     {
/*     */       protected SingletonEntry()
/*     */       {
/*     */       }
/*     */ 
/*     */       public Integer getKey()
/*     */       {
/* 131 */         return Integer.valueOf(Int2ShortMaps.Singleton.this.key); } 
/* 132 */       public Short getValue() { return Short.valueOf(Int2ShortMaps.Singleton.this.value); }
/*     */ 
/*     */       public int getIntKey() {
/* 135 */         return Int2ShortMaps.Singleton.this.key;
/*     */       }
/*     */ 
/*     */       public short getShortValue() {
/* 139 */         return Int2ShortMaps.Singleton.this.value; } 
/* 140 */       public short setValue(short value) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */       public Short setValue(Short value) {
/* 143 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       public boolean equals(Object o) {
/* 146 */         if (!(o instanceof Map.Entry)) return false;
/* 147 */         Map.Entry e = (Map.Entry)o;
/*     */ 
/* 149 */         return (Int2ShortMaps.Singleton.this.key == ((Integer)e.getKey()).intValue()) && (Int2ShortMaps.Singleton.this.value == ((Short)e.getValue()).shortValue());
/*     */       }
/*     */       public int hashCode() {
/* 152 */         return Int2ShortMaps.Singleton.this.key ^ Int2ShortMaps.Singleton.this.value; } 
/* 153 */       public String toString() { return Int2ShortMaps.Singleton.this.key + "->" + Int2ShortMaps.Singleton.this.value; }
/*     */ 
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class EmptyMap extends Int2ShortFunctions.EmptyFunction
/*     */     implements Int2ShortMap, Serializable, Cloneable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     public boolean containsValue(short v)
/*     */     {
/*  67 */       return false; } 
/*  68 */     public void putAll(Map<? extends Integer, ? extends Short> m) { throw new UnsupportedOperationException(); } 
/*     */     public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
/*  70 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*  72 */     public IntSet keySet() { return IntSets.EMPTY_SET; } 
/*     */     public ShortCollection values() {
/*  74 */       return ShortSets.EMPTY_SET; } 
/*  75 */     public boolean containsValue(Object ov) { return false; } 
/*  76 */     private Object readResolve() { return Int2ShortMaps.EMPTY_MAP; } 
/*  77 */     public Object clone() { return Int2ShortMaps.EMPTY_MAP; } 
/*  78 */     public boolean isEmpty() { return true; } 
/*     */     public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
/*  80 */       return int2ShortEntrySet();
/*     */     }
/*  82 */     public int hashCode() { return 0; }
/*     */ 
/*     */     public boolean equals(Object o) {
/*  85 */       if (!(o instanceof Map)) return false;
/*     */ 
/*  87 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     public String toString() {
/*  90 */       return "{}";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.ints.Int2ShortMaps
 * JD-Core Version:    0.6.2
 */